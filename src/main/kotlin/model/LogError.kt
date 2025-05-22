package es.iesraprog2425.pruebaes.model

import es.iesraprog2425.pruebaes.utils.Time
/**
 * Clase que representa un registro (log) de un error ocurrido.
 *
 * @property msjError Mensaje descriptivo del error.
 * @param fecha Fecha en formato 'dd-MM-yyyy' en que se registró el error.
 * @param hora Hora en formato 'HH:mm:ss' en que se registró el error.
 */
class LogError(
    val msjError: String,
    fecha: String,
    hora: String
) : Log(fecha, hora) {

    companion object {
        /** Nombre simplificado de la clase para mostrar en texto, sin el prefijo "Log". */
        val nombreClase = LogError::class.simpleName?.replace("Log", "")

        /**
         * Crea una instancia de [LogError] con la fecha y hora actual.
         *
         * @param msjError Mensaje descriptivo del error.
         * @return Nueva instancia de [LogError] con fecha y hora actuales.
         */
        fun crear(msjError: String): LogError {
            val (fecha, hora) = Time.obtenerFechayHoraActualF()
            return LogError(msjError, fecha, hora)
        }
    }

    /**
     * Devuelve una representación textual del log, incluyendo la información base y el mensaje de error.
     */
    override fun toString(): String {
        return "${super.toString().replace("Log", nombreClase ?: "Error")} $msjError"
    }

    /**
     * Obtiene la descripción del error.
     *
     * @return Mensaje de error.
     */
    override fun obtenerLog(): String {
        return msjError
    }
}
