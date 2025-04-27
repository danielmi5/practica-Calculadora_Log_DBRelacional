package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.model.TipoLog

interface ServiceLog {
    fun crearFicheroLog(ruta: String): String
    fun obtenerUltimoLog(ruta: String): String
    fun añadirRegistro(ruta: String, tipoRegistro: TipoLog, msj: String)
}