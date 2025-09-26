package com.dappsm.data_core.firebase

import com.dappsm.data_core.model.Nota
import com.google.firebase.firestore.FirebaseFirestore
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
}
