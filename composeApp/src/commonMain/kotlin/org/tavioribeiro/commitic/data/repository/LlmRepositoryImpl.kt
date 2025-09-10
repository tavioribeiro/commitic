package org.tavioribeiro.commitic.data.repository

import org.tavioribeiro.commitic.data.datasource.local.LlmLocalDataSource
import org.tavioribeiro.commitic.data.mapper.toDomain
import org.tavioribeiro.commitic.data.mapper.toDto
import org.tavioribeiro.commitic.domain.model.llm.LlmDomainModel
import org.tavioribeiro.commitic.domain.model.llm.LlmFailure
import org.tavioribeiro.commitic.domain.repository.LlmRepository
import org.tavioribeiro.commitic.domain.util.RequestResult

class LlmRepositoryImpl(
    private val localDataSource: LlmLocalDataSource
) : LlmRepository {

    override suspend fun saveLlm(llm: LlmDomainModel): RequestResult<Unit, LlmFailure> {
        return try {
            val llmDto = llm.toDto()

            localDataSource.saveLlm(llmDto)

            RequestResult.Success(Unit)
        } catch (e: Exception) {
            println("Erro ao buscar projetos: ${e.message}")
            RequestResult.Failure(LlmFailure.Unexpected(e))
        }
    }


    override suspend fun getLlms(): RequestResult<List<LlmDomainModel>, LlmFailure> {
        return try {
            val llmsDto = localDataSource.getLlms()
            val llmsDomain = llmsDto.map { it.toDomain() }

            RequestResult.Success(llmsDomain)
        } catch (e: Exception) {
            println("Erro ao buscar projetos: ${e.message}")
            RequestResult.Failure(LlmFailure.Unexpected(e))
        }
    }


    override suspend fun deleteLlm(llm: LlmDomainModel): RequestResult<Unit, LlmFailure> {
        return try {
            val llmDto = llm.toDto()

            localDataSource.deleteLlm(llmDto)

            RequestResult.Success(Unit)
        } catch (e: Exception) {
            println("Erro ao buscar projetos: ${e.message}")
            RequestResult.Failure(LlmFailure.Unexpected(e))
        }
    }
}