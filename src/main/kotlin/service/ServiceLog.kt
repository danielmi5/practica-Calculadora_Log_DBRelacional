package es.iesraprog2425.pruebaes.service

interface ServiceLog {
    fun crearFicheroLog(ruta: String): String
    fun obtenerUltimoLog(ruta: String): String
    fun añadirRegistro(ruta: String, linea: String)
}