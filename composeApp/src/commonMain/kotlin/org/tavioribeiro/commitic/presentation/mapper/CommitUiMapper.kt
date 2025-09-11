package org.tavioribeiro.commitic.presentation.mapper

import org.tavioribeiro.commitic.domain.model.commit.CommitDomainModel
import org.tavioribeiro.commitic.presentation.model.CommitUiModel


fun CommitUiModel.toDomain(): CommitDomainModel {
    return CommitDomainModel(
        id = this.id,
        name = this.name,
        path = this.path
    )
}

fun CommitDomainModel.toUiModel(): CommitUiModel {
    return CommitUiModel(
        id = this.id,
        name = this.name,
        path = this.path
    )
}