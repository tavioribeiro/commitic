package org.tavioribeiro.commitic.domain.usecase.commit

import org.tavioribeiro.commitic.domain.model.commit.CommitCleanupMode
import org.tavioribeiro.commitic.domain.model.commit.CommitFailure
import org.tavioribeiro.commitic.domain.repository.CommitRepository
import org.tavioribeiro.commitic.domain.util.RequestResult

class DeleteCommitsByBranchUseCase(
    private val commitRepository: CommitRepository
) {
    suspend operator fun invoke(
        projectId: Long,
        branchName: String,
        mode: CommitCleanupMode
    ): RequestResult<Unit, CommitFailure> {
        val limit = when (mode) {
            CommitCleanupMode.Last -> 1
            CommitCleanupMode.LastTwo -> 2
            CommitCleanupMode.All -> null
        }

        return commitRepository.deleteCommitsByProjectIdAndBranch(projectId, branchName, limit)
    }
}
