package org.tavioribeiro.commitic.domain.usecase.preferences

import org.tavioribeiro.commitic.domain.repository.PreferencesRepository

class GetSelectedLlmUseCase(private val preferencesRepository: PreferencesRepository) {
    operator fun invoke(): String? = preferencesRepository.getSelectedLlmId()
}