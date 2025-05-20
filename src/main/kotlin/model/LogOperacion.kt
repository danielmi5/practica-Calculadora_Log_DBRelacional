package es.iesraprog2425.pruebaes.model

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
        return "${this::class.simpleName?.replace("Log","")}| $fecha | $hora | --> $num1 ${operador.simbolos[0]} $num2 = $resultado"
    }
}