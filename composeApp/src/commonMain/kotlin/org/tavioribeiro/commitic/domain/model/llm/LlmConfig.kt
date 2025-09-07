package org.tavioribeiro.commitic.domain.model.llm

enum class ApiProvider(val displayName: String, val key: String) {
    OPEN_AI("OpenAI", "openai"),
    GEMINI("Google Gemini", "gemini"),
    ANTHROPIC("Anthropic Claude", "anthropic"),
    GROQ("Groq", "groq")
}

sealed class LlmConfig {
    abstract val id: Long?
    abstract val name: String
    abstract val provider: ApiProvider
    abstract val apiKey: String

    data class OpenAiConfig(
        override val id: Long? = null,
        override val name: String,
        override val apiKey: String,
        val model: String = "gpt-4o"
    ) : LlmConfig() {
        override val provider: ApiProvider get() = ApiProvider.OPEN_AI
    }

    data class AnthropicConfig(
        override val id: Long? = null,
        override val name: String,
        override val apiKey: String,
        val model: String = "claude-3-sonnet-20240229",
        val apiVersion: String = "2023-06-01"
    ) : LlmConfig() {
        override val provider: ApiProvider get() = ApiProvider.ANTHROPIC
    }
}
