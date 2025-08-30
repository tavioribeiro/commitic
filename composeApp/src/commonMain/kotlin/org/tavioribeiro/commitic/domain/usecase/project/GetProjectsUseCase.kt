package org.tavioribeiro.commitic.domain.usecase.project

import org.tavioribeiro.commitic.domain.model.project.ProjectDomainModel
import org.tavioribeiro.commitic.domain.model.project.ProjectFailure
import org.tavioribeiro.commitic.domain.repository.ProjectRepository
import org.tavioribeiro.commitic.domain.util.Result


class GetProjectsUseCase(
    private val projectRepository: ProjectRepository
) {
    suspend operator fun invoke(): Result<List<ProjectDomainModel>, ProjectFailure> {
        println("--- CAMADA DE DOMÍNIO (USE CASE) ---")
        println("Executando regra de negócio para buscar projetos...")
        return projectRepository.getProjects()
    }
}