package org.tavioribeiro.commitic.presentation.features.main.tabs.project_tab

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.tavioribeiro.commitic.domain.model.ProjectDomainModel
import org.tavioribeiro.commitic.domain.usecase.DeleteProjectUseCase
import org.tavioribeiro.commitic.domain.usecase.GetProjectsUseCase
import org.tavioribeiro.commitic.domain.usecase.SaveProjectUseCase
import org.tavioribeiro.commitic.domain.util.Outcome
import org.tavioribeiro.commitic.presentation.components.toast.ToastViewModel
import org.tavioribeiro.commitic.presentation.components.toast.model.ToastType
import org.tavioribeiro.commitic.presentation.components.toast.model.ToastUiModel
import org.tavioribeiro.commitic.presentation.mapper.toDomain
import org.tavioribeiro.commitic.presentation.mapper.toUiModel
import org.tavioribeiro.commitic.presentation.model.ProjectUiModel

data class ProjectsUiState(
    val isLoading: Boolean = false,
    val projects: List<ProjectUiModel> = emptyList(),
    val error: String? = null
)


class ProjectsViewModel(
    private val toastViewModel: ToastViewModel,
    private val getProjectsUseCase: GetProjectsUseCase,
    private val saveProjectUseCase: SaveProjectUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase
) {
    private val _uiState = MutableStateFlow(ProjectsUiState())
    val uiState: StateFlow<ProjectsUiState> = _uiState.asStateFlow()


    private val _uiEvent = MutableSharedFlow<String>()
    val uiEvent = _uiEvent.asSharedFlow()


    suspend fun loadProjects() {
        _uiState.update { it.copy(isLoading = true) }

        val domainProjects = getProjectsUseCase()
        val uiProjects = domainProjects.map {
            it.toUiModel()
        }


        coroutineScope {
            launch(Dispatchers.Main) {
                _uiState.update { it.copy(projects = uiProjects) }
                delay(500)
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }


    suspend fun onSaveProjectClicked(projectToSave: ProjectUiModel) {
        val project = projectToSave.toDomain()
        saveProjectUseCase(project)
    }


    suspend fun deleteProject(projectToDelete: ProjectUiModel) {
        val project = projectToDelete.toDomain()
        val outcome = deleteProjectUseCase(project)


        toastViewModel.showToast(
            ToastUiModel(
                title = "Ação Concluída",
                message = "A operação foi realizada com sucesso.",
                type = ToastType.INFO
            )
        )

        when (outcome) {
            is Outcome.Success -> {
                // Sucesso!
                _uiEvent.emit("Projeto salvo com sucesso!")
                // Opcional: Você pode querer recarregar a lista de projetos aqui.
                // fetchProjects()

            }
            is Outcome.Error -> {
                // Erro!
                val errorMessage = outcome.exception.message ?: "Ocorreu um erro desconhecido."
                _uiEvent.emit("Falha ao salvar: $errorMessage")
            }
        }
    }
}