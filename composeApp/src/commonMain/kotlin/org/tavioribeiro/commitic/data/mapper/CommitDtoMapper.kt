package org.tavioribeiro.commitic.data.mapper

import org.tavioribeiro.commitic.data.model.dtos.CommitDTOModel
import org.tavioribeiro.commitic.domain.model.commit.CommitDomainModel


fun CommitDomainModel.toDto(): CommitDTOModel {
    return CommitDTOModel(
        id = this.id,
        projectId = this.projectId,
        branchName = this.branchName,
        taskObjective = this.taskObjective,
        category = this.category,
        summary = this.summary,
        commitMessage = this.commitMessage
    )
}


fun CommitDTOModel.toDomain(): CommitDomainModel {
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