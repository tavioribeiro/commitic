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
import org.tavioribeiro.commitic.presentation.model.ThreeStepStatusColors
import org.tavioribeiro.commitic.presentation.model.ThreeStepStatusModel
import kotlin.collections.map

data class CommitsTabUiState(
    var isProjectLoading: Boolean = false,
    val availableProjectSelectOptions: List<SelectOptionModel> = emptyList(),
    val selectedProjectIndex: Int? = 4,

    var isCurrentBranchLoading: Boolean = false,
    var currentBranch: String = "",

    var isLlmLoading: Boolean = false,
    val availableLlmSelectOptions: List<SelectOptionModel> = emptyList(),
    val selectedLlmIndex: Int? = 4,

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
): ViewModel(){

    private val _uiState = MutableStateFlow(CommitsTabUiState())
    val uiState: StateFlow<CommitsTabUiState> = _uiState.asStateFlow()

    private var availableProjects = emptyList<ProjectUiModel>()
    private var availableLlms = emptyList<LlmUiModel>()

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
                    availableProjects = result.data.map { it.toUiModel() }

                    val selectOptions = availableProjects.map {
                        SelectOptionModel(label = it.name, value = it.id.toString())
                    }

                    _uiState.update { it.copy(availableProjectSelectOptions = selectOptions) }
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

                    availableLlms = result.data.map { it.toUiModel() }
                    val selectOptions = availableLlms.map {
                        val apiEnum = LlmAvailableApis.fromValue(it.company)

                        SelectOptionModel(label = "${it.model} | ${apiEnum?.displayName}", value = it.id.toString())
                    }

                    _uiState.update { it.copy(availableLlmSelectOptions = selectOptions) }
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


    fun onProjectSelected(projectId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isCurrentBranchLoading = true) }

            println("Project selected: $projectId")

            val projectPath = availableProjects.firstOrNull { it.id == projectId.toLong() }?.path
            if (projectPath != null) {
                this@CommitsTabViewModel.getCurrentBranch(projectPath)
                _uiState.update { it.copy(selectedProjectIndex = uiState.value.availableProjectSelectOptions.indexOfFirst { it.value == projectId }) }

                _uiState.update { it.copy(isCurrentBranchLoading = false) }
            }
        }
    }


    private fun getCurrentBranch(projectPath: String) {
        viewModelScope.launch {
            if(!_uiState.value.isCurrentBranchLoading) {
                _uiState.update { it.copy(isCurrentBranchLoading = true) }
            }

            val result = executeCommandUseCase(path = projectPath, command = "git branch --show-current")

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



    fun onGenerateCommitClicked(projectId: String, llmId: String) {
        viewModelScope.launch {
            if (!_uiState.value.isGenaratingCommitLoading) {
                _uiState.update { it.copy(isGenaratingCommitLoading = true, commitText = "") }
            }

            val projectUiModel = availableProjects.firstOrNull { it.id == projectId.toLong() }
            val llmUiModel = availableLlms.firstOrNull { it.id == llmId.toLong() }

            val projectDomain = projectUiModel?.toDomain()
            val llmDomain = llmUiModel?.toDomain()

            if (projectDomain == null || llmDomain == null) {
                _uiState.update { it.copy(isGenaratingCommitLoading = false) }
                println("Erro: Projeto ou LLM não encontrado.")
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
                        println("Falha ao gerar commit: ${result.error}")
                        toastViewModel.showToast(
                            ToastUiModel(
                                title = "Erro",
                                message = "Falha ao gerar commit",
                                type = ToastType.ERROR,
                                duration = 1500
                            )
                        )
                        _uiState.update { it.copy(isGenaratingCommitLoading = false) }
                    }

                    is ProgressResult.Progress -> {
                         when (result.percent){
                             1 -> _uiState.update {
                                 it.copy(
                                     stepsAndProgress = ThreeStepStatusModel(
                                         currentStep =  LlmAgents.fromValue(result.percent)?.taskDescription ?: "Não iniciado",
                                         stepOneColor = ThreeStepStatusColors.ORANGE,
                                         stepTwoColor = ThreeStepStatusColors.GRAY,
                                         stepThreeColor = ThreeStepStatusColors.GRAY,
                                         stepFourColor = ThreeStepStatusColors.GRAY
                                     )
                                 )
                             }
                             2 -> _uiState.update {
                                 it.copy(
                                     stepsAndProgress = ThreeStepStatusModel(
                                         currentStep = LlmAgents.fromValue(result.percent)?.taskDescription ?: "Não iniciado",
                                         stepOneColor = ThreeStepStatusColors.GREEN,
                                         stepTwoColor = ThreeStepStatusColors.ORANGE,
                                         stepThreeColor = ThreeStepStatusColors.GRAY,
                                         stepFourColor = ThreeStepStatusColors.GRAY
                                     )
                                 )
                             }
                             3 -> _uiState.update {
                                 it.copy(
                                     stepsAndProgress = ThreeStepStatusModel(
                                         currentStep = LlmAgents.fromValue(result.percent)?.taskDescription ?: "Não iniciado",
                                         stepOneColor = ThreeStepStatusColors.GREEN,
                                         stepTwoColor = ThreeStepStatusColors.GREEN,
                                         stepThreeColor = ThreeStepStatusColors.ORANGE,
                                         stepFourColor = ThreeStepStatusColors.GRAY
                                     )
                                 )
                             }
                             4 -> _uiState.update {
                                 it.copy(
                                     stepsAndProgress = ThreeStepStatusModel(
                                         currentStep = LlmAgents.fromValue(result.percent)?.taskDescription ?: "Não iniciado",
                                         stepOneColor = ThreeStepStatusColors.GREEN,
                                         stepTwoColor = ThreeStepStatusColors.GREEN,
                                         stepThreeColor = ThreeStepStatusColors.GREEN,
                                         stepFourColor = ThreeStepStatusColors.ORANGE
                                     )
                                 )
                             }
                             5 -> _uiState.update {
                                 it.copy(
                                     stepsAndProgress = ThreeStepStatusModel(
                                         currentStep = "Finalizado com sucesso \uD83D\uDE42\uD83D\uDC4D",
                                         stepOneColor = ThreeStepStatusColors.GREEN,
                                         stepTwoColor = ThreeStepStatusColors.GREEN,
                                         stepThreeColor = ThreeStepStatusColors.GREEN,
                                         stepFourColor = ThreeStepStatusColors.GREEN
                                     )
                                 )
                             }
                         }
                    }
                }
            }
        }
    }



     fun onSaveCommitClicked() {
        viewModelScope.launch {
            if(!_uiState.value.isProjectLoading){
                _uiState.update { it.copy(isProjectLoading = true) }
            }


            val commit = CommitDomainModel(
                0,
                "",
                ""

            )
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