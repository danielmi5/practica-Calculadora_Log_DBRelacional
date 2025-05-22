package es.iesraprog2425.pruebaes.model

import es.iesraprog2425.pruebaes.utils.Time

/**
 * Clase base abstracta para representar un log con fecha y hora.
 *
 * @property fecha Fecha del log en formato 'dd-MM-yyyy'.
 * @property hora Hora del log en formato 'HH:mm:ss'.
 */
abstract class Log {

    val fecha: String
    val hora: String

    /**
     * Constructor que inicializa el log con la fecha y hora actuales.
     */
    constructor() {
        val (fechaActual, horaActual) = Time.obtenerFechayHoraActualF()
        this.fecha = fechaActual
        this.hora = horaActual
    }

    /**
     * Constructor que inicializa el log con una fecha y hora específicas.
     *
     * @param fecha Fecha en formato 'dd-MM-yyyy'.
     * @param hora Hora en formato 'HH:mm:ss'.
     */
    constructor(fecha: String, hora: String) {
        this.fecha = fecha
        this.hora = hora
    }

    /**
     * Representación textual del log con fecha y hora formateadas.
     *
     * @return Cadena con el formato: "Log - fecha_formateada - hora ===>"
     */
    override fun toString(): String {
        return "Log - ${Time.formatearFecha(fecha)} - $hora ===>"
    }

    /**
     * Método abstracto para obtener la descripción específica del log.
     *
     * @return Texto descriptivo del log.
     */
    abstract fun obtenerLog(): String
}
