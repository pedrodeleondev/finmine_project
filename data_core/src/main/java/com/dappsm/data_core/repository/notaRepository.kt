package com.dappsm.data_core.repository

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.dappsm.data_core.dao.NotaDao
import com.dappsm.data_core.firebase.FirebaseNotaService
import com.dappsm.data_core.model.Nota
import com.dappsm.data_core.work.SyncNotasWorker
import kotlinx.coroutines.flow.Flow

class NotaRepository(
    private val dao: NotaDao,
    private val firebaseService: FirebaseNotaService
) {
    fun getNotasByEmail(email: String): Flow<List<Nota>> = dao.getNotasByEmail(email)

    suspend fun getNotaById(id: String): Nota? = dao.getNotaById(id)

    suspend fun insertNota(context: Context, nota: Nota, hayInternet: Boolean) {
        val n = nota.copy(pendingAction = if (hayInternet) null else "insert")
        dao.insertNota(n)
        if (hayInternet) firebaseService.guardarNota(n)
        else enqueueOneTime(context)
    }

    suspend fun updateNota(context: Context, nota: Nota, hayInternet: Boolean) {
        val n = nota.copy(pendingAction = if (hayInternet) null else "update")
        dao.updateNota(n)
        if (hayInternet) firebaseService.guardarNota(n)
        else enqueueOneTime(context)
    }

    suspend fun deleteNota(context: Context, nota: Nota, hayInternet: Boolean) {
        if (hayInternet) {
            firebaseService.borrarNota(nota.id)
            dao.deleteRow(nota)
        } else {
            dao.markDeleted(nota.id)
            enqueueOneTime(context)
        }
    }

    suspend fun getPending(): List<Nota> = dao.getPending()

    suspend fun clearPending(id: String) = dao.clearPending(id)

    private fun enqueueOneTime(context: Context) {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).setRequiresBatteryNotLow(true).build()
        val req: WorkRequest = OneTimeWorkRequestBuilder<SyncNotasWorker>().setConstraints(constraints).build()
        WorkManager.getInstance(context).enqueue(req)
    }
}
