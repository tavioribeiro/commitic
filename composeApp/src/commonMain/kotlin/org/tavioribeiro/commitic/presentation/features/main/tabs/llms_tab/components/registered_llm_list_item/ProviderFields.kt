package org.tavioribeiro.commitic.presentation.features.main.tabs.llms_tab.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.tavioribeiro.commitic.domain.model.llm.LlmConfig
import org.tavioribeiro.commitic.presentation.features.main.tabs.llms_tab.LLMsTabViewModel
import org.tavioribeiro.commitic.presentation.features.main.tabs.llms_tab.LlmsTabUiState

@Composable
fun OpenAiFields(uiState: LlmsTabUiState, viewModel: LLMsTabViewModel) {
    val form = uiState.currentFormState as LlmConfig.OpenAI
    OutlinedTextField(
        value = form.apiKey,
        onValueChange = { newApiKey ->
            viewModel.onFieldChange { (it as LlmConfig.OpenAI).copy(apiKey = newApiKey) }
        },
        label = { Text("API Key") },
        modifier = Modifier.fillMaxWidth(),
        isError = uiState.apiKeyError != null,
        supportingText = { uiState.apiKeyError?.let { Text(it) } }
    )
    OutlinedTextField(
        value = form.model,
        onValueChange = { newModel ->
            viewModel.onFieldChange { (it as LlmConfig.OpenAI).copy(model = newModel) }
        },
        label = { Text("Nome do Modelo") },
        modifier = Modifier.fillMaxWidth(),
        isError = uiState.modelError != null,
        supportingText = { uiState.modelError?.let { Text(it) } }
    )
    OutlinedTextField(
        value = form.baseUrl ?: "",
        onValueChange = { newUrl ->
            viewModel.onFieldChange { (it as LlmConfig.OpenAI).copy(baseUrl = newUrl) }
        },
        label = { Text("Base URL (Opcional)") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun GroqFields(uiState: LlmsTabUiState, viewModel: LLMsTabViewModel) {
    val form = uiState.currentFormState as LlmConfig.Groq
    OutlinedTextField(
        value = form.apiKey,
        onValueChange = { newApiKey ->
            viewModel.onFieldChange { (it as LlmConfig.Groq).copy(apiKey = newApiKey) }
        },
        label = { Text("API Key") },
        modifier = Modifier.fillMaxWidth(),
        isError = uiState.apiKeyError != null,
        supportingText = { uiState.apiKeyError?.let { Text(it) } }
    )
    OutlinedTextField(
        value = form.model,
        onValueChange = { newModel ->
            viewModel.onFieldChange { (it as LlmConfig.Groq).copy(model = newModel) }
        },
        label = { Text("Nome do Modelo") },
        modifier = Modifier.fillMaxWidth(),
        isError = uiState.modelError != null,
        supportingText = { uiState.modelError?.let { Text(it) } }
    )
}

@Composable
fun GeminiFields(uiState: LlmsTabUiState, viewModel: LLMsTabViewModel) {
    val form = uiState.currentFormState as LlmConfig.Gemini
    OutlinedTextField(
        value = form.apiKey,
        onValueChange = { newApiKey ->
            viewModel.onFieldChange { (it as LlmConfig.Gemini).copy(apiKey = newApiKey) }
        },
        label = { Text("API Key") },
        modifier = Modifier.fillMaxWidth(),
        isError = uiState.apiKeyError != null,
        supportingText = { uiState.apiKeyError?.let { Text(it) } }
    )
    OutlinedTextField(
        value = form.model,
        onValueChange = { newModel ->
            viewModel.onFieldChange { (it as LlmConfig.Gemini).copy(model = newModel) }
        },
        label = { Text("Nome do Modelo") },
        modifier = Modifier.fillMaxWidth(),
        isError = uiState.modelError != null,
        supportingText = { uiState.modelError?.let { Text(it) } }
    )
}

@Composable
fun ClaudeFields(uiState: LlmsTabUiState, viewModel: LLMsTabViewModel) {
    val form = uiState.currentFormState as LlmConfig.Claude
    OutlinedTextField(
        value = form.apiKey,
        onValueChange = { newApiKey ->
            viewModel.onFieldChange { (it as LlmConfig.Claude).copy(apiKey = newApiKey) }
        },
        label = { Text("API Key") },
        modifier = Modifier.fillMaxWidth(),
        isError = uiState.apiKeyError != null,
        supportingText = { uiState.apiKeyError?.let { Text(it) } }
    )
    OutlinedTextField(
        value = form.model,
        onValueChange = { newModel ->
            viewModel.onFieldChange { (it as LlmConfig.Claude).copy(model = newModel) }
        },
        label = { Text("Nome do Modelo") },
        modifier = Modifier.fillMaxWidth(),
        isError = uiState.modelError != null,
        supportingText = { uiState.modelError?.let { Text(it) } }
    )
    OutlinedTextField(
        value = form.anthropicVersion,
        onValueChange = { newVersion ->
            viewModel.onFieldChange { (it as LlmConfig.Claude).copy(anthropicVersion = newVersion) }
        },
        label = { Text("Anthropic Version") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun OpenRouterFields(uiState: LlmsTabUiState, viewModel: LLMsTabViewModel) {
    val form = uiState.currentFormState as LlmConfig.OpenRouter
    OutlinedTextField(
        value = form.apiKey,
        onValueChange = { newApiKey ->
            viewModel.onFieldChange { (it as LlmConfig.OpenRouter).copy(apiKey = newApiKey) }
        },
        label = { Text("API Key") },
        modifier = Modifier.fillMaxWidth(),
        isError = uiState.apiKeyError != null,
        supportingText = { uiState.apiKeyError?.let { Text(it) } }
    )
    OutlinedTextField(
        value = form.model,
        onValueChange = { newModel ->
            viewModel.onFieldChange { (it as LlmConfig.OpenRouter).copy(model = newModel) }
        },
        label = { Text("Nome do Modelo") },
        modifier = Modifier.fillMaxWidth(),
        isError = uiState.modelError != null,
        supportingText = { uiState.modelError?.let { Text(it) } }
    )
    OutlinedTextField(
        value = form.baseUrl ?: "",
        onValueChange = { newUrl ->
            viewModel.onFieldChange { (it as LlmConfig.OpenRouter).copy(baseUrl = newUrl) }
        },
        label = { Text("Base URL (Opcional)") },
        modifier = Modifier.fillMaxWidth()
    )
}