package com.dappsm.feat_config.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dappsm.feat_config.isdarkmode.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ConfigViewModel(private val repo: SettingsRepository) : ViewModel() {

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _colorIngreso = MutableStateFlow(Color(0xFFAFC357))
    val colorIngreso: StateFlow<Color> = _colorIngreso.asStateFlow()

    private val _colorEgreso = MutableStateFlow(Color(0xFFD9585A))
    val colorEgreso: StateFlow<Color> = _colorEgreso.asStateFlow()

    init {
        viewModelScope.launch {
            repo.loadSettings().collect { settingsEntity ->
                settingsEntity?.let {
                    _isDarkMode.value = it.isDarkMode
                    _colorIngreso.value = Color(it.ingresoColor)
                    _colorEgreso.value = Color(it.egresoColor)
                }
            }
        }
    }

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            repo.setDarkMode(enabled)
        }
    }

    fun setColors(ingreso: Color, egreso: Color) {
        viewModelScope.launch {
            repo.setColors(ingreso.toArgb().toLong(), egreso.toArgb().toLong())
        }
    }
}
