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
        private const val KEY_SELECTED_DELAY_BETWEEN_STEPS = "selected_delay_between_steps"
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

    fun saveSelectedDelayBetweenStepsValue(seconds: Int) {
        println("00000000 $seconds")
        settings[KEY_SELECTED_DELAY_BETWEEN_STEPS] = seconds
    }

    fun getSelectedDelayBetweenStepsValue(): Int {
        return settings[KEY_SELECTED_DELAY_BETWEEN_STEPS] ?: 0
    }
}
