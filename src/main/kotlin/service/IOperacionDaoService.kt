package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.model.LogOperacion

interface IOperacionDaoService {
    fun crearOperacion(operacion: LogOperacion)
    fun obtenerOperacionPorFecha(fecha: String): List<LogOperacion>
    fun obtenerOperacionPorHora(hora: String): List<LogOperacion>
    fun obtenerOperacionPorFechaYHora(fecha: String, hora: String): LogOperacion?
    fun actualizarOperacion(operacion: LogOperacion): Boolean
    fun eliminarOperacion(fecha: String, hora: String): Boolean
    fun eliminarOperacionPorFecha(fecha: String): Int
    fun eliminarOperacionPorHora(hora: String): Int
    fun obtenerOperaciones(): List<LogOperacion>
}