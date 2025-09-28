package com.dappsm.feat_config.isdarkmode

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class SettingsRepository(private val dao: SettingsDao) {

    fun loadSettings(): Flow<SettingsEntity?> {
        return dao.getSettings()
    }

    suspend fun setDarkMode(enabled: Boolean) {
        val current = dao.getSettings().firstOrNull()
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
        val current = dao.getSettings().firstOrNull()
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
