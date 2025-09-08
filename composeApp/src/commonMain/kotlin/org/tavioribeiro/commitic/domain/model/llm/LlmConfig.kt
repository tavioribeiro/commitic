package org.tavioribeiro.commitic.domain.model.llm

sealed interface LlmConfig {
    val id: Long?
    val name: String
    val providerType: ProviderType

    data class OpenAI(
        override val id: Long?,
        override val name: String,
        val apiKey: String,
        val baseUrl: String?,
        val model: String
    ) : LlmConfig {
        override val providerType: ProviderType = ProviderType.OPENAI
    }

    data class Groq(
        override val id: Long?,
        override val name: String,
        val apiKey: String,
        val model: String
    ) : LlmConfig {
        override val providerType: ProviderType = ProviderType.GROQ
    }

    data class Gemini(
        override val id: Long?,
        override val name: String,
        val apiKey: String,
        val model: String
    ) : LlmConfig {
        override val providerType: ProviderType = ProviderType.GEMINI
    }

    data class Claude(
        override val id: Long?,
        override val name: String,
        val apiKey: String,
        val model: String,
        val anthropicVersion: String
    ) : LlmConfig {
        override val providerType: ProviderType = ProviderType.CLAUDE
    }

    data class OpenRouter(
        override val id: Long?,
        override val name: String,
        val apiKey: String,
        val baseUrl: String?,
        val model: String
    ) : LlmConfig {
        override val providerType: ProviderType = ProviderType.OPENROUTER
    }
}

