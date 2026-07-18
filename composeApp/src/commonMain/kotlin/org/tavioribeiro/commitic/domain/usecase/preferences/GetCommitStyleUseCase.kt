package org.tavioribeiro.commitic.domain.usecase.preferences

import org.tavioribeiro.commitic.domain.repository.PreferencesRepository

class GetCommitStyleUseCase(private val preferencesRepository: PreferencesRepository) {
    operator fun invoke(): String? = preferencesRepository.getCommitStyle()
}
