package org.tavioribeiro.commitic.presentation.features.main.tabs.llms_tab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.tavioribeiro.commitic.domain.model.llm.*
import org.tavioribeiro.commitic.domain.usecase.llm.DeleteLlmConfigUseCase
import org.tavioribeiro.commitic.domain.usecase.llm.GetLlmConfigsUseCase
import org.tavioribeiro.commitic.domain.usecase.llm.SaveLlmConfigUseCase
import org.tavioribeiro.commitic.domain.util.RequestResult
import org.tavioribeiro.commitic.presentation.components.toast.ToastViewModel
import org.tavioribeiro.commitic.presentation.components.toast.model.ToastType
import org.tavioribeiro.commitic.presentation.components.toast.model.ToastUiModel

data class LlmsTabUiState(
    val isLoading: Boolean = false,
    val configs: List<LlmConfig> = emptyList(),
    val selectedProvider: ProviderType = ProviderType.OPENAI,
    val currentFormState: LlmConfig = LlmConfig.OpenAI(null, "", "", null, "gpt-4"),
    val nameError: String? = null,
    val apiKeyError: String? = null,
    val modelError: String? = null
)

class LLMsTabViewModel(
    private val toastViewModel: ToastViewModel,
    private val saveLlmConfigUseCase: SaveLlmConfigUseCase,
    private val getLlmConfigsUseCase: GetLlmConfigsUseCase,
    private val deleteLlmConfigUseCase: DeleteLlmConfigUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LlmsTabUiState())
    val uiState: StateFlow<LlmsTabUiState> = _uiState.asStateFlow()


     fun loadConfigs() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = getLlmConfigsUseCase()) {
                is RequestResult.Success -> {
                    _uiState.update { it.copy(configs = result.data, isLoading = false) }
                }
                is RequestResult.Failure -> {
                    toastViewModel.showToast(ToastUiModel("Erro", "Falha ao carregar configurações.", ToastType.ERROR))
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun deleteConfig(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (deleteLlmConfigUseCase(id)) {
                is RequestResult.Success -> {
                    toastViewModel.showToast(ToastUiModel("Sucesso", "Configuração deletada!", ToastType.SUCCESS))
                    this@LLMsTabViewModel.loadConfigs()
                }
                is RequestResult.Failure -> {
                    toastViewModel.showToast(ToastUiModel("Erro", "Falha ao deletar configuração.", ToastType.ERROR))
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun onProviderSelected(providerType: ProviderType) {
        _uiState.update {
            val newFormState = when (providerType) {
                ProviderType.OPENAI -> LlmConfig.OpenAI(null, "", "", null, "gpt-4")
                ProviderType.GROQ -> LlmConfig.Groq(null, "", "", "llama3-8b-8192")
                ProviderType.GEMINI -> LlmConfig.Gemini(null, "", "", "gemini-1.5-flash")
                ProviderType.CLAUDE -> LlmConfig.Claude(null, "", "", "claude-3-opus-20240229", "2023-06-01")
                ProviderType.OPENROUTER -> LlmConfig.OpenRouter(null, "", "", null, "google/gemini-flash-1.5")
            }
            it.copy(
                selectedProvider = providerType,
                currentFormState = newFormState,
                nameError = null,
                apiKeyError = null,
                modelError = null
            )
        }
    }

    fun onFieldChange(update: (LlmConfig) -> LlmConfig) {
        _uiState.update {
            it.copy(
                currentFormState = update(it.currentFormState),
                nameError = null,
                apiKeyError = null,
                modelError = null
            )
        }
    }

    fun onSaveClicked() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val configToSave = _uiState.value.currentFormState
            val result = saveLlmConfigUseCase(configToSave)

            when (result) {
                is RequestResult.Success -> {
                    toastViewModel.showToast(ToastUiModel("Sucesso", "Configuração salva!", ToastType.SUCCESS))
                    onProviderSelected(_uiState.value.selectedProvider)
                    this@LLMsTabViewModel.loadConfigs()
                }
                is RequestResult.Failure -> {
                    when (val failure = result.failure) {
                        is LlmFailure.InvalidName -> _uiState.update { it.copy(nameError = failure.reason) }
                        is LlmFailure.InvalidApiKey -> _uiState.update { it.copy(apiKeyError = failure.reason) }
                        is LlmFailure.InvalidModel -> _uiState.update { it.copy(modelError = failure.reason) }
                        is LlmFailure.Unexpected -> toastViewModel.showToast(ToastUiModel("Erro", "Ocorreu um erro inesperado.", ToastType.ERROR))
                    }
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        }
    }
}