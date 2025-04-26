package es.iesraprog2425.pruebaes.utils

import java.io.File

interface GestionFicheros {
    fun aniadirLinea(ruta: String, linea: String)
    fun existeFichero(ruta: String): Boolean
    fun existeDirectorio(ruta: String): Boolean
    fun crearDirectorio(ruta: String)
    fun crearFichero(ruta: String)
    fun obtenerRutaUltimoFicheroModificado(ruta: String): String
    fun leerFichero(ruta: String): List<String>
    fun tieneFicheros(ruta: String): Boolean
}