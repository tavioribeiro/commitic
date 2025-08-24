package org.tavioribeiro.commitic.domain.usecase

import org.tavioribeiro.commitic.domain.model.ProjectDomainModel
import org.tavioribeiro.commitic.domain.repository.ProjectRepository

class SaveProjectUseCase(
    private val projectRepository: ProjectRepository
) {
    suspend operator fun invoke(project: ProjectDomainModel) {
        // Aqui poderiam entrar regras de negócio, como validações.
        // Ex: if (project.name.isBlank()) throw InvalidProjectNameException()
        projectRepository.saveProject(project)
    }
}