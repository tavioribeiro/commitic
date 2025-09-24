package org.tavioribeiro.commitic.data.mapper

import org.tavioribeiro.commitic.data.model.dtos.CommitDTOModel
import org.tavioribeiro.commitic.domain.model.commit.CommitDomainModel


fun CommitDomainModel.toDto(): CommitDTOModel {
    return CommitDTOModel(
        id = this.id,
        name = this.name,
        path = this.path
    )
}

fun CommitDTOModel.toDomain(): CommitDomainModel {
    return CommitDomainModel(
        id = this.id,
        name = this.name,
        path = this.path
    )
}