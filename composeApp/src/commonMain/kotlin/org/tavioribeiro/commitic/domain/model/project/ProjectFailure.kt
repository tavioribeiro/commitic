package org.tavioribeiro.commitic.domain.model.project

sealed interface ProjectFailure {
    data class InvalidName(val reason: String) : ProjectFailure
    data class InvalidPath(val reason: String) : ProjectFailure
    data class Unexpected(val throwable: Throwable) : ProjectFailure
}
