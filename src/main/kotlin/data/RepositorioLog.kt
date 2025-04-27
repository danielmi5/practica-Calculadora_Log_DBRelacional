package es.iesraprog2425.pruebaes.data

import es.iesraprog2425.pruebaes.model.Log

interface RepositorioLog {
    fun subirLog(log: Log)
    fun subirLogs(listaLogs: List<Log>)
    fun obtenerLogs(): List<String>
}