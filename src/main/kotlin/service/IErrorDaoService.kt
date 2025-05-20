package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.model.LogError
import es.iesraprog2425.pruebaes.model.LogOperacion

interface IErrorDaoService {
    fun crearError(operacion: LogError)
    fun obtenerErrorPorFecha(fecha: String): List<LogError>
    fun obtenerErrorPorHora(hora: String): List<LogError>
    fun obtenerErrorPorFechaYHora(fecha: String, hora: String): LogError?
    fun actualizarError(operacion: LogError): Boolean
    fun eliminarError(fecha: String, hora: String): Boolean
    fun eliminarErrorPorFecha(fecha: String): Boolean
    fun eliminarErrorPorHora(hora: String): Boolean
    fun obtenerErrores(): List<LogError>
}