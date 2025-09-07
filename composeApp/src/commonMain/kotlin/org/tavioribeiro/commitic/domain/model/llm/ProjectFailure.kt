package org.tavioribeiro.commitic.domain.model.llm


sealed interface LlmFailure {
    data class InvalidName(val reason: String) : LlmFailure
    data class InvalidPath(val reason: String) : LlmFailure
    data class Unexpected(val throwable: Throwable) : LlmFailure
}
