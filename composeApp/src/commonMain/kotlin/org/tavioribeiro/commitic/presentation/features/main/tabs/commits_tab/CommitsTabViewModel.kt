package org.tavioribeiro.commitic.presentation.features.main.tabs.commits_tab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.tavioribeiro.commitic.domain.model.agents.LlmAgents
import org.tavioribeiro.commitic.domain.model.commit.CommitDomainModel
import org.tavioribeiro.commitic.domain.model.commit.CommitFailure
import org.tavioribeiro.commitic.domain.model.llm.LlmAvailableApis
import org.tavioribeiro.commitic.domain.model.llm.LlmFailure
import org.tavioribeiro.commitic.domain.model.llm.ProgressResult
import org.tavioribeiro.commitic.domain.usecase.commit.GenerateCommitUseCase
import org.tavioribeiro.commitic.domain.usecase.commit.SaveCommitUseCase
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
import org.tavioribeiro.commitic.presentation.model.LlmUiModel
import org.tavioribeiro.commitic.presentation.model.ProjectUiModel
import org.tavioribeiro.commitic.presentation.model.SelectOptionModel
import org.tavioribeiro.commitic.presentation.model.ThreeStepStatusColors
import org.tavioribeiro.commitic.presentation.model.ThreeStepStatusModel




data class CommitsTabUiState(
    var isProjectLoading: Boolean = false,
    val availableProjectSelectOptions: List<SelectOptionModel> = emptyList(),
    val selectedProjectIndex: Int? = null, // Modificado

    var isCurrentBranchLoading: Boolean = false,
    var currentBranch: String = "",

    var isLlmLoading: Boolean = false,
    val availableLlmSelectOptions: List<SelectOptionModel> = emptyList(),
    val selectedLlmIndex: Int? = null, // Modificado

    var isGenaratingCommitLoading: Boolean = false,
    val commitText: String = "Seu commit aparecerá aqui.",
    val stepsAndProgress: ThreeStepStatusModel = ThreeStepStatusModel(
        currentStep = "Não iniciado",
        stepOneColor = ThreeStepStatusColors.GRAY,
        stepTwoColor = ThreeStepStatusColors.GRAY,
        stepThreeColor = ThreeStepStatusColors.GRAY,
        stepFourColor = ThreeStepStatusColors.GRAY
    ),
)




