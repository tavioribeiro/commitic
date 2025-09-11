package org.tavioribeiro.commitic.data.datasource.local

expect class ConsoleDataSource() {
    suspend fun execute(command: String, path: String): Result<String>
}