package es.iesraprog2425.pruebaes.utils

import es.iesraprog2425.pruebaes.model.Log
import es.iesraprog2425.pruebaes.model.TipoLog

object Utils {
    fun obtenerColumansSql(columnas: List<Pair<String, String>>, clavePrimaria: Pair<String, String>?): String{
        val cadena = if (clavePrimaria != null){
            "${clavePrimaria.first} ${clavePrimaria.second}, ${obtenerColumnas(columnas)}"
        } else obtenerColumnas(columnas)
        return cadena
    }

    private fun obtenerColumnas(columnas: List<Pair<String, String>>): String{
        var cadena = ""
        columnas.forEach { (nombreColumna, tipoColumna) -> cadena += "$nombreColumna $tipoColumna, " }

        return cadena.substring(0, cadena.length - 2)
    }

    fun pasarValues(listaColumnas: List<String>): String{
        return if (listaColumnas.size > 1) "?, ".repeat(listaColumnas.size).substring(0, listaColumnas.size*3-2) else "?"
    }

    fun pasarColumnas(listaColumnas: List<String>): String{
        return listaColumnas.toString().substring(1, listaColumnas.toString().length-1)
    }

    fun Log.mapearValores(): List<String>{
        return this.toString().split("|").map { it.trim() }
    }

    fun List<String>.mapearALog(): List<Log>{
        val listaLogs = mutableListOf<Log>()
        for (linea in this) {
            val valores = linea.split("|").map { it.trim() }
            listaLogs.add(Log(valores[0],valores[1], TipoLog.obtenerTipo(valores[2]),valores[3]))
        }
        return listaLogs
    }
}