package org.tavioribeiro.commitic.domain.model.llm


sealed interface LlmFailure {
    data class InvalidCompany(val reason: String) : LlmFailure
    data class InvalidModel(val reason: String) : LlmFailure
    data class InvalidApiToken(val reason: String) : LlmFailure
    data class InvalidId(val reason: String) : LlmFailure
    data class NotFound(val reason: String) : LlmFailure
    data class Unexpected(val throwable: Throwable) : LlmFailure
}