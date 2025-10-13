package org.tavioribeiro.commitic.domain.usecase.commit

import org.tavioribeiro.commitic.domain.model.commit.CommitDomainModel
import org.tavioribeiro.commitic.domain.model.commit.CommitFailure
import org.tavioribeiro.commitic.domain.repository.CommitRepository
import org.tavioribeiro.commitic.domain.util.RequestResult

class SaveCommitUseCase(
    private val commitRepository: CommitRepository
) {
    suspend operator fun invoke(commit: CommitDomainModel): RequestResult<Unit, CommitFailure> {
        if (commit.commitMessage.isBlank()) {
            return RequestResult.Failure(CommitFailure.InvalidName("O commit não pode ser vazio."))
        }

        if (commit.branchName.isBlank()) {
            return RequestResult.Failure(CommitFailure.InvalidName("A branch não pode ser vazia."))
        }

        if (commit.taskObjective.isBlank()) {
            return RequestResult.Failure(CommitFailure.InvalidName("A tarefa não pode ser vazia."))
        }

        if (commit.category.isBlank()) {
            return RequestResult.Failure(CommitFailure.InvalidName("A categoria não pode ser vazia."))
        }

        if (commit.summary.isBlank()) {
            return RequestResult.Failure(CommitFailure.InvalidName("O resumo não pode ser vazio."))
        }

        return commitRepository.saveCommit(commit)
    }
}