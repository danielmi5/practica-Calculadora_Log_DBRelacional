package es.iesraprog2425.pruebaes.data

interface FichLog: RepositorioLog {
    fun obtenerRuta(): String?
    fun crearLog(rutaLog: String)
    fun guardarRutaLog(ruta: String)
}