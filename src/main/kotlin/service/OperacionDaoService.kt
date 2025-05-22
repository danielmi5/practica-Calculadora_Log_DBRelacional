package es.iesraprog2425.pruebaes.service

import data.dao.IDao
import es.iesraprog2425.pruebaes.model.LogOperacion

class OperacionDaoService(private val dao: IDao<LogOperacion>): IOperacionDaoService {
    override fun crearOperacion(operacion: LogOperacion) {
        dao.crear(operacion)
    }

    override fun obtenerOperacionPorFecha(fecha: String): List<LogOperacion> {
        require(fecha.isNotBlank()) { "La fecha no puede estar vacía." }
        require(regexFecha.matches(fecha)) { "Formato de fecha no válido, debes usar 'dd-MM-yyyy'" }

        return dao.obtenerPorFecha(fecha)
    }

    override fun obtenerOperacionPorHora(hora: String): List<LogOperacion> {
        require(hora.isNotBlank()) { "La hora no puede estar vacía." }
        require(regexHora.matches(hora)) { "Formato de hora no válido, debes usar 'HH:MM:SS'" }

        return dao.obtenerPorHora(hora)
    }

    override fun obtenerOperacionPorFechaYHora(fecha: String, hora: String): LogOperacion? {
        require(fecha.isNotBlank()) { "La fecha no puede estar vacía." }
        require(hora.isNotBlank()) { "La hora no puede estar vacía." }
        require(regexFecha.matches(fecha)) { "Formato de fecha no válido, debes usar 'dd-MM-yyyy'" }
        require(regexHora.matches(hora)) { "Formato de hora no válido, debes usar 'HH:MM:SS'" }

        return dao.obtenerPorFechaYHora(fecha, hora)
    }


    override fun eliminarOperacion(fecha: String, hora: String): Boolean {
        require(fecha.isNotBlank()) { "La fecha no puede estar vacía." }
        require(hora.isNotBlank()) { "La hora no puede estar vacía." }
        require(regexFecha.matches(fecha)) { "Formato de fecha no válido, debes usar 'dd-MM-yyyy'" }
        require(regexHora.matches(hora)) { "Formato de hora no válido, debes usar 'HH:MM:SS'" }

        return dao.eliminar(fecha, hora)
    }

    override fun eliminarOperacionPorFecha(fecha: String): Int {
        require(fecha.isNotBlank()) { "La fecha no puede estar vacía." }
        require(regexFecha.matches(fecha)) { "Formato de fecha no válido, debes usar 'dd-MM-yyyy'" }

        return dao.eliminarPorFecha(fecha)
    }

    override fun eliminarOperacionPorHora(hora: String): Int {
        require(hora.isNotBlank()) { "La hora no puede estar vacía." }
        require(regexHora.matches(hora)) { "Formato de hora no válido, debes usar 'HH:MM:SS'" }

        return dao.eliminarPorHora(hora)
    }

    override fun obtenerOperaciones(): List<LogOperacion> {
        return dao.obtenerTodos()
    }

    override fun actualizarOperacion(operacion: LogOperacion): Boolean {
        return false
    }

    companion object{
        val regexFecha = Regex("""\d{2}-\d{2}-\d{4}""")
        val regexHora = Regex("""\d{2}:\d{2}:\d{2}""")
    }

}