package org.tavioribeiro.commitic.presentation.mapper

import org.tavioribeiro.commitic.domain.model.project.ProjectDomainModel
import org.tavioribeiro.commitic.presentation.model.ProjectUiModel


fun ProjectUiModel.toDomain(): ProjectDomainModel {
    return ProjectDomainModel(
        id = this.id,
        name = this.name,
        path = this.path
    )
}

fun ProjectDomainModel.toUiModel(): ProjectUiModel {
    return ProjectUiModel(
        id = this.id,
        name = this.name,
        path = this.path
    )
}