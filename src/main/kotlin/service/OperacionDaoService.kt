package es.iesraprog2425.pruebaes.service

import data.dao.IDao
import es.iesraprog2425.pruebaes.model.LogOperacion

/**
 * Servicio que actúa como intermediario entre la lógica de negocio y el DAO
 * para la gestión de operaciones de tipo [LogOperacion].
 *
 * @property dao DAO genérico para realizar operaciones CRUD sobre [LogOperacion].
 */
class OperacionDaoService(private val dao: IDao<LogOperacion>) : IOperacionDaoService {

    /**
     * Crea un nuevo registro de operación en la fuente de datos.
     *
     * @param operacion Objeto [LogOperacion] a crear.
     */
    override fun crearOperacion(operacion: LogOperacion) {
        dao.crear(operacion)
    }

    /**
     * Obtiene una lista de operaciones que coinciden con la fecha indicada.
     *
     * @param fecha Fecha en formato `dd-MM-yyyy`. No puede estar vacía.
     * @return Lista de [LogOperacion] correspondientes a la fecha dada.
     * @throws IllegalArgumentException si la fecha está vacía o el formato es incorrecto.
     */
    override fun obtenerOperacionPorFecha(fecha: String): List<LogOperacion> {
        require(fecha.isNotBlank()) { "La fecha no puede estar vacía." }
        require(regexFecha.matches(fecha)) { "Formato de fecha no válido, debes usar 'dd-MM-yyyy'" }

        return dao.obtenerPorFecha(fecha)
    }

    /**
     * Obtiene una lista de operaciones que coinciden con la hora indicada.
     *
     * @param hora Hora en formato `HH:mm:ss`. No puede estar vacía.
     * @return Lista de [LogOperacion] correspondientes a la hora dada.
     * @throws IllegalArgumentException si la hora está vacía o el formato es incorrecto.
     */
    override fun obtenerOperacionPorHora(hora: String): List<LogOperacion> {
        require(hora.isNotBlank()) { "La hora no puede estar vacía." }
        require(regexHora.matches(hora)) { "Formato de hora no válido, debes usar 'HH:mm:ss'" }

        return dao.obtenerPorHora(hora)
    }

    /**
     * Obtiene una operación que coincida con la fecha y hora indicadas.
     *
     * @param fecha Fecha en formato `dd-MM-yyyy`. No puede estar vacía.
     * @param hora Hora en formato `HH:mm:ss`. No puede estar vacía.
     * @return [LogOperacion] que coincide con fecha y hora, o `null` si no existe.
     * @throws IllegalArgumentException si la fecha o la hora están vacías o tienen formato incorrecto.
     */
    override fun obtenerOperacionPorFechaYHora(fecha: String, hora: String): LogOperacion? {
        require(fecha.isNotBlank()) { "La fecha no puede estar vacía." }
        require(hora.isNotBlank()) { "La hora no puede estar vacía." }
        require(regexFecha.matches(fecha)) { "Formato de fecha no válido, debes usar 'dd-MM-yyyy'" }
        require(regexHora.matches(hora)) { "Formato de hora no válido, debes usar 'HH:mm:ss'" }

        return dao.obtenerPorFechaYHora(fecha, hora)
    }

    /**
     * Elimina una operación que coincida con la fecha y hora indicadas.
     *
     * @param fecha Fecha en formato `dd-MM-yyyy`. No puede estar vacía.
     * @param hora Hora en formato `HH:mm:ss`. No puede estar vacía.
     * @return `true` si la operación fue eliminada, `false` en caso contrario.
     * @throws IllegalArgumentException si la fecha o la hora están vacías o tienen formato incorrecto.
     */
    override fun eliminarOperacion(fecha: String, hora: String): Boolean {
        require(fecha.isNotBlank()) { "La fecha no puede estar vacía." }
        require(hora.isNotBlank()) { "La hora no puede estar vacía." }
        require(regexFecha.matches(fecha)) { "Formato de fecha no válido, debes usar 'dd-MM-yyyy'" }
        require(regexHora.matches(hora)) { "Formato de hora no válido, debes usar 'HH:mm:ss'" }

        return dao.eliminar(fecha, hora)
    }

    /**
     * Elimina todas las operaciones que coincidan con la fecha indicada.
     *
     * @param fecha Fecha en formato `dd-MM-yyyy`. No puede estar vacía.
     * @return Cantidad de operaciones eliminadas.
     * @throws IllegalArgumentException si la fecha está vacía o tiene formato incorrecto.
     */
    override fun eliminarOperacionPorFecha(fecha: String): Int {
        require(fecha.isNotBlank()) { "La fecha no puede estar vacía." }
        require(regexFecha.matches(fecha)) { "Formato de fecha no válido, debes usar 'dd-MM-yyyy'" }

        return dao.eliminarPorFecha(fecha)
    }

    /**
     * Elimina todas las operaciones que coincidan con la hora indicada.
     *
     * @param hora Hora en formato `HH:mm:ss`. No puede estar vacía.
     * @return Cantidad de operaciones eliminadas.
     * @throws IllegalArgumentException si la hora está vacía o tiene formato incorrecto.
     */
    override fun eliminarOperacionPorHora(hora: String): Int {
        require(hora.isNotBlank()) { "La hora no puede estar vacía." }
        require(regexHora.matches(hora)) { "Formato de hora no válido, debes usar 'HH:mm:ss'" }

        return dao.eliminarPorHora(hora)
    }

    /**
     * Obtiene todas las operaciones almacenadas.
     *
     * @return Lista completa de [LogOperacion].
     */
    override fun obtenerOperaciones(): List<LogOperacion> {
        return dao.obtenerTodos()
    }

    /**
     * Actualiza una operación existente.
     *
     * @param operacion Operación a actualizar.
     * @return Siempre retorna `false` (no implementado).
     */
    override fun actualizarOperacion(operacion: LogOperacion): Boolean {
        return false
    }

    companion object {
        /** Expresión regular para validar el formato de fecha `dd-MM-yyyy`. */
        val regexFecha = Regex("""\d{2}-\d{2}-\d{4}""")

        /** Expresión regular para validar el formato de hora `HH:mm:ss`. */
        val regexHora = Regex("""\d{2}:\d{2}:\d{2}""")
    }
}