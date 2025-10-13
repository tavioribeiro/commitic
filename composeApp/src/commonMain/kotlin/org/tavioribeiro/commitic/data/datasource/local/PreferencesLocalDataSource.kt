package org.tavioribeiro.commitic.data.datasource.local


import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

class PreferencesLocalDataSource(
    private val settings: Settings
) {
    companion object {
        private const val KEY_SELECTED_PROJECT_ID = "selected_project_id"
        private const val KEY_SELECTED_LLM_ID = "selected_llm_id"
    }

    fun saveSelectedProjectId(id: String) {
        settings[KEY_SELECTED_PROJECT_ID] = id
    }

    fun getSelectedProjectId(): String? {
        return settings[KEY_SELECTED_PROJECT_ID]
    }

    fun saveSelectedLlmId(id: String) {
        settings[KEY_SELECTED_LLM_ID] = id
    }

    fun getSelectedLlmId(): String? {
        return settings[KEY_SELECTED_LLM_ID]
    }
}
