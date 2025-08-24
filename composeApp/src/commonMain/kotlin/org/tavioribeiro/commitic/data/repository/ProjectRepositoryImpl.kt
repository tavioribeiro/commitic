package org.tavioribeiro.commitic.data.repository

import org.tavioribeiro.commitic.data.datasource.local.ProjectLocalDataSource
import org.tavioribeiro.commitic.data.mapper.toDomain
import org.tavioribeiro.commitic.data.mapper.toDto
import org.tavioribeiro.commitic.domain.model.ProjectDomainModel
import org.tavioribeiro.commitic.domain.repository.ProjectRepository

class ProjectRepositoryImpl(
    private val localDataSource: ProjectLocalDataSource
) : ProjectRepository {

    override fun saveProject(project: ProjectDomainModel) {
        // Mapeamos o modelo de domínio para o DTO
        val projectDto = project.toDto()
        // Chamamos o DataSource com o modelo correto
        localDataSource.saveProject(projectDto)
    }


    override fun getProjects(): List<ProjectDomainModel> {
        println("--- CAMADA DO REPOSITÓRIO ---")
        println("Buscando DTOs do DataSource e mapeando para DomainModels...")
        val projectDtos = localDataSource.getProjects()
        // Mapeamos cada item da lista para o modelo de domínio
        return projectDtos.map { it.toDomain() }
    }


}