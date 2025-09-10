package org.tavioribeiro.commitic.domain.usecase.llm

import org.tavioribeiro.commitic.domain.model.llm.LlmDomainModel
import org.tavioribeiro.commitic.domain.model.llm.LlmFailure
import org.tavioribeiro.commitic.domain.repository.LlmRepository
import org.tavioribeiro.commitic.domain.util.RequestResult


class GetLlmsUseCase(
    private val llmRepository: LlmRepository
) {
    suspend operator fun invoke(): RequestResult<List<LlmDomainModel>, LlmFailure> {
        return llmRepository.getLlms()
    }
}