package es.iesraprog2425.pruebaes.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Time {

    fun obtenerFechaActual(): String{
        val fechaActual = LocalDateTime.now().format(formateoFecha)
        return fechaActual
    }

    fun obtenerHoraActual(): String{
        val horaActual = LocalDateTime.now().format(formateoHora)
        return horaActual
    }

    private val formateoFecha = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy")
    private val formateoHora = DateTimeFormatter.ofPattern("HH:mm:ss")
}