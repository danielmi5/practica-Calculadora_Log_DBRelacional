package es.iesraprog2425.pruebaes.model

import es.iesraprog2425.pruebaes.utils.Time
class LogError(
    val msjError: String,
    fecha: String,
    hora: String
) : Log(fecha, hora) {

    override fun toString(): String {
        return "${this::class.simpleName?.replace("Log", "")} | $fecha | $hora | --> $msjError"
    }

    companion object {
        fun crear(msjError: String): LogError {
            val (fecha, hora) = Time.obtenerFechayHoraActualF()
            return LogError(msjError, fecha, hora)
        }
    }
}