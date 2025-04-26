package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.model.Operadores

interface ServiceOperaciones {
    fun pedirNumero(msj: String, msjError: String = "Número no válido!"): Double
    fun pedirInfo(): Triple<Double, Operadores, Double>
    fun realizarOperacion(): String
    fun realizarOperacion(num1: Double, num2: Double, op: Operadores): String
}