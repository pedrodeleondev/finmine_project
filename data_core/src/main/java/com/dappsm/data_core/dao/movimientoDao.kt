package com.dappsm.data_core.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dappsm.data_core.model.Movimiento
import kotlinx.coroutines.flow.Flow

@Dao
interface MovimientoDao {
    @Query("SELECT * FROM movimientos WHERE isDeleted = 0 AND usuarioEmail = :email ORDER BY fecha DESC")
    fun getMovimientosByEmail(email: String): Flow<List<Movimiento>>

    @Query("SELECT * FROM movimientos WHERE id = :id LIMIT 1")
    suspend fun getMovimientoById(id: String): Movimiento?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovimiento(mov: Movimiento)

    @Update
    suspend fun updateMovimiento(mov: Movimiento)

    @Delete
    suspend fun deleteRow(mov: Movimiento)

    @Query("UPDATE movimientos SET isDeleted = 1, pendingAction = 'delete' WHERE id = :id")
    suspend fun markDeleted(id: String)

    @Query("SELECT * FROM movimientos WHERE pendingAction IS NOT NULL")
    suspend fun getPending(): List<Movimiento>

    @Query("UPDATE movimientos SET pendingAction = NULL WHERE id = :id")
    suspend fun clearPending(id: String)
}
