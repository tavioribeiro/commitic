package org.tavioribeiro.commitic.data.repository

import org.tavioribeiro.commitic.data.datasource.local.ProjectLocalDataSource
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
}