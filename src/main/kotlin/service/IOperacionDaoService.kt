package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.model.LogOperacion

/**
 * Interfaz que define las operaciones CRUD para los logs de tipo operación.
 */
interface IOperacionDaoService {

    /**
     * Crea una nueva operación en la base de datos.
     *
     * @param operacion Objeto [LogOperacion] a guardar.
     */
    fun crearOperacion(operacion: LogOperacion)

    /**
     * Obtiene todas las operaciones registradas en una fecha específica.
     *
     * @param fecha Fecha en formato `dd-MM-yyyy`.
     * @return Lista de [LogOperacion] encontradas en esa fecha.
     */
    fun obtenerOperacionPorFecha(fecha: String): List<LogOperacion>

    /**
     * Obtiene todas las operaciones registradas a una hora específica.
     *
     * @param hora Hora en formato `HH:mm:ss`.
     * @return Lista de [LogOperacion] encontradas en esa hora.
     */
    fun obtenerOperacionPorHora(hora: String): List<LogOperacion>

    /**
     * Obtiene una operación registrada en una fecha y hora específica.
     *
     * @param fecha Fecha en formato `dd-MM-yyyy`.
     * @param hora Hora en formato `HH:mm:ss`.
     * @return [LogOperacion] encontrada, o `null` si no existe.
     */
    fun obtenerOperacionPorFechaYHora(fecha: String, hora: String): LogOperacion?

    /**
     * Actualiza una operación existente.
     *
     * @param operacion Objeto [LogOperacion] con la información actualizada.
     * @return `true` si la operación fue actualizada correctamente, `false` en caso contrario.
     */
    fun actualizarOperacion(operacion: LogOperacion): Boolean

    /**
     * Elimina una operación que coincide con la fecha y hora proporcionadas.
     *
     * @param fecha Fecha en formato `dd-MM-yyyy`.
     * @param hora Hora en formato `HH:mm:ss`.
     * @return `true` si la operación fue eliminada, `false` en caso contrario.
     */
    fun eliminarOperacion(fecha: String, hora: String): Boolean

    /**
     * Elimina todas las operaciones de una fecha específica.
     *
     * @param fecha Fecha en formato `dd-MM-yyyy`.
     * @return Número de operaciones eliminadas.
     */
    fun eliminarOperacionPorFecha(fecha: String): Int

    /**
     * Elimina todas las operaciones registradas a una hora específica.
     *
     * @param hora Hora en formato `HH:mm:ss`.
     * @return Número de operaciones eliminadas.
     */
    fun eliminarOperacionPorHora(hora: String): Int

    /**
     * Obtiene todas las operaciones almacenadas.
     *
     * @return Lista de todas las [LogOperacion].
     */
    fun obtenerOperaciones(): List<LogOperacion>
}
