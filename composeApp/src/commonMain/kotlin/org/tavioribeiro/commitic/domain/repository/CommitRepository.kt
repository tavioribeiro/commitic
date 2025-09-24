package org.tavioribeiro.commitic.domain.repository


import org.tavioribeiro.commitic.domain.model.commit.CommitDomainModel
import org.tavioribeiro.commitic.domain.model.commit.CommitFailure
import org.tavioribeiro.commitic.domain.model.llm.LlmDomainModel
import org.tavioribeiro.commitic.domain.model.project.ProjectDomainModel
import org.tavioribeiro.commitic.domain.util.RequestResult

interface CommitRepository {
    suspend fun generateCommit(project: ProjectDomainModel, llm: LlmDomainModel): RequestResult<Unit, CommitFailure>
    suspend fun saveCommit(commit: CommitDomainModel): RequestResult<Unit, CommitFailure>
    suspend fun getCommits(): RequestResult<List<CommitDomainModel>, CommitFailure>
    suspend fun deleteCommit(commit: CommitDomainModel): RequestResult<Unit, CommitFailure>
}