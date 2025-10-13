package org.tavioribeiro.commitic.data.repository

import org.tavioribeiro.commitic.data.datasource.local.ConsoleDataSource
import org.tavioribeiro.commitic.domain.model.console.ConsoleFailure
import org.tavioribeiro.commitic.domain.repository.ConsoleRepository
import org.tavioribeiro.commitic.domain.util.RequestResult

class ConsoleRepositoryImpl(
    private val consoleDataSource: ConsoleDataSource
) : ConsoleRepository {

    override suspend fun executeCommand(command: String, path: String): RequestResult<String, ConsoleFailure> {
        return try {
            consoleDataSource.execute(command, path).fold(
                onSuccess = { output -> RequestResult.Success(output) },
                onFailure = { error -> RequestResult.Failure(ConsoleFailure.CommandError(error.message ?: "Erro desconhecido")) }
            )
        } catch (e: Exception) {
            RequestResult.Failure(ConsoleFailure.Unexpected(e))
        }
    }
}

