package org.tavioribeiro.commitic.domain.usecase.commit

import org.tavioribeiro.commitic.domain.model.commit.CommitDomainModel
import org.tavioribeiro.commitic.domain.model.commit.CommitFailure
import org.tavioribeiro.commitic.domain.repository.CommitRepository
import org.tavioribeiro.commitic.domain.util.RequestResult


class GetCommitsUseCase(
    private val commitRepository: CommitRepository
) {
    suspend operator fun invoke(): RequestResult<List<CommitDomainModel>, CommitFailure> {
        return commitRepository.getCommits()
    }
}