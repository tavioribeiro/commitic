package org.tavioribeiro.commitic.domain.usecase.preferences

import org.tavioribeiro.commitic.domain.repository.PreferencesRepository

class SaveSelectedDelayBetweenStepsUseCase(private val preferencesRepository: PreferencesRepository) {
    operator fun invoke(seconds: Int) = preferencesRepository.saveSelectedDelayBetweenStepsValue(seconds)
}