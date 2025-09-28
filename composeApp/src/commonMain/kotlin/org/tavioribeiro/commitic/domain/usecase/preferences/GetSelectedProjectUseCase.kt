package org.tavioribeiro.commitic.domain.usecase.preferences

import org.tavioribeiro.commitic.domain.repository.PreferencesRepository

class GetSelectedProjectUseCase(private val preferencesRepository: PreferencesRepository) {
    operator fun invoke(): String? = preferencesRepository.getSelectedProjectId()
}