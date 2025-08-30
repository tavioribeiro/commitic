package org.tavioribeiro.commitic.domain.util

sealed class Result<out S, out F> {
    data class Success<out S>(val data: S) : Result<S, Nothing>()
    data class Failure<out F>(val failure: F) : Result<Nothing, F>()
}