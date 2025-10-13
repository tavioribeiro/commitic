package org.tavioribeiro.commitic.domain.usecase.directory

import org.tavioribeiro.commitic.domain.model.directory.DirectoryFailure
import org.tavioribeiro.commitic.domain.repository.FileSystemRepository
import org.tavioribeiro.commitic.domain.util.RequestResult


class CheckDirectoryExistsUseCase(
    private val fileSystemRepository: FileSystemRepository
) {
    suspend operator fun invoke(path: String): RequestResult<Boolean, DirectoryFailure> {
        if (path.isBlank()) {
            return RequestResult.Failure(DirectoryFailure.InvalidPath("O caminho não pode estar vazio."))
        }
        return fileSystemRepository.checkDirectoryExists(path)
    }
}