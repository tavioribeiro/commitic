package org.tavioribeiro.commitic.domain.usecase

import org.tavioribeiro.commitic.domain.model.ProjectDomainModel
import org.tavioribeiro.commitic.domain.repository.ProjectRepository
import org.tavioribeiro.commitic.domain.util.Outcome

class DeleteProjectUseCase(
    private val projectRepository: ProjectRepository
) {
    suspend operator fun invoke(project: ProjectDomainModel): Outcome<Unit> {
        if (project.name.isBlank()) {
            return Outcome.Error(IllegalArgumentException("O nome do projeto não pode ser vazio."))
        }

        if (project.path.isBlank()) {
            return Outcome.Error(IllegalArgumentException("O caminho do projeto não pode ser vazio."))
        }

        return projectRepository.deleteProject(project)
    }
}