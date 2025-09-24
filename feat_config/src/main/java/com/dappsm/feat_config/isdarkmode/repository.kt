package com.dappsm.feat_config.isdarkmode

class SettingsRepository(private val dao: SettingsDao) {

    suspend fun isDarkMode(): Boolean {
        return dao.getSettings()?.isDarkMode ?: false
    }

    suspend fun setDarkMode(enabled: Boolean) {
        dao.saveSettings(SettingsEntity(id = 1, isDarkMode = enabled))
    }
}