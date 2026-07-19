package org.tavioribeiro.commitic.domain.usecase.preferences

import org.tavioribeiro.commitic.domain.repository.PreferencesRepository

class SavePrStyleUseCase(private val preferencesRepository: PreferencesRepository) {
    operator fun invoke(style: String) = preferencesRepository.savePrStyle(style)
}
