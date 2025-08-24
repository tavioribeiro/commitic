package org.tavioribeiro.commitic.presentation.features.main.tabs.project_tab

import org.tavioribeiro.commitic.domain.model.ProjectDomainModel
import org.tavioribeiro.commitic.domain.usecase.SaveProjectUseCase

class ProjectsViewModel(
    private val saveProjectUseCase: SaveProjectUseCase
) {
    suspend fun onSaveProjectClicked(projectToSave: ProjectDomainModel) {
        // Chama o UseCase com o modelo de domínio
        saveProjectUseCase(projectToSave)
    }
}