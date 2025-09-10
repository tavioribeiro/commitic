package org.tavioribeiro.commitic.domain.util

sealed class RequestResult<out S, out F> {
    data class Success<out S>(val data: S) : RequestResult<S, Nothing>()
    data class Failure<out F>(val failure: F) : RequestResult<Nothing, F>()
}