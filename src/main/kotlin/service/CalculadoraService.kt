package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.model.Operadores

/**
 * Implementación del servicio [RealizarOperacionesService] que se encarga de
 * ejecutar operaciones matemáticas básicas (suma, resta, multiplicación y división).
 */
class CalculadoraService : RealizarOperacionesService {

    /**
     * Realiza un cálculo matemático entre dos números según el operador proporcionado.
     *
     * @param numero1 Primer número del cálculo.
     * @param operador Operador matemático que determina la operación a realizar.
     * @param numero2 Segundo número del cálculo.
     * @return Resultado de la operación matemática.
     *
     * @throws ArithmeticException si se intenta dividir por cero.
     */
    override fun realizarCalculo(numero1: Double, operador: Operadores, numero2: Double): Double {
        return when (operador) {
            Operadores.SUMA -> numero1 + numero2
            Operadores.RESTA -> numero1 - numero2
            Operadores.MULTIPLICACION -> numero1 * numero2
            Operadores.DIVISION -> numero1 / numero2
        }
    }
}




