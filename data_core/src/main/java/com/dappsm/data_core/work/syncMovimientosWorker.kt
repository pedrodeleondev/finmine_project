package com.dappsm.data_core.work


import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dappsm.data_core.database.AppDatabaseInstance
import com.dappsm.data_core.firebase.FirebaseMovimientoService

class SyncMovimientosWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val dao = AppDatabaseInstance.database.movimientoDao()
        val service = FirebaseMovimientoService()
        val pending = dao.getPending()
        for (m in pending) {
            if (m.pendingAction == "delete") {
                service.borrarMovimiento(m.id)
                dao.deleteRow(m)
            } else {
                service.guardarMovimiento(m.copy(pendingAction = null))
                dao.clearPending(m.id)
            }
        }
        return Result.success()
    }
}
