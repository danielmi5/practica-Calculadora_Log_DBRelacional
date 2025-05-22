package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.model.Log


interface ILogDaoService {
    fun obtenerLogsPorFecha(fecha: String): List<Log>
    fun obtenerLogsPorHora(hora: String): List<Log>
    fun obtenerLogPorFechaYHora(fecha: String, hora: String): Log?
    fun eliminarLog(fecha: String, hora: String): Boolean
    fun eliminarLogsPorFecha(fecha: String): Boolean
    fun eliminarLogsPorHora(hora: String): Boolean
    fun obtenerLogs(): List<Log>
}