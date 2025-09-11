package org.tavioribeiro.commitic.domain.usecase.commit

import org.tavioribeiro.commitic.domain.model.commit.CommitDomainModel
import org.tavioribeiro.commitic.domain.model.commit.CommitFailure
import org.tavioribeiro.commitic.domain.repository.CommitRepository
import org.tavioribeiro.commitic.domain.util.RequestResult

class SaveCommitUseCase(
    private val commitRepository: CommitRepository
) {
    suspend operator fun invoke(commit: CommitDomainModel): RequestResult<Unit, CommitFailure> {
        if (commit.name.isBlank()) {
            return RequestResult.Failure(CommitFailure.InvalidName("O nome do projeto não pode ser vazio."))
        }

        if (commit.path.isBlank()) {
            return RequestResult.Failure(CommitFailure.InvalidPath("O caminho do projeto não pode ser vazio."))
        }

        return commitRepository.saveCommit(commit)
    }
}