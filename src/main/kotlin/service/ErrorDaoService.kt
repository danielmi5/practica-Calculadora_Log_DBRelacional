package es.iesraprog2425.pruebaes.service

import data.dao.IDao
import es.iesraprog2425.pruebaes.model.LogError
import es.iesraprog2425.pruebaes.model.LogOperacion


class ErrorDaoService(private val dao: IDao<LogError>): IErrorDaoService{

    override fun crearError(operacion: LogError) {
        dao.crear(operacion)
    }

    override fun obtenerErrorPorFecha(fecha: String): List<LogError> {
        require(fecha.isNotBlank()) { "La fecha no puede estar vacía." }
        require(regexFecha.matches(fecha)) { "Formato de fecha inválido. Use 'dd-MM-yyyy'" }

        return dao.obtenerPorFecha(fecha)
    }

    override fun obtenerErrorPorHora(hora: String): List<LogError> {
        require(hora.isNotBlank()) { "La hora no puede estar vacía." }
        require(regexHora.matches(hora)) { "Formato de hora inválido. Use 'HH:MM:SS'" }

        return dao.obtenerPorHora(hora)
    }

    override fun obtenerErrorPorFechaYHora(fecha: String, hora: String): LogError? {
        require(fecha.isNotBlank()) { "La fecha no puede estar vacía." }
        require(hora.isNotBlank()) { "La hora no puede estar vacía." }
        require(regexFecha.matches(fecha)) { "Formato de fecha no válido. Debes usar 'dd-MM-yyyy'" }
        require(regexHora.matches(hora)) { "Formato de hora no válido. Debes usar 'HH:MM:SS'" }

        return dao.obtenerPorFechaYHora(fecha, hora)
    }

    override fun eliminarError(fecha: String, hora: String): Boolean {
        require(fecha.isNotBlank()) { "La fecha no puede estar vacía." }
        require(hora.isNotBlank()) { "La hora no puede estar vacía." }
        require(regexFecha.matches(fecha)) { "Formato de fecha no válido. Debes usar 'dd-MM-yyyy'" }
        require(regexHora.matches(hora)) { "Formato de hora no válido. Debes usar 'HH:MM:SS'" }

        return dao.eliminar(fecha, hora)
    }

    override fun eliminarErrorPorFecha(fecha: String): Boolean {
        require(fecha.isNotBlank()) { "La fecha no puede estar vacía." }
        require(regexFecha.matches(fecha)) { "Formato de fecha no válido. Debes usar 'dd-MM-yyyy'" }

        return dao.eliminarPorFecha(fecha)
    }

    override fun eliminarErrorPorHora(hora: String): Boolean {
        require(hora.isNotBlank()) { "La hora no puede estar vacía." }
        require(regexHora.matches(hora)) { "Formato de hora no válido. Debes usar 'HH:MM:SS'" }

        return dao.eliminarPorHora(hora)
    }

    override fun obtenerErrores(): List<LogError> {
        return dao.obtenerTodos()
    }

    override fun actualizarError(operacion: LogError): Boolean {
        return false
    }

    companion object{
        val regexFecha = Regex("""\d{2}-\d{2}-\d{4}""")
        val regexHora = Regex("""\d{2}:\d{2}:\d{2}""")
    }
}