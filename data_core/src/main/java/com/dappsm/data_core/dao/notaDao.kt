package com.dappsm.data_core.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dappsm.data_core.model.Nota
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDao {
    @Query("SELECT * FROM notas WHERE isDeleted = 0 ORDER BY fecha DESC")
    fun getAllNotas(): Flow<List<Nota>>

    @Query("SELECT * FROM notas WHERE id = :id LIMIT 1")
    suspend fun getNotaById(id: String): Nota?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNota(n: Nota)

    @Update
    suspend fun updateNota(n: Nota)

    @Delete
    suspend fun deleteRow(n: Nota)

    @Query("UPDATE notas SET isDeleted = 1, pendingAction = 'delete' WHERE id = :id")
    suspend fun markDeleted(id: String)

    @Query("SELECT * FROM notas WHERE pendingAction IS NOT NULL")
    suspend fun getPending(): List<Nota>

    @Query("UPDATE notas SET pendingAction = NULL WHERE id = :id")
    suspend fun clearPending(id: String)
}
