package org.tavioribeiro.commitic.data.datasource.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.tavioribeiro.commitic.db.LlmSchemaQueries
import org.tavioribeiro.commitic.domain.model.llm.LlmConfig
import org.tavioribeiro.commitic.domain.model.llm.ProviderType

class LlmLocalDataSource(private val db: LlmSchemaQueries) {

    suspend fun saveConfig(config: LlmConfig){
         withContext(Dispatchers.IO) {
            when (config) {
                is LlmConfig.OpenAI -> db.insertConfig(config.id, config.name, config.providerType, config.apiKey, config.baseUrl, config.model, null)
                is LlmConfig.Groq -> db.insertConfig(config.id, config.name, config.providerType, config.apiKey, null, config.model, null)
                is LlmConfig.Gemini -> db.insertConfig(config.id, config.name, config.providerType, config.apiKey, null, config.model, null)
                is LlmConfig.Claude -> db.insertConfig(config.id, config.name, config.providerType, config.apiKey, null, config.model, config.anthropicVersion)
                is LlmConfig.OpenRouter -> db.insertConfig(config.id, config.name, config.providerType, config.apiKey, config.baseUrl, config.model, null)
            }
        }
    }

    suspend fun getConfigs(): List<LlmConfig> {
        return withContext(Dispatchers.IO) {
            db.selectAllConfigs().executeAsList().map { entity ->
                when (entity.provider_type) {
                    ProviderType.OPENAI -> LlmConfig.OpenAI(
                        entity.config_id,
                        entity.name,
                        entity.api_key!!,
                        entity.base_url,
                        entity.model_name!!
                    )

                    ProviderType.GROQ -> LlmConfig.Groq(
                        entity.config_id,
                        entity.name,
                        entity.api_key!!,
                        entity.model_name!!
                    )

                    ProviderType.GEMINI -> LlmConfig.Gemini(
                        entity.config_id,
                        entity.name,
                        entity.api_key!!,
                        entity.model_name!!
                    )

                    ProviderType.CLAUDE -> LlmConfig.Claude(
                        entity.config_id,
                        entity.name,
                        entity.api_key!!,
                        entity.model_name!!,
                        entity.anthropic_version!!
                    )

                    ProviderType.OPENROUTER -> LlmConfig.OpenRouter(
                        entity.config_id,
                        entity.name,
                        entity.api_key!!,
                        entity.base_url,
                        entity.model_name!!
                    )
                }
            }
        }
    }

    suspend fun deleteConfig(id: Long) = withContext(Dispatchers.IO) {
        db.deleteConfigById(id)
    }
}
