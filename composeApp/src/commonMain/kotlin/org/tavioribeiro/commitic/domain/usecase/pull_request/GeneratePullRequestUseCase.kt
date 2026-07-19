package org.tavioribeiro.commitic.domain.usecase.pull_request

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.tavioribeiro.commitic.domain.model.commit.CommitDomainModel
import org.tavioribeiro.commitic.domain.model.commit.CommitFailure
import org.tavioribeiro.commitic.domain.model.commit.CommitLanguage
import org.tavioribeiro.commitic.domain.model.llm.LlmDomainModel
import org.tavioribeiro.commitic.domain.model.llm.ProgressResult
import org.tavioribeiro.commitic.domain.model.project.ProjectDomainModel
import org.tavioribeiro.commitic.domain.model.pull_request.PullRequestDomainModel
import org.tavioribeiro.commitic.domain.repository.CommitRepository
import org.tavioribeiro.commitic.domain.repository.ConsoleRepository
import org.tavioribeiro.commitic.domain.repository.FileSystemRepository
import org.tavioribeiro.commitic.domain.repository.LlmRepository
import org.tavioribeiro.commitic.domain.util.RequestResult
import org.tavioribeiro.commitic.presentation.mapper.toUiModel

class GeneratePullRequestUseCase(
    private val llmRepository: LlmRepository,
    private val commitRepository: CommitRepository,
    private val consoleRepository: ConsoleRepository,
    private val fileSystemRepository: FileSystemRepository
) {
    operator fun invoke(project: ProjectDomainModel, llm: LlmDomainModel, language: CommitLanguage = CommitLanguage.PORTUGUES): Flow<ProgressResult<PullRequestDomainModel, CommitFailure>> = flow {
        var currentBranch = ""
        val commitList: List<CommitDomainModel>


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


        when (val gitBranchResult = consoleRepository.executeCommand(command = "git branch --show-current", path = project.path)) {
            is RequestResult.Failure -> {
                emit(ProgressResult.Failure(CommitFailure.InvalidPath("Falha ao executar o comando git. Verifique se o git está instalado.")))
                return@flow
            }
            is RequestResult.Success -> {
                currentBranch = gitBranchResult.data.trim()
            }
        }


        if(currentBranch.isBlank()){
            emit(ProgressResult.Failure(CommitFailure.InvalidPath("Falha ao executar o comando git. Verifique se o git está instalado.")))
        }
        if(project.id == null){
            emit(ProgressResult.Failure(CommitFailure.InvalidPath("Falha ao executar o comando git. Verifique se o git está instalado.")))
        }

        when (val commitListResult = commitRepository.getCommitsByProjectIdAndBranch(project.id!!, currentBranch)) {
            is RequestResult.Failure -> {
                emit(ProgressResult.Failure(CommitFailure.InvalidPath("Falha ao executar o comando git. Verifique se o git está instalado.")))
                return@flow
            }
            is RequestResult.Success -> {
                 commitList = commitListResult.data.map {
                    it
                }
            }
        }


        val rawPrompt = """
            **Task:** Generate a Pull Request (PR) description from the provided commit analysis text.

            **INPUT:**
            You will receive a text with the following sections: `CONTEXT`, `CHANGES`, and `GENERATED COMMIT`.
            
            **OUTPUT (Follow this format strictly):**
            
            ---
            
            `TYPE #TICKET: Title derived from GENERATED COMMIT`
            
            #### 📜 Description
            
            Narrative paragraph that elaborates on `CONTEXT.Objective`. Explain **what** the change does and **why** it matters. Be direct and informative.
            
            #### 📑 Main Changes
            
            Analyze the `CHANGES` section and follow these rules:
            *   Group changes by logical themes into **bold subtitles**. Do not group by file name.
            *   Within each theme, list the changes using bullet points (`*`).
            *   **Do not copy the `CHANGES` text**. Rewrite each point to explain the change clearly for a reviewer.
            
            ---
            
            **Additional Rules:**
            *   **Title TYPE:** Use `FEAT` for `CONTEXT.Category: FEATURE`. Use `FIX` for `CONTEXT.Category: BUGFIX`.
            *   **#TICKET:** Use `#TICKET` as a placeholder.
            *   **Language:** Generate the ENTIRE output in ${language.displayName} — title, section headers, description, and main changes. Every word must be in ${language.displayName}.

            **COMMIT INFORMATION:**
            ${
            commitList.joinToString(separator = "\n") { commit ->
                """
                    █████████████████████████████████████████████
                        Branch Name: ${commit.branchName}
                        Task Objective: ${commit.taskObjective}
                        Category: ${commit.category}
                        Summary: ${commit.summary}
                        Commit Message: ${commit.commitMessage}
                        --------------------------------------------------
                    """.trimIndent()
            }
        }
        """.trimIndent()

        val pullRequestText = when (val result = llmRepository.textToLlm(rawPrompt, llm)) {
            is RequestResult.Success -> result.data.trim()
            is RequestResult.Failure -> {
                emit(ProgressResult.Failure(result.failure))
                return@flow
            }
        }

        if(project.id != null){
            val pullRequestDomainModel = PullRequestDomainModel(
                id = project.id,
                text = pullRequestText
            )

            emit(ProgressResult.Success(pullRequestDomainModel))
        }

        emit(ProgressResult.Progress(5))
    }
}