package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.model.LogOperacion
import es.iesraprog2425.pruebaes.model.Operadores

/**
 * Interfaz que define los servicios para interactuar con el usuario y realizar operaciones matemáticas.
 */
interface OperacionesService {

    /**
     * Solicita al usuario que ingrese un número.
     *
     * @param msj Mensaje que se muestra para pedir el número.
     * @param msjError Mensaje de error que se muestra si la entrada no es válida (por defecto "Número no válido!").
     * @return El número ingresado como [Double].
     */
    fun pedirNumero(msj: String, msjError: String = "Número no válido!"): Double

    /**
     * Solicita al usuario que ingrese dos números y un operador.
     *
     * @return Un [Triple] que contiene el primer número, el operador y el segundo número.
     */
    fun pedirInfo(): Triple<Double, Operadores, Double>

    /**
     * Realiza una operación solicitando los datos al usuario.
     *
     * @return Un [LogOperacion] con el resultado y detalles de la operación.
     */
    fun realizarOperacion(): LogOperacion

    /**
     * Realiza una operación con los números y operador dados.
     *
     * @param num1 Primer número.
     * @param num2 Segundo número.
     * @param op Operador a aplicar.
     * @return Un [LogOperacion] con el resultado y detalles de la operación.
     */
    fun realizarOperacion(num1: Double, num2: Double, op: Operadores): LogOperacion
}