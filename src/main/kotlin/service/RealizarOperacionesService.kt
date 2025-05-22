package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.model.Operadores

interface RealizarOperacionesService {
    fun realizarCalculo(numero1: Double, operador: Operadores, numero2: Double): Double
}