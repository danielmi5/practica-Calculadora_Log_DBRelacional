package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.app.InfoCalcException
import es.iesraprog2425.pruebaes.model.LogOperacion
import es.iesraprog2425.pruebaes.model.Operadores
import es.iesraprog2425.pruebaes.ui.IEntradaSalida

/**
 * Servicio responsable de gestionar la interacción entre el usuario y el servicio de cálculo.
 * Implementa la interfaz [OperacionesService].
 *
 * @property ui Interfaz para entrada/salida con el usuario.
 * @property calculador Servicio encargado de realizar los cálculos matemáticos.
 */
class GestorOperacionesService(
    private val ui: IEntradaSalida,
    private val calculador: RealizarOperacionesService
) : OperacionesService {

    /**
     * Solicita un número al usuario, mostrando un mensaje personalizado.
     *
     * @param msj Mensaje que se muestra al pedir el número.
     * @param msjError Mensaje de error lanzado si el número es inválido.
     * @return Número introducido por el usuario.
     * @throws InfoCalcException si el número no es válido.
     */
    override fun pedirNumero(msj: String, msjError: String): Double {
        return ui.pedirDouble(msj) ?: throw InfoCalcException(msjError)
    }

    /**
     * Solicita al usuario los datos necesarios para realizar una operación:
     * dos números y un operador válido.
     *
     * @return Triple con el primer número, el operador y el segundo número.
     * @throws InfoCalcException si alguno de los datos no es válido.
     */
    override fun pedirInfo(): Triple<Double, Operadores, Double> = Triple(
        pedirNumero("Introduce el primer número", "El primer número no es válido!"),
        Operadores.getOperador(ui.pedirInfo("Introduce el operador (+, -, *, /)").firstOrNull())
            ?: throw InfoCalcException("El operador no es válido!"),
        pedirNumero("Introduce el segundo número", "El segundo número no es válido!")
    )

    /**
     * Realiza una operación solicitando los valores al usuario.
     *
     * @return Log de la operación realizada.
     * @throws InfoCalcException si los valores ingresados son inválidos.
     */
    override fun realizarOperacion(): LogOperacion {
        val (num1, op, num2) = pedirInfo()
        return LogOperacion.crear(num1, op, num2, calculador.realizarCalculo(num1, op, num2))
    }

    /**
     * Realiza una operación con los valores proporcionados como argumento.
     *
     * @param num1 Primer número.
     * @param num2 Segundo número.
     * @param op Operador matemático.
     * @return Log de la operación realizada.
     */
    override fun realizarOperacion(num1: Double, num2: Double, op: Operadores): LogOperacion {
        return LogOperacion.crear(num1, op, num2, calculador.realizarCalculo(num1, op, num2))
    }
}