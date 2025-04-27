package es.iesraprog2425.pruebaes.model

data class Log(val fecha: String, val hora: String,val tipoLog: TipoLog, val registro: String){
    override fun toString(): String {
        return "$fecha | $hora | $tipoLog | $registro"
    }

    companion object{
        fun obtenerPropiedades(): List<String>{
            return listOf("Fecha", "Hora", "Tipo", "Registro")
        }
    }
}
