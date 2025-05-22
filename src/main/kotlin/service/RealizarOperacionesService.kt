package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.model.Operadores

/**
 * Interfaz que define el servicio para realizar operaciones matemáticas básicas.
 */
interface RealizarOperacionesService {

    /**
     * Realiza un cálculo matemático entre dos números usando un operador especificado.
     *
     * @param numero1 Primer operando en la operación.
     * @param operador Operador matemático a aplicar (por ejemplo, suma, resta, multiplicación, división).
     * @param numero2 Segundo operando en la operación.
     * @return El resultado del cálculo como un valor [Double].
     */
    fun realizarCalculo(numero1: Double, operador: Operadores, numero2: Double): Double
}