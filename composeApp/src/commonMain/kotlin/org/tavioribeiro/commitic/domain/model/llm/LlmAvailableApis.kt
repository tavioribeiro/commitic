package org.tavioribeiro.commitic.domain.model.llm

enum class LlmAvailableApis(val value: String, val displayName: String) {
    OPENAI("openai", "OpenAI"),
    GEMINI("gemini", "Gemini"),
    OPEN_ROUTER("open_router", "Open Router"),
    GROQ("groq", "Groq"),
    DEEPSEEK("deepseek", "DeepSeek"),
    CLAUDE("claude", "Claude"),
    QWEN("qwen", "Qwen"),
    HUGGING_FACE("hugging_face", "Hugging Face");

    
    companion object {
        fun fromValue(value: String): LlmAvailableApis? {
            return entries.find { it.value == value }
        }
    }
}