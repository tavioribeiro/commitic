package org.tavioribeiro.commitic.presentation.features.main.tabs.project_tab

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.tavioribeiro.commitic.domain.model.ProjectDomainModel
import org.tavioribeiro.commitic.domain.usecase.GetProjectsUseCase
import org.tavioribeiro.commitic.domain.usecase.SaveProjectUseCase
import org.tavioribeiro.commitic.presentation.mapper.toUiModel
import org.tavioribeiro.commitic.presentation.model.ProjectUiModel

data class ProjectsUiState(
    val isLoading: Boolean = false,
    val projects: List<ProjectUiModel> = emptyList(),
    val error: String? = null
)

class ProjectsViewModel(
    private val getProjectsUseCase: GetProjectsUseCase,
    private val saveProjectUseCase: SaveProjectUseCase
) {

    // 3. Crie o StateFlow para expor o estado para a UI
    private val _uiState = MutableStateFlow(ProjectsUiState())
    val uiState: StateFlow<ProjectsUiState> = _uiState.asStateFlow()

    fun loadProjects() {
        // Use o viewModelScope para lançar coroutines que são canceladas
        // automaticamente quando o ViewModel é destruído.
        // No momento, como suas chamadas são síncronas, não é estritamente necessário,
        // mas é uma boa prática para quando for assíncrono.

        println("--- CAMADA VIEWMODEL ---")
        println("Iniciando o carregamento dos projetos...")

        // Atualiza o estado para "carregando"
        _uiState.update { it.copy(isLoading = true) }

        val domainProjects = getProjectsUseCase()
        val uiProjects = domainProjects.map { it.toUiModel() }

        // Atualiza o estado com a lista de projetos e finaliza o carregamento
        _uiState.update { it.copy(isLoading = false, projects = uiProjects) }
    }


    suspend fun onSaveProjectClicked(projectToSave: ProjectDomainModel) {
        // Chama o UseCase com o modelo de domínio
        saveProjectUseCase(projectToSave)
    }
}