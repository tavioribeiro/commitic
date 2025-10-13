package org.tavioribeiro.commitic.domain.model.console

sealed interface ConsoleFailure {
    data class CommandError(val errorOutput: String) : ConsoleFailure
    data class Unexpected(val throwable: Throwable) : ConsoleFailure
}