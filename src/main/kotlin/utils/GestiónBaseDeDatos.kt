package es.iesraprog2425.pruebaes.utils

import java.sql.PreparedStatement

interface GestiónBaseDeDatos {
    fun conectarBD()
    fun cerrarBD()
    fun crearTabla(nombreTabla: String, columnas: List<Pair<String, String>>, clavePrimaria: Pair<String, String>? = null)
    fun borrarTabla(nombreTabla: String)
    fun eliminarDatosTabla(nombreTabla: String)
    fun insertarDatosTabla(nombreTabla: String, valores: List<Any>, listaColumnasAInsertar: List<String>)
}