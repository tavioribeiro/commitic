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

data class CommitsTabUiState(
    val isLoading: Boolean = false,
    val commits: List<CommitUiModel> = emptyList(),
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


    private val _commitNameInputWarningState = MutableStateFlow("")
    val commitNameInputWarningState = _commitNameInputWarningState.asStateFlow()

    private val _pathInputWarningState = MutableStateFlow("")
    val pathInputWarningState = _pathInputWarningState.asStateFlow()



    //TROCADO POR TOAST
    //private val _uiEvent = MutableSharedFlow<String>()
    //val uiEvent = _uiEvent.asSharedFlow()
    //_uiEvent.emit("Projeto deletado com sucesso")


    private fun loadProjects() {

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

                    _uiState.update { it.copy(isLoading = false, commits = uiCommits) }
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


            _commitNameInputWarningState.update { "" }
            _pathInputWarningState.update { "" }


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
                            _commitNameInputWarningState.update { "O nome não pode ser vazio." }
                        }

                        is CommitFailure.InvalidPath -> {
                            _pathInputWarningState.update { "O caminho não pode ser vazio." }
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