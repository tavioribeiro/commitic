package org.tavioribeiro.commitic.domain.usecase

import org.tavioribeiro.commitic.domain.model.ProjectDomainModel
import org.tavioribeiro.commitic.domain.repository.ProjectRepository

class GetProjectsUseCase(
    private val projectRepository: ProjectRepository
) {
    // Usar 'operator fun invoke' permite chamar a classe como se fosse uma função
    // Ex: getProjectsUseCase() em vez de getProjectsUseCase.execute()
    suspend operator fun invoke(): List<ProjectDomainModel> {
        println("--- CAMADA DE DOMÍNIO (USE CASE) ---")
        println("Executando regra de negócio para buscar projetos...")
        return projectRepository.getProjects()
    }
}