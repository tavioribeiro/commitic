package org.tavioribeiro.commitic.domain.usecase.project

import org.tavioribeiro.commitic.domain.model.project.ProjectDomainModel
import org.tavioribeiro.commitic.domain.model.project.ProjectFailure
import org.tavioribeiro.commitic.domain.repository.ProjectRepository
import org.tavioribeiro.commitic.domain.util.RequestResult

class DeleteProjectUseCase(
    private val projectRepository: ProjectRepository
) {
    suspend operator fun invoke(project: ProjectDomainModel): RequestResult<Unit, ProjectFailure> {
        val projectId = project.id ?: return RequestResult.Failure(
            ProjectFailure.NotFound("O projeto não possui um ID e não pode ser deletado.")
        )

        if (projectId <= 0) {
            return RequestResult.Failure(ProjectFailure.InvalidId("ID do projeto é inválido."))
        }

        return projectRepository.deleteProject(project)
    }
}