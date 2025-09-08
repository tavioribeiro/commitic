package org.tavioribeiro.commitic.domain.usecase.llm

import org.tavioribeiro.commitic.domain.repository.LlmRepository

class GetLlmConfigsUseCase(private val llmRepository: LlmRepository) {
    suspend operator fun invoke() = llmRepository.getConfigs()
}