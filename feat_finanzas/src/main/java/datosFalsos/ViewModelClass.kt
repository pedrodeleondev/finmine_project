package datosFalsos

class ViewModelClass {
    data class Movimiento(
        val id: Int,
        val fechayhora: String,
        val tipo: String,
        val monto: Int,
        val motivo: String,
        val metodopago: String
    )

    object movimientosRepo{
        private var movimientos: MutableList<Movimiento> = mutableListOf(
            Movimiento(1, "27/09/2025 13:00:00", "Egreso", 240, "Compra de regalo","Efectivo")

        )
        fun obtenerMovimientos(): List<Movimiento> {
            val copia = mutableListOf<Movimiento>()
            for (i in 0 until movimientos.size) {
                val e = movimientos[i]
                copia.add(Movimiento(e.id,e.fechayhora, e.tipo, e.monto, e.motivo, e.metodopago))
            }
            return copia.toList()
        }

    }
}