class CommitsTabViewModel(
    private val toastViewModel: ToastViewModel,
    private val saveCommitUseCase: SaveCommitUseCase,
    private val getProjectsUseCase: GetProjectsUseCase,
    private val executeCommandUseCase: ExecuteCommandUseCase,
    private val getLlmsUseCase: GetLlmsUseCase,
    private val generateCommitUseCase: GenerateCommitUseCase,
    private val getSelectedProjectUseCase: GetSelectedProjectUseCase,
    private val saveSelectedProjectUseCase: SaveSelectedProjectUseCase,
    private val getSelectedLlmUseCase: GetSelectedLlmUseCase,
    private val saveSelectedLlmUseCase: SaveSelectedLlmUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CommitsTabUiState())
    val uiState: StateFlow<CommitsTabUiState> = _uiState.asStateFlow()

    private var availableProjects = emptyList<ProjectUiModel>()
    private var availableLlms = emptyList<LlmUiModel>()

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
                    applySavedProjectSelection()
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
                    applySavedLlmSelection()
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

    private fun applySavedProjectSelection() {
        val savedProjectId = getSelectedProjectUseCase() ?: return
        val project = availableProjects.firstOrNull { it.id.toString() == savedProjectId } ?: return
        val savedIndex = availableProjects.indexOf(project)

        if (savedIndex != -1) {
            _uiState.update { it.copy(selectedProjectIndex = savedIndex) }
            getCurrentBranch(project.path)
        }
    }

    private fun applySavedLlmSelection() {
        val savedLlmId = getSelectedLlmUseCase() ?: return
        val savedIndex = availableLlms.indexOfFirst { it.id.toString() == savedLlmId }

        if (savedIndex != -1) {
            _uiState.update { it.copy(selectedLlmIndex = savedIndex) }
        }
    }

    fun onProjectSelected(projectId: String) {
        viewModelScope.launch {
            saveSelectedProjectUseCase(projectId)

            val project = availableProjects.firstOrNull { it.id == projectId.toLong() }
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

    fun onGenerateCommitClicked(projectId: String, llmId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isGenaratingCommitLoading = true, commitText = "") }

            val projectDomain = availableProjects.firstOrNull { it.id == projectId.toLong() }?.toDomain()
            val llmDomain = availableLlms.firstOrNull { it.id == llmId.toLong() }?.toDomain()

            if (projectDomain == null || llmDomain == null) {
                _uiState.update { it.copy(isGenaratingCommitLoading = false) }
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
                        _uiState.update { it.copy(isGenaratingCommitLoading = true) }
                    }

                    is ProgressResult.Success -> {
                        _uiState.update {
                            it.copy(
                                isGenaratingCommitLoading = false,
                                commitText = result.value
                            )
                        }
                    }

                    is ProgressResult.Failure -> {
                        _uiState.update { it.copy(isGenaratingCommitLoading = false) }
                        toastViewModel.showToast(
                            ToastUiModel(
                                title = "Erro",
                                message = "Falha ao gerar commit: ${result.error}",
                                type = ToastType.ERROR
                            )
                        )
                    }

                    is ProgressResult.Progress -> {
                        val currentStepDescription = LlmAgents.fromValue(result.percent)?.taskDescription ?: "Processando..."
                        val stepColors = when (result.percent) {
                            1 -> Triple(ThreeStepStatusColors.ORANGE, ThreeStepStatusColors.GRAY, ThreeStepStatusColors.GRAY)
                            2 -> Triple(ThreeStepStatusColors.GREEN, ThreeStepStatusColors.ORANGE, ThreeStepStatusColors.GRAY)
                            3 -> Triple(ThreeStepStatusColors.GREEN, ThreeStepStatusColors.GREEN, ThreeStepStatusColors.ORANGE)
                            4 -> Triple(ThreeStepStatusColors.GREEN, ThreeStepStatusColors.GREEN, ThreeStepStatusColors.GREEN)
                            else -> Triple(ThreeStepStatusColors.GRAY, ThreeStepStatusColors.GRAY, ThreeStepStatusColors.GRAY)
                        }
                        val finalStepColor = if (result.percent == 4) ThreeStepStatusColors.ORANGE else ThreeStepStatusColors.GRAY
                        val finalDescription = if (result.percent == 5) "Finalizado com sucesso!" else currentStepDescription

                        _uiState.update {
                            it.copy(
                                stepsAndProgress = ThreeStepStatusModel(
                                    currentStep = finalDescription,
                                    stepOneColor = stepColors.first,
                                    stepTwoColor = stepColors.second,
                                    stepThreeColor = stepColors.third,
                                    stepFourColor = finalStepColor
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun onSaveCommitClicked() {
        viewModelScope.launch {
            _uiState.update { it.copy(isProjectLoading = true) }
            val commit = CommitDomainModel(0, "", "")
            when (val result = saveCommitUseCase(commit)) {
                is RequestResult.Success -> {
                    toastViewModel.showToast(
                        ToastUiModel(
                            title = "Sucesso",
                            message = "Commit salvo com sucesso",
                            type = ToastType.SUCCESS
                        )
                    )
                }

                is RequestResult.Failure -> {
                    toastViewModel.showToast(
                        ToastUiModel(
                            title = "Erro",
                            message = "Falha ao salvar commit",
                            type = ToastType.ERROR
                        )
                    )
                }
            }
            _uiState.update { it.copy(isProjectLoading = false) }
        }
    }
}