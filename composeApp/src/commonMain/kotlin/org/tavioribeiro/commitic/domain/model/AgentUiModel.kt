package org.tavioribeiro.commitic.domain.model

data class AgentUiModel (
    val model: String,
    val messages: List<AgentMessageUiModel>,
    val maxTokens: Int? = null,
    val temperature: Double? = null,
    val topP: Double? = null,
    val stream: Boolean? = null,
    val stop: String? = null
)

data class AgentMessageUiModel(
    val role: String,
    val content: String
)