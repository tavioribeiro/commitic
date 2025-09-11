package org.tavioribeiro.commitic.domain.model.commit

data class CommitDomainModel(
    val id: Long? = 0,
    var name: String,
    var path: String
)