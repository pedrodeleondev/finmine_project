package com.dappsm.data_core.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(tableName = "notas")
data class Nota(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val usuarioId: Int,
    val contenido: String,
    val fecha: LocalDateTime,
    val isDeleted: Boolean = false,
    val pendingAction: String? = null
) {
    fun toMap(): Map<String, Any?> {
        val f = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        return mapOf(
            "id" to id,
            "usuarioId" to usuarioId,
            "contenido" to contenido,
            "fecha" to fecha.format(f),
            "isDeleted" to isDeleted
        )
    }
    companion object {
        fun fromMap(map: Map<String, Any?>): Nota {
            val f = DateTimeFormatter.ISO_LOCAL_DATE_TIME
            return Nota(
                id = map["id"] as String,
                usuarioId = (map["usuarioId"] as Number).toInt(),
                contenido = map["contenido"] as String,
                fecha = LocalDateTime.parse(map["fecha"] as String, f),
                isDeleted = (map["isDeleted"] as? Boolean) ?: false,
                pendingAction = null
            )
        }
    }
}
