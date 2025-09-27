package com.dappsm.data_core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dappsm.data_core.dao.MovimientoDao
import com.dappsm.data_core.dao.NotaDao
import com.dappsm.data_core.model.Movimiento
import com.dappsm.data_core.model.Nota

@Database(
    entities = [Movimiento::class, Nota::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movimientoDao(): MovimientoDao
    abstract fun notaDao(): NotaDao
}
