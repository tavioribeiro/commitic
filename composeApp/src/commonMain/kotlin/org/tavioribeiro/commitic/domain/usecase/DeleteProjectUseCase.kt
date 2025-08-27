package org.tavioribeiro.commitic.domain.usecase

import org.tavioribeiro.commitic.domain.model.ProjectDomainModel
import org.tavioribeiro.commitic.domain.repository.ProjectRepository

class DeleteProjectUseCase(
    private val projectRepository: ProjectRepository
) {
    suspend operator fun invoke(project: ProjectDomainModel){
        projectRepository.deleteProject(project)
    }
}