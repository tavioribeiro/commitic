package org.tavioribeiro.commitic.domain.model.llm

enum class ProviderType(val displayName: String) {
    OPENAI("OpenAI"),
    GROQ("Groq"),
    GEMINI("Gemini"),
    CLAUDE("Claude"),
    OPENROUTER("OpenRouter")
}