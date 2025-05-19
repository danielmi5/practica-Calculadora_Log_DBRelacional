package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.model.LogOperacion

interface IOperacionDaoService {
    fun crearOperacion(operacion: LogOperacion)
    fun obtenerOperacionPorFecha(fecha: String): List<LogOperacion>
    fun obtenerOperacionPorHora(hora: String): List<LogOperacion>
    fun obtenerObtenerPorFechaYHora(fecha: String, hora: String): LogOperacion?
    fun actualizar(operacion: LogOperacion)
    fun eliminar(fecha: String, hora: String)
    fun obtenerOperaciones(): List<LogOperacion>
}