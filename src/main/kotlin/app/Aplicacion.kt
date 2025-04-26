package es.iesraprog2425.pruebaes.app


import es.iesraprog2425.pruebaes.model.Operadores

import es.iesraprog2425.pruebaes.service.ServiceLog
import es.iesraprog2425.pruebaes.service.ServiceOperaciones
import es.iesraprog2425.pruebaes.ui.IEntradaSalida

class Aplicacion(private val rutaFichero: String, private val gestorOperaciones: ServiceOperaciones, private val ui: IEntradaSalida, private val gestorLog: ServiceLog) {


    fun iniciar() {
        ui.pausar()

        var registro = ""
        do {
            try {
                ui.limpiarPantalla()
                val lineaResultado = gestorOperaciones.realizarOperacion()
                ui.mostrar(lineaResultado)
                registro = "OPERACIÓN - " + lineaResultado
            } catch (e: NumberFormatException) {
                ui.mostrarError(e.message ?: "Se ha producido un error!")
                registro = "ERROR - " + e.message.toString()
            } catch (e: InfoCalcException) {
                ui.mostrarError(e.message ?: "Se ha producido un error!")
                registro = "ERROR - " + e.message.toString()
            } finally {
                gestorLog.añadirRegistro(rutaFichero, registro)
            }
        } while (ui.preguntar())
        ui.limpiarPantalla()
    }


    fun iniciar(numero1: String, numero2: String, op: String) {
        val triple = manejarDatos(numero1, numero2, op)
        val num1 = triple.first
        val num2 = triple.second
        val operador = triple.third
        ui.pausar()

        var registro = ""
        try {
            val lineaResultado = gestorOperaciones.realizarOperacion(num1, num2, operador)
            ui.mostrar(lineaResultado)
            registro = "OPERACIÓN - " + lineaResultado
        } catch (e: Exception) {
            ui.mostrarError(e.message ?: "Se ha producido un error!")
            registro = "ERROR - " + e.message.toString()
        } finally {
            gestorLog.añadirRegistro(rutaFichero, registro)
        }

        if (ui.preguntar()) {
            iniciar()
        } else{
            ui.pausar()
            ui.limpiarPantalla()
        }


    }

    private fun manejarDatos(num1: String, num2: String, operador: String): Triple<Double, Double, Operadores>{
        val numero1 = num1.toDoubleOrNull() ?: throw IllegalArgumentException("Valor no válido")
        val numero2 = num2.toDoubleOrNull() ?: throw IllegalArgumentException("Valor no válido")
        val op = Operadores.getOperador(operador[0]) ?: throw IllegalArgumentException("Operador no válido")

        return Triple(numero1, numero2, op)
    }



}