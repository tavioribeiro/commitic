package org.tavioribeiro.commitic.domain.repository

import org.tavioribeiro.commitic.domain.model.directory.DirectoryFailure
import org.tavioribeiro.commitic.domain.util.RequestResult



interface FileSystemRepository {
    suspend fun checkDirectoryExists(path: String): RequestResult<Boolean, DirectoryFailure>
}