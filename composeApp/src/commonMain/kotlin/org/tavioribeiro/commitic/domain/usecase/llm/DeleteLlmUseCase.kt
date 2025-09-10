package org.tavioribeiro.commitic.domain.usecase.llm

import org.tavioribeiro.commitic.domain.model.llm.LlmDomainModel
import org.tavioribeiro.commitic.domain.model.llm.LlmFailure
import org.tavioribeiro.commitic.domain.repository.LlmRepository
import org.tavioribeiro.commitic.domain.util.RequestResult

class DeleteLlmUseCase(
    private val llmRepository: LlmRepository
) {
    suspend operator fun invoke(llm: LlmDomainModel): RequestResult<Unit, LlmFailure> {
        val llmId = llm.id ?: return RequestResult.Failure(
            LlmFailure.NotFound("O LLM não possui um ID e não pode ser deletado.")
        )

        if (llmId <= 0) {
            return RequestResult.Failure(LlmFailure.InvalidId("ID do LLM é inválido."))
        }

        return llmRepository.deleteLlm(llm)
    }
}