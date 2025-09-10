package org.tavioribeiro.commitic.domain.model.project

data class ProjectDomainModel(
    val id: Long? = 0,
    var name: String,
    var path: String
)