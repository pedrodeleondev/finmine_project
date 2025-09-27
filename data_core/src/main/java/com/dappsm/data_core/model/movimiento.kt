package com.dappsm.data_core.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(tableName = "movimientos")
data class Movimiento(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val usuarioId: Int,
    val tipo: String,
    val cantidad: String,
    val fecha: LocalDateTime,
    val mes: Int,
    val hora: String,
    val metodoPago: String,
    val motivo: String?,
    val isDeleted: Boolean = false,
    val pendingAction: String? = null
) {
    fun toMap(): Map<String, Any?> {
        val f = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        return mapOf(
            "id" to id,
            "usuarioId" to usuarioId,
            "tipo" to tipo,
            "cantidad" to cantidad,
            "fecha" to fecha.format(f),
            "mes" to mes,
            "hora" to hora,
            "metodoPago" to metodoPago,
            "motivo" to motivo,
            "isDeleted" to isDeleted
        )
    }
    companion object {
        fun fromMap(map: Map<String, Any?>): Movimiento {
            val f = DateTimeFormatter.ISO_LOCAL_DATE_TIME
            return Movimiento(
                id = map["id"] as String,
                usuarioId = (map["usuarioId"] as Number).toInt(),
                tipo = map["tipo"] as String,
                cantidad = map["cantidad"].toString(),
                fecha = LocalDateTime.parse(map["fecha"] as String, f),
                mes = (map["mes"] as Number).toInt(),
                hora = map["hora"] as String,
                metodoPago = map["metodoPago"] as String,
                motivo = map["motivo"] as String?,
                isDeleted = (map["isDeleted"] as? Boolean) ?: false,
                pendingAction = null
            )
        }
    }
}
