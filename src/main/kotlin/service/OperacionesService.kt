package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.model.LogOperacion
import es.iesraprog2425.pruebaes.model.Operadores

interface OperacionesService {
    fun pedirNumero(msj: String, msjError: String = "Número no válido!"): Double
    fun pedirInfo(): Triple<Double, Operadores, Double>
    fun realizarOperacion(): LogOperacion
    fun realizarOperacion(num1: Double, num2: Double, op: Operadores): LogOperacion
}