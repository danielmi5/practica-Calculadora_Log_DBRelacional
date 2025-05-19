package es.iesraprog2425.pruebaes.model

class LogOperacion(val num1: Double, val operador: Operadores, val num2: Double, val resultado: Double) : Log() {
    override fun toString(): String {
        return "${this::class.simpleName?.replace("Log","")} --> $num1 ${operador.simbolos[0]} $num2 = $resultado"
    }
}