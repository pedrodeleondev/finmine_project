package com.dappsm.feat_finanzas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dappsm.data_core.database.AppDatabaseInstance
import com.dappsm.data_core.firebase.FirebaseMovimientoService
import com.dappsm.data_core.model.Movimiento
import com.dappsm.data_core.repository.MovimientoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MovimientoUiViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = MovimientoRepository(AppDatabaseInstance.database.movimientoDao(), FirebaseMovimientoService())
    val movimientos = repo.getAllMovimientos().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun agregar(usuarioId: Int, tipo: String, cantidadStr: String, metodoPago: String, motivo: String?) {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val m = Movimiento(
            usuarioId = usuarioId,
            tipo = tipo,
            cantidad = cantidadStr,
            fecha = now,
            mes = now.monthValue,
            hora = now.format(formatter),
            metodoPago = metodoPago,
            motivo = motivo
        )
        viewModelScope.launch {
            repo.insertMovimiento(getApplication(), m, false)
        }
    }

    fun eliminar(mov: Movimiento) {
        viewModelScope.launch {
            repo.deleteMovimiento(getApplication(), mov, false)
        }
    }
}
