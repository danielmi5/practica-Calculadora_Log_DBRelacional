package es.iesraprog2425.pruebaes.utils

import es.iesraprog2425.pruebaes.model.Log
import es.iesraprog2425.pruebaes.model.TipoLog
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class BaseDatos(private val url: String, private val driver: String, private val usuario: String, private val contra: String) : GestionBaseDeDatos {
    private var conexion: Connection? = null;

    override fun conectarBD() {
        try {
            Class.forName(driver)
            conexion = DriverManager.getConnection(url, usuario, contra)
            println("Conexión exitosa")

            if (!existeTabla("Log")) crearTabla(Log::class.simpleName.toString(), listOf(Pair("Fecha", "TEXT"), Pair("Hora", "TEXT"), Pair("Tipo", "TEXT"), Pair("Registro", "TEXT")))

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
        conexion?.prepareStatement("CREATE TABLE $nombreTabla (${Utils.obtenerColumansSql(columnas, clavePrimaria)})")?.execute() ?: throw SQLException("No se pudo crear la tabla")
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

    override fun insertarMultiplesDatosTabla(nombreTabla: String, listaValores: List<List<Any>>, listaColumnasAInsertar: List<String>, seSobrescribe: Boolean
    ) {
        if (seSobrescribe) eliminarDatosTabla(nombreTabla)

        //VARIOS INSERT NO MUY BIEN ÓPTIMO - PERO PARA AHORRARME LÍNEAS UTILIZO LA FUNCIÓN DE ARRIBA
        //TODO: PLANTEAR SI UTILIZAR UN SOLO INSERT...
        for (valores in listaValores) {
            insertarDatosTabla(nombreTabla, valores, listaColumnasAInsertar)
        }

    }

    override fun obtenerDatosTabla(nombreTabla: String): List<Log> { // TODO: Devolver consulta y obtener los datos en otra clase, para permitir escalabilidad
        val logs = mutableListOf<Log>()
        val statement = conexion?.createStatement()
        val consulta = statement?.executeQuery("SELECT * FROM $nombreTabla") ?: throw SQLException("No se pudo mantener la conexión")
        while (consulta.next()) {
            val fecha = consulta.getString("fecha")
            val hora = consulta.getString("hora")
            val tipoLog = consulta.getString("tipo")
            val registro = consulta.getString("registro")
            logs.add(Log(fecha, hora, TipoLog.obtenerTipo(tipoLog), registro))
        }
        return logs
    }

    private fun existeTabla(nombreTabla: String): Boolean {
        return try {
            conexion?.prepareStatement("SELECT 1 FROM $nombreTabla LIMIT 1")?.executeQuery() ?: throw IllegalArgumentException("")
            true
        } catch (e: Exception) {
            false
        }
    }
}