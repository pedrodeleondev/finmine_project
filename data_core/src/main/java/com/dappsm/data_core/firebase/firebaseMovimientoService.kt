package com.dappsm.data_core.firebase

import com.dappsm.data_core.model.Movimiento
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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

    fun getMovimientosByEmail(email: String): Flow<List<Movimiento>> = callbackFlow {
        val listener: ListenerRegistration = collection
            .whereEqualTo("usuarioEmail", email)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val list = snapshot.documents.mapNotNull { it.data?.let { d -> Movimiento.fromMap(d) } }
                    trySend(list)
                }
            }
        awaitClose { listener.remove() }
    }
}
