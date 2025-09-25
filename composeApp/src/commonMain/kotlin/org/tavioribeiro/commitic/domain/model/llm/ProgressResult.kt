package org.tavioribeiro.commitic.domain.model.llm

sealed class ProgressResult<out T, out E> {
    object Loading : ProgressResult<Nothing, Nothing>()
    data class Progress(val percent: Int) : ProgressResult<Nothing, Nothing>()
    data class Success<T>(val value: T) : ProgressResult<T, Nothing>()
    data class Failure<E>(val error: E) : ProgressResult<Nothing, E>()
}
