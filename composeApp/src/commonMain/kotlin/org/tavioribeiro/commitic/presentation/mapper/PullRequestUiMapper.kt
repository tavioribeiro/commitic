package org.tavioribeiro.commitic.presentation.mapper



import org.tavioribeiro.commitic.domain.model.pull_request.PullRequestDomainModel
import org.tavioribeiro.commitic.presentation.model.PullRequestUiModel


fun PullRequestDomainModel.toUiModel(): PullRequestUiModel {
    return PullRequestUiModel(
        id = this.id,
        text = this.text
    )
}


fun PullRequestUiModel.toDomain(): PullRequestDomainModel {
    return PullRequestDomainModel(
        id = this.id,
        text = this.text
    )
}