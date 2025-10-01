package org.tavioribeiro.commitic.domain.usecase.project

import org.tavioribeiro.commitic.domain.model.commit.CommitFailure
import org.tavioribeiro.commitic.domain.model.llm.ProgressResult
import org.tavioribeiro.commitic.domain.model.project.ProjectDomainModel
import org.tavioribeiro.commitic.domain.model.project.ProjectFailure
import org.tavioribeiro.commitic.domain.repository.ConsoleRepository
import org.tavioribeiro.commitic.domain.repository.FileSystemRepository
import org.tavioribeiro.commitic.domain.repository.ProjectRepository
import org.tavioribeiro.commitic.domain.util.RequestResult

class SaveProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val fileSystemRepository: FileSystemRepository,
    private val consoleRepository: ConsoleRepository
) {
    suspend operator fun invoke(project: ProjectDomainModel): RequestResult<Unit, ProjectFailure> {
        if (project.name.isBlank()) {
            return RequestResult.Failure(ProjectFailure.InvalidName("O nome do projeto não pode ser vazio."))
        }

        if (project.path.isBlank()) {
            return RequestResult.Failure(ProjectFailure.InvalidPath("O caminho do projeto não pode ser vazio."))
        }

        val checkDirectoryExists = fileSystemRepository.checkDirectoryExists(project.path)

        return when (checkDirectoryExists) {
            is RequestResult.Success -> {
                if(checkDirectoryExists.data) {
                    val gitInfoResult = consoleRepository.executeCommand(command = "test -d .git", path = project.path)

                    when (gitInfoResult) {
                        is RequestResult.Success -> {

                            if (gitInfoResult.data == "1") {
                                return RequestResult.Failure(ProjectFailure.InvalidPath("O caminho do projeto não é um repositório git válido."))
                            }
                            else{
                                projectRepository.saveProject(project)
                            }
                        }
                        is RequestResult.Failure -> {
                            return RequestResult.Failure(ProjectFailure.InvalidPath("Falha ao executar o comando git. Verifique se o caminho do projeto está correto e se é um repositório git."))
                        }
                    }
                } else {
                    RequestResult.Failure(ProjectFailure.NotFoundPath("O caminho do projeto não existe."))
                }
            }
            is RequestResult.Failure -> {
                RequestResult.Failure(ProjectFailure.InvalidPath("Falha ao verificar o caminho do projeto."))
            }
        }
    }
}