package es.iesraprog2425.pruebaes.data.dao

import data.dao.IDao
import es.iesraprog2425.pruebaes.model.LogOperacion
import es.iesraprog2425.pruebaes.model.Operadores
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
        val operaciones = mutableListOf<LogOperacion>()
        val sql = "SELECT * FROM OPERACION WHERE fecha = ?"
        try {
            ds.connection.use { conn -> conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, fecha)
                stmt.executeQuery().use {
                    rs ->
                    while (rs.next()){
                        val fech = rs.getString(1)
                        val hora = rs.getString(2)
                        val num1 = rs.getDouble(3)
                        val operador = Operadores.getOperador(rs.getString(4)) ?: throw IllegalArgumentException("Datos no válidos insertados en la base de datos")
                        val num2 = rs.getDouble(5)
                        val res = rs.getDouble(6)
                        operaciones.add(LogOperacion(num1, operador, num2, res, fech, hora))
                    }
                }
            }
            }
        } catch (e: SQLException){
            throw SQLException("No se ha podido obtener las operaciones ${e.message}")
        } catch (e: SQLTimeoutException){
            throw SQLException("Se ha terminado el tiempo de espera para obtener las operaciones ${e.message}")
        }
        return operaciones
    }

    override fun obtenerPorHora(hour: String): List<LogOperacion> {
        val operaciones = mutableListOf<LogOperacion>()
        val sql = "SELECT * FROM OPERACION WHERE hora = ?"
        try {
            ds.connection.use { conn -> conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, hour)
                stmt.executeQuery().use {
                        rs ->
                    while (rs.next()){
                        val fech = rs.getString(1)
                        val hora = rs.getString(2)
                        val num1 = rs.getDouble(3)
                        val operador = Operadores.getOperador(rs.getString(4)) ?: throw IllegalArgumentException("Datos no válidos insertados en la base de datos")
                        val num2 = rs.getDouble(5)
                        val res = rs.getDouble(6)
                        operaciones.add(LogOperacion(num1, operador, num2, res, fech, hora))
                    }
                }
            }
            }
        } catch (e: SQLException){
            throw SQLException("No se ha podido obtener las operaciones ${e.message}")
        } catch (e: SQLTimeoutException){
            throw SQLException("Se ha terminado el tiempo de espera para obtener las operaciones ${e.message}")
        }
        return operaciones
    }

    override fun obtenerPorFechaYHora(fecha: String, hora: String): LogOperacion? {
        val sql = "SELECT * FROM OPERACION WHERE fecha = ? AND hora = ?"
        try {
            ds.connection.use { conn -> conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, fecha)
                stmt.setString(2, hora)
                stmt.executeQuery().use {
                        rs ->
                    return if (rs.next()){
                        val fech = rs.getString(1)
                        val hor = rs.getString(2)
                        val num1 = rs.getDouble(3)
                        val operador = Operadores.getOperador(rs.getString(4)) ?: throw IllegalArgumentException("Datos no válidos insertados en la base de datos")
                        val num2 = rs.getDouble(5)
                        val res = rs.getDouble(6)
                        LogOperacion(num1, operador, num2, res, fech, hor)
                    } else null
                }
            }
            }
        } catch (e: SQLException){
            throw SQLException("No se ha podido obtener la operación ${e.message}")
        } catch (e: SQLTimeoutException){
            throw SQLException("Se ha terminado el tiempo de espera para obtener la operación ${e.message}")
        }

    }

    override fun eliminar(fecha: String, hora: String): Boolean {
        val operaciones = mutableListOf<LogOperacion>()
        val sql = "DELETE FROM OPERACION WHERE fecha = ? AND hora = ?"
        try {
            ds.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, fecha)
                    stmt.setString(2, hora)
                    return stmt.executeUpdate() > 0
                }
            }
        }catch (e: SQLException){
            throw SQLException("No se ha podido eliminar la operación ${e.message}")
        } catch (e: SQLTimeoutException){
            throw SQLException("Se ha pasado el tiempo para eliminar la operación ${e.message}")
        }

    }

    override fun eliminarPorFecha(fecha: String): Boolean {
        val sql = "DELETE FROM OPERACION WHERE fecha = ?"
        try {
            ds.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, fecha)
                    return stmt.executeUpdate() > 0
                }
            }
        } catch (e: SQLException) {
            throw SQLException("No se ha podido eliminar por fecha: ${e.message}")
        } catch (e: SQLTimeoutException) {
            throw SQLException("Se ha terminado el tiempo para eliminar por fecha: ${e.message}")
        }
    }

    override fun eliminarPorHora(hora: String): Boolean {
        val sql = "DELETE FROM OPERACION WHERE hora = ?"
        try {
            ds.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, hora)
                    return stmt.executeUpdate() > 0
                }
            }
        } catch (e: SQLException) {
            throw SQLException("No se ha podido eliminar por hora: ${e.message}")
        } catch (e: SQLTimeoutException) {
            throw SQLException("Se ha terminado el tiempo para eliminar por hora: ${e.message}")
        }
    }



    override fun obtenerTodos(): List<LogOperacion> {
        val operaciones = mutableListOf<LogOperacion>()
        val sql = "SELECT * FROM OPERACION"
        try {
            ds.connection.use { conn -> conn.createStatement().use { stmt ->
                stmt.executeQuery(sql).use {
                        rs ->
                    while (rs.next()){
                        val fech = rs.getString(1)
                        val hora = rs.getString(2)
                        val num1 = rs.getDouble(3)
                        val operador = Operadores.getOperador(rs.getString(4)) ?: throw IllegalArgumentException("Datos no válidos insertados en la base de datos")
                        val num2 = rs.getDouble(5)
                        val res = rs.getDouble(6)
                        operaciones.add(LogOperacion(num1, operador, num2, res, fech, hora))
                    }
                }
            }
            }
        } catch (e: SQLException){
            throw SQLException("No se ha podido obtener las operaciones ${e.message}")
        } catch (e: SQLTimeoutException){
            throw SQLException("Se ha terminado el tiempo de espera para obtener las operaciones ${e.message}")
        }
        return operaciones
    }

    override fun actualizar(objeto: LogOperacion) {
        TODO("Not yet implemented")
    }


}