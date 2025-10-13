package org.tavioribeiro.commitic.domain.model.directory

sealed interface DirectoryFailure {
    data class InvalidPath(val message: String) : DirectoryFailure
    data class Unexpected(val throwable: Throwable) : DirectoryFailure
}