package org.tavioribeiro.commitic.domain.usecase.preferences


import org.tavioribeiro.commitic.domain.repository.PreferencesRepository

class SaveSelectedProjectUseCase(private val preferencesRepository: PreferencesRepository) {
    operator fun invoke(id: String) = preferencesRepository.saveSelectedProjectId(id)
}