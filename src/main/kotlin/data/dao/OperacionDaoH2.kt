package es.iesraprog2425.pruebaes.data.dao

import data.dao.IDao
import es.iesraprog2425.pruebaes.model.LogOperacion
import es.iesraprog2425.pruebaes.model.Operadores
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.SQLTimeoutException
import javax.sql.DataSource

/**
 * Implementación de [IDao] para la entidad [LogOperacion] usando una base de datos H2.
 *
 * @property ds Fuente de datos (DataSource) para conexión a la base de datos.
 */
class OperacionDaoH2(private val ds: DataSource) : IDao<LogOperacion> {

    /**
     * Inserta una nueva operación en la tabla OPERACION.
     *
     * @param operacion Objeto [LogOperacion] que se insertará.
     * @throws SQLException Si ocurre un error en la inserción.
     */
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
                stmt.execute()
            } }
        } catch (e: SQLException) {
            throw SQLException("No se ha podido insertar la operación: ${e.message}")
        } catch (e: SQLTimeoutException) {
            throw SQLException("Se ha terminado el tiempo de espera para insertar la operación: ${e.message}")
        }
    }

    /**
     * Obtiene la lista de operaciones realizadas en una fecha específica.
     *
     * @param fecha Fecha para filtrar las operaciones (formato esperado por la BD).
     * @return Lista de [LogOperacion] que coinciden con la fecha.
     * @throws SQLException Si ocurre un error al consultar.
     */
    override fun obtenerPorFecha(fecha: String): List<LogOperacion> {
        val operaciones = mutableListOf<LogOperacion>()
        val sql = "SELECT * FROM OPERACION WHERE fecha = ?"
        try {
            ds.connection.use { conn -> conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, fecha)
                stmt.executeQuery().use { rs ->
                    while (rs.next()) {
                        val fech = rs.getString(1)
                        val hora = rs.getString(2)
                        val num1 = rs.getDouble(3)
                        val operador = Operadores.getOperador(rs.getString(4))
                            ?: throw IllegalArgumentException("Datos no válidos en la base de datos")
                        val num2 = rs.getDouble(5)
                        val res = rs.getDouble(6)
                        operaciones.add(LogOperacion(num1, operador, num2, res, fech, hora))
                    }
                }
            } }
        } catch (e: SQLException) {
            throw SQLException("No se ha podido obtener las operaciones: ${e.message}")
        } catch (e: SQLTimeoutException) {
            throw SQLException("Se ha terminado el tiempo de espera para obtener las operaciones: ${e.message}")
        }
        return operaciones
    }

    /**
     * Obtiene la lista de operaciones realizadas en una hora específica.
     *
     * @param hour Hora para filtrar las operaciones (formato esperado por la BD).
     * @return Lista de [LogOperacion] que coinciden con la hora.
     * @throws SQLException Si ocurre un error al consultar.
     */
    override fun obtenerPorHora(hour: String): List<LogOperacion> {
        val operaciones = mutableListOf<LogOperacion>()
        val sql = "SELECT * FROM OPERACION WHERE hora = ?"
        try {
            ds.connection.use { conn -> conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, hour)
                stmt.executeQuery().use { rs ->
                    while (rs.next()) {
                        val fech = rs.getString(1)
                        val hora = rs.getString(2)
                        val num1 = rs.getDouble(3)
                        val operador = Operadores.getOperador(rs.getString(4))
                            ?: throw IllegalArgumentException("Datos no válidos en la base de datos")
                        val num2 = rs.getDouble(5)
                        val res = rs.getDouble(6)
                        operaciones.add(LogOperacion(num1, operador, num2, res, fech, hora))
                    }
                }
            } }
        } catch (e: SQLException) {
            throw SQLException("No se ha podido obtener las operaciones: ${e.message}")
        } catch (e: SQLTimeoutException) {
            throw SQLException("Se ha terminado el tiempo de espera para obtener las operaciones: ${e.message}")
        }
        return operaciones
    }

    /**
     * Obtiene una operación que coincide con la fecha y hora específicas.
     *
     * @param fecha Fecha de la operación.
     * @param hora Hora de la operación.
     * @return [LogOperacion] encontrada o null si no existe.
     * @throws SQLException Si ocurre un error al consultar.
     */
    override fun obtenerPorFechaYHora(fecha: String, hora: String): LogOperacion? {
        val sql = "SELECT * FROM OPERACION WHERE fecha = ? AND hora = ?"
        try {
            ds.connection.use { conn -> conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, fecha)
                stmt.setString(2, hora)
                stmt.executeQuery().use { rs ->
                    return if (rs.next()) {
                        val fech = rs.getString(1)
                        val hor = rs.getString(2)
                        val num1 = rs.getDouble(3)
                        val operador = Operadores.getOperador(rs.getString(4))
                            ?: throw IllegalArgumentException("Datos no válidos en la base de datos")
                        val num2 = rs.getDouble(5)
                        val res = rs.getDouble(6)
                        LogOperacion(num1, operador, num2, res, fech, hor)
                    } else null
                }
            } }
        } catch (e: SQLException) {
            throw SQLException("No se ha podido obtener la operación: ${e.message}")
        } catch (e: SQLTimeoutException) {
            throw SQLException("Se ha terminado el tiempo de espera para obtener la operación: ${e.message}")
        }
    }

    /**
     * Elimina una operación según la fecha y hora dadas.
     *
     * @param fecha Fecha de la operación a eliminar.
     * @param hora Hora de la operación a eliminar.
     * @return `true` si se eliminó alguna fila, `false` si no se encontró.
     * @throws SQLException Si ocurre un error al eliminar.
     */
    override fun eliminar(fecha: String, hora: String): Boolean {
        val sql = "DELETE FROM OPERACION WHERE fecha = ? AND hora = ?"
        try {
            ds.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, fecha)
                    stmt.setString(2, hora)
                    return stmt.executeUpdate() > 0
                }
            }
        } catch (e: SQLException) {
            throw SQLException("No se ha podido eliminar la operación: ${e.message}")
        } catch (e: SQLTimeoutException) {
            throw SQLException("Se ha pasado el tiempo para eliminar la operación: ${e.message}")
        }
    }

    /**
     * Elimina todas las operaciones realizadas en una fecha específica.
     *
     * @param fecha Fecha para eliminar las operaciones.
     * @return Número de filas eliminadas.
     * @throws SQLException Si ocurre un error al eliminar.
     */
    override fun eliminarPorFecha(fecha: String): Int {
        val sql = "DELETE FROM OPERACION WHERE fecha = ?"
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
     * Elimina todas las operaciones realizadas en una hora específica.
     *
     * @param hora Hora para eliminar las operaciones.
     * @return Número de filas eliminadas.
     * @throws SQLException Si ocurre un error al eliminar.
     */
    override fun eliminarPorHora(hora: String): Int {
        val sql = "DELETE FROM OPERACION WHERE hora = ?"
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
     * Obtiene todas las operaciones registradas.
     *
     * @return Lista con todas las operaciones.
     * @throws SQLException Si ocurre un error al consultar.
     */
    override fun obtenerTodos(): List<LogOperacion> {
        val operaciones = mutableListOf<LogOperacion>()
        val sql = "SELECT * FROM OPERACION"
        try {
            ds.connection.use { conn -> conn.createStatement().use { stmt ->
                stmt.executeQuery(sql).use { rs ->
                    while (rs.next()) {
                        val fech = rs.getString(1)
                        val hora = rs.getString(2)
                        val num1 = rs.getDouble(3)
                        val operador = Operadores.getOperador(rs.getString(4))
                            ?: throw IllegalArgumentException("Datos no válidos en la base de datos")
                        val num2 = rs.getDouble(5)
                        val res = rs.getDouble(6)
                        operaciones.add(LogOperacion(num1, operador, num2, res, fech, hora))
                    }
                }
            } }
        } catch (e: SQLException) {
            throw SQLException("No se ha podido obtener las operaciones: ${e.message}")
        } catch (e: SQLTimeoutException) {
            throw SQLException("Se ha terminado el tiempo de espera para obtener las operaciones: ${e.message}")
        }
        return operaciones
    }

    /**
     * Actualiza una operación existente en la base de datos.
     *
     * @param objeto Objeto [LogOperacion] con los datos actualizados.
     * @throws NotImplementedError Actualmente no implementado.
     */
    override fun actualizar(objeto: LogOperacion) {
        TODO("Not yet implemented")
    }
}
