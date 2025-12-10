package org.tavioribeiro.commitic.domain.usecase.preferences

import org.tavioribeiro.commitic.domain.repository.PreferencesRepository

class GetSelectedDelayBetweenStepsUseCase(private val preferencesRepository: PreferencesRepository) {
    operator fun invoke(): Int = preferencesRepository.getSelectedDelayBetweenStepsValue()
}