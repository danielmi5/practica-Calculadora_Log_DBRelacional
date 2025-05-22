package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.model.Log


interface ILogDaoService {
    fun obtenerLogsPorFecha(fecha: String, tipo: String? = null): List<Log>
    fun obtenerLogsPorHora(hora: String, tipo: String? = null): List<Log>
    fun obtenerLogPorFechaYHora(fecha: String, hora: String, tipo: String? = null): Log?
    fun eliminarLog(fecha: String, hora: String, tipo: String? = null): Boolean
    fun eliminarLogsPorFecha(fecha: String, tipo: String? = null): Int
    fun eliminarLogsPorHora(hora: String, tipo: String? = null): Int
    fun obtenerLogs(tipo: String? = null): List<Log>
    fun crearLog(log: Log)
}