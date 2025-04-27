package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.model.Log
import es.iesraprog2425.pruebaes.model.TipoLog

interface ServiceLog {
    fun crearFicheroLog(ruta: String)
    fun guardarRutaFicheroLog(ruta: String)
    fun obtenerUltimoLog(): String
    fun obtenerLineasUltimoLog(enBaseDatos: Boolean = false): List<String>
    fun obtenerDatosUltimoLog(enBaseDatos: Boolean = false): List<List<String>>
    fun obtenerLogsUltimoLog(enBaseDatos: Boolean = false): List<Log>
    fun añadirNuevoRegistro(tipoRegistro: TipoLog, msj: String)
    fun subirLogsABaseDatos(listaLog: List<Log>)
    fun obtenerLogsActuales(): List<Log>
}