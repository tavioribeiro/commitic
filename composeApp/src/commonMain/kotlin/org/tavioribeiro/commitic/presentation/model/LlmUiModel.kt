package org.tavioribeiro.commitic.presentation.model

import org.jetbrains.compose.resources.DrawableResource

data class LlmUiModel(
    val id: Long? = null,
    var company: String = "",
    var model: String = "",
    var apiToken: String = "",
    var iconResource: DrawableResource?= null
)