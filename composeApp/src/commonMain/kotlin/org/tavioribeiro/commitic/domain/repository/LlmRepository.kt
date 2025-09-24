package org.tavioribeiro.commitic.domain.repository


import org.tavioribeiro.commitic.domain.model.commit.CommitFailure
import org.tavioribeiro.commitic.domain.model.llm.LlmDomainModel
import org.tavioribeiro.commitic.domain.model.llm.LlmFailure
import org.tavioribeiro.commitic.domain.model.project.ProjectDomainModel
import org.tavioribeiro.commitic.domain.util.RequestResult

interface LlmRepository {
    suspend fun textToLlm(message:String, llm: LlmDomainModel): RequestResult<String, CommitFailure>
    suspend fun saveLlm(llm: LlmDomainModel): RequestResult<Unit, LlmFailure>
    suspend fun getLlms(): RequestResult<List<LlmDomainModel>, LlmFailure>
    suspend fun deleteLlm(llm: LlmDomainModel): RequestResult<Unit, LlmFailure>
}