package org.tavioribeiro.commitic.data.repository

import org.tavioribeiro.commitic.data.datasource.local.PreferencesLocalDataSource
import org.tavioribeiro.commitic.domain.repository.PreferencesRepository

class PreferencesRepositoryImpl(
    private val localDataSource: PreferencesLocalDataSource
) : PreferencesRepository {
    override fun saveSelectedProjectId(id: String) {
        println("Saving selected Project ID: $id")
        localDataSource.saveSelectedProjectId(id)
    }

    override fun getSelectedProjectId(): String? {
        return localDataSource.getSelectedProjectId()
    }

    override fun saveSelectedLlmId(id: String) {
        println("Saving selected LLM ID: $id")
        localDataSource.saveSelectedLlmId(id)
    }

    override fun getSelectedLlmId(): String? {
        return localDataSource.getSelectedLlmId()
    }
}