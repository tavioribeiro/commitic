package org.tavioribeiro.commitic.domain.repository


interface PreferencesRepository {
    fun saveSelectedProjectId(id: String)
    fun getSelectedProjectId(): String?
    fun saveSelectedLlmId(id: String)
    fun getSelectedLlmId(): String?
    fun saveSelectedDelayBetweenStepsValue(seconds: Int)
    fun getSelectedDelayBetweenStepsValue(): Int
}