package es.iesraprog2425.pruebaes.model

import es.iesraprog2425.pruebaes.model.LogError.Companion
import es.iesraprog2425.pruebaes.utils.Time

class LogOperacion(
    val num1: Double,
    val operador: Operadores,
    val num2: Double,
    val resultado: Double,
    fecha: String,
    hora: String
) : Log(fecha, hora) {

    companion object {
        val nombreClase = LogOperacion::class.simpleName?.replace("Log", "")

        fun crear(
            num1: Double,
            operador: Operadores,
            num2: Double,
            resultado: Double
        ): LogOperacion {
            val (fecha, hora) = Time.obtenerFechayHoraActualF()
            return LogOperacion(num1, operador, num2, resultado, fecha, hora)
        }
    }

    override fun toString(): String {
        return "${super.toString().replace("Log", nombreClase ?: "Operación")} $num1 ${operador.simbolos[0]} $num2 = $resultado"
    }

    override fun obtenerLog(): String{
        return "$num1 ${operador.simbolos[0]} $num2 = $resultado"
    }
}