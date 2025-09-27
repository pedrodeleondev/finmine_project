package com.dappsm.feat_config.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ConfigViewModel : ViewModel() {

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _colorIngreso = MutableStateFlow(Color(0xFFAFC357)) // Default verde
    val colorIngreso: StateFlow<Color> = _colorIngreso.asStateFlow()

    private val _colorEgreso = MutableStateFlow(Color(0xFFD9585A)) // Default rojo
    val colorEgreso: StateFlow<Color> = _colorEgreso.asStateFlow()

    fun setDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled
    }

    fun setColors(ingreso: Color, egreso: Color) {
        _colorIngreso.value = ingreso
        _colorEgreso.value = egreso
    }
}
