package org.tavioribeiro.commitic.domain.usecase.commit

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.tavioribeiro.commitic.domain.model.agent.LlmAgents
import org.tavioribeiro.commitic.domain.model.commit.CommitDomainModel
import org.tavioribeiro.commitic.domain.model.commit.CommitFailure
import org.tavioribeiro.commitic.domain.model.llm.LlmDomainModel
import org.tavioribeiro.commitic.domain.model.llm.ProgressResult
import org.tavioribeiro.commitic.domain.model.project.ProjectDomainModel
import org.tavioribeiro.commitic.domain.repository.ConsoleRepository
import org.tavioribeiro.commitic.domain.repository.FileSystemRepository
import org.tavioribeiro.commitic.domain.repository.LlmRepository
import org.tavioribeiro.commitic.domain.util.RequestResult

class GenerateCommitUseCase(
    private val llmRepository: LlmRepository,
    private val consoleRepository: ConsoleRepository,
    private val fileSystemRepository: FileSystemRepository
) {
    operator fun invoke(project: ProjectDomainModel, llm: LlmDomainModel, delayBetweenSteps: Int): Flow<ProgressResult<CommitDomainModel, CommitFailure>> = flow {
        var currentBranch = ""

        emit(ProgressResult.Loading)

        if (project.path.isBlank()) {
            emit(ProgressResult.Failure(CommitFailure.InvalidPath("O caminho do projeto não pode ser vazio.")))
            return@flow
        }


        if (llm.apiToken.isBlank() || llm.model.isBlank() || llm.company.isBlank()) {
            emit(ProgressResult.Failure(CommitFailure.InvalidName("Dados da API LLM (token, modelo, empresa) não podem ser vazios.")))
            return@flow
        }


        when (val checkDirectoryResult = fileSystemRepository.checkDirectoryExists(project.path)) {
            is RequestResult.Failure -> {
                emit(ProgressResult.Failure(CommitFailure.InvalidPath("Falha ao verificar o caminho do projeto.")))
                return@flow
            }
            is RequestResult.Success -> {
                if (!checkDirectoryResult.data) {
                    emit(ProgressResult.Failure(CommitFailure.InvalidPath("O caminho do projeto especificado não existe.")))
                    return@flow
                }
            }
        }


        when (val gitCheckResult = consoleRepository.executeCommand(command = "test -d .git", path = project.path)) {
            is RequestResult.Failure -> {
                emit(ProgressResult.Failure(CommitFailure.InvalidPath("Falha ao executar o comando git. Verifique se o git está instalado.")))
                return@flow
            }
            is RequestResult.Success -> {
                if (gitCheckResult.data == "1") {
                    emit(ProgressResult.Failure(CommitFailure.InvalidPath("O caminho do projeto não é um repositório git válido.")))
                    return@flow
                }
            }
        }

        when (val gitCheckResult = consoleRepository.executeCommand(command = "test -d .git", path = project.path)) {
            is RequestResult.Failure -> {
                emit(ProgressResult.Failure(CommitFailure.InvalidPath("Falha ao executar o comando git. Verifique se o git está instalado.")))
                return@flow
            }
            is RequestResult.Success -> {
                if (gitCheckResult.data == "1") {
                    emit(ProgressResult.Failure(CommitFailure.InvalidPath("O caminho do projeto não é um repositório git válido.")))
                    return@flow
                }
            }
        }


        when (val gitBranchResult = consoleRepository.executeCommand(command = "git branch --show-current", path = project.path)) {
            is RequestResult.Failure -> {
                emit(ProgressResult.Failure(CommitFailure.InvalidPath("Falha ao executar o comando git. Verifique se o git está instalado.")))
                return@flow
            }
            is RequestResult.Success -> {
                currentBranch = gitBranchResult.data.trim()
            }
        }


        val currentDetailsChanges = when (
            val gitInfoResult = consoleRepository.executeCommand(command = "git --no-pager diff -w --unified=0 HEAD", path = project.path)){
            is RequestResult.Success -> gitInfoResult.data
            is RequestResult.Failure -> {
                emit(ProgressResult.Failure(CommitFailure.InvalidPath("Falha ao obter as mudanças (git diff).")))
                return@flow
            }
        }

        if (currentDetailsChanges.isBlank()) {
            emit(ProgressResult.Failure(CommitFailure.InvalidName("Não há mudanças para commitar.")))
            return@flow
        }



        emit(ProgressResult.Progress(1))
        val taskObjectivePrompt = LlmAgents.STEP_ONE.instructions + "\n\n" + currentDetailsChanges
        val taskObjective = when (val result = llmRepository.textToLlm(taskObjectivePrompt, llm)) {
            is RequestResult.Success -> result.data.trim()
            is RequestResult.Failure -> {
                emit(ProgressResult.Failure(result.failure))
                return@flow
            }
        }




        if (delayBetweenSteps > 0) delay(delayBetweenSteps.toLong() * 1000)




        emit(ProgressResult.Progress(2))
        val categoryPrompt = LlmAgents.STEP_TWO.instructions + "\n\n" + currentDetailsChanges
        val category = when (val result = llmRepository.textToLlm(categoryPrompt, llm)) {
            is RequestResult.Success -> result.data.trim()
            is RequestResult.Failure -> {
                emit(ProgressResult.Failure(result.failure))
                return@flow
            }
        }


        if (delayBetweenSteps > 0) delay(delayBetweenSteps.toLong() * 1000)


        emit(ProgressResult.Progress(3))
        val summaryPrompt = """
            OBJETIVO DA TAREFA: $taskObjective
            CATEGORIA DA MUDANÇA: $category

            MUDANÇAS ATUAIS (DIFF):
            $currentDetailsChanges
        """.trimIndent() + "\n\n" + LlmAgents.STEP_THREE.instructions
        val summary = when (val result = llmRepository.textToLlm(summaryPrompt, llm)) {
            is RequestResult.Success -> result.data.trim()
            is RequestResult.Failure -> {
                emit(ProgressResult.Failure(result.failure))
                return@flow
            }
        }


        if (delayBetweenSteps > 0) delay(delayBetweenSteps.toLong() * 1000)


        emit(ProgressResult.Progress(4))
        val commitPrompt = """
            OBJETIVO DA TAREFA: $taskObjective
            CATEGORIA DA MUDANÇA: $category

            RESUMO DAS MUDANÇAS:
            $summary
        """.trimIndent() + "\n\n" + LlmAgents.STEP_FOUR.instructions
        val commitMessage = when (val result = llmRepository.textToLlm(commitPrompt, llm)) {
            is RequestResult.Success -> result.data.trim()
            is RequestResult.Failure -> {
                emit(ProgressResult.Failure(result.failure))
                return@flow
            }
        }

        if(project.id != null){
            val commitDomainModel = CommitDomainModel(
                projectId = project.id ,
                branchName = currentBranch,
                taskObjective = taskObjective,
                category = category,
                summary = summary,
                commitMessage = commitMessage
            )

            emit(ProgressResult.Success(commitDomainModel))
        }

        emit(ProgressResult.Progress(5))
    }
}