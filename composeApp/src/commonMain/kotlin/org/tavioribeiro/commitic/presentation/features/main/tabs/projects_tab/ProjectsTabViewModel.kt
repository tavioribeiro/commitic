package org.tavioribeiro.commitic.presentation.features.main.tabs.projects_tab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.tavioribeiro.commitic.domain.model.project.ProjectFailure
import org.tavioribeiro.commitic.domain.usecase.project.DeleteProjectUseCase
import org.tavioribeiro.commitic.domain.usecase.project.GetProjectsUseCase
import org.tavioribeiro.commitic.domain.usecase.project.SaveProjectUseCase
import org.tavioribeiro.commitic.domain.util.RequestResult
import org.tavioribeiro.commitic.presentation.components.toast.ToastViewModel
import org.tavioribeiro.commitic.presentation.components.toast.model.ToastType
import org.tavioribeiro.commitic.presentation.components.toast.model.ToastUiModel
import org.tavioribeiro.commitic.presentation.mapper.toDomain
import org.tavioribeiro.commitic.presentation.mapper.toUiModel
import org.tavioribeiro.commitic.presentation.model.ProjectUiModel

data class ProjectsTabUiState(
    val isLoading: Boolean = false,
    val projects: List<ProjectUiModel> = emptyList(),
    val error: String? = null
)


class ProjectsTabViewModel(
    private val toastViewModel: ToastViewModel,
    private val getProjectsUseCase: GetProjectsUseCase,
    private val saveProjectUseCase: SaveProjectUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase
): ViewModel(){

    private val _uiState = MutableStateFlow(ProjectsTabUiState())
    val uiState: StateFlow<ProjectsTabUiState> = _uiState.asStateFlow()


    private val _projectNameInputWarningState = MutableStateFlow("")
    val projectNameInputWarningState = _projectNameInputWarningState.asStateFlow()

    private val _pathInputWarningState = MutableStateFlow("")
    val pathInputWarningState = _pathInputWarningState.asStateFlow()



    //TROCADO POR TOAST
    //private val _uiEvent = MutableSharedFlow<String>()
    //val uiEvent = _uiEvent.asSharedFlow()
    //_uiEvent.emit("Projeto deletado com sucesso")

    fun loadProjects() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = getProjectsUseCase()

            when (result) {
                is RequestResult.Success -> {
                    val uiProjects = result.data.map {
                        it.toUiModel()
                    }

                    _uiState.update { it.copy(isLoading = false, projects = uiProjects) }
                }

                is RequestResult.Failure -> {
                    _uiState.update { it.copy(isLoading = false) }

                    when (result.failure) {
                        is ProjectFailure.Unexpected -> {
                            toastViewModel.showToast(
                                ToastUiModel(
                                    title = "Erro",
                                    message = "Falha ao buscar projetos",
                                    type = ToastType.ERROR,
                                    duration = 1500
                                )
                            )
                        }
                        else -> {}
                    }
                }
            }
        }
    }


     fun onSaveProjectClicked(projectToSave: ProjectUiModel) {
        viewModelScope.launch {
            if(!_uiState.value.isLoading){
                _uiState.update { it.copy(isLoading = true) }
            }


            val project = projectToSave.toDomain()
            val result = saveProjectUseCase(project)


            _projectNameInputWarningState.update { "" }
            _pathInputWarningState.update { "" }


            when (result) {
                is RequestResult.Success -> {
                    this@ProjectsTabViewModel.loadProjects()

                    toastViewModel.showToast(
                        ToastUiModel(
                            title = "Sucesso",
                            message = "Projeto salvo com sucesso",
                            type = ToastType.SUCCESS,
                            duration = 1500
                        )
                    )
                }

                is RequestResult.Failure -> {
                    when (result.failure) {
                        is ProjectFailure.InvalidName -> {
                            _projectNameInputWarningState.update { "O nome não pode ser vazio." }
                        }

                        is ProjectFailure.InvalidPath -> {
                            _pathInputWarningState.update { "O caminho não pode ser vazio." }
                        }

                        is ProjectFailure.Unexpected -> {
                            toastViewModel.showToast(
                                ToastUiModel(
                                    title = "Erro",
                                    message = "Falha ao salvar projeto",
                                    type = ToastType.ERROR,
                                    duration = 1500
                                )
                            )
                        }
                        else -> {}
                    }
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }


     fun deleteProject(projectToDelete: ProjectUiModel) {
         viewModelScope.launch {
             _uiState.update { it.copy(isLoading = true) }

             val project = projectToDelete.toDomain()
             val result = deleteProjectUseCase(project)


             when (result) {
                 is RequestResult.Success -> {
                     this@ProjectsTabViewModel.loadProjects()

                     toastViewModel.showToast(
                         ToastUiModel(
                             title = "Sucesso",
                             message = "Projeto deletado com sucesso",
                             type = ToastType.SUCCESS,
                             duration = 1500
                         )
                     )
                 }

                 is RequestResult.Failure -> {
                     when (result.failure) {
                         is ProjectFailure.Unexpected -> {
                             toastViewModel.showToast(
                                 ToastUiModel(
                                     title = "Erro",
                                     message = "Falha ao deletar projeto",
                                     type = ToastType.ERROR,
                                     duration = 1500
                                 )
                             )
                         }

                         else -> {}
                     }
                 }
             }
         }
     }
}