package org.tavioribeiro.commitic.presentation.features.main.tabs.llms_tab


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
import org.tavioribeiro.commitic.domain.model.llm.LlmAvailableApis
import org.tavioribeiro.commitic.domain.model.llm.LlmFailure
import org.tavioribeiro.commitic.domain.usecase.llm.DeleteLlmUseCase
import org.tavioribeiro.commitic.domain.usecase.llm.GetLlmsUseCase
import org.tavioribeiro.commitic.domain.usecase.llm.SaveLlmUseCase
import org.tavioribeiro.commitic.domain.util.RequestResult
import org.tavioribeiro.commitic.presentation.components.toast.ToastViewModel
import org.tavioribeiro.commitic.presentation.components.toast.model.ToastType
import org.tavioribeiro.commitic.presentation.components.toast.model.ToastUiModel
import org.tavioribeiro.commitic.presentation.mapper.toDomain
import org.tavioribeiro.commitic.presentation.mapper.toSelectOption
import org.tavioribeiro.commitic.presentation.mapper.toUiModel
import org.tavioribeiro.commitic.presentation.model.LlmUiModel
import org.tavioribeiro.commitic.presentation.model.SelectOptionModel

data class LlmsTabUiState(
    val isLoading: Boolean = false,
    val llms: List<LlmUiModel> = emptyList(),
    val apiOptions: List<SelectOptionModel> = emptyList(),
    val error: String? = null
)


class LlmsTabViewModel(
    private val toastViewModel: ToastViewModel,
    private val getLlmsUseCase: GetLlmsUseCase,
    private val saveLlmUseCase: SaveLlmUseCase,
    private val deleteLlmUseCase: DeleteLlmUseCase
): ViewModel(){

    private val _uiState = MutableStateFlow(LlmsTabUiState())
    val uiState: StateFlow<LlmsTabUiState> = _uiState.asStateFlow()


    private val _llmNameInputWarningState = MutableStateFlow("")
    val llmNameInputWarningState = _llmNameInputWarningState.asStateFlow()

    private val _pathInputWarningState = MutableStateFlow("")
    val pathInputWarningState = _pathInputWarningState.asStateFlow()

    init {
        loadApiOptions()
    }

    private fun loadApiOptions() {
        val options = LlmAvailableApis.entries.map { it.toSelectOption() }
        _uiState.update { it.copy(apiOptions = options) }
    }


    fun loadLlms() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = getLlmsUseCase()

            when (result) {
                is RequestResult.Success -> {
                    val uiLlmsOriginal = result.data.map {
                        it.toUiModel()
                    }

                    val uiLlmsFormated = uiLlmsOriginal.map {
                        val apiEnum = LlmAvailableApis.fromValue(it.company)

                        if (apiEnum != null) {
                            val displayName = apiEnum.displayName
                            it.copy(
                                company = displayName,
                                iconResource = when (it.company){
                                    LlmAvailableApis.OPENAI.value -> Res.drawable.image_chatgpt_dark
                                    LlmAvailableApis.OPEN_ROUTER.value -> Res.drawable.image_openrouter_dark
                                    LlmAvailableApis.GEMINI.value -> Res.drawable.image_gemini_dark
                                    LlmAvailableApis.GROQ.value -> Res.drawable.image_groq_dark
                                    LlmAvailableApis.DEEPSEEK.value -> Res.drawable.image_deepseek_dark
                                    LlmAvailableApis.CLAUDE.value -> Res.drawable.image_claude_dark
                                    LlmAvailableApis.QWEN.value -> Res.drawable.image_qwen_dark
                                    LlmAvailableApis.HUGGING_FACE.value -> Res.drawable.image_hugginface_dark
                                    else -> Res.drawable.icon_llm
                                }
                            )
                        }
                        else{
                            it.copy()
                        }
                    }

                    _uiState.update { it.copy(isLoading = false, llms = uiLlmsFormated) }
                }

                is RequestResult.Failure -> {
                    _uiState.update { it.copy(isLoading = false) }

                    when (result.failure) {
                        is LlmFailure.Unexpected -> {
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


    fun onSaveLlmClicked(llmToSave: LlmUiModel) {
        viewModelScope.launch {
            if(!_uiState.value.isLoading){
                _uiState.update { it.copy(isLoading = true) }
            }


            val llm = llmToSave.toDomain()
            val result = saveLlmUseCase(llm)


            _llmNameInputWarningState.update { "" }
            _pathInputWarningState.update { "" }


            when (result) {
                is RequestResult.Success -> {
                    this@LlmsTabViewModel.loadLlms()

                    toastViewModel.showToast(
                        ToastUiModel(
                            title = "Sucesso",
                            message = "Modelo salvo com sucesso",
                            type = ToastType.SUCCESS,
                            duration = 1500
                        )
                    )
                }

                is RequestResult.Failure -> {
                    when (result.failure) {
                        is LlmFailure.InvalidModel-> {
                            _llmNameInputWarningState.update { "O nome não pode ser vazio." }
                        }

                        is LlmFailure.InvalidApiToken -> {
                            _pathInputWarningState.update { "O caminho não pode ser vazio." }
                        }

                        is LlmFailure.Unexpected -> {
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


    fun deleteLlm(llmToDelete: LlmUiModel) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val llm = llmToDelete.toDomain()
            val result = deleteLlmUseCase(llm)


            when (result) {
                is RequestResult.Success -> {
                    this@LlmsTabViewModel.loadLlms()

                    toastViewModel.showToast(
                        ToastUiModel(
                            title = "Sucesso",
                            message = "Modelo deletado com sucesso",
                            type = ToastType.SUCCESS,
                            duration = 1500
                        )
                    )
                }

                is RequestResult.Failure -> {
                    when (result.failure) {
                        is LlmFailure.Unexpected -> {
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