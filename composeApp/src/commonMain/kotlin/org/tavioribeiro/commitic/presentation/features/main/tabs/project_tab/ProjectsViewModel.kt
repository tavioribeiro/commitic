package org.tavioribeiro.commitic.presentation.features.main.tabs.project_tab

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.tavioribeiro.commitic.domain.model.project.ProjectFailure
import org.tavioribeiro.commitic.domain.usecase.project.DeleteProjectUseCase
import org.tavioribeiro.commitic.domain.usecase.project.GetProjectsUseCase
import org.tavioribeiro.commitic.domain.usecase.project.SaveProjectUseCase
import org.tavioribeiro.commitic.domain.util.Result
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


    //TROCADO POR TOAST
    //private val _uiEvent = MutableSharedFlow<String>()
    //val uiEvent = _uiEvent.asSharedFlow()
    //_uiEvent.emit("Projeto deletado com sucesso")

    suspend fun loadProjects() {
        _uiState.update { it.copy(isLoading = true) }

        val result = getProjectsUseCase()

        when (result) {
            is Result.Success -> {
                val uiProjects = result.data.map {
                    it.toUiModel()
                }

                _uiState.update { it.copy(isLoading = false, projects = uiProjects) }
            }
            is Result.Failure -> {
                _uiState.update { it.copy(isLoading = false) }

                when (result.failure) {
                    is ProjectFailure.InvalidName -> {
                        //TODO("implementar a mensagem na View)
                    }
                    is ProjectFailure.InvalidPath -> {
                        //TODO("implementar a mensagem na View)
                    }
                    is ProjectFailure.SaveError -> {
                        //TODO("implementar a mensagem na View)
                    }

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
                }
            }
        }
    }


    suspend fun onSaveProjectClicked(projectToSave: ProjectUiModel) {
        _uiState.update { it.copy(isLoading = true) }

        val project = projectToSave.toDomain()
        val result = saveProjectUseCase(project)

        _uiState.update { it.copy(isLoading = false) }

        when (result) {
            is Result.Success -> {
                toastViewModel.showToast(
                    ToastUiModel(
                        title = "Sucesso",
                        message = "Projeto salvo com sucesso",
                        type = ToastType.SUCCESS,
                        duration = 1500
                    )
                )
            }
            is Result.Failure -> {
                when (result.failure) {
                    is ProjectFailure.InvalidName -> {
                        //TODO("implementar a mensagem na View)
                    }
                    is ProjectFailure.InvalidPath -> {
                        //TODO("implementar a mensagem na View)
                    }
                    is ProjectFailure.SaveError -> {
                        //TODO("implementar a mensagem na View)
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
                }
            }
        }
    }


    suspend fun deleteProject(projectToDelete: ProjectUiModel) {
        _uiState.update { it.copy(isLoading = true) }

        val project = projectToDelete.toDomain()
        val result = deleteProjectUseCase(project)

        _uiState.update { it.copy(isLoading = false) }

        when (result) {
            is Result.Success -> {
                toastViewModel.showToast(
                    ToastUiModel(
                        title = "Sucesso",
                        message = "Projeto deletado com sucesso",
                        type = ToastType.SUCCESS,
                        duration = 1500
                    )
                )
            }

            is Result.Failure -> {
                when (result.failure) {
                    is ProjectFailure.InvalidName -> {
                        //TODO("implementar a mensagem na View)
                    }

                    is ProjectFailure.InvalidPath -> {
                        toastViewModel.showToast(
                            ToastUiModel(
                                title = "Erro",
                                message = "O caminho não pode ser vazio",
                                type = ToastType.ERROR,
                                duration = 1500
                            )
                        )
                    }

                    is ProjectFailure.SaveError -> {
                        //TODO("implementar a mensagem na View)
                    }

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
                }
            }
        }
    }
}