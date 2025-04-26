package es.iesraprog2425.pruebaes.utils

import java.sql.Connection

interface GestiónBaseDeDatos {
    fun conectarBD(): Connection
    fun cerrarBD(conexion: Connection)
}