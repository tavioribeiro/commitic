package org.tavioribeiro.commitic.presentation.mapper

import org.tavioribeiro.commitic.domain.model.llm.LlmDomainModel
import org.tavioribeiro.commitic.presentation.model.LlmUiModel


fun LlmUiModel.toDomain(): LlmDomainModel {
    return LlmDomainModel(
        id = this.id,
        company = this.company,
        model = this.model,
        apiToken = this.apiToken
    )
}

fun LlmDomainModel.toUiModel(): LlmUiModel {
    return LlmUiModel(
        id = this.id,
        company = this.company,
        model = this.model,
        apiToken = this.apiToken
    )
}