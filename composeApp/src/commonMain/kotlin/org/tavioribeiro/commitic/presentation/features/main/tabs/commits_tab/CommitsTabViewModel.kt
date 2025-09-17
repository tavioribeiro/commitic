package org.tavioribeiro.commitic.presentation.features.main.tabs.commits_tab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import commitic.composeapp.generated.resources.Res
import commitic.composeapp.generated.resources.icon_llm
import commitic.composeapp.generated.resources.image_chatgpt_dark
import commitic.composeapp.generated.resources.image_claude_dark
import commitic.composeapp.generated.resources.image_deepseek_dark
import commitic.composeapp.generated.resources.image_gemini_dark
import commitic.composeapp.generated.resources.image_groq_dark
import commitic.composeapp.generated.resources.image_hugginface_dark
import commitic.composeapp.generated.resources.image_openrouter_dark
import commitic.composeapp.generated.resources.image_qwen_dark
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.tavioribeiro.commitic.domain.model.commit.CommitFailure
import org.tavioribeiro.commitic.domain.model.llm.LlmAvailableApis
import org.tavioribeiro.commitic.domain.model.llm.LlmFailure
import org.tavioribeiro.commitic.domain.usecase.commit.SaveCommitUseCase
import org.tavioribeiro.commitic.domain.usecase.console.ExecuteCommandUseCase
import org.tavioribeiro.commitic.domain.usecase.llm.GetLlmsUseCase
import org.tavioribeiro.commitic.domain.usecase.project.GetProjectsUseCase
import org.tavioribeiro.commitic.domain.util.RequestResult
import org.tavioribeiro.commitic.presentation.components.toast.ToastViewModel
import org.tavioribeiro.commitic.presentation.components.toast.model.ToastType
import org.tavioribeiro.commitic.presentation.components.toast.model.ToastUiModel
import org.tavioribeiro.commitic.presentation.mapper.toDomain
import org.tavioribeiro.commitic.presentation.mapper.toUiModel
import org.tavioribeiro.commitic.presentation.model.CommitUiModel
import org.tavioribeiro.commitic.presentation.model.SelectOptionModel
import kotlin.collections.map

data class CommitsTabUiState(
    var isProjectLoading: Boolean = false,
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

    var isCurrentBranchLoading: Boolean = false,
    var currentBranch: String = "",

    var isLlmLoading: Boolean = false,
    val availableLlms: List<SelectOptionModel> = emptyList(),
    val selectedLlmIndex: Int? = 4,
)


class CommitsTabViewModel(
    private val toastViewModel: ToastViewModel,
    private val saveCommitUseCase: SaveCommitUseCase,
    private val getProjectsUseCase: GetProjectsUseCase,
    private val executeCommandUseCase: ExecuteCommandUseCase,
    private val getLlmsUseCase: GetLlmsUseCase

): ViewModel(){

    private val _uiState = MutableStateFlow(CommitsTabUiState())
    val uiState: StateFlow<CommitsTabUiState> = _uiState.asStateFlow()

    init {
        this.loadProjects()
        this.loadLlms()
    }


    private fun loadProjects() {
        viewModelScope.launch {
            if(!_uiState.value.isProjectLoading){
                _uiState.update { it.copy(isProjectLoading = true) }
            }

            val result = getProjectsUseCase()

            when (result) {
                is RequestResult.Success -> {
                    _uiState.update { it.copy(isProjectLoading = false) }
                    val projects = result.data.map { it.toUiModel() }

                    val selectOptions = projects.map {
                        SelectOptionModel(label = it.name, value = it.path)
                    }

                    _uiState.update { it.copy(availableProjects = selectOptions) }
                }
                is RequestResult.Failure -> {
                    _uiState.update { it.copy(isProjectLoading = false) }

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


    private fun loadLlms() {
        viewModelScope.launch {
            if(!_uiState.value.isLlmLoading){
                _uiState.update { it.copy(isLlmLoading = true) }
            }

            val result = getLlmsUseCase()

            when (result) {
                is RequestResult.Success -> {
                    _uiState.update { it.copy(isLlmLoading = false) }

                    val llms = result.data.map { it.toUiModel() }
                    val selectOptions = llms.map {
                        SelectOptionModel(label = "${it.model} | (${it.company})", value = it.id.toString())
                    }

                    _uiState.update { it.copy(availableLlms = selectOptions) }
                }
                is RequestResult.Failure -> {
                    _uiState.update { it.copy(isLlmLoading = false) }
                    when (result.failure) {
                        is LlmFailure.Unexpected -> {
                            toastViewModel.showToast(
                                ToastUiModel(
                                    title = "Erro",
                                    message = "Falha ao buscar LLMs",
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


    fun getCurrentBranch(projectPath: String) {
        viewModelScope.launch {
            if(!_uiState.value.isCurrentBranchLoading) {
                _uiState.update { it.copy(isCurrentBranchLoading = true) }
            }

            val result = executeCommandUseCase(path = projectPath, command ="git branch --show-current")

            when (result) {
                is RequestResult.Success -> {
                    _uiState.update { it.copy(isCurrentBranchLoading = false) }

                    val currentBranch = result.data.trim()

                    if(currentBranch.isNotEmpty()){
                        _uiState.update { it.copy(currentBranch = currentBranch) }
                    }
                }
                is RequestResult.Failure -> {
                    _uiState.update { it.copy(isCurrentBranchLoading = false) }
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
            if(!_uiState.value.isProjectLoading){
                _uiState.update { it.copy(isProjectLoading = true) }
            }


            val commit = commitToSave.toDomain()
            val result = saveCommitUseCase(commit)




            when (result) {
                is RequestResult.Success -> {
                   // this@CommitsTabViewModel.loadCommits()

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
                    _uiState.update { it.copy(isProjectLoading = false) }
                }
            }
        }
    }
}