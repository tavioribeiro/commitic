package org.tavioribeiro.commitic.domain.usecase.commit

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.tavioribeiro.commitic.domain.model.agents.LlmAgents
import org.tavioribeiro.commitic.domain.model.commit.CommitDomainModel
import org.tavioribeiro.commitic.domain.model.commit.CommitFailure
import org.tavioribeiro.commitic.domain.model.llm.LlmDomainModel
import org.tavioribeiro.commitic.domain.model.llm.ProgressResult
import org.tavioribeiro.commitic.domain.model.project.ProjectDomainModel
import org.tavioribeiro.commitic.domain.repository.ConsoleRepository
import org.tavioribeiro.commitic.domain.repository.LlmRepository
import org.tavioribeiro.commitic.domain.util.RequestResult

class GenerateCommitUseCase(
    private val llmRepository: LlmRepository,
    private val consoleRepository: ConsoleRepository
) {
    operator fun invoke(project: ProjectDomainModel, llm: LlmDomainModel): Flow<ProgressResult<CommitDomainModel, CommitFailure>> = flow {
        emit(ProgressResult.Loading)

        if (project.path.isBlank()) {
            emit(ProgressResult.Failure(CommitFailure.InvalidPath("O caminho do projeto não pode ser vazio.")))
            return@flow
        }
        if (llm.apiToken.isBlank() || llm.model.isBlank() || llm.company.isBlank()) {
            emit(ProgressResult.Failure(CommitFailure.InvalidName("Dados da API LLM (token, modelo, empresa) não podem ser vazios.")))
            return@flow
        }

        val gitInfoResult = consoleRepository.executeCommand(command = "git --no-pager diff -w --unified=0 HEAD", path = project.path)
        val currentDetailsChanges = when (gitInfoResult) {
            is RequestResult.Success -> gitInfoResult.data
            is RequestResult.Failure -> {
                emit(ProgressResult.Failure(CommitFailure.InvalidPath("Falha ao executar o comando git. Verifique se o caminho do projeto está correto e se é um repositório git.")))
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




        emit(ProgressResult.Progress(2))
        val categoryPrompt = LlmAgents.STEP_TWO.instructions + "\n\n" + currentDetailsChanges
        val category = when (val result = llmRepository.textToLlm(categoryPrompt, llm)) {
            is RequestResult.Success -> result.data.trim()
            is RequestResult.Failure -> {
                emit(ProgressResult.Failure(result.failure))
                return@flow
            }
        }




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
                branchName = "main",
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