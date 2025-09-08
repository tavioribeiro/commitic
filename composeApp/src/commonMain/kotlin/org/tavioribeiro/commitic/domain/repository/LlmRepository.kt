package org.tavioribeiro.commitic.domain.repository

import org.tavioribeiro.commitic.domain.model.llm.LlmConfig
import org.tavioribeiro.commitic.domain.model.llm.LlmFailure
import org.tavioribeiro.commitic.domain.util.RequestResult

interface LlmRepository {
    suspend fun saveConfig(config: LlmConfig): RequestResult<Unit, LlmFailure>
    suspend fun getConfigs(): RequestResult<List<LlmConfig>, LlmFailure>
    suspend fun deleteConfig(id: Long): RequestResult<Unit, LlmFailure>
}