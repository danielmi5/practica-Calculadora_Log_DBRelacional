package es.iesraprog2425.pruebaes.utils

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException

class BaseDatos(private val url: String, private val driver: String, private val usuario: String, private val contra: String) : GestiónBaseDeDatos {
    private var conexion: Connection? = null;
    override fun conectarBD() {
        try {
            Class.forName(driver)
            conexion = DriverManager.getConnection(url, usuario, contra)
            println("Conexión exitosa")
        } catch (e: SQLException) {
            println("Error en la conexión: ${e.message}")
        } catch (e: ClassNotFoundException) {
            println("No se encontró el driver JDBC: ${e.message}")
        }
    }

    override fun cerrarBD() {
        conexion?.close()
        println("Conexión cerrada")
    }

    override fun crearTabla(
        nombreTabla: String,
        columnas: List<Pair<String, String>>,
        clavePrimaria: Pair<String, String>?
    ) {
        conexion?.prepareStatement("CREATE TABLE $nombreTabla (${Utils.obtenerColumansSql(columnas, clavePrimaria)});")?.execute() ?: throw SQLException("No se pudo crear la tabla")
    }

    override fun borrarTabla(nombreTabla: String) {
        conexion?.prepareStatement("DROP TABLE IF EXISTS $nombreTabla CASCADE")?.execute() ?: throw SQLException("No se pudo borrar la tabla ${nombreTabla.uppercase()}")
    }

    override fun eliminarDatosTabla(nombreTabla: String){
        conexion?.prepareStatement("DELETE FROM $nombreTabla ")?.execute() ?: throw SQLException("No se pudo borrar los datos de la tabla ${nombreTabla.uppercase()}")
    }

    override fun insertarDatosTabla(nombreTabla: String, valores: List<Any>, listaColumnasAInsertar: List<String>) {
        val stm = conexion?.prepareStatement("INSERT INTO $nombreTabla (${Utils.pasarColumnas(listaColumnasAInsertar)}) VALUES (${Utils.pasarValues(listaColumnasAInsertar)})") ?: throw SQLException("No se pudo insertar en la tabla $nombreTabla")
        var cont = 1
        for (valor in valores) {
            when (valor) {
                is String -> stm.setString(cont, valor)
                is Int -> stm.setInt(cont, valor)
                is Double -> stm.setDouble(cont, valor)
            }
            cont++
        }
        stm.executeUpdate()
    }

}