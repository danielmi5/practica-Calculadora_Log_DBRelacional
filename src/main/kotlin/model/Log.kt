package es.iesraprog2425.pruebaes.model

import es.iesraprog2425.pruebaes.utils.Time

abstract class Log() {
    val fecha: String = Time.obtenerFechaActual()
    val hora: String = Time.obtenerHoraActual()
}