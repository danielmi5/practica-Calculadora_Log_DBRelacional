package es.iesraprog2425.pruebaes.data.dao

import data.dao.IDao
import es.iesraprog2425.pruebaes.model.LogOperacion
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.SQLTimeoutException
import javax.sql.DataSource

class OperacionDaoH2(private val ds: DataSource): IDao<LogOperacion>{
    override fun crear(operacion: LogOperacion) {
        val sql = "INSERT INTO OPERACION (fecha, hora, num1, operador, num2, resultado) VALUES (?, ?, ?, ?, ?, ?)"
        try {
            ds.connection.use { conn -> conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, operacion.fecha)
                stmt.setString(2, operacion.hora)
                stmt.setDouble(3, operacion.num1)
                stmt.setString(4, operacion.operador.name)
                stmt.setDouble(5, operacion.num2)
                stmt.setDouble(6, operacion.resultado)
                }
            }
        } catch (e: SQLException){
            throw SQLException("No se ha podido insertar el operación ${e.message}")
        } catch (e: SQLTimeoutException){
            throw SQLException("Se ha terminado el tiempo de espera para insertar la operación ${e.message}")
        }

}

    override fun obtenerPorFecha(fecha: String): List<LogOperacion> {
        TODO("Not yet implemented")
    }

    override fun obtenerPorHora(hora: String): List<LogOperacion> {
        TODO("Not yet implemented")
    }

    override fun obtenerPorFechaYHora(fechaYHora: String): LogOperacion? {
        TODO("Not yet implemented")
    }

    override fun eliminar(fecha: String, hora: String) {
        TODO("Not yet implemented")
    }

    override fun obtenerTodos(): List<LogOperacion> {
        TODO("Not yet implemented")
    }

    override fun actualizar(objeto: LogOperacion) {
        TODO("Not yet implemented")
    }


}