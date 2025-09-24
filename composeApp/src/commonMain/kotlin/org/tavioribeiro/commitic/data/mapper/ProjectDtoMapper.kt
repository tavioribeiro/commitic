package org.tavioribeiro.commitic.data.mapper

import org.tavioribeiro.commitic.data.model.dtos.ProjectDTOModel
import org.tavioribeiro.commitic.domain.model.project.ProjectDomainModel


fun ProjectDomainModel.toDto(): ProjectDTOModel {
    return ProjectDTOModel(
        id = this.id,
        name = this.name,
        path = this.path
    )
}

fun ProjectDTOModel.toDomain(): ProjectDomainModel {
    return ProjectDomainModel(
        id = this.id,
        name = this.name,
        path = this.path
    )
}