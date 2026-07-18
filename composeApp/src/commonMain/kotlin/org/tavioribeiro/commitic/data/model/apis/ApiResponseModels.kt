package org.tavioribeiro.commitic.data.model.apis

import kotlinx.serialization.Serializable


@Serializable
data class OpenAiApiResponse(
    val choices: List<Choice> = emptyList(),
    val error: ApiError? = null
)

@Serializable
data class ApiError(
    val message: String? = null,
    val code: String? = null
)

//------

@Serializable
data class GeminiResponse(
    val candidates: List<Candidate> = emptyList(),
    val error: ApiError? = null
)

@Serializable
data class Candidate(
    val content: ResponseContent
)

@Serializable
data class ResponseContent(
    val parts: List<ResponsePart>
)

@Serializable
data class ResponsePart(
    val text: String
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
