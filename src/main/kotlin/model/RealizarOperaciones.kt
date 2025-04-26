package es.iesraprog2425.pruebaes.model

interface RealizarOperaciones {
    fun realizarCalculo(numero1: Double, operador: Operadores, numero2: Double): Double
}