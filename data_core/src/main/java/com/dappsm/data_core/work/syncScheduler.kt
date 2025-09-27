package com.dappsm.data_core.work


import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object SyncScheduler {
    fun schedulePeriodic(context: Context) {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).setRequiresBatteryNotLow(true).build()
        val movReq = PeriodicWorkRequestBuilder<SyncMovimientosWorker>(12, TimeUnit.HOURS).setConstraints(constraints).build()
        val notaReq = PeriodicWorkRequestBuilder<SyncNotasWorker>(12, TimeUnit.HOURS).setConstraints(constraints).build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork("sync_movimientos", ExistingPeriodicWorkPolicy.UPDATE, movReq)
        WorkManager.getInstance(context).enqueueUniquePeriodicWork("sync_notas", ExistingPeriodicWorkPolicy.UPDATE, notaReq)
    }
}
