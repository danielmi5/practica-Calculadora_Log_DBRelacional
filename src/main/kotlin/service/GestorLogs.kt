package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.data.FichLog
import es.iesraprog2425.pruebaes.data.RepositorioLog
import es.iesraprog2425.pruebaes.data.RepositorioLogFich
import es.iesraprog2425.pruebaes.model.Log
import es.iesraprog2425.pruebaes.model.TipoLog
import es.iesraprog2425.pruebaes.utils.BaseDatos
import es.iesraprog2425.pruebaes.utils.GestionFicheros
import es.iesraprog2425.pruebaes.utils.Utils.mapearALog
import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GestorLogs(private val repoLogFich: FichLog, private val repoLogBaseDatos: RepositorioLog) : ServiceLog {
    var logsAntesDeUtilizar: List<Log> = listOf()

    override fun crearFicheroLog(ruta: String) {
        val fechaActual = LocalDateTime.now().format(formateo)
        val nombreLog = "$ruta/log$fechaActual.txt"
        repoLogFich.crearLog(nombreLog)
    }

    override fun guardarRutaFicheroLog(ruta: String) {
        repoLogFich.guardarRutaLog(ruta)
        logsAntesDeUtilizar = obtenerLogsUltimoLog()
    }


    override fun añadirNuevoRegistro(tipoRegistro: TipoLog, msj: String) {
        val formato = LocalDateTime.now().format(formateoLog).split("|")
        val log = Log("${formato[0].trim()}", "${formato[1].trim()}" ,tipoRegistro, msj)
        repoLogFich.subirLog(log)
    }

    override fun subirLogsABaseDatos(listaLogs: List<Log>) {
        repoLogBaseDatos.subirLogs(listaLogs)
    }

    override fun obtenerUltimoLog(): String {
        return repoLogFich.obtenerRuta() ?: throw IllegalArgumentException("No hay fichero de log existente")
    }

    override fun obtenerLineasUltimoLog(enBaseDatos: Boolean): List<String> {
        return if (enBaseDatos) repoLogBaseDatos.obtenerLogs() else repoLogFich.obtenerLogs()
    }

    override fun obtenerDatosUltimoLog(enBaseDatos: Boolean): List<List<String>>{
        val listaListadoDatos = mutableListOf<List<String>>()
        val lineas = if (enBaseDatos) repoLogBaseDatos.obtenerLogs() else repoLogFich.obtenerLogs()
        for (linea in lineas) {
            listaListadoDatos.add((linea.split("-").map { it.trim() }))
        }
        return listaListadoDatos
    }

    override fun obtenerLogsUltimoLog(enBaseDatos: Boolean): List<Log>{
        val lineas = if (enBaseDatos) repoLogBaseDatos.obtenerLogs() else repoLogFich.obtenerLogs()
        val listaLogs = lineas.mapearALog()
        return listaLogs
    }

    override fun obtenerLogsActuales(): List<Log> {
        val listaLog = mutableListOf<Log>()
        val lista = obtenerLogsUltimoLog()
        val numLogs = lista.size-logsAntesDeUtilizar.size
        for (i in (lista.size-numLogs)..lista.size-1) {
            listaLog.add(lista[i])
        }
        return listaLog
    }

    companion object {
        val formateo = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        val formateoLog = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy | HH:mm:ss")
    }

}