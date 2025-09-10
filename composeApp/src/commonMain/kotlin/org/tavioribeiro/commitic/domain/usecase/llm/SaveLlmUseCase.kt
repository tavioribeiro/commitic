package org.tavioribeiro.commitic.domain.usecase.llm

import org.tavioribeiro.commitic.domain.model.llm.LlmDomainModel
import org.tavioribeiro.commitic.domain.model.llm.LlmFailure
import org.tavioribeiro.commitic.domain.repository.LlmRepository
import org.tavioribeiro.commitic.domain.util.RequestResult

class SaveLlmUseCase(
    private val llmRepository: LlmRepository
) {
    suspend operator fun invoke(llm: LlmDomainModel): RequestResult<Unit, LlmFailure> {
        if (llm.company.isBlank()) {
            return RequestResult.Failure(LlmFailure.InvalidCompany("O nome da empresa não pode ser vazio."))
        }

        if (llm.model.isBlank()) {
            return RequestResult.Failure(LlmFailure.InvalidModel("O nome do modelo не pode ser vazio."))
        }

        if (llm.apiToken.isBlank()) {
            return RequestResult.Failure(LlmFailure.InvalidApiToken("O token da API не pode ser vazio."))
        }

        return llmRepository.saveLlm(llm)
    }
}