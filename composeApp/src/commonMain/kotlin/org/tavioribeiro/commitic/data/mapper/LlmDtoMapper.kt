package org.tavioribeiro.commitic.data.mapper

import org.tavioribeiro.commitic.data.model.LlmDTOModel
import org.tavioribeiro.commitic.domain.model.llm.LlmDomainModel


fun LlmDomainModel.toDto(): LlmDTOModel {
    return LlmDTOModel(
        id = checkNotNull(this.id) { "ID do LlmDomainModel não pode ser nulo para converter para DTO" },
        company = this.company,
        model = this.model,
        api_token = this.apiToken
    )
}


fun LlmDTOModel.toDomain(): LlmDomainModel {
    return LlmDomainModel(
        id = this.id,
        company = this.company,
        model = this.model,
        apiToken = this.api_token
    )
}