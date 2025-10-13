package org.tavioribeiro.commitic.data.repository

import org.tavioribeiro.commitic.data.datasource.local.CommitLocalDataSource
import org.tavioribeiro.commitic.data.datasource.remote.LlmRemoteDataSource
import org.tavioribeiro.commitic.data.mapper.toDomain
import org.tavioribeiro.commitic.data.mapper.toDto
import org.tavioribeiro.commitic.domain.model.commit.CommitDomainModel
import org.tavioribeiro.commitic.domain.model.commit.CommitFailure
import org.tavioribeiro.commitic.domain.model.llm.LlmDomainModel
import org.tavioribeiro.commitic.domain.model.project.ProjectDomainModel
import org.tavioribeiro.commitic.domain.repository.CommitRepository
import org.tavioribeiro.commitic.domain.util.RequestResult

class CommitRepositoryImpl(
    private val localDataSource: CommitLocalDataSource
) : CommitRepository {



    override suspend fun saveCommit(commit: CommitDomainModel): RequestResult<Unit, CommitFailure> {
        return try {
            val commitDto = commit.toDto()

            localDataSource.saveCommit(commitDto)

            RequestResult.Success(Unit)
        } catch (e: Exception) {
            println("Erro ao buscar projetos: ${e.message}")
            RequestResult.Failure(CommitFailure.Unexpected(e))
        }
    }


    override suspend fun getCommits(): RequestResult<List<CommitDomainModel>, CommitFailure> {
        return try {
            val commitsDto = localDataSource.getCommits()
            val commitsDomain = commitsDto.map { it.toDomain() }

            RequestResult.Success(commitsDomain)
        } catch (e: Exception) {
            println("Erro ao buscar projetos: ${e.message}")
            RequestResult.Failure(CommitFailure.Unexpected(e))
        }
    }


    override suspend fun deleteCommit(commit: CommitDomainModel): RequestResult<Unit, CommitFailure> {
        return try {
            val commitDto = commit.toDto()

            localDataSource.deleteCommit(commitDto)

            RequestResult.Success(Unit)
        } catch (e: Exception) {
            println("Erro ao buscar projetos: ${e.message}")
            RequestResult.Failure(CommitFailure.Unexpected(e))
        }
    }
}