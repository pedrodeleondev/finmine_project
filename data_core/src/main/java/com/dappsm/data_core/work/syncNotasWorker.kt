package com.dappsm.data_core.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dappsm.data_core.database.AppDatabaseInstance
import com.dappsm.data_core.firebase.FirebaseNotaService

class SyncNotasWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val dao = AppDatabaseInstance.database.notaDao()
        val service = FirebaseNotaService()
        val pending = dao.getPending()
        for (n in pending) {
            if (n.pendingAction == "delete") {
                service.borrarNota(n.id)
                dao.deleteRow(n)
            } else {
                service.guardarNota(n.copy(pendingAction = null))
                dao.clearPending(n.id)
            }
        }
        return Result.success()
    }
}
