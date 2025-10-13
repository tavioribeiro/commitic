package org.tavioribeiro.commitic.domain.usecase.preferences

import org.tavioribeiro.commitic.domain.repository.PreferencesRepository

class SaveSelectedLlmUseCase(private val preferencesRepository: PreferencesRepository) {
    operator fun invoke(id: String) = preferencesRepository.saveSelectedLlmId(id)
}