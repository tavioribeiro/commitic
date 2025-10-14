package org.tavioribeiro.commitic.presentation.features.main.tabs.pull_request

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.tavioribeiro.commitic.domain.model.agents.LlmAgents
import org.tavioribeiro.commitic.domain.model.llm.LlmAvailableApis
import org.tavioribeiro.commitic.domain.model.llm.ProgressResult
import org.tavioribeiro.commitic.domain.usecase.commit.GenerateCommitUseCase
import org.tavioribeiro.commitic.domain.usecase.console.ExecuteCommandUseCase
import org.tavioribeiro.commitic.domain.usecase.llm.GetLlmsUseCase
import org.tavioribeiro.commitic.domain.usecase.preferences.GetSelectedLlmUseCase
import org.tavioribeiro.commitic.domain.usecase.preferences.GetSelectedProjectUseCase
import org.tavioribeiro.commitic.domain.usecase.preferences.SaveSelectedLlmUseCase
import org.tavioribeiro.commitic.domain.usecase.preferences.SaveSelectedProjectUseCase
import org.tavioribeiro.commitic.domain.usecase.project.GetProjectsUseCase
import org.tavioribeiro.commitic.domain.util.RequestResult
import org.tavioribeiro.commitic.presentation.components.toast.ToastViewModel
import org.tavioribeiro.commitic.presentation.components.toast.model.ToastType
import org.tavioribeiro.commitic.presentation.components.toast.model.ToastUiModel
import org.tavioribeiro.commitic.presentation.mapper.toDomain
import org.tavioribeiro.commitic.presentation.mapper.toUiModel
import org.tavioribeiro.commitic.presentation.model.CommitUiModel
import org.tavioribeiro.commitic.presentation.model.LlmUiModel
import org.tavioribeiro.commitic.presentation.model.ProjectUiModel
import org.tavioribeiro.commitic.presentation.model.SelectOptionModel
import org.tavioribeiro.commitic.presentation.model.FiveStepStatusColors
import org.tavioribeiro.commitic.presentation.model.FiveStepStatusModel




data class PullRequestTabUiState(
    var isProjectLoading: Boolean = false,
    val availableProjectSelectOptions: List<SelectOptionModel> = emptyList(),
    val selectedProjectIndex: Int? = null,

    var isCurrentBranchLoading: Boolean = false,
    var currentBranch: String = "",

    var isLlmLoading: Boolean = false,
    val availableLlmSelectOptions: List<SelectOptionModel> = emptyList(),
    val selectedLlmIndex: Int? = null,

    var isGenaratingPullRequestLoading: Boolean = false,
    val pullRequestText: String = "O texto da Pull Request irá aparecer aqui.",

)




