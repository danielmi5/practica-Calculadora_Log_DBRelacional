package es.iesraprog2425.pruebaes.utils

import es.iesraprog2425.pruebaes.model.Mes
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date

object Time {

    fun obtenerFechaActual(): LocalDateTime{
        val fechaActual = LocalDateTime.now()
        return fechaActual
    }
    /*
    fun obtenerHoraActualF(): String{
        val horaActual = LocalDateTime.now().format(formateoHora)
        return horaActual
    }*/

    fun obtenerFechayHoraActualF(): Pair<String, String>{
        val fechayHora = obtenerFechaActual().toString().split("T")
        val fecha = fechayHora[0].split("-").reversed().joinToString("-")
        val hora = fechayHora[1].split(".")[0]
        return Pair(fecha, hora)
    }

    fun formatearFecha(fecha: String): String{
        val f = fecha.split("-")
        val dia = f[0]; val mes = f[1]; val anio = f[2]
        return "$dia de ${Mes.obtenerPorNumero(mes.toInt())?.name?.lowercase()} de $anio"
    }

    private val formateoFecha = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy")
    private val formateoHora = DateTimeFormatter.ofPattern("HH:mm:ss")
    private val formateoFechaSql = DateTimeFormatter.ofPattern("dd-MM-yyyy")
}