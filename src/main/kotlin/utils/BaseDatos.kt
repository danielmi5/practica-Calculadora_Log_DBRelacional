package es.iesraprog2425.pruebaes.utils

import data.db.DataSource
import java.io.File
/**
 * Objeto responsable de la gestión y creación de la base de datos.
 *
 * Proporciona métodos para crear la base de datos y opcionalmente borrar los datos existentes
 * ejecutando scripts SQL desde archivos externos.
 */
object BaseDatos {
    private const val SCRIPT_SQL = "./src/main/resources/scriptBD.sql"
    private const val SCRIPT_BORRADO_SQL = "./src/main/resources/scriptBorradoBD.sql"

    /**
     * Crea la base de datos ejecutando un script SQL.
     *
     * Si el parámetro [borrarDatos] es `true`, primero ejecuta un script para borrar los datos existentes
     * antes de crear la estructura de la base de datos.
     *
     * @param borrarDatos Indica si se deben borrar los datos existentes antes de crear la base (por defecto `false`).
     */
    fun crearBaseDeDatos(borrarDatos: Boolean = false) {
        if (borrarDatos) {
            val script = File(SCRIPT_BORRADO_SQL).readText()
            DataSource.obtenerDataSource().connection.use {
                it.createStatement().use {
                    it.execute(script)
                }
            }
        }

        val script = File(SCRIPT_SQL).readText()
        DataSource.obtenerDataSource().connection.use {
            it.createStatement().use {
                it.execute(script)
            }
        }
    }
}