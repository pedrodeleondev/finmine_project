package com.dappsm.feat_config.isdarkmode

class SettingsRepository(private val dao: SettingsDao) {

    suspend fun loadSettings(): SettingsEntity? {
        return dao.getSettings()
    }

    suspend fun setDarkMode(enabled: Boolean) {
        val current = dao.getSettings()
        dao.saveSettings(
            SettingsEntity(
                id = 1,
                isDarkMode = enabled,
                ingresoColor = current?.ingresoColor ?: 0xFFAFC357,
                egresoColor = current?.egresoColor ?: 0xFFD9585A
            )
        )
    }

    suspend fun setColors(ingreso: Long, egreso: Long) {
        val current = dao.getSettings()
        dao.saveSettings(
            SettingsEntity(
                id = 1,
                isDarkMode = current?.isDarkMode ?: false,
                ingresoColor = ingreso,
                egresoColor = egreso
            )
        )
    }
}
