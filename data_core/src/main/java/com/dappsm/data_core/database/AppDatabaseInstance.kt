package com.dappsm.data_core.database

import android.content.Context
import androidx.room.Room

object AppDatabaseInstance {
    lateinit var database: AppDatabase
        private set

    fun init(context: Context) {
        if (!::database.isInitialized) {
            database = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "finmine-db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
