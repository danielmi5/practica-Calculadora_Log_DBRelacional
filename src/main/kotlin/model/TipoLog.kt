package es.iesraprog2425.pruebaes.model

enum class TipoLog  {
    OPERACION, ERROR;

    companion object {
        fun obtenerTipo(tipo: String): TipoLog {
            for (tipoLog in entries) {
                if (tipoLog.name == tipo) return tipoLog
            }
            return ERROR
        }
    }
}