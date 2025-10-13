package org.tavioribeiro.commitic.domain.usecase.project

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


        when (val checkDirectoryResult = fileSystemRepository.checkDirectoryExists(project.path)) {
            is RequestResult.Failure -> {
                return RequestResult.Failure(ProjectFailure.InvalidPath("Falha ao verificar o caminho do projeto."))
            }
            is RequestResult.Success -> {
                if (!checkDirectoryResult.data) {
                    return RequestResult.Failure(ProjectFailure.NotFoundPath("O caminho do projeto não existe."))
                }
            }
        }


        when (val gitInfoResult = consoleRepository.executeCommand(command = "test -d .git", path = project.path)) {
            is RequestResult.Failure -> {
                return RequestResult.Failure(ProjectFailure.InvalidPath("Falha ao executar o comando git. Verifique se o caminho do projeto está correto e se é um repositório git."))
            }
            is RequestResult.Success -> {
                if (gitInfoResult.data == "1") {
                    return RequestResult.Failure(ProjectFailure.InvalidPath("O caminho do projeto não é um repositório git válido."))
                }
            }
        }

        return projectRepository.saveProject(project)
    }
}