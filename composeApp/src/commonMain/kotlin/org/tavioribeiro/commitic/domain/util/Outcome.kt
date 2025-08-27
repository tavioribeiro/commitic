package org.tavioribeiro.commitic.domain.util

sealed class Outcome<out T> {
    data class Success<out T>(val data: T) : Outcome<T>()
    data class Error(val exception: Exception) : Outcome<Nothing>()
}