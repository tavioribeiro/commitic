package org.tavioribeiro.commitic.data.datasource.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

actual class ConsoleDataSource {

    actual suspend fun execute(command: String, path: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val parts = command.split("\\s".toRegex())
            val process = ProcessBuilder(*parts.toTypedArray())
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .start()

            process.waitFor(60, TimeUnit.SECONDS)

            val output = process.inputStream.bufferedReader().readText()
            val error = process.errorStream.bufferedReader().readText()

            if (process.exitValue() == 0) {
                Result.success(output)
            } else {
                val errorMessage = error.ifBlank { "Comando falhou com código de saída: ${process.exitValue()}" }
                Result.failure(RuntimeException(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}