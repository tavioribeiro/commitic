package org.tavioribeiro.commitic.domain.model.commit

data class CommitDomainModel(
    val id: Long? = null,
    val projectId: Long,
    val branchName: String,
    val taskObjective: String,
    val category: String,
    val summary: String,
    val commitMessage: String
)