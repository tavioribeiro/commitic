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

    override fun saveSelectedDelayBetweenStepsValue(seconds: Int) {
        localDataSource.saveSelectedDelayBetweenStepsValue(seconds)
    }

    override fun getSelectedDelayBetweenStepsValue(): Int {
        return localDataSource.getSelectedDelayBetweenStepsValue()
    }

    override fun saveCommitLanguage(language: String) {
        localDataSource.saveCommitLanguage(language)
    }

    override fun getCommitLanguage(): String? {
        return localDataSource.getCommitLanguage()
    }

    override fun saveCommitStyle(style: String) {
        localDataSource.saveCommitStyle(style)
    }

    override fun getCommitStyle(): String? {
        return localDataSource.getCommitStyle()
    }

    override fun savePrLanguage(language: String) {
        localDataSource.savePrLanguage(language)
    }

    override fun getPrLanguage(): String? {
        return localDataSource.getPrLanguage()
    }

    override fun savePrStyle(style: String) {
        localDataSource.savePrStyle(style)
    }

    override fun getPrStyle(): String? {
        return localDataSource.getPrStyle()
    }
}