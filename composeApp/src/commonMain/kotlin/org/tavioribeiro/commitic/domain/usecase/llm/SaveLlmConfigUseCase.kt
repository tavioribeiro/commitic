package org.tavioribeiro.commitic.domain.usecase.llm

import org.tavioribeiro.commitic.domain.repository.LlmRepository
import org.tavioribeiro.commitic.domain.model.llm.LlmConfig

class SaveLlmConfigUseCase(private val llmRepository: LlmRepository) {
    suspend operator fun invoke(config: LlmConfig) = llmRepository.saveConfig(config)
}