package org.tavioribeiro.commitic.data.model.dtos

data class LlmDTOModel(
    val id: Long,
    val company: String,
    val model: String,
    val api_token: String
)