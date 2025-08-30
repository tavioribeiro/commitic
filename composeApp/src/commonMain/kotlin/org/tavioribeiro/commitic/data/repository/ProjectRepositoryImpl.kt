package org.tavioribeiro.commitic.data.repository

import org.tavioribeiro.commitic.data.datasource.local.ProjectLocalDataSource
import org.tavioribeiro.commitic.data.mapper.toDomain
import org.tavioribeiro.commitic.data.mapper.toDto
import org.tavioribeiro.commitic.domain.model.ProjectDomainModel
import org.tavioribeiro.commitic.domain.repository.ProjectRepository
import org.tavioribeiro.commitic.domain.util.Outcome

class ProjectRepositoryImpl(
    private val localDataSource: ProjectLocalDataSource
) : ProjectRepository {

    override fun saveProject(project: ProjectDomainModel) {
        val projectDto = project.toDto()
        localDataSource.saveProject(projectDto)
    }


    override suspend fun getProjects(): List<ProjectDomainModel> {
        val projectDtos = localDataSource.getProjects()
        return projectDtos.map { it.toDomain() }
    }


    override fun deleteProject(project: ProjectDomainModel): Outcome<Unit> {
        return try {
            val projectDto = project.toDto()

            localDataSource.deleteProject(projectDto)

            Outcome.Success(Unit)
        } catch (e: Exception) {
            Outcome.Error(e)
        }
    }
}