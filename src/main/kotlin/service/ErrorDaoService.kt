package es.iesraprog2425.pruebaes.service

import data.dao.IDao
import es.iesraprog2425.pruebaes.model.LogError


class ErrorDaoService(private val dao: IDao<LogError>): IErrorDaoService{
    override fun crearOperacion(error: LogError) {
        TODO("Not yet implemented")
    }

    override fun obtenerOperacionPorFecha(fecha: String): List<LogError> {
        TODO("Not yet implemented")
    }

    override fun obtenerOperacionPorHora(hora: String): List<LogError> {
        TODO("Not yet implemented")
    }

    override fun obtenerObtenerPorFechaYHora(fecha: String, hora: String): LogError? {
        TODO("Not yet implemented")
    }

    override fun actualizar(error: LogError) {
        TODO("Not yet implemented")
    }

    override fun eliminar(fecha: String, hora: String) {
        TODO("Not yet implemented")
    }

    override fun obtenerOperaciones(): List<LogError> {
        TODO("Not yet implemented")
    }

}