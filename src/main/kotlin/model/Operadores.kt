package es.iesraprog2425.pruebaes.model

/**
 * Enum que representa los operadores matemáticos disponibles en el sistema.
 *
 * Cada operador puede estar asociado a uno o más símbolos que lo representan.
 *
 * @property simbolos Lista de caracteres que representan al operador.
 */
enum class Operadores(val simbolos: List<Char>) {
    /** Operador de suma: '+' */
    SUMA(listOf('+')),

    /** Operador de resta: '-' */
    RESTA(listOf('-')),

    /** Operador de multiplicación: '*', 'x' */
    MULTIPLICACION(listOf('*', 'x')),

    /** Operador de división: ':', '/' */
    DIVISION(listOf(':', '/'));

    companion object {

        /**
         * Devuelve el operador correspondiente a un símbolo dado.
         *
         * @param operador Carácter representando un operador (por ejemplo, '+', '-', '*', '/').
         * @return El operador correspondiente o null si no coincide con ninguno.
         */
        fun getOperador(operador: Char?): Operadores? =
            operador?.let { op -> entries.find { op in it.simbolos } }

        /**
         * Devuelve el operador correspondiente a su nombre textual.
         *
         * @param operacion Nombre del operador como string (por ejemplo, "SUMA").
         * @return El operador correspondiente o null si no coincide con ninguno.
         */
        fun getOperador(operacion: String): Operadores? = when (operacion) {
            "SUMA" -> SUMA
            "RESTA" -> RESTA
            "MULTIPLICACION" -> MULTIPLICACION
            "DIVISION" -> DIVISION
            else -> null
        }
    }
}
