package org.tavioribeiro.commitic.domain.usecase.project

import org.tavioribeiro.commitic.domain.model.project.ProjectDomainModel
import org.tavioribeiro.commitic.domain.model.project.ProjectFailure
import org.tavioribeiro.commitic.domain.repository.FileSystemRepository
import org.tavioribeiro.commitic.domain.repository.ProjectRepository
import org.tavioribeiro.commitic.domain.util.RequestResult

class SaveProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val fileSystemRepository: FileSystemRepository
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
                if (checkDirectoryExists.data) {
                    projectRepository.saveProject(project)
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