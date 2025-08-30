package org.tavioribeiro.commitic.domain.repository


import org.tavioribeiro.commitic.domain.model.project.ProjectDomainModel
import org.tavioribeiro.commitic.domain.model.project.ProjectFailure
import org.tavioribeiro.commitic.domain.util.Result

interface ProjectRepository {
    suspend fun saveProject(project: ProjectDomainModel): Result<Unit, ProjectFailure>
    suspend fun getProjects(): Result<List<ProjectDomainModel>, ProjectFailure>
    suspend fun deleteProject(project: ProjectDomainModel): Result<Unit, ProjectFailure>
}