package org.tavioribeiro.commitic.presentation.mapper

import org.tavioribeiro.commitic.domain.model.llm.LlmAvailableApis
import org.tavioribeiro.commitic.domain.model.llm.LlmDomainModel
import org.tavioribeiro.commitic.presentation.model.LlmUiModel
import org.tavioribeiro.commitic.presentation.model.SelectOptionModel


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


fun LlmAvailableApis.toSelectOption(): SelectOptionModel {
    return SelectOptionModel(
        label = this.displayName,
        value = this.value
    )
}