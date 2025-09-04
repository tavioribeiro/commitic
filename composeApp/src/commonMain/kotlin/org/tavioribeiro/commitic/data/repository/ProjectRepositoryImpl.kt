package org.tavioribeiro.commitic.data.repository

import org.tavioribeiro.commitic.data.datasource.local.ProjectLocalDataSource
import org.tavioribeiro.commitic.data.mapper.toDomain
import org.tavioribeiro.commitic.data.mapper.toDto
import org.tavioribeiro.commitic.domain.model.project.ProjectDomainModel
import org.tavioribeiro.commitic.domain.model.project.ProjectFailure
import org.tavioribeiro.commitic.domain.repository.ProjectRepository
import org.tavioribeiro.commitic.domain.util.Result

class ProjectRepositoryImpl(
    private val localDataSource: ProjectLocalDataSource
) : ProjectRepository {

    override suspend fun saveProject(project: ProjectDomainModel): Result<Unit, ProjectFailure> {
        return try {
            val projectDto = project.toDto()

            localDataSource.saveProject(projectDto)

            Result.Success(Unit)
        } catch (e: Exception) {
            println("Erro ao buscar projetos: ${e.message}")
            Result.Failure(ProjectFailure.Unexpected(e))
        }
    }


    override suspend fun getProjects(): Result<List<ProjectDomainModel>, ProjectFailure> {
        return try {
            val projectsDto = localDataSource.getProjects()
            val projectsDomain = projectsDto.map { it.toDomain() }

            Result.Success(projectsDomain)
        } catch (e: Exception) {
            println("Erro ao buscar projetos: ${e.message}")
            Result.Failure(ProjectFailure.Unexpected(e))
        }
    }


    override suspend fun deleteProject(project: ProjectDomainModel): Result<Unit, ProjectFailure> {
        return try {
            val projectDto = project.toDto()

            localDataSource.deleteProject(projectDto)

            Result.Success(Unit)
        } catch (e: Exception) {
            println("Erro ao buscar projetos: ${e.message}")
            Result.Failure(ProjectFailure.Unexpected(e))
        }
    }
}