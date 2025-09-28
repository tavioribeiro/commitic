package org.tavioribeiro.commitic.data.repository

import org.tavioribeiro.commitic.data.datasource.local.PreferencesLocalDataSource
import org.tavioribeiro.commitic.domain.repository.PreferencesRepository

class PreferencesRepositoryImpl(
    private val localDataSource: PreferencesLocalDataSource
) : PreferencesRepository {
    override fun saveSelectedProjectId(id: String) {
        localDataSource.saveSelectedProjectId(id)
    }

    override fun getSelectedProjectId(): String? {
        return localDataSource.getSelectedProjectId()
    }

    override fun saveSelectedLlmId(id: String) {
        localDataSource.saveSelectedLlmId(id)
    }

    override fun getSelectedLlmId(): String? {
        return localDataSource.getSelectedLlmId()
    }
}