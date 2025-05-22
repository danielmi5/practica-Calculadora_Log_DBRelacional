package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.model.Log
import es.iesraprog2425.pruebaes.model.LogError
import es.iesraprog2425.pruebaes.model.LogOperacion

class LogDaoService(private val errorDao: IErrorDaoService, private val operacionDao: IOperacionDaoService) : ILogDaoService {
    override fun obtenerLogsPorFecha(fecha: String, tipo: String?): List<Log> {
        val errores = if (tipo == null || tipo == "ERROR") errorDao.obtenerErrorPorFecha(fecha) else emptyList()
        val operaciones = if (tipo == null || tipo == "OPERACION") operacionDao.obtenerOperacionPorFecha(fecha) else emptyList()
        return (errores + operaciones).sortedBy { it.hora }
    }

    override fun obtenerLogsPorHora(hora: String, tipo: String?): List<Log> {
        val errores = if (tipo == null || tipo == "ERROR") errorDao.obtenerErrorPorHora(hora) else emptyList()
        val operaciones = if (tipo == null || tipo == "OPERACION") operacionDao.obtenerOperacionPorHora(hora) else emptyList()
        return (errores + operaciones).sortedBy { it.fecha }
    }

    override fun obtenerLogPorFechaYHora(fecha: String, hora: String, tipo: String?): Log? {
        if (tipo == null || tipo == "OPERACION") {
            val operacion = operacionDao.obtenerOperacionPorFechaYHora(fecha, hora)
            if (operacion != null) return operacion
        }
        if (tipo == null || tipo == "ERROR") {
            val error = errorDao.obtenerErrorPorFechaYHora(fecha, hora)
            if (error != null) return error
        }
        return null
    }

    override fun eliminarLog(fecha: String, hora: String, tipo: String?): Boolean {
        val eliminaOperacion = if (tipo == null || tipo == "OPERACION") operacionDao.eliminarOperacion(fecha, hora) else false
        val eliminaError = if (tipo == null || tipo == "ERROR") errorDao.eliminarError(fecha, hora) else false
        return eliminaOperacion || eliminaError
    }

    override fun eliminarLogsPorFecha(fecha: String, tipo: String?): Int {
        val eliminaOperacion = if (tipo == null || tipo == "OPERACION") operacionDao.eliminarOperacionPorFecha(fecha) else 0
        val eliminaError = if (tipo == null || tipo == "ERROR") errorDao.eliminarErrorPorFecha(fecha) else 0
        return eliminaOperacion + eliminaError
    }

    override fun eliminarLogsPorHora(hora: String, tipo: String?): Int {
        val eliminaOperacion = if (tipo == null || tipo == "OPERACION") operacionDao.eliminarOperacionPorHora(hora) else 0
        val eliminaError = if (tipo == null || tipo == "ERROR") errorDao.eliminarErrorPorHora(hora) else 0
        return eliminaOperacion + eliminaError
    }

    override fun obtenerLogs(tipo: String?): List<Log> {
        val errores = if (tipo == null || tipo == "ERROR") errorDao.obtenerErrores() else emptyList()
        val operaciones = if (tipo == null || tipo == "OPERACION") operacionDao.obtenerOperaciones() else emptyList()
        return (errores + operaciones).sortedWith(compareBy({ it.fecha }, { it.hora }))
    }

    override fun crearLog(log: Log) {
        when(log){
            is LogOperacion -> operacionDao.crearOperacion(log)
            is LogError -> errorDao.crearError(log)
        }
    }
}