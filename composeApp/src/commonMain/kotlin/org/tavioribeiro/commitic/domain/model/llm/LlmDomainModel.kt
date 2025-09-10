package org.tavioribeiro.commitic.domain.model.llm

data class LlmDomainModel(
    val id: Long? = null,
    val company: String,
    val model: String,
    val apiToken: String
)