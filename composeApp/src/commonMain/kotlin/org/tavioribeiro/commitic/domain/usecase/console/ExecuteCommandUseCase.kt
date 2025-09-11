package org.tavioribeiro.commitic.domain.usecase.console

import org.tavioribeiro.commitic.domain.model.console.ConsoleFailure
import org.tavioribeiro.commitic.domain.repository.ConsoleRepository
import org.tavioribeiro.commitic.domain.util.RequestResult


class ExecuteCommandUseCase(
    private val consoleRepository: ConsoleRepository
) {
    suspend operator fun invoke(command: String, path: String): RequestResult<String, ConsoleFailure> {
        if (command.isBlank()) {
            return RequestResult.Failure(ConsoleFailure.CommandError("O comando não pode estar vazio."))
        }
        if (path.isBlank()) {
            return RequestResult.Failure(ConsoleFailure.CommandError("O caminho não pode estar vazio."))
        }

        return consoleRepository.executeCommand(command, path)
    }
}