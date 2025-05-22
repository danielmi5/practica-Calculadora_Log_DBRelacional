package es.iesraprog2425.pruebaes.app


import es.iesraprog2425.pruebaes.model.Log
import es.iesraprog2425.pruebaes.model.LogError
import es.iesraprog2425.pruebaes.model.LogOperacion
import es.iesraprog2425.pruebaes.service.ILogDaoService



import es.iesraprog2425.pruebaes.service.OperacionesService
import es.iesraprog2425.pruebaes.ui.IEntradaSalida

class Aplicacion(
    private val gestorOperaciones: OperacionesService,
    private val ui: IEntradaSalida,
    private val logsDaoService: ILogDaoService
) {

    private fun ejecutarCalculadora() {
        ui.pausar()
        var logActual: Log? = null
        do {
            try {
                ui.limpiarPantalla()
                val logResultado = gestorOperaciones.realizarOperacion()
                ui.mostrar(logResultado.obtenerLog())
                logActual = logResultado
            } catch (e: NumberFormatException) {
                ui.mostrarError(e.message ?: "Se ha producido un error!")
                logActual = LogError.crear(e.message.toString())
            } catch (e: InfoCalcException) {
                ui.mostrarError(e.message ?: "Se ha producido un error!")
                logActual = LogError.crear(e.message.toString())
            } catch (e: Exception) {
                ui.mostrarError(e.message ?: "Se ha producido un error!")
                logActual = LogError.crear(e.message.toString())
            } finally {
                when(logActual){
                    is LogOperacion -> operacionDaoService.crearOperacion(logActual)
                    is LogError -> errorDaoService.crearError(logActual)
                }
            }
        } while (ui.preguntar())
        ui.limpiarPantalla()
    }

    fun iniciar(){
        var salir = false
        while (!salir) {
            ui.mostrar("""
                <<< Menú Principal >>>
                1. Ejecutar Calculadora
                2. Gestionar Logs
                3. Salir
            """.trimIndent())
            when (ui.pedirOpcion("Elige una opción >> ")) {
                1 -> ejecutarCalculadora()
                2 -> menuGestionarLogs()
                3 -> salir = true
                else -> ui.mostrarError("Opción equivocada")
            }
        }
    }




    private fun menuGestionarLogs() {
        var salir = false
        while (!salir) {
            ui.mostrar("""
                <<< Gestión Logs >>>
                1. Obtener logs por fecha
                2. Obtener logs por hora
                3. Obtener log por fecha y hora
                4. Eliminar log por fecha y hora
                5. Eliminar logs por fecha
                6. Eliminar logs por hora
                7. Obtener todos los logs
                8. Salir
            """.trimIndent())
            when (ui.pedirOpcion("Elige una opción")) {
                1 -> mostrarLogPorFecha()
                2 -> mostrarLogPorHora()
                3 -> mostrarLogPorFechaYHora()
                4 -> eliminarLogPorFechaYHora()
                5 -> eliminarLogPorFecha()
                6 -> eliminarLogPorHora()
                7 -> mostrarLogs()
                8 -> salir = true
                else -> ui.mostrarError("Opción equivocada")
            }
            ui.limpiarPantalla()
        }
    }

    private fun mostrarLogPorFecha() {}
    private fun mostrarLogPorHora() {}
    private fun mostrarLogPorFechaYHora() {}
    private fun eliminarLogPorFechaYHora() {}
    private fun eliminarLogPorFecha() {}
    private fun eliminarLogPorHora() {}
    private fun mostrarLogs() {}


}



    /*fun iniciar(numero1: String, numero2: String, op: String) {
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
            //gestorLog.añadirRegistro(rutaFichero, registro)
        }

        if (ui.preguntar()) {
            iniciar()
        } else{
            ui.pausar()
            ui.limpiarPantalla()
        }


    }*/

    /*private fun manejarDatos(num1: String, num2: String, operador: String): Triple<Double, Double, Operadores>{
        val numero1 = num1.toDoubleOrNull() ?: throw IllegalArgumentException("Valor no válido")
        val numero2 = num2.toDoubleOrNull() ?: throw IllegalArgumentException("Valor no válido")
        val op = Operadores.getOperador(operador[0]) ?: throw IllegalArgumentException("Operador no válido")

        return Triple(numero1, numero2, op)
    }*/



