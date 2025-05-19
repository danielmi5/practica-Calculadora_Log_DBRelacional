package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.model.LogError

interface IErrorDaoService {
    fun crearOperacion(error: LogError)
    fun obtenerOperacionPorFecha(fecha: String): List<LogError>
    fun obtenerOperacionPorHora(hora: String): List<LogError>
    fun obtenerObtenerPorFechaYHora(fecha: String, hora: String): LogError?
    fun actualizar(error: LogError)
    fun eliminar(fecha: String, hora: String)
    fun obtenerOperaciones(): List<LogError>
}