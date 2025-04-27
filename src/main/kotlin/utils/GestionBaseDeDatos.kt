package es.iesraprog2425.pruebaes.utils

import es.iesraprog2425.pruebaes.model.Log

interface GestionBaseDeDatos {
    fun conectarBD()
    fun cerrarBD()
    fun crearTabla(nombreTabla: String, columnas: List<Pair<String, String>>, clavePrimaria: Pair<String, String>? = null)
    fun borrarTabla(nombreTabla: String)
    fun eliminarDatosTabla(nombreTabla: String)
    fun insertarDatosTabla(nombreTabla: String, valores: List<Any>, listaColumnasAInsertar: List<String>)
    fun insertarMultiplesDatosTabla(nombreTabla: String, listaValores: List<List<Any>>, listaColumnasAInsertar: List<String>, seSobrescribe: Boolean)
    fun obtenerDatosTabla(nombreTabla: String): List<Log>
    // fun obtenerDatosTabla(nombreTabla: String, columnas: List<String>): List<String> TODO: En caso de querer datos específicos de un log realizar...
}