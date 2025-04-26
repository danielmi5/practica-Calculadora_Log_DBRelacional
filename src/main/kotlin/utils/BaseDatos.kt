package es.iesraprog2425.pruebaes.utils

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class BaseDatos(private val url: String, private val driver: String, private val usuario: String, private val contra: String) : GestiónBaseDeDatos {
    override fun conectarBD(): Connection {
        try {
            Class.forName(driver)
            val conexion = DriverManager.getConnection(url, usuario, contra)
            println("Conexión exitosa")
            return conexion
        } catch (e: SQLException) {
            println("Error en la conexión: ${e.message}")
        } catch (e: ClassNotFoundException) {
            println("No se encontró el driver JDBC: ${e.message}")
        }
        throw IllegalArgumentException("ERROR CONECTAR BD")
    }

    override fun cerrarBD(conexion: Connection) {
        conexion.close()
        println("Conexión cerrada")
    }
}