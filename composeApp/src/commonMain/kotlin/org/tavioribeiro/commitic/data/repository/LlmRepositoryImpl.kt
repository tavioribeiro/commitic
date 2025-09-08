package org.tavioribeiro.commitic.data.repository

import org.tavioribeiro.commitic.data.datasource.local.LlmLocalDataSource
import org.tavioribeiro.commitic.domain.model.llm.LlmConfig
import org.tavioribeiro.commitic.domain.model.llm.LlmFailure
import org.tavioribeiro.commitic.domain.repository.LlmRepository
import org.tavioribeiro.commitic.domain.util.RequestResult


class LlmRepositoryImpl(
    private val localDataSource: LlmLocalDataSource
) : LlmRepository {

    override suspend fun saveConfig(config: LlmConfig): RequestResult<Unit, LlmFailure> {
        if (config.name.isBlank()) {
            return RequestResult.Failure(LlmFailure.InvalidName("O nome da configuração não pode ser vazio."))
        }
        when (config) {
            is LlmConfig.OpenAI -> {
                if (config.apiKey.isBlank()) return RequestResult.Failure(LlmFailure.InvalidApiKey("A API Key não pode ser vazia."))
                if (config.model.isBlank()) return RequestResult.Failure(LlmFailure.InvalidModel("O nome do modelo não pode ser vazio."))
            }
            is LlmConfig.Groq -> {
                if (config.apiKey.isBlank()) return RequestResult.Failure(LlmFailure.InvalidApiKey("A API Key não pode ser vazia."))
                if (config.model.isBlank()) return RequestResult.Failure(LlmFailure.InvalidModel("O nome do modelo não pode ser vazio."))
            }
            else -> {}
        }

        return try {
            localDataSource.saveConfig(config)
            RequestResult.Success(Unit)
        } catch (e: Exception) {
            RequestResult.Failure(LlmFailure.Unexpected(e))
        }
    }

    override suspend fun getConfigs(): RequestResult<List<LlmConfig>, LlmFailure> {
        return try {
            val configs = localDataSource.getConfigs()
            RequestResult.Success(configs)
        } catch (e: Exception) {
            RequestResult.Failure(LlmFailure.Unexpected(e))
        }
    }

    override suspend fun deleteConfig(id: Long): RequestResult<Unit, LlmFailure> {
        return try {
            localDataSource.deleteConfig(id)
            RequestResult.Success(Unit)
        } catch (e: Exception) {
            RequestResult.Failure(LlmFailure.Unexpected(e))
        }
    }
}

