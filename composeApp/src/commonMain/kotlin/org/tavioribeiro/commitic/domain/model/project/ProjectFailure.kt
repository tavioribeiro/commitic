package org.tavioribeiro.commitic.domain.model.project

sealed interface ProjectFailure {
    data class InvalidName(val reason: String) : ProjectFailure
    data class InvalidPath(val reason: String) : ProjectFailure
    data class Unexpected(val throwable: Throwable) : ProjectFailure
}

/*package org.tavioribeiro.commitic.domain.model.project

sealed interface ProjectFailure {
    data class Validation(val errors: List<ValidationError>) : ProjectFailure
    data class Unexpected(val throwable: Throwable) : ProjectFailure
}

sealed interface ValidationError {
    data class InvalidName(val reason: String) : ValidationError
    data class InvalidPath(val reason: String) : ValidationError
}*/

