package org.tavioribeiro.commitic.presentation.model

data class CommitUiModel(
    val id: Long? = 0,
    var name: String = "",
    var path: String = ""
)