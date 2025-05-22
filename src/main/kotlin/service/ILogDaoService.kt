package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.model.Log


/**
 * Interfaz que define las operaciones para acceder y manipular logs,
 * incluyendo errores y operaciones, en la base de datos.
 */
interface ILogDaoService {

    /**
     * Obtiene todos los logs registrados en una fecha específica, filtrando opcionalmente por tipo.
     *
     * @param fecha Fecha en formato `dd-MM-yyyy`.
     * @param tipo Tipo de log a filtrar: `"ERROR"`, `"OPERACION"` o `null` para ambos.
     * @return Lista de logs encontrados.
     */
    fun obtenerLogsPorFecha(fecha: String, tipo: String? = null): List<Log>

    /**
     * Obtiene todos los logs registrados en una hora específica, filtrando opcionalmente por tipo.
     *
     * @param hora Hora en formato `HH:mm:ss`.
     * @param tipo Tipo de log a filtrar: `"ERROR"`, `"OPERACION"` o `null` para ambos.
     * @return Lista de logs encontrados.
     */
    fun obtenerLogsPorHora(hora: String, tipo: String? = null): List<Log>

    /**
     * Obtiene un log registrado en una fecha y hora específica, filtrando opcionalmente por tipo.
     *
     * @param fecha Fecha en formato `dd-MM-yyyy`.
     * @param hora Hora en formato `HH:mm:ss`.
     * @param tipo Tipo de log a buscar: `"ERROR"`, `"OPERACION"` o `null` para ambos.
     * @return Log encontrado o `null` si no existe.
     */
    fun obtenerLogPorFechaYHora(fecha: String, hora: String, tipo: String? = null): Log?

    /**
     * Elimina un log específico por fecha y hora, filtrando opcionalmente por tipo.
     *
     * @param fecha Fecha en formato `dd-MM-yyyy`.
     * @param hora Hora en formato `HH:mm:ss`.
     * @param tipo Tipo de log a eliminar: `"ERROR"`, `"OPERACION"` o `null` para ambos.
     * @return `true` si el log fue eliminado, `false` si no se encontró.
     */
    fun eliminarLog(fecha: String, hora: String, tipo: String? = null): Boolean

    /**
     * Elimina todos los logs registrados en una fecha específica, filtrando opcionalmente por tipo.
     *
     * @param fecha Fecha en formato `dd-MM-yyyy`.
     * @param tipo Tipo de logs a eliminar: `"ERROR"`, `"OPERACION"` o `null` para ambos.
     * @return Número de logs eliminados.
     */
    fun eliminarLogsPorFecha(fecha: String, tipo: String? = null): Int

    /**
     * Elimina todos los logs registrados a una hora específica, filtrando opcionalmente por tipo.
     *
     * @param hora Hora en formato `HH:mm:ss`.
     * @param tipo Tipo de logs a eliminar: `"ERROR"`, `"OPERACION"` o `null` para ambos.
     * @return Número de logs eliminados.
     */
    fun eliminarLogsPorHora(hora: String, tipo: String? = null): Int

    /**
     * Obtiene todos los logs almacenados, con opción a filtrar por tipo.
     *
     * @param tipo Tipo de logs a obtener: `"ERROR"`, `"OPERACION"` o `null` para obtener todos.
     * @return Lista de logs encontrados.
     */
    fun obtenerLogs(tipo: String? = null): List<Log>

    /**
     * Registra un nuevo log en la base de datos.
     *
     * @param log Log a crear, puede ser de tipo [LogOperacion] o [LogError].
     */
    fun crearLog(log: Log)
}
