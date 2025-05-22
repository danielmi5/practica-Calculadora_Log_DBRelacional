package es.iesraprog2425.pruebaes.data.dao

import data.dao.IDao
import es.iesraprog2425.pruebaes.model.LogError
import es.iesraprog2425.pruebaes.model.LogOperacion
import java.sql.SQLException
import java.sql.SQLTimeoutException
import javax.sql.DataSource

/**
 * Implementación de [IDao] para la gestión de registros de errores en una base de datos H2.
 *
 * Esta clase permite crear, consultar, eliminar y obtener registros de errores almacenados
 * en una tabla llamada `ERROR` con las columnas `fecha`, `hora` y `msjError`.
 *
 * @property ds Fuente de datos utilizada para obtener conexiones a la base de datos.
 */
class ErrorDaoH2(private val ds: DataSource) : IDao<LogError>{
    /**
     * Inserta un nuevo registro de error en la base de datos.
     *
     * @param operacion El objeto [LogError] que contiene la información del error a insertar.
     * @throws SQLException si ocurre un error durante la inserción en la base de datos.
     */
    override fun crear(operacion: LogError) {
        val sql = "INSERT INTO ERROR (fecha, hora, msjError) VALUES (?, ?, ?)"
        try {
            ds.connection.use { conn -> conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, operacion.fecha)
                stmt.setString(2, operacion.hora)
                stmt.setString(3, operacion.msjError)

                stmt.execute()
                }
            }
        } catch (e: SQLException){
            throw SQLException("No se ha podido insertar el error ${e.message}")
        } catch (e: SQLTimeoutException){
            throw SQLException("Se ha terminado el tiempo de espera para insertar el error ${e.message}")
        }

    }

    /**
     * Obtiene una lista de errores registrados en una fecha específica.
     *
     * @param fecha La fecha para filtrar los errores (formato esperado según la base de datos).
     * @return Lista de [LogError] correspondientes a la fecha indicada.
     * @throws SQLException si ocurre un error al realizar la consulta.
     */
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

    /**
     * Obtiene una lista de errores registrados en una hora específica.
     *
     * @param hour La hora para filtrar los errores (formato esperado según la base de datos).
     * @return Lista de [LogError] correspondientes a la hora indicada.
     * @throws SQLException si ocurre un error al realizar la consulta.
     */
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

    /**
     * Obtiene un único error registrado en una fecha y hora específica.
     *
     * @param fecha La fecha del error.
     * @param hora La hora del error.
     * @return Un objeto [LogError] si se encuentra, o `null` si no existe ningún error con esos datos.
     * @throws SQLException si ocurre un error al realizar la consulta.
     */
    override fun obtenerPorFechaYHora(fecha: String, hora: String): LogError? {
        val errores = mutableListOf<LogError>()
        val sql = "SELECT * FROM ERROR WHERE fecha = ? AND hora = ?"
        try {
            ds.connection.use { conn -> conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, fecha)
                stmt.setString(2, hora)
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

    /**
     * Elimina un error de la base de datos según la fecha y hora indicadas.
     *
     * @param fecha La fecha del error a eliminar.
     * @param hora La hora del error a eliminar.
     * @return `true` si se eliminó al menos un registro, `false` en caso contrario.
     * @throws SQLException si ocurre un error al eliminar el registro.
     */
    override fun eliminar(fecha: String, hora: String): Boolean {
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

    /**
     * Elimina todos los errores registrados en una fecha específica.
     *
     * @param fecha La fecha para eliminar los errores.
     * @return La cantidad de registros eliminados.
     * @throws SQLException si ocurre un error durante la eliminación.
     */
    override fun eliminarPorFecha(fecha: String): Int {
        val sql = "DELETE FROM ERROR WHERE fecha = ?"
        try {
            ds.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, fecha)
                    return stmt.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            throw SQLException("No se ha podido eliminar por fecha: ${e.message}")
        } catch (e: SQLTimeoutException) {
            throw SQLException("Se ha terminado el tiempo para eliminar por fecha: ${e.message}")
        }
    }

    /**
     * Elimina todos los errores registrados en una hora específica.
     *
     * @param hora La hora para eliminar los errores.
     * @return La cantidad de registros eliminados.
     * @throws SQLException si ocurre un error durante la eliminación.
     */
    override fun eliminarPorHora(hora: String): Int {
        val sql = "DELETE FROM ERROR WHERE hora = ?"
        try {
            ds.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, hora)
                    return stmt.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            throw SQLException("No se ha podido eliminar por hora: ${e.message}")
        } catch (e: SQLTimeoutException) {
            throw SQLException("Se ha terminado el tiempo para eliminar por hora: ${e.message}")
        }
    }


    /**
     * Obtiene todos los registros de errores almacenados en la base de datos.
     *
     * @return Lista con todos los [LogError] almacenados.
     * @throws SQLException si ocurre un error durante la consulta.
     */
    override fun obtenerTodos(): List<LogError> {
        val errores = mutableListOf<LogError>()
        val sql = "SELECT * FROM ERROR"
        try {
            ds.connection.use { conn -> conn.createStatement().use { stmt ->
                stmt.executeQuery(sql).use {
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
            throw SQLException("Se ha terminado el tiempo de espera para obtener los errores ${e.message}")
        }
        return errores
    }

    /**
     * Actualiza un registro de error en la base de datos.
     *
     * Este método no está implementado.
     *
     * @param error El objeto [LogError] con los datos actualizados.
     */
    override fun actualizar(error: LogError) {
        TODO("Not yet implemented")
    }

}