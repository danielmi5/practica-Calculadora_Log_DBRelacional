package es.iesraprog2425.pruebaes.model

import es.iesraprog2425.pruebaes.utils.Time
class LogError(
    val msjError: String,
    fecha: String,
    hora: String
) : Log(fecha, hora) {

    override fun toString(): String {
        return "${super.toString().replace("Log", nombreClase?: "Error")} $msjError"
    }

    override fun obtenerLog(): String {
        return msjError
    }

    companion object {
        val nombreClase = LogError::class.simpleName?.replace("Log", "")
        fun crear(msjError: String): LogError {
            val (fecha, hora) = Time.obtenerFechayHoraActualF()
            return LogError(msjError, fecha, hora)
        }
    }
}