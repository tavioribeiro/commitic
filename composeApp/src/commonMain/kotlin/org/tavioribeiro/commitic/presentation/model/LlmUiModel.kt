package org.tavioribeiro.commitic.presentation.model

data class LlmUiModel(
    val id: Long? = null,
    var company: String = "",
    var model: String = "",
    var apiToken: String = ""
)