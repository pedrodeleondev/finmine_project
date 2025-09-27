package com.dappsm.feat_config.isdarkmode

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey val id: Int = 1,
    val isDarkMode: Boolean = false,
    val ingresoColor: Long = 0xFFAFC357, // verde por defecto
    val egresoColor: Long = 0xFFD9585A   // rojo por defecto
)
