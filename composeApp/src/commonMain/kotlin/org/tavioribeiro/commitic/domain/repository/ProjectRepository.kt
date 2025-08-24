package org.tavioribeiro.commitic.domain.repository


import org.tavioribeiro.commitic.domain.model.ProjectDomainModel

interface ProjectRepository {
    fun saveProject(project: ProjectDomainModel)
    fun getProjects(): List<ProjectDomainModel>
}