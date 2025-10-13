package org.tavioribeiro.commitic.presentation.model

data class CommitUiModel(
    val id: Long? = null,
    val projectId: Long,
    val branchName: String,
    val taskObjective: String,
    val category: String,
    val summary: String,
    val commitMessage: String
)