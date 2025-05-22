package es.iesraprog2425.pruebaes.service

import data.dao.IDao
import es.iesraprog2425.pruebaes.model.LogError
import es.iesraprog2425.pruebaes.model.LogOperacion

/**
 * Servicio de acceso a datos (DAO) para manejar errores registrados en el sistema.
 * Implementa [IErrorDaoService] utilizando un DAO genérico de [LogError].
 *
 * @property dao Implementación de [IDao] especializada en [LogError].
 */
class ErrorDaoService(private val dao: IDao<LogError>) : IErrorDaoService {

    /**
     * Crea un nuevo log de error.
     *
     * @param operacion Error a registrar.
     */
    override fun crearError(operacion: LogError) {
        dao.crear(operacion)
    }

    /**
     * Obtiene los errores registrados en una fecha específica.
     *
     * @param fecha Fecha en formato 'dd-MM-yyyy'.
     * @return Lista de errores en esa fecha.
     * @throws IllegalArgumentException si la fecha está vacía o tiene formato inválido.
     */
    override fun obtenerErrorPorFecha(fecha: String): List<LogError> {
        require(fecha.isNotBlank()) { "La fecha no puede estar vacía." }
        require(regexFecha.matches(fecha)) { "Formato de fecha inválido. Use 'dd-MM-yyyy'" }

        return dao.obtenerPorFecha(fecha)
    }

    /**
     * Obtiene los errores registrados en una hora específica.
     *
     * @param hora Hora en formato 'HH:MM:SS'.
     * @return Lista de errores en esa hora.
     * @throws IllegalArgumentException si la hora está vacía o tiene formato inválido.
     */
    override fun obtenerErrorPorHora(hora: String): List<LogError> {
        require(hora.isNotBlank()) { "La hora no puede estar vacía." }
        require(regexHora.matches(hora)) { "Formato de hora inválido. Use 'HH:MM:SS'" }

        return dao.obtenerPorHora(hora)
    }

    /**
     * Obtiene un error específico por fecha y hora.
     *
     * @param fecha Fecha en formato 'dd-MM-yyyy'.
     * @param hora Hora en formato 'HH:MM:SS'.
     * @return Error encontrado o `null` si no existe.
     * @throws IllegalArgumentException si fecha/hora están vacías o mal formateadas.
     */
    override fun obtenerErrorPorFechaYHora(fecha: String, hora: String): LogError? {
        require(fecha.isNotBlank()) { "La fecha no puede estar vacía." }
        require(hora.isNotBlank()) { "La hora no puede estar vacía." }
        require(regexFecha.matches(fecha)) { "Formato de fecha no válido. Debes usar 'dd-MM-yyyy'" }
        require(regexHora.matches(hora)) { "Formato de hora no válido. Debes usar 'HH:MM:SS'" }

        return dao.obtenerPorFechaYHora(fecha, hora)
    }

    /**
     * Elimina un error específico según fecha y hora.
     *
     * @param fecha Fecha del error.
     * @param hora Hora del error.
     * @return `true` si fue eliminado correctamente, `false` si no se encontró.
     */
    override fun eliminarError(fecha: String, hora: String): Boolean {
        require(fecha.isNotBlank()) { "La fecha no puede estar vacía." }
        require(hora.isNotBlank()) { "La hora no puede estar vacía." }
        require(regexFecha.matches(fecha)) { "Formato de fecha no válido. Debes usar 'dd-MM-yyyy'" }
        require(regexHora.matches(hora)) { "Formato de hora no válido. Debes usar 'HH:MM:SS'" }

        return dao.eliminar(fecha, hora)
    }

    /**
     * Elimina todos los errores registrados en una fecha.
     *
     * @param fecha Fecha en formato 'dd-MM-yyyy'.
     * @return Cantidad de errores eliminados.
     */
    override fun eliminarErrorPorFecha(fecha: String): Int {
        require(fecha.isNotBlank()) { "La fecha no puede estar vacía." }
        require(regexFecha.matches(fecha)) { "Formato de fecha no válido. Debes usar 'dd-MM-yyyy'" }

        return dao.eliminarPorFecha(fecha)
    }

    /**
     * Elimina todos los errores registrados en una hora específica.
     *
     * @param hora Hora en formato 'HH:MM:SS'.
     * @return Cantidad de errores eliminados.
     */
    override fun eliminarErrorPorHora(hora: String): Int {
        require(hora.isNotBlank()) { "La hora no puede estar vacía." }
        require(regexHora.matches(hora)) { "Formato de hora no válido. Debes usar 'HH:MM:SS'" }

        return dao.eliminarPorHora(hora)
    }

    /**
     * Obtiene todos los errores registrados.
     *
     * @return Lista completa de errores.
     */
    override fun obtenerErrores(): List<LogError> {
        return dao.obtenerTodos()
    }

    /**
     * (No implementado) Actualiza un error existente.
     *
     * @param operacion Error a actualizar.
     * @return `false` indicando que no está implementado.
     */
    override fun actualizarError(operacion: LogError): Boolean {
        return false
    }

    companion object {
        /** Expresión regular para validar fechas con formato 'dd-MM-yyyy'. */
        val regexFecha = Regex("""\d{2}-\d{2}-\d{4}""")

        /** Expresión regular para validar horas con formato 'HH:MM:SS'. */
        val regexHora = Regex("""\d{2}:\d{2}:\d{2}""")
    }
}