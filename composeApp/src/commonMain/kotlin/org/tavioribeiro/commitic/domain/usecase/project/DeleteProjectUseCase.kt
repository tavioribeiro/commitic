package org.tavioribeiro.commitic.domain.usecase.project

import org.tavioribeiro.commitic.domain.model.project.ProjectDomainModel
import org.tavioribeiro.commitic.domain.model.project.ProjectFailure
import org.tavioribeiro.commitic.domain.repository.ProjectRepository
import org.tavioribeiro.commitic.domain.util.RequestResult

class DeleteProjectUseCase(
    private val projectRepository: ProjectRepository
) {
    suspend operator fun invoke(project: ProjectDomainModel): RequestResult<Unit, ProjectFailure> {
        if (project.name.isBlank()) {
            return RequestResult.Failure(ProjectFailure.InvalidName("O nome não pode ser vazio."))
        }

        if (project.path.isBlank()) {
            return RequestResult.Failure(ProjectFailure.InvalidPath("O caminho não pode ser vazio."))
        }

        return projectRepository.deleteProject(project)
    }
}