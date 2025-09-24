package org.tavioribeiro.commitic.data.model.apis

import kotlinx.serialization.Serializable


@Serializable
data class OpenAiApiResponse(
    val choices: List<Choice>
)
@Serializable
data class Choice(
    val message: ResponseMessage
)
@Serializable
data class ResponseMessage(
    val role: String,
    val content: String
)
