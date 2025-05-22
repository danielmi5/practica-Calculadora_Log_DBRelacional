package es.iesraprog2425.pruebaes.model

import es.iesraprog2425.pruebaes.model.LogError.Companion
import es.iesraprog2425.pruebaes.utils.Time

/**
 * Clase que representa un registro (log) de una operación matemática realizada.
 *
 * @property num1 Primer número de la operación.
 * @property operador Operador matemático utilizado.
 * @property num2 Segundo número de la operación.
 * @property resultado Resultado de la operación.
 * @param fecha Fecha en formato 'dd-MM-yyyy' cuando se realizó la operación.
 * @param hora Hora en formato 'HH:mm:ss' cuando se realizó la operación.
 */
class LogOperacion(
    val num1: Double,
    val operador: Operadores,
    val num2: Double,
    val resultado: Double,
    fecha: String,
    hora: String
) : Log(fecha, hora) {

    companion object {
        /** Nombre simplificado de la clase para mostrar en texto, sin el prefijo "Log". */
        val nombreClase = LogOperacion::class.simpleName?.replace("Log", "")

        /**
         * Crea una instancia de [LogOperacion] con la fecha y hora actual.
         *
         * @param num1 Primer número de la operación.
         * @param operador Operador matemático utilizado.
         * @param num2 Segundo número de la operación.
         * @param resultado Resultado de la operación.
         * @return Nueva instancia de [LogOperacion] con fecha y hora actuales.
         */
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

    /**
     * Devuelve una representación en texto de la operación registrada,
     * incluyendo la información base del log y la operación realizada.
     */
    override fun toString(): String {
        return "${super.toString().replace("Log", nombreClase ?: "Operación")} $num1 ${operador.simbolos[0]} $num2 = $resultado"
    }

    /**
     * Obtiene la descripción de la operación en formato legible.
     *
     * @return Cadena con la operación y resultado, ejemplo: "5.0 + 3.0 = 8.0".
     */
    override fun obtenerLog(): String {
        return "$num1 ${operador.simbolos[0]} $num2 = $resultado"
    }
}
