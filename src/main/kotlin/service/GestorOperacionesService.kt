package es.iesraprog2425.pruebaes.service

import es.iesraprog2425.pruebaes.app.InfoCalcException
import es.iesraprog2425.pruebaes.model.LogOperacion
import es.iesraprog2425.pruebaes.model.Operadores
import es.iesraprog2425.pruebaes.ui.IEntradaSalida

class GestorOperacionesService(private val ui: IEntradaSalida, private val calculador: RealizarOperacionesService) : OperacionesService{
    override fun pedirNumero(msj: String, msjError: String): Double {
        return ui.pedirDouble(msj) ?: throw InfoCalcException(msjError)
    }

    override fun pedirInfo() = Triple(
        pedirNumero("Introduce el primer número", "El primer número no es válido!"),
        Operadores.getOperador(ui.pedirInfo("Introduce el operador (+, -, *, /)").firstOrNull())
            ?: throw InfoCalcException("El operador no es válido!"),
        pedirNumero("Introduce el segundo número", "El segundo número no es válido!"))


    override fun realizarOperacion(): LogOperacion {
        val (num1, op, num2) = pedirInfo()
        return LogOperacion.crear(num1, op, num2, calculador.realizarCalculo(num1, op, num2))
    }

    override fun realizarOperacion(num1: Double, num2: Double, op: Operadores): LogOperacion{
        return LogOperacion.crear(num1, op, num2, calculador.realizarCalculo(num1, op, num2))
    }
}