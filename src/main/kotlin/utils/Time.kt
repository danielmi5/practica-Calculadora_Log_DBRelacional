package es.iesraprog2425.pruebaes.utils

import es.iesraprog2425.pruebaes.model.Mes
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date

/**
 * Objeto utilitario para operaciones relacionadas con fechas y horas.
 *
 * Proporciona métodos para obtener la fecha y hora actual, formatear fechas
 * y devolver la fecha en formatos personalizados.
 */
object Time {

    /**
     * Obtiene la fecha y hora actual del sistema.
     *
     * @return La fecha y hora actual como un objeto [LocalDateTime].
     */
    fun obtenerFechaActual(): LocalDateTime {
        val fechaActual = LocalDateTime.now()
        return fechaActual
    }

    /**
     * Obtiene la fecha y hora actual formateadas como un par de cadenas.
     *
     * La fecha se devuelve en formato "dd-MM-yyyy" y la hora en formato "HH:mm:ss".
     *
     * @return Un [Pair] donde el primer elemento es la fecha y el segundo la hora.
     */
    fun obtenerFechayHoraActualF(): Pair<String, String> {
        val fechayHora = obtenerFechaActual().toString().split("T")
        val fecha = fechayHora[0].split("-").reversed().joinToString("-")
        val hora = fechayHora[1].split(".")[0]
        return Pair(fecha, hora)
    }

    /**
     * Formatea una fecha dada en formato "dd-MM-yyyy" a un formato más legible.
     *
     * Por ejemplo, convierte "23-05-2025" en "23 de mayo de 2025".
     *
     * @param fecha Fecha en formato "dd-MM-yyyy".
     * @return La fecha formateada en forma legible.
     */
    fun formatearFecha(fecha: String): String {
        val f = fecha.split("-")
        val dia = f[0]
        val mes = f[1]
        val anio = f[2]
        return "$dia de ${Mes.obtenerPorNumero(mes.toInt())?.name?.lowercase()} de $anio"
    }

    // Formateadores de fecha
    private val formateoFecha = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy")
    private val formateoHora = DateTimeFormatter.ofPattern("HH:mm:ss")
    private val formateoFechaSql = DateTimeFormatter.ofPattern("dd-MM-yyyy")
}