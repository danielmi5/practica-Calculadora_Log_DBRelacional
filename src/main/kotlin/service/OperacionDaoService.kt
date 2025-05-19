package es.iesraprog2425.pruebaes.service

import data.dao.IDao
import es.iesraprog2425.pruebaes.model.LogOperacion

class OperacionDaoService(private val dao: IDao<LogOperacion>): IOperacionDaoService {
    override fun crearOperacion(operacion: LogOperacion) {
        TODO("Not yet implemented")
    }

    override fun obtenerOperacionPorFecha(fecha: String): List<LogOperacion> {
        TODO("Not yet implemented")
    }

    override fun obtenerOperacionPorHora(hora: String): List<LogOperacion> {
        TODO("Not yet implemented")
    }

    override fun obtenerObtenerPorFechaYHora(fecha: String, hora: String): LogOperacion? {
        TODO("Not yet implemented")
    }

    override fun actualizar(operacion: LogOperacion) {
        TODO("Not yet implemented")
    }

    override fun eliminar(fecha: String, hora: String) {
        TODO("Not yet implemented")
    }

    override fun obtenerOperaciones(): List<LogOperacion> {
        TODO("Not yet implemented")
    }
}