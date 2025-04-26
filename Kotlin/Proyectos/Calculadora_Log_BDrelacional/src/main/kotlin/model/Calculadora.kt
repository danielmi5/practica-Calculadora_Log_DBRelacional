package es.iesraprog2425.pruebaes.model

class Calculadora : RealizarOperaciones {
    override fun realizarCalculo(numero1: Double, operador: Operadores, numero2: Double): Double {
        return when (operador) {
            Operadores.SUMA -> numero1 + numero2
            Operadores.RESTA -> numero1 - numero2
            Operadores.MULTIPLICACION -> numero1 * numero2
            Operadores.DIVISION -> numero1 / numero2
        }
    }
}




