package org.tavioribeiro.commitic.domain.repository

import kotlinx.coroutines.flow.Flow
import org.tavioribeiro.commitic.domain.model.ProjectDomainModel

interface ProjectRepository {
    fun getProjects(): Flow<List<ProjectDomainModel>>
    suspend fun saveProject(project: ProjectDomainModel)
}