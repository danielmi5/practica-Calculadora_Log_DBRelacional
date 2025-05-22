package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.model.Log

class LogDaoService(private val errorDao: IErrorDaoService, private val operacionDao: IOperacionDaoService) : ILogDaoService {
    override fun obtenerLogsPorFecha(fecha: String): List<Log> {
        val errores = errorDao.obtenerErrorPorFecha(fecha)
        val operaciones = operacionDao.obtenerOperacionPorFecha(fecha)
        return (errores + operaciones).sortedBy { it.hora }
    }

    override fun obtenerLogsPorHora(hora: String): List<Log> {
        val errores = errorDao.obtenerErrorPorHora(hora)
        val operaciones = operacionDao.obtenerOperacionPorHora(hora)
        return (errores + operaciones).sortedBy { it.fecha }
    }

    override fun obtenerLogPorFechaYHora(fecha: String, hora: String): Log? {
        val operacion = operacionDao.obtenerOperacionPorFechaYHora(fecha, hora)
        if (operacion != null) return operacion
        val error = errorDao.obtenerErrorPorFechaYHora(fecha, hora)
        return error
    }

    override fun eliminarLog(fecha: String, hora: String): Boolean {
        val eliminaOperacion = operacionDao.eliminarOperacion(fecha, hora)
        val eliminaError = errorDao.eliminarError(fecha, hora)
        return eliminaOperacion || eliminaError
    }

    override fun eliminarLogsPorFecha(fecha: String): Boolean {
        val eliminaOperacion = operacionDao.eliminarOperacionPorFecha(fecha)
        val eliminaError = errorDao.eliminarErrorPorFecha(fecha)
        return eliminaOperacion || eliminaError
    }

    override fun eliminarLogsPorHora(hora: String): Boolean {
        val eliminaOperacion = operacionDao.eliminarOperacionPorHora(hora)
        val eliminaError = errorDao.eliminarErrorPorHora(hora)
        return eliminaOperacion || eliminaError
    }

    override fun obtenerLogs(): List<Log> {
        val errores = errorDao.obtenerErrores()
        val operaciones = operacionDao.obtenerOperaciones()
        return (errores + operaciones).sortedBy { it.fecha }
    }

}