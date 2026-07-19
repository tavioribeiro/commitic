package org.tavioribeiro.commitic.domain.usecase.preferences

import org.tavioribeiro.commitic.domain.repository.PreferencesRepository

class SavePrLanguageUseCase(private val preferencesRepository: PreferencesRepository) {
    operator fun invoke(language: String) = preferencesRepository.savePrLanguage(language)
}
