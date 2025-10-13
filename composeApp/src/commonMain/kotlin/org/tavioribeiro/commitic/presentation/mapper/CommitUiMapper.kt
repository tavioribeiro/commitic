package org.tavioribeiro.commitic.presentation.mapper


import org.tavioribeiro.commitic.domain.model.commit.CommitDomainModel
import org.tavioribeiro.commitic.presentation.model.CommitUiModel


fun CommitDomainModel.toUiModel(): CommitUiModel {
    return CommitUiModel(
        id = this.id,
        projectId = this.projectId,
        branchName = this.branchName,
        taskObjective = this.taskObjective,
        category = this.category,
        summary = this.summary,
        commitMessage = this.commitMessage
    )
}


fun CommitUiModel.toDomain(): CommitDomainModel {
    return CommitDomainModel(
        id = this.id,
        projectId = this.projectId,
        branchName = this.branchName,
        taskObjective = this.taskObjective,
        category = this.category,
        summary = this.summary,
        commitMessage = this.commitMessage
    )
}