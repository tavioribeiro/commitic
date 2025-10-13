package org.tavioribeiro.commitic.data.repository

import org.tavioribeiro.commitic.core.utils.directoryExists
import org.tavioribeiro.commitic.domain.model.directory.DirectoryFailure
import org.tavioribeiro.commitic.domain.repository.FileSystemRepository
import org.tavioribeiro.commitic.domain.util.RequestResult

class FileSystemRepositoryImpl : FileSystemRepository {

    override suspend fun checkDirectoryExists(path: String): RequestResult<Boolean, DirectoryFailure> {
        return try {
            val exists = directoryExists(path)
            RequestResult.Success(exists)
        } catch (e: Exception) {
            RequestResult.Failure(DirectoryFailure.Unexpected(e))
        }
    }
}