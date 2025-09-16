package org.tavioribeiro.commitic.presentation.features.main.tabs.commits_tab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.tavioribeiro.commitic.domain.model.commit.CommitFailure
import org.tavioribeiro.commitic.domain.usecase.commit.DeleteCommitUseCase
import org.tavioribeiro.commitic.domain.usecase.commit.GetCommitsUseCase
import org.tavioribeiro.commitic.domain.usecase.commit.SaveCommitUseCase
import org.tavioribeiro.commitic.domain.util.RequestResult
import org.tavioribeiro.commitic.presentation.components.toast.ToastViewModel
import org.tavioribeiro.commitic.presentation.components.toast.model.ToastType
import org.tavioribeiro.commitic.presentation.components.toast.model.ToastUiModel
import org.tavioribeiro.commitic.presentation.mapper.toDomain
import org.tavioribeiro.commitic.presentation.mapper.toUiModel
import org.tavioribeiro.commitic.presentation.model.CommitUiModel
import org.tavioribeiro.commitic.presentation.model.SelectOptionModel

data class CommitsTabUiState(
    val isLoading: Boolean = false,
    val availableProjects: List<SelectOptionModel> = mutableListOf(
        SelectOptionModel(label = "Calculadora", value = "calculator"),
        SelectOptionModel(label = "Lista de Tarefas", value = "todo_list"),
        SelectOptionModel(label = "App de Previsão do Tempo", value = "weather_app"),
        SelectOptionModel(label = "Gerador de Citações", value = "quote_generator"),
        SelectOptionModel(label = "Blog Pessoal", value = "personal_blog"),
        SelectOptionModel(label = "Aplicativo de Receitas", value = "recipe_app"),
        SelectOptionModel(label = "Clone do Twitter", value = "twitter_clone"),
        SelectOptionModel(label = "App de Chat em Tempo Real", value = "realtime_chat"),
        SelectOptionModel(label = "Plataforma de E-commerce", value = "ecommerce_platform"),
        SelectOptionModel(label = "Controle Financeiro", value = "finance_tracker")
    ),
    val selectedProjectIndex: Int? = 4,
    val availableLlms: List<SelectOptionModel> = emptyList(),
    val selectedLlmIndex: Int? = 4,
    val error: String? = null
)


class CommitsTabViewModel(
    private val toastViewModel: ToastViewModel,
    private val getCommitsUseCase: GetCommitsUseCase,
    private val saveCommitUseCase: SaveCommitUseCase,
    private val deleteCommitUseCase: DeleteCommitUseCase
): ViewModel(){

    private val _uiState = MutableStateFlow(CommitsTabUiState())
    val uiState: StateFlow<CommitsTabUiState> = _uiState.asStateFlow()



    private fun loadProjects() {
        viewModelScope.launch {

        }
    }

    fun loadCommits() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = getCommitsUseCase()

            when (result) {
                is RequestResult.Success -> {
                    val uiCommits = result.data.map {
                        it.toUiModel()
                    }

                    //_uiState.update { it.copy(isLoading = false, commits = uiCommits) }
                }

                is RequestResult.Failure -> {
                    _uiState.update { it.copy(isLoading = false) }

                    when (result.failure) {
                        is CommitFailure.Unexpected -> {
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


     fun onSaveCommitClicked(commitToSave: CommitUiModel) {
        viewModelScope.launch {
            if(!_uiState.value.isLoading){
                _uiState.update { it.copy(isLoading = true) }
            }


            val commit = commitToSave.toDomain()
            val result = saveCommitUseCase(commit)




            when (result) {
                is RequestResult.Success -> {
                    this@CommitsTabViewModel.loadCommits()

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
                        is CommitFailure.InvalidName -> {

                        }

                        is CommitFailure.InvalidPath -> {

                        }

                        is CommitFailure.Unexpected -> {
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


     fun deleteCommit(commitToDelete: CommitUiModel) {
         viewModelScope.launch {
             _uiState.update { it.copy(isLoading = true) }

             val commit = commitToDelete.toDomain()
             val result = deleteCommitUseCase(commit)


             when (result) {
                 is RequestResult.Success -> {
                     this@CommitsTabViewModel.loadCommits()

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
                         is CommitFailure.Unexpected -> {
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