package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.model.Log
import es.iesraprog2425.pruebaes.model.TipoLog
import es.iesraprog2425.pruebaes.utils.GestionFicheros
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GestorLogs(private val fich: GestionFicheros) : ServiceLog {
    override fun crearFicheroLog(ruta: String): String {
        val fechaActual = LocalDateTime.now().format(formateo)
        val nombreLog = "$ruta/log$fechaActual.txt"
        fich.crearFichero(nombreLog)
        return nombreLog
    }

    override fun añadirRegistro(ruta: String, tipoRegistro: TipoLog, msj: String) {
        val formato = LocalDateTime.now().format(formateoLog).split("-")
        val log = Log("${formato[0].trim()}", "${formato[1].trim()}" ,tipoRegistro, msj)
        fich.aniadirLinea(ruta, log.toString())
    }

    override fun obtenerUltimoLog(ruta: String): String {
        return fich.obtenerRutaUltimoFicheroModificado(ruta)
    }

    companion object {
        val formateo = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        val formateoLog = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy - HH:mm:ss")
    }

}