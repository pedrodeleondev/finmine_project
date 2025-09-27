package com.dappsm.data_core.repository

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.dappsm.data_core.dao.MovimientoDao
import com.dappsm.data_core.firebase.FirebaseMovimientoService
import com.dappsm.data_core.model.Movimiento
import com.dappsm.data_core.work.SyncMovimientosWorker
import kotlinx.coroutines.flow.Flow

class MovimientoRepository(
    private val dao: MovimientoDao,
    private val firebaseService: FirebaseMovimientoService
) {
    fun getMovimientosByEmail(email: String): Flow<List<Movimiento>> =
        dao.getMovimientosByEmail(email)

    suspend fun getMovimientoById(id: String): Movimiento? = dao.getMovimientoById(id)

    suspend fun insertMovimiento(context: Context, movimiento: Movimiento, hayInternet: Boolean) {
        val m = movimiento.copy(pendingAction = if (hayInternet) null else "insert")
        dao.insertMovimiento(m)
        if (hayInternet) firebaseService.guardarMovimiento(m)
        else enqueueOneTime(context)
    }

    suspend fun updateMovimiento(context: Context, movimiento: Movimiento, hayInternet: Boolean) {
        val m = movimiento.copy(pendingAction = if (hayInternet) null else "update")
        dao.updateMovimiento(m)
        if (hayInternet) firebaseService.guardarMovimiento(m)
        else enqueueOneTime(context)
    }

    suspend fun deleteMovimiento(context: Context, movimiento: Movimiento, hayInternet: Boolean) {
        if (hayInternet) {
            firebaseService.borrarMovimiento(movimiento.id)
            dao.deleteRow(movimiento)
        } else {
            dao.markDeleted(movimiento.id)
            enqueueOneTime(context)
        }
    }

    suspend fun getPending(): List<Movimiento> = dao.getPending()

    suspend fun clearPending(id: String) = dao.clearPending(id)

    private fun enqueueOneTime(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()
        val req: WorkRequest = OneTimeWorkRequestBuilder<SyncMovimientosWorker>()
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(context).enqueue(req)
    }
}
