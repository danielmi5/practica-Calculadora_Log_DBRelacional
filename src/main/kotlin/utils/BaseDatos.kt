package es.iesraprog2425.pruebaes.utils

import data.db.DataSource
import java.io.File

object BaseDatos {
    private const val SCRIPT_SQL = "./src/main/resources/scriptBD.sql"
    private const val SCRIPT_BORRADO_SQL = "./src/main/resources/scriptBorradoBD.sql"

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