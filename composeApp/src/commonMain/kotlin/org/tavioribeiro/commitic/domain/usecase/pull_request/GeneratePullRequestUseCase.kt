package org.tavioribeiro.commitic.domain.usecase.pull_request

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.tavioribeiro.commitic.domain.model.commit.CommitDomainModel
import org.tavioribeiro.commitic.domain.model.commit.CommitFailure
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
    operator fun invoke(project: ProjectDomainModel, llm: LlmDomainModel): Flow<ProgressResult<PullRequestDomainModel, CommitFailure>> = flow {
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


        val commitPrompt = """
            **Tarefa:** Gerar uma descrição de Pull Request (PR) a partir do texto de análise de commit fornecido.

            **INPUT:**
            Você receberá um texto com as seções: `CONTEXTO`, `MUDANÇAS` e `COMMIT GERADO`.
            
            **OUTPUT (Siga este formato estritamente):**
            
            ---
            
            `TIPO #TICKET: Título derivado do COMMIT GERADO`
            
            #### 📜 Descrição
            
            Parágrafo narrativo que elabora o `CONTEXTO.Objetivo`. Explique **o quê** a mudança faz e **por quê** ela é importante. Seja direto e informativo.
            
            #### 📑 Mudanças Principais
            
            Analise a seção `MUDANÇAS` e siga estas regras:
            *   Agrupe as alterações por temas lógicos em **subtítulos em negrito**. Não agrupe por nome de arquivo.
            *   Dentro de cada tema, liste as alterações usando bullet points (`*`).
            *   **Não copie o texto de `MUDANÇAS`**. Reescreva cada ponto para explicar a alteração de forma clara para um revisor.
            
            ---
            
            **Regras Adicionais:**
            *   **TIPO do Título:** Use `FEAT` para `CONTEXTO.Categoria: FEATURE`. Use `FIX` para `CONTEXTO.Categoria: BUGFIX`.
            *   **#TICKET:** Use `#TICKET` como um placeholder.

            **INFORMAÇÕES DO COMMIT:**
            ${
            commitList.joinToString(separator = "\n") { commit ->
                """
                    █████████████████████████████████████████████
                        Nome da Branch: ${commit.branchName}
                        Objetivo da Tarefa: ${commit.taskObjective}
                        Categoria: ${commit.category}
                        Resumo: ${commit.summary}
                        Mensagem do Commit: ${commit.commitMessage}
                        --------------------------------------------------
                    """.trimIndent()
            }
        }
        """.trimIndent()

        val pullRequestText = when (val result = llmRepository.textToLlm(commitPrompt, llm)) {
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