package org.tavioribeiro.commitic.domain.usecase.commit

import org.tavioribeiro.commitic.domain.model.commit.CommitFailure
import org.tavioribeiro.commitic.domain.model.llm.LlmDomainModel
import org.tavioribeiro.commitic.domain.model.project.ProjectDomainModel
import org.tavioribeiro.commitic.domain.repository.CommitRepository
import org.tavioribeiro.commitic.domain.util.RequestResult

class GenerateCommitUseCase(
    private val commitRepository: CommitRepository
) {
    suspend operator fun invoke(project: ProjectDomainModel, llm: LlmDomainModel): RequestResult<String, CommitFailure> {
        if (project.path.isBlank()) {
            return RequestResult.Failure(CommitFailure.InvalidName("O nome do projeto não pode ser vazio."))
        }

        if (llm.apiToken.isBlank()) {
            return RequestResult.Failure(CommitFailure.InvalidName("O nome do projeto não pode ser vazio."))
        }

        if (llm.model.isBlank()) {
            return RequestResult.Failure(CommitFailure.InvalidName("O nome do projeto não pode ser vazio."))
        }

        if (llm.company.isBlank()) {
            return RequestResult.Failure(CommitFailure.InvalidName("O nome do projeto não pode ser vazio."))
        }

        return commitRepository.generateCommit(project, llm)
    }
}