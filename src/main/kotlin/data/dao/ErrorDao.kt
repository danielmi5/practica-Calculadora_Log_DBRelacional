package es.iesraprog2425.pruebaes.data.dao

import data.dao.IDao
import es.iesraprog2425.pruebaes.model.LogError
import javax.sql.DataSource

class ErrorDao(private val ds: DataSource) : IDao<LogError>{
    override fun crear(objeto: LogError) {
        TODO("Not yet implemented")
    }

    override fun obtenerPorFecha(fecha: String): List<LogError> {
        TODO("Not yet implemented")
    }

    override fun obtenerPorHora(hora: String): List<LogError> {
        TODO("Not yet implemented")
    }

    override fun obtenerPorFechaYHora(fechaYHora: String): LogError? {
        TODO("Not yet implemented")
    }

    override fun eliminar(fecha: String, hora: String) {
        TODO("Not yet implemented")
    }

    override fun obtenerTodos(): List<LogError> {
        TODO("Not yet implemented")
    }

    override fun actualizar(objeto: LogError) {
        TODO("Not yet implemented")
    }

}