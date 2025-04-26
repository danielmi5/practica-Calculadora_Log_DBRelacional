package es.iesraprog2425.pruebaes.utils

object Utils {
    fun obtenerColumansSql(columnas: List<Pair<String, String>>, clavePrimaria: Pair<String, String>? = null): String{
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
}