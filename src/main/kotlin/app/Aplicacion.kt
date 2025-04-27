package es.iesraprog2425.pruebaes.app


import es.iesraprog2425.pruebaes.model.Log
import es.iesraprog2425.pruebaes.model.Operadores
import es.iesraprog2425.pruebaes.model.TipoLog

import es.iesraprog2425.pruebaes.service.ServiceLog
import es.iesraprog2425.pruebaes.service.ServiceOperaciones
import es.iesraprog2425.pruebaes.ui.IEntradaSalida
import es.iesraprog2425.pruebaes.utils.BaseDatos

class Aplicacion(private val gestorOperaciones: ServiceOperaciones, private val ui: IEntradaSalida, private val gestorLog: ServiceLog) {


    fun iniciar() {
        ui.pausar()

        var tipoRegistro = TipoLog.OPERACION
        var msj = ""
        do {
            try {
                ui.limpiarPantalla()
                val lineaResultado = gestorOperaciones.realizarOperacion()
                ui.mostrar(lineaResultado)
                tipoRegistro = TipoLog.OPERACION
                msj = lineaResultado
            } catch (e: NumberFormatException) {
                ui.mostrarError(e.message ?: "Se ha producido un error!")
                tipoRegistro = TipoLog.ERROR
                msj = e.message.toString()
            } catch (e: InfoCalcException) {
                ui.mostrarError(e.message ?: "Se ha producido un error!")
                tipoRegistro = TipoLog.ERROR
                msj = e.message.toString()
            } finally {
                gestorLog.añadirNuevoRegistro(tipoRegistro, msj)
            }
        } while (ui.preguntar())
        pedirGuardadoEnBD()
        ui.pausar()
        ui.limpiarPantalla()
    }


    fun iniciar(numero1: String, numero2: String, op: String) {
        val triple = manejarDatos(numero1, numero2, op)
        val num1 = triple.first
        val num2 = triple.second
        val operador = triple.third
        ui.pausar()
        var tipoRegistro = TipoLog.OPERACION
        var msj = ""
        try {
            val lineaResultado = gestorOperaciones.realizarOperacion(num1, num2, operador)
            ui.mostrar(lineaResultado)
            tipoRegistro = TipoLog.OPERACION
            msj = lineaResultado
        } catch (e: Exception) {
            ui.mostrarError(e.message ?: "Se ha producido un error!")
            tipoRegistro = TipoLog.ERROR
            msj = e.message.toString()
        } finally {
            gestorLog.añadirNuevoRegistro(tipoRegistro, msj)
        }

        if (ui.preguntar()) {
            iniciar()
        } else{
            pedirGuardadoEnBD()
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

    private fun pedirGuardadoEnBD(){
        if (ui.preguntar("¿Quieres guardar los registros en la base de datos? (s/n): ")){
            val lineas = gestorLog.obtenerLogsUltimoLog()
            gestorLog.subirLogsABaseDatos(lineas)
            ui.mostrar("Se han subido los logs correctamente")
        }
    }

}