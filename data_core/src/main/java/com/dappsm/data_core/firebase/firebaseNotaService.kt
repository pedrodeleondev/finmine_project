package com.dappsm.data_core.firebase

import com.dappsm.data_core.model.Nota
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseNotaService {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("notas")

    suspend fun guardarNota(nota: Nota) {
        collection.document(nota.id).set(nota.toMap()).await()
    }

    suspend fun borrarNota(id: String) {
        collection.document(id).delete().await()
    }

    suspend fun obtenerNotas(): List<Nota> {
        val snapshot = collection.get().await()
        return snapshot.documents.mapNotNull { it.data?.let { d -> Nota.fromMap(d) } }
    }

    fun getNotasByEmail(email: String): Flow<List<Nota>> = callbackFlow {
        val listener: ListenerRegistration = collection
            .whereEqualTo("usuarioEmail", email)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val list = snapshot.documents.mapNotNull { it.data?.let { d -> Nota.fromMap(d) } }
                    trySend(list)
                }
            }
        awaitClose { listener.remove() }
    }
}
