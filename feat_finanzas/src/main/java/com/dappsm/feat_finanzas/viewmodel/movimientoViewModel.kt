package com.dappsm.feat_finanzas.viewmodel

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dappsm.data_core.database.AppDatabaseInstance
import com.dappsm.data_core.firebase.FirebaseMovimientoService
import com.dappsm.data_core.model.Movimiento
import com.dappsm.data_core.repository.MovimientoRepository
import com.dappsm.feat_config.isdarkmode.AppDatabase
import com.dappsm.feat_config.isdarkmode.SettingsRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MovimientoUiViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = MovimientoRepository(
        AppDatabaseInstance.database.movimientoDao(),
        FirebaseMovimientoService()
    )

    private val settingsRepo: SettingsRepository =
        SettingsRepository(AppDatabase.getInstance(app).settingsDao())

    private val emailUsuario: String =
        FirebaseAuth.getInstance().currentUser?.email ?: "desconocido"

    val movimientos = repo.getMovimientosByEmail(emailUsuario)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _ingresoColor = MutableStateFlow(Color(0xFFAFC357))
    val ingresoColor: StateFlow<Color> = _ingresoColor

    private val _egresoColor = MutableStateFlow(Color(0xFFD9585A))
    val egresoColor: StateFlow<Color> = _egresoColor

    init {
        viewModelScope.launch {
            settingsRepo.loadSettings().collect { settingsEntity ->
                if (settingsEntity == null) {
                    val defaultIngresoColorValue = 0xFFAFC357L
                    val defaultEgresoColorValue = 0xFFD9585AL
                    _ingresoColor.value = Color(defaultIngresoColorValue)
                    _egresoColor.value = Color(defaultEgresoColorValue)
                    settingsRepo.setColors(defaultIngresoColorValue, defaultEgresoColorValue)
                } else {
                    _ingresoColor.value = Color(settingsEntity.ingresoColor)
                    _egresoColor.value = Color(settingsEntity.egresoColor)
                }
            }
        }
    }

    fun agregar(
        usuarioId: Int,
        tipo: String,
        cantidadStr: String,
        metodoPago: String,
        motivo: String?
    ) {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

        val m = Movimiento(
            usuarioId = usuarioId,
            usuarioEmail = emailUsuario,
            tipo = tipo,
            cantidad = cantidadStr,
            fecha = now,
            mes = now.monthValue,
            hora = now.format(formatter),
            metodoPago = metodoPago,
            motivo = motivo
        )

        viewModelScope.launch {
            repo.insertMovimiento(getApplication(), m, hayInternet = true)
        }
    }

    fun actualizar(
        movimiento: Movimiento,
        tipo: String,
        cantidadStr: String,
        metodoPago: String,
        motivo: String?
    ) {
        val actualizado = movimiento.copy(
            tipo = tipo,
            cantidad = cantidadStr,
            metodoPago = metodoPago,
            motivo = motivo
        )
        viewModelScope.launch {
            repo.updateMovimiento(getApplication(), actualizado, hayInternet = true)
        }
    }

    fun eliminar(mov: Movimiento) {
        viewModelScope.launch {
            repo.deleteMovimiento(getApplication(), mov, hayInternet = true)
        }
    }
}
