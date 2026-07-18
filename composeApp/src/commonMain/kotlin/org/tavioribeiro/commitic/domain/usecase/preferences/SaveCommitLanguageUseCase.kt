package org.tavioribeiro.commitic.domain.usecase.preferences

import org.tavioribeiro.commitic.domain.repository.PreferencesRepository

class SaveCommitLanguageUseCase(private val preferencesRepository: PreferencesRepository) {
    operator fun invoke(language: String) = preferencesRepository.saveCommitLanguage(language)
}
