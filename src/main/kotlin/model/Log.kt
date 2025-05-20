package es.iesraprog2425.pruebaes.model

import es.iesraprog2425.pruebaes.utils.Time

abstract class Log {
    val fecha: String
    val hora: String

    constructor() {
        val fechaYHora = Time.obtenerFechayHoraActualF()
        this.fecha = fechaYHora.first
        this.hora = fechaYHora.second
    }

    constructor(fecha: String, hora: String) {
        this.fecha = fecha
        this.hora = hora
    }
}