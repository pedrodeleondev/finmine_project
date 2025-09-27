package com.dappsm.feat_notas.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dappsm.data_core.database.AppDatabaseInstance
import com.dappsm.data_core.firebase.FirebaseNotaService
import com.dappsm.data_core.model.Nota
import com.dappsm.data_core.repository.NotaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class NotaUiViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = NotaRepository(AppDatabaseInstance.database.notaDao(), FirebaseNotaService())
    val notas = repo.getAllNotas().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun agregar(usuarioId: Int, contenido: String) {
        val n = Nota(usuarioId = usuarioId, contenido = contenido, fecha = LocalDateTime.now())
        viewModelScope.launch {
            repo.insertNota(getApplication(), n, false)
        }
    }

    fun eliminar(nota: Nota) {
        viewModelScope.launch {
            repo.deleteNota(getApplication(), nota, false)
        }
    }
}
