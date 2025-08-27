package org.tavioribeiro.commitic.domain.repository


import org.tavioribeiro.commitic.domain.model.ProjectDomainModel

interface ProjectRepository {
    fun saveProject(project: ProjectDomainModel)
    suspend fun getProjects(): List<ProjectDomainModel>
    fun deleteProject(project: ProjectDomainModel)
}