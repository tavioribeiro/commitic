package org.tavioribeiro.commitic.presentation.features.main.tabs.llms_tab.components.registered_llm_list_item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.tavioribeiro.commitic.domain.model.llm.LlmConfig
import org.tavioribeiro.commitic.domain.model.llm.ProviderType
import org.tavioribeiro.commitic.presentation.features.main.tabs.llms_tab.LLMsTabViewModel
import org.tavioribeiro.commitic.presentation.features.main.tabs.llms_tab.LlmsTabUiState
import org.tavioribeiro.commitic.presentation.features.main.tabs.llms_tab.components.ClaudeFields
import org.tavioribeiro.commitic.presentation.features.main.tabs.llms_tab.components.GeminiFields
import org.tavioribeiro.commitic.presentation.features.main.tabs.llms_tab.components.GroqFields
import org.tavioribeiro.commitic.presentation.features.main.tabs.llms_tab.components.OpenAiFields
import org.tavioribeiro.commitic.presentation.features.main.tabs.llms_tab.components.OpenRouterFields

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LlmsFormSection(
    uiState: LlmsTabUiState,
    viewModel: LLMsTabViewModel
) {
    val currentForm = uiState.currentFormState
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        ExposedDropdownMenuBox(
            expanded = isDropdownExpanded,
            onExpandedChange = { isDropdownExpanded = it }
        ) {
            OutlinedTextField(
                value = uiState.selectedProvider.displayName,
                onValueChange = {},
                readOnly = true,
                label = { Text("Provedor de API") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false }
            ) {
                ProviderType.values().forEach { provider ->
                    DropdownMenuItem(
                        text = { Text(provider.displayName) },
                        onClick = {
                            viewModel.onProviderSelected(provider)
                            isDropdownExpanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = currentForm.name,
            onValueChange = { newName ->
                viewModel.onFieldChange {
                    when (it) {
                        is LlmConfig.OpenAI -> it.copy(name = newName)
                        is LlmConfig.Groq -> it.copy(name = newName)
                        is LlmConfig.Claude -> it.copy(name = newName)
                        is LlmConfig.Gemini -> it.copy(name = newName)
                        is LlmConfig.OpenRouter -> it.copy(name = newName)
                    }
                }
            },
            label = { Text("Nome da Configuração") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.nameError != null,
            supportingText = { uiState.nameError?.let { Text(it) } }
        )

        when (currentForm) {
            is LlmConfig.OpenAI -> OpenAiFields(uiState, viewModel)
            is LlmConfig.Groq -> GroqFields(uiState, viewModel)
            is LlmConfig.Claude -> ClaudeFields(uiState, viewModel)
            is LlmConfig.Gemini -> GeminiFields(uiState, viewModel)
            is LlmConfig.OpenRouter -> OpenRouterFields(uiState, viewModel)
        }

        Button(
            onClick = { viewModel.onSaveClicked() },
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Salvar Configuração")
            }
        }
    }
}