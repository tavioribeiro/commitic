package org.tavioribeiro.commitic.domain.model.commit

sealed interface CommitFailure {
    data class InvalidName(val reason: String) : CommitFailure
    data class InvalidPath(val reason: String) : CommitFailure
    data class Unexpected(val throwable: Throwable) : CommitFailure
}

/*package org.tavioribeiro.commitic.domain.model.commit

sealed interface CommitFailure {
    data class Validation(val errors: List<ValidationError>) : CommitFailure
    data class Unexpected(val throwable: Throwable) : CommitFailure
}

sealed interface ValidationError {
    data class InvalidName(val reason: String) : ValidationError
    data class InvalidPath(val reason: String) : ValidationError
}*/

