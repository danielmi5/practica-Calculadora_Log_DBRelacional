package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.model.Log
import es.iesraprog2425.pruebaes.model.LogError
import es.iesraprog2425.pruebaes.model.LogOperacion

/**
 * Servicio para gestionar logs que pueden ser de tipo error u operación,
 * combinando las funcionalidades de los servicios específicos de errores y operaciones.
 *
 * @property errorDao Servicio para la gestión de logs de errores.
 * @property operacionDao Servicio para la gestión de logs de operaciones.
 */
class LogDaoService(
    private val errorDao: IErrorDaoService,
    private val operacionDao: IOperacionDaoService
) : ILogDaoService {

    /**
     * Obtiene una lista de logs filtrados por fecha y tipo.
     *
     * @param fecha Fecha para filtrar los logs en formato `dd-MM-yyyy`.
     * @param tipo Tipo de log a filtrar: `"ERROR"`, `"OPERACION"`, o `null` para ambos.
     * @return Lista de logs ordenados por hora.
     */
    override fun obtenerLogsPorFecha(fecha: String, tipo: String?): List<Log> {
        val errores = if (tipo == null || tipo == "ERROR") errorDao.obtenerErrorPorFecha(fecha) else emptyList()
        val operaciones = if (tipo == null || tipo == "OPERACION") operacionDao.obtenerOperacionPorFecha(fecha) else emptyList()
        return (errores + operaciones).sortedBy { it.hora }
    }

    /**
     * Obtiene una lista de logs filtrados por hora y tipo.
     *
     * @param hora Hora para filtrar los logs en formato `HH:mm:ss`.
     * @param tipo Tipo de log a filtrar: `"ERROR"`, `"OPERACION"`, o `null` para ambos.
     * @return Lista de logs ordenados por fecha.
     */
    override fun obtenerLogsPorHora(hora: String, tipo: String?): List<Log> {
        val errores = if (tipo == null || tipo == "ERROR") errorDao.obtenerErrorPorHora(hora) else emptyList()
        val operaciones = if (tipo == null || tipo == "OPERACION") operacionDao.obtenerOperacionPorHora(hora) else emptyList()
        return (errores + operaciones).sortedBy { it.fecha }
    }

    /**
     * Obtiene un log que coincida con la fecha, hora y tipo especificados.
     *
     * @param fecha Fecha del log en formato `dd-MM-yyyy`.
     * @param hora Hora del log en formato `HH:mm:ss`.
     * @param tipo Tipo de log: `"ERROR"`, `"OPERACION"`, o `null` para buscar en ambos.
     * @return El log que coincida o `null` si no se encuentra.
     */
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

    /**
     * Elimina un log que coincida con la fecha, hora y tipo especificados.
     *
     * @param fecha Fecha del log en formato `dd-MM-yyyy`.
     * @param hora Hora del log en formato `HH:mm:ss`.
     * @param tipo Tipo de log: `"ERROR"`, `"OPERACION"`, o `null` para ambos.
     * @return `true` si se eliminó algún log, `false` en caso contrario.
     */
    override fun eliminarLog(fecha: String, hora: String, tipo: String?): Boolean {
        val eliminaOperacion = if (tipo == null || tipo == "OPERACION") operacionDao.eliminarOperacion(fecha, hora) else false
        val eliminaError = if (tipo == null || tipo == "ERROR") errorDao.eliminarError(fecha, hora) else false
        return eliminaOperacion || eliminaError
    }

    /**
     * Elimina logs que coincidan con la fecha y tipo especificados.
     *
     * @param fecha Fecha para filtrar los logs en formato `dd-MM-yyyy`.
     * @param tipo Tipo de log: `"ERROR"`, `"OPERACION"`, o `null` para ambos.
     * @return Cantidad total de logs eliminados.
     */
    override fun eliminarLogsPorFecha(fecha: String, tipo: String?): Int {
        val eliminaOperacion = if (tipo == null || tipo == "OPERACION") operacionDao.eliminarOperacionPorFecha(fecha) else 0
        val eliminaError = if (tipo == null || tipo == "ERROR") errorDao.eliminarErrorPorFecha(fecha) else 0
        return eliminaOperacion + eliminaError
    }

    /**
     * Elimina logs que coincidan con la hora y tipo especificados.
     *
     * @param hora Hora para filtrar los logs en formato `HH:mm:ss`.
     * @param tipo Tipo de log: `"ERROR"`, `"OPERACION"`, o `null` para ambos.
     * @return Cantidad total de logs eliminados.
     */
    override fun eliminarLogsPorHora(hora: String, tipo: String?): Int {
        val eliminaOperacion = if (tipo == null || tipo == "OPERACION") operacionDao.eliminarOperacionPorHora(hora) else 0
        val eliminaError = if (tipo == null || tipo == "ERROR") errorDao.eliminarErrorPorHora(hora) else 0
        return eliminaOperacion + eliminaError
    }

    /**
     * Obtiene todos los logs, opcionalmente filtrados por tipo.
     *
     * @param tipo Tipo de log: `"ERROR"`, `"OPERACION"`, o `null` para todos.
     * @return Lista ordenada de logs por fecha y hora.
     */
    override fun obtenerLogs(tipo: String?): List<Log> {
        val errores = if (tipo == null || tipo == "ERROR") errorDao.obtenerErrores() else emptyList()
        val operaciones = if (tipo == null || tipo == "OPERACION") operacionDao.obtenerOperaciones() else emptyList()
        return (errores + operaciones).sortedWith(compareBy({ it.fecha }, { it.hora }))
    }

    /**
     * Crea un nuevo log.
     *
     * @param log Log a crear, puede ser de tipo [LogOperacion] o [LogError].
     */
    override fun crearLog(log: Log) {
        when(log){
            is LogOperacion -> operacionDao.crearOperacion(log)
            is LogError -> errorDao.crearError(log)
        }
    }
}
