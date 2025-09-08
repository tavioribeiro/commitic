package org.tavioribeiro.commitic.domain.usecase.llm

import org.tavioribeiro.commitic.domain.repository.LlmRepository

class DeleteLlmConfigUseCase(private val llmRepository: LlmRepository) {
    suspend operator fun invoke(id: Long) = llmRepository.deleteConfig(id)
}