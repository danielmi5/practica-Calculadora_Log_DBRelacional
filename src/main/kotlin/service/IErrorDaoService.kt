package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.model.LogError
import es.iesraprog2425.pruebaes.model.LogOperacion

/**
 * Interfaz que define las operaciones de acceso y manipulación
 * para logs de tipo error en la base de datos.
 */
interface IErrorDaoService {

    /**
     * Crea y almacena un nuevo log de error.
     *
     * @param operacion Objeto [LogError] que representa el error a guardar.
     */
    fun crearError(operacion: LogError)

    /**
     * Obtiene todos los errores registrados en una fecha específica.
     *
     * @param fecha Fecha en formato `dd-MM-yyyy`.
     * @return Lista de errores encontrados.
     */
    fun obtenerErrorPorFecha(fecha: String): List<LogError>

    /**
     * Obtiene todos los errores registrados a una hora específica.
     *
     * @param hora Hora en formato `HH:mm:ss`.
     * @return Lista de errores encontrados.
     */
    fun obtenerErrorPorHora(hora: String): List<LogError>

    /**
     * Obtiene un error registrado en una fecha y hora específica.
     *
     * @param fecha Fecha en formato `dd-MM-yyyy`.
     * @param hora Hora en formato `HH:mm:ss`.
     * @return Log de error encontrado o `null` si no existe.
     */
    fun obtenerErrorPorFechaYHora(fecha: String, hora: String): LogError?

    /**
     * Actualiza la información de un error existente.
     *
     * @param operacion Objeto [LogError] con la información actualizada.
     * @return `true` si la actualización fue exitosa, `false` si no se encontró el error.
     */
    fun actualizarError(operacion: LogError): Boolean

    /**
     * Elimina un error específico por fecha y hora.
     *
     * @param fecha Fecha en formato `dd-MM-yyyy`.
     * @param hora Hora en formato `HH:mm:ss`.
     * @return `true` si el error fue eliminado, `false` si no se encontró.
     */
    fun eliminarError(fecha: String, hora: String): Boolean

    /**
     * Elimina todos los errores registrados en una fecha específica.
     *
     * @param fecha Fecha en formato `dd-MM-yyyy`.
     * @return Número de errores eliminados.
     */
    fun eliminarErrorPorFecha(fecha: String): Int

    /**
     * Elimina todos los errores registrados a una hora específica.
     *
     * @param hora Hora en formato `HH:mm:ss`.
     * @return Número de errores eliminados.
     */
    fun eliminarErrorPorHora(hora: String): Int

    /**
     * Obtiene todos los errores almacenados.
     *
     * @return Lista de todos los logs de errores.
     */
    fun obtenerErrores(): List<LogError>
}