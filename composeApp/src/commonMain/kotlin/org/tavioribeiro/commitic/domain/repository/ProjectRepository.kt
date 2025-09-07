package org.tavioribeiro.commitic.domain.repository


import org.tavioribeiro.commitic.domain.model.project.ProjectDomainModel
import org.tavioribeiro.commitic.domain.model.project.ProjectFailure
import org.tavioribeiro.commitic.domain.util.RequestResult

interface ProjectRepository {
    suspend fun saveProject(project: ProjectDomainModel): RequestResult<Unit, ProjectFailure>
    suspend fun getProjects(): RequestResult<List<ProjectDomainModel>, ProjectFailure>
    suspend fun deleteProject(project: ProjectDomainModel): RequestResult<Unit, ProjectFailure>
}