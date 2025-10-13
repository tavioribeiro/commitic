package org.tavioribeiro.commitic.domain.usecase.commit

import org.tavioribeiro.commitic.domain.model.commit.CommitDomainModel
import org.tavioribeiro.commitic.domain.model.commit.CommitFailure
import org.tavioribeiro.commitic.domain.repository.CommitRepository
import org.tavioribeiro.commitic.domain.util.RequestResult

class DeleteCommitUseCase(
    private val commitRepository: CommitRepository
) {
    suspend operator fun invoke(commit: CommitDomainModel): RequestResult<Unit, CommitFailure> {
        if (commit.id == null) {
            return RequestResult.Failure(CommitFailure.InvalidName("O id não pode ser vazio."))
        }

        return commitRepository.deleteCommit(commit)
    }
}