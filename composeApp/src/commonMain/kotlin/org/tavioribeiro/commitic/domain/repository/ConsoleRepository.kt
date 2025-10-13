package org.tavioribeiro.commitic.domain.repository

import org.tavioribeiro.commitic.domain.model.console.ConsoleFailure
import org.tavioribeiro.commitic.domain.util.RequestResult

interface ConsoleRepository {
    suspend fun executeCommand(command: String, path: String): RequestResult<String, ConsoleFailure>
}