class PullRequestTabViewModel(
    private val toastViewModel: ToastViewModel,
    private val getProjectsUseCase: GetProjectsUseCase,
    private val executeCommandUseCase: ExecuteCommandUseCase,
    private val getLlmsUseCase: GetLlmsUseCase,
    private val generateCommitUseCase: GenerateCommitUseCase,
    private val getSelectedProjectUseCase: GetSelectedProjectUseCase,
    private val saveSelectedProjectUseCase: SaveSelectedProjectUseCase,
    private val getSelectedLlmUseCase: GetSelectedLlmUseCase,
    private val saveSelectedLlmUseCase: SaveSelectedLlmUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PullRequestTabUiState())
    val uiState: StateFlow<PullRequestTabUiState> = _uiState.asStateFlow()

    private var availableProjects = emptyList<ProjectUiModel>()
    private var availableLlms = emptyList<LlmUiModel>()

    private var commitUiModel = CommitUiModel(
        id = null,
        projectId = 0,
        branchName = "",
        taskObjective = "",
        category = "",
        summary = "",
        commitMessage = ""
    )

    init {
        loadProjects()
        loadLlms()
    }

    private fun loadProjects() {
        viewModelScope.launch {
            _uiState.update { it.copy(isProjectLoading = true) }
            when (val result = getProjectsUseCase()) {
                is RequestResult.Success -> {
                    availableProjects = result.data.map { it.toUiModel() }
                    val selectOptions = availableProjects.map {
                        SelectOptionModel(label = it.name, value = it.id.toString())
                    }
                    _uiState.update {
                        it.copy(
                            isProjectLoading = false,
                            availableProjectSelectOptions = selectOptions
                        )
                    }

                    getLastSelectedProject()
                }

                is RequestResult.Failure -> {
                    _uiState.update { it.copy(isProjectLoading = false) }
                    toastViewModel.showToast(
                        ToastUiModel(
                            title = "Erro",
                            message = "Falha ao buscar projetos",
                            type = ToastType.ERROR
                        )
                    )
                }
            }
        }
    }

    private fun loadLlms() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLlmLoading = true) }
            when (val result = getLlmsUseCase()) {
                is RequestResult.Success -> {
                    availableLlms = result.data.map { it.toUiModel() }
                    val selectOptions = availableLlms.map {
                        val apiEnum = LlmAvailableApis.fromValue(it.company)
                        SelectOptionModel(
                            label = "${it.model} | ${apiEnum?.displayName}",
                            value = it.id.toString()
                        )
                    }
                    _uiState.update {
                        it.copy(
                            isLlmLoading = false,
                            availableLlmSelectOptions = selectOptions
                        )
                    }
                    getLastSelectedLlm()
                }

                is RequestResult.Failure -> {
                    _uiState.update { it.copy(isLlmLoading = false) }
                    toastViewModel.showToast(
                        ToastUiModel(
                            title = "Erro",
                            message = "Falha ao buscar LLMs",
                            type = ToastType.ERROR
                        )
                    )
                }
            }
        }
    }

    private fun getLastSelectedProject() {
        val savedProjectId = getSelectedProjectUseCase() ?: return
        onProjectSelected(savedProjectId)
    }

    private fun getLastSelectedLlm() {
        val savedLlmId = getSelectedLlmUseCase() ?: return
        val savedIndex = availableLlms.indexOfFirst { it.id.toString() == savedLlmId }

        if (savedIndex != -1) {
            _uiState.update { it.copy(selectedLlmIndex = savedIndex) }
        }
    }

    fun onProjectSelected(projectId: String) {
        viewModelScope.launch {
            saveSelectedProjectUseCase(projectId)

            val project = availableProjects.firstOrNull { it.id.toString() == projectId }

            if (project != null) {
                val projectIndex = availableProjects.indexOf(project)
                _uiState.update { it.copy(selectedProjectIndex = projectIndex) }
                getCurrentBranch(project.path)
            }
        }
    }

    fun onLlmSelected(llmId: String) {
        saveSelectedLlmUseCase(llmId)
        val llmIndex = availableLlms.indexOfFirst { it.id.toString() == llmId }
        if (llmIndex != -1) {
            _uiState.update { it.copy(selectedLlmIndex = llmIndex) }
        }
    }

    private fun getCurrentBranch(projectPath: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isCurrentBranchLoading = true) }
            when (val result = executeCommandUseCase(path = projectPath, command = "git branch --show-current")) {
                is RequestResult.Success -> {
                    val currentBranch = result.data.trim()
                    _uiState.update {
                        it.copy(
                            isCurrentBranchLoading = false,
                            currentBranch = currentBranch
                        )
                    }
                }

                is RequestResult.Failure -> {
                    _uiState.update { it.copy(isCurrentBranchLoading = false) }
                    toastViewModel.showToast(
                        ToastUiModel(
                            title = "Erro",
                            message = "Falha ao buscar branch atual",
                            type = ToastType.ERROR
                        )
                    )
                }
            }
        }
    }

    fun onGenerateCommitClicked(projectIndex: Int, llmIndex: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isGenaratingPullRequestLoading = true, pullRequestText = "") }

            val projectDomain = availableProjects.getOrNull(projectIndex)?.toDomain()
            val llmDomain = availableLlms.getOrNull(llmIndex)?.toDomain()

            if (projectDomain == null || llmDomain == null) {
                _uiState.update { it.copy(isGenaratingPullRequestLoading = false) }
                toastViewModel.showToast(
                    ToastUiModel(
                        title = "Atenção",
                        message = "Selecione um projeto e um modelo de LLM.",
                        type = ToastType.WARNING
                    )
                )
                return@launch
            }

            generateCommitUseCase(projectDomain, llmDomain).collect { result ->
                when (result) {
                    is ProgressResult.Loading -> {
                        _uiState.update { it.copy(isGenaratingPullRequestLoading = true) }
                    }

                    is ProgressResult.Success -> {

                        commitUiModel = result.value.toUiModel()

                        _uiState.update {
                            it.copy(
                                isGenaratingPullRequestLoading = false,
                                pullRequestText = result.value.commitMessage
                            )
                        }
                    }

                    is ProgressResult.Failure -> {
                        _uiState.update { it.copy(isGenaratingPullRequestLoading = false) }
                        toastViewModel.showToast(
                            ToastUiModel(
                                title = "Erro",
                                message = "Falha ao gerar commit: ${result.error}",
                                type = ToastType.ERROR
                            )
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}