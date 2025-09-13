package org.tavioribeiro.commitic.domain.usecase.project

import org.tavioribeiro.commitic.domain.model.project.ProjectDomainModel
import org.tavioribeiro.commitic.domain.model.project.ProjectFailure
import org.tavioribeiro.commitic.domain.repository.ProjectRepository
import org.tavioribeiro.commitic.domain.usecase.directory.CheckDirectoryExistsUseCase
import org.tavioribeiro.commitic.domain.util.RequestResult

class SaveProjectUseCase(
    private val projectRepository: ProjectRepository,
    private val checkDirectoryExistsUseCase: CheckDirectoryExistsUseCase
) {
    suspend operator fun invoke(project: ProjectDomainModel): RequestResult<Unit, ProjectFailure> {
        if (project.name.isBlank()) {
            return RequestResult.Failure(ProjectFailure.InvalidName("O nome do projeto não pode ser vazio."))
        }

        if (project.path.isBlank()) {
            return RequestResult.Failure(ProjectFailure.InvalidPath("O caminho do projeto não pode ser vazio."))
        }

        return when (val directoryResult = checkDirectoryExistsUseCase(project.path)) {
            is RequestResult.Success -> {
                if (directoryResult.data) {
                    projectRepository.saveProject(project)
                } else {
                    RequestResult.Failure(ProjectFailure.InvalidPath("O caminho do projeto não existe."))
                }
            }
            is RequestResult.Failure -> {
                RequestResult.Failure(ProjectFailure.InvalidPath("Falha ao verificar o caminho do projeto."))
            }
        }
    }
}