package com.dappsm.feat_notas.datosFalsos

import kotlin.collections.get

class ViewModelClass {
    data class Nota(
        val id: Int,
        val fechayhoraNota:String,
        val comentario:String
    )

    object movimientosRepo{

        private var notas: MutableList<Nota> = mutableListOf(
            Nota(1,"27/09/2025 13:00:00","hola como estas"),
            Nota(2,"27/09/1025 13:80:00","hola como estas"),
            Nota(3,"27/09/3025 13:00:00","hola como estas"),
            Nota(4,"27/09/4025 13:00:00","hola como estas")
        )
        fun obtenerNotas(): List<Nota> {
            val copia = mutableListOf<Nota>()
            for (i in 0 until notas.size) {
                val e = notas[i]
                copia.add(Nota(e.id,e.fechayhoraNota, e.comentario))
            }
            return copia.toList()
        }
    }
}