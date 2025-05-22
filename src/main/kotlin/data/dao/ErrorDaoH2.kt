package es.iesraprog2425.pruebaes.data.dao

import data.dao.IDao
import es.iesraprog2425.pruebaes.model.LogError
import es.iesraprog2425.pruebaes.model.LogOperacion
import java.sql.SQLException
import java.sql.SQLTimeoutException
import javax.sql.DataSource

class ErrorDaoH2(private val ds: DataSource) : IDao<LogError>{
    override fun crear(operacion: LogError) {
        val sql = "INSERT INTO ERROR (fecha, hora, msjError) VALUES (?, ?, ?)"
        try {
            ds.connection.use { conn -> conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, operacion.fecha)
                stmt.setString(2, operacion.hora)
                stmt.setString(3, operacion.msjError)
                }
            }
        } catch (e: SQLException){
            throw SQLException("No se ha podido insertar el error ${e.message}")
        } catch (e: SQLTimeoutException){
            throw SQLException("Se ha terminado el tiempo de espera para insertar el error ${e.message}")
        }

    }

    override fun obtenerPorFecha(fecha: String): List<LogError> {
        val errores = mutableListOf<LogError>()
        val sql = "SELECT * FROM ERROR WHERE fecha = ?"
        try {
            ds.connection.use { conn -> conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, fecha)
                stmt.executeQuery().use {
                        rs ->
                    while (rs.next()){
                        val fech = rs.getString(1)
                        val hora = rs.getString(2)
                        val msjError = rs.getString(3)
                        errores.add(LogError(msjError, fech, hora))
                    }
                }
            }
            }
        } catch (e: SQLException){
            throw SQLException("No se ha podido obtener los errores ${e.message}")
        } catch (e: SQLTimeoutException){
            throw SQLException("Se ha terminado el tiempo para obtener los errores ${e.message}")
        }
        return errores
    }

    override fun obtenerPorHora(hour: String): List<LogError> {
        val errores = mutableListOf<LogError>()
        val sql = "SELECT * FROM ERROR WHERE hora = ?"
        try {
            ds.connection.use { conn -> conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, hour)
                stmt.executeQuery().use {
                        rs ->
                    while (rs.next()){
                        val fech = rs.getString(1)
                        val hora = rs.getString(2)
                        val msjError = rs.getString(3)
                        errores.add(LogError(msjError, fech, hora))
                    }
                }
            }
            }
        } catch (e: SQLException){
            throw SQLException("No se ha podido obtener los errores ${e.message}")
        } catch (e: SQLTimeoutException){
            throw SQLException("Se ha terminado el tiempo para obtener los errores ${e.message}")
        }
        return errores
    }

    override fun obtenerPorFechaYHora(fecha: String, hora: String): LogError? {
        val errores = mutableListOf<LogError>()
        val sql = "SELECT * FROM ERROR WHERE fecha = ? AND hora = ?"
        try {
            ds.connection.use { conn -> conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, fecha)
                stmt.setString(1, hora)
                stmt.executeQuery().use {
                        rs ->
                    return if (rs.next()){
                        val fech = rs.getString(1)
                        val hor = rs.getString(2)
                        val msjError = rs.getString(3)
                        LogError(msjError, fech, hor)
                    } else null
                }
            }
            }
        } catch (e: SQLException){
            throw SQLException("No se ha podido obtener el error ${e.message}")
        } catch (e: SQLTimeoutException){
            throw SQLException("Se ha terminado el tiempo para obtener el error ${e.message}")
        }


    }

    override fun eliminar(fecha: String, hora: String): Boolean {
        val operaciones = mutableListOf<LogOperacion>()
        val sql = "DELETE FROM ERROR WHERE fecha = ? AND hora = ?"
        try {
            ds.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, fecha)
                    stmt.setString(2, hora)
                    return stmt.executeUpdate() > 0
                }
            }
        }catch (e: SQLException){
            throw SQLException("No se ha podido eliminar el error ${e.message}")
        } catch (e: SQLTimeoutException){
            throw SQLException("Se ha pasado el tiempo para eliminar el error ${e.message}")
        }

    }

    override fun eliminarPorFecha(fecha: String): Boolean {
        val sql = "DELETE FROM ERROR WHERE fecha = ?"
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
        val sql = "DELETE FROM ERROR WHERE hora = ?"
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



    override fun obtenerTodos(): List<LogError> {
        val operaciones = mutableListOf<LogError>()
        val sql = "SELECT * FROM ERROR"
        try {
            ds.connection.use { conn -> conn.createStatement().use { stmt ->
                stmt.executeQuery(sql).use {
                        rs ->
                    while (rs.next()){
                        val fech = rs.getString(1)
                        val hora = rs.getString(2)
                        val msjError = rs.getString(3)
                        LogError(msjError, fech, hora)
                    }
                }
            }
            }
        } catch (e: SQLException){
            throw SQLException("No se ha podido obtener los errores ${e.message}")
        } catch (e: SQLTimeoutException){
            throw SQLException("Se ha terminado el tiempo de espera para obtener los errores ${e.message}")
        }
        return operaciones
    }

    override fun actualizar(error: LogError) {
        TODO("Not yet implemented")
    }

}