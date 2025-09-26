package com.dappsm.data_core.firebase

import com.dappsm.data_core.model.Movimiento
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseMovimientoService {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("movimientos")

    suspend fun guardarMovimiento(movimiento: Movimiento) {
        collection.document(movimiento.id).set(movimiento.toMap()).await()
    }

    suspend fun borrarMovimiento(id: String) {
        collection.document(id).delete().await()
    }

    suspend fun obtenerMovimientos(): List<Movimiento> {
        val snapshot = collection.get().await()
        return snapshot.documents.mapNotNull { it.data?.let { d -> Movimiento.fromMap(d) } }
    }
}
