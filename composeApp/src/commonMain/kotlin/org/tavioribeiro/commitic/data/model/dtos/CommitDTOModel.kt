package org.tavioribeiro.commitic.data.model.dtos

data class CommitDTOModel(
    val id: Long?,
    val projectId: Long,
    val branchName: String,
    val taskObjective: String,
    val category: String,
    val summary: String,
    val commitMessage: String
)