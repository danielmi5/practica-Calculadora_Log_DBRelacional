package es.iesraprog2425.pruebaes.app


import es.iesraprog2425.pruebaes.model.Log
import es.iesraprog2425.pruebaes.model.LogError
import es.iesraprog2425.pruebaes.model.LogOperacion
import es.iesraprog2425.pruebaes.service.ILogDaoService



import es.iesraprog2425.pruebaes.service.OperacionesService
import es.iesraprog2425.pruebaes.ui.IEntradaSalida
import java.sql.SQLException

/**
 * Clase principal de la aplicación que gestiona la interacción con el usuario y la lógica de negocio.
 *
 * Esta clase coordina la ejecución de operaciones a través de [gestorOperaciones], la interacción
 * con el usuario mediante la interfaz [ui], y el manejo de logs mediante [logsDaoService].
 *
 * @property gestorOperaciones Servicio encargado de realizar operaciones de cálculo.
 * @property ui Interfaz para la entrada y salida de datos con el usuario.
 * @property logsDaoService Servicio para la gestión de logs (registro, obtención, eliminación).
 */

class Aplicacion(
    private val gestorOperaciones: OperacionesService,
    private val ui: IEntradaSalida,
    private val logsDaoService: ILogDaoService
) {

    /**
     * Ejecuta la calculadora, mostrando la interfaz al usuario y gestionando la captura
     * de errores mediante logs.
     *
     * Permite realizar operaciones en un ciclo hasta que el usuario decida salir.
     * Captura y registra errores de distintos tipos.
     */
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
                if (logActual != null) logsDaoService.crearLog(logActual)

            }
        } while (ui.preguntar())
        ui.limpiarPantalla()
    }

    /**
     * Inicia la aplicación mostrando el menú principal con opciones para ejecutar
     * la calculadora, gestionar logs o salir.
     *
     * El menú se muestra en bucle hasta que el usuario decide salir.
     */
    fun iniciar(){
        var salir = false
        while (!salir) {
            ui.mostrar("""
                <<< Menú Principal >>>
                1. Ejecutar Calculadora
                2. Gestionar Logs
                3. Salir
            """.trimIndent())
            when (ui.pedirOpcion("Elige una opción")) {
                1 -> ejecutarCalculadora()
                2 -> menuGestionarLogs()
                3 -> salir = true
                else -> ui.mostrarError("Opción equivocada")
            }
        }
    }



    /**
     * Muestra el menú de gestión de logs con varias opciones para consultar, eliminar
     * y listar logs según diferentes criterios.
     *
     * El menú se repite hasta que el usuario decide salir.
     */
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
            try {
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
            } catch (e: SQLException) {
                ui.mostrarError(e.message ?: "Se ha producido un error!")
            } catch (e: IllegalArgumentException) {
                ui.mostrarError(e.message ?: "Se ha producido un error!")
            } catch (e: Exception) {
                ui.mostrarError(e.message ?: "Se ha producido un error!")
            }
            ui.pausar()
            ui.limpiarPantalla()
        }
    }

    /**
     * Solicita al usuario una fecha y muestra los logs correspondientes a dicha fecha.
     *
     * Valida el formato de la fecha y solicita el tipo de log a mostrar.
     * Muestra un mensaje si no existen logs para la fecha indicada.
     */
    private fun mostrarLogPorFecha() {
        val fecha = ui.pedirInfo("Introduce la fecha que quieras buscar Logs (formato: dd-MM-yyyy)")
        require(fecha.matches(Regex("""\d{2}-\d{2}-\d{4}"""))) { "La fecha debe tener el formato dd-MM-yyyy" }

        val tipo = ui.pedirTipoLog()
        val lista = logsDaoService.obtenerLogsPorFecha(fecha, tipo)
        if (lista.isEmpty()) {
            ui.mostrar("No hay logs del $fecha en la base de datos.")
        } else {
            ui.mostrarLista("Lista de logs del $fecha", lista.map { it.toString() })
        }
    }

    /**
     * Solicita al usuario una hora y muestra los logs correspondientes a dicha hora.
     *
     * Valida el formato de la hora y solicita el tipo de log a mostrar.
     * Muestra un mensaje si no existen logs para la hora indicada.
     */
    private fun mostrarLogPorHora() {
        val hora = ui.pedirInfo("Introduce la hora que quieras buscar Logs (formato: HH:mm:ss)")
        require(hora.matches(Regex("""\d{2}:\d{2}:\d{2}"""))) { "La hora debe tener el formato HH:mm:ss" }

        val tipo = ui.pedirTipoLog()
        val lista = logsDaoService.obtenerLogsPorHora(hora, tipo)
        if (lista.isEmpty()) {
            ui.mostrar("No hay logs de las $hora en la base de datos.")
        } else {
            ui.mostrarLista("Lista de logs de las $hora", lista.map { it.toString() })
        }
    }

    /**
     * Solicita al usuario una fecha y una hora para mostrar un log específico.
     *
     * Valida los formatos y solicita el tipo de log a mostrar.
     * Muestra un mensaje si no existe ningún log con esos datos.
     */
    private fun mostrarLogPorFechaYHora() {
        val fecha = ui.pedirInfo("Introduce la fecha que quieras buscar Logs (formato: dd-MM-yyyy)")
        require(fecha.matches(Regex("""\d{2}-\d{2}-\d{4}"""))) { "La fecha debe tener el formato dd-MM-yyyy" }

        val hora = ui.pedirInfo("Introduce la hora que quieras buscar Logs (formato: HH:mm:ss)")
        require(hora.matches(Regex("""\d{2}:\d{2}:\d{2}"""))) { "La hora debe tener el formato HH:mm:ss" }

        val tipo = ui.pedirTipoLog()
        val log = logsDaoService.obtenerLogPorFechaYHora(fecha, hora, tipo)
        if (log == null) {
            ui.mostrar("No hay ningún log del $fecha a las $hora en la base de datos.")
        } else {
            ui.mostrar("Log del $fecha a las $hora --> $log")
        }
    }

    /**
     * Solicita al usuario una fecha y una hora para eliminar un log específico.
     *
     * Valida los formatos y solicita el tipo de log a eliminar.
     * Muestra un mensaje confirmando la eliminación o indicando que no se encontró el log.
     */
    private fun eliminarLogPorFechaYHora() {
        val fecha = ui.pedirInfo("Introduce la fecha que quieras eliminar Logs (formato: dd-MM-yyyy)")
        require(fecha.matches(Regex("""\d{2}-\d{2}-\d{4}"""))) { "La fecha debe tener el formato dd-MM-yyyy" }

        val hora = ui.pedirInfo("Introduce la hora que quieras eliminar Logs (formato: HH:mm:ss)")
        require(hora.matches(Regex("""\d{2}:\d{2}:\d{2}"""))) { "La hora debe tener el formato HH:mm:ss" }

        val tipo = ui.pedirTipoLog()
        val eliminado = logsDaoService.eliminarLog(fecha, hora, tipo)
        if (eliminado) {
            ui.mostrar("Log del $fecha a las $hora eliminado correctamente.")
        } else {
            ui.mostrar("No se encontró ningún log del $fecha a las $hora para eliminar.")
        }
    }

    /**
     * Solicita al usuario una fecha para eliminar todos los logs de dicha fecha.
     *
     * Valida el formato y solicita el tipo de log a eliminar.
     * Muestra un mensaje con la cantidad de logs eliminados o si no se encontraron logs.
     */
    private fun eliminarLogPorFecha() {
        val fecha = ui.pedirInfo("Introduce la fecha que quieras eliminar Logs (formato: dd-MM-yyyy)")
        require(fecha.matches(Regex("""\d{2}-\d{2}-\d{4}"""))) { "La fecha debe tener el formato dd-MM-yyyy" }

        val tipo = ui.pedirTipoLog()
        val eliminados = logsDaoService.eliminarLogsPorFecha(fecha, tipo)
        if (eliminados > 0) {
            ui.mostrar("$eliminados logs del $fecha eliminados correctamente.")
        } else {
            ui.mostrar("No se encontraron logs del $fecha para eliminar.")
        }
    }

    /**
     * Solicita al usuario una fecha para eliminar todos los logs de dicha fecha.
     *
     * Valida el formato y solicita el tipo de log a eliminar.
     * Muestra un mensaje con la cantidad de logs eliminados o si no se encontraron logs.
     */
    private fun eliminarLogPorHora() {
        val hora = ui.pedirInfo("Introduce la hora que quieras eliminar Logs (formato: HH:mm:ss)")
        require(hora.matches(Regex("""\d{2}:\d{2}:\d{2}"""))) { "La hora debe tener el formato HH:mm:ss" }

        val tipo = ui.pedirTipoLog()
        val eliminados = logsDaoService.eliminarLogsPorHora(hora, tipo)
        if (eliminados > 0) {
            ui.mostrar("$eliminados logs de las $hora eliminados correctamente.")
        } else {
            ui.mostrar("No se encontraron logs de las $hora para eliminar.")
        }
    }

    /**
     * Muestra todos los logs guardados en la base de datos, según el tipo seleccionado por el usuario.
     *
     * Muestra un mensaje si no hay logs almacenados.
     */
    private fun mostrarLogs() {
        val tipo = ui.pedirTipoLog()
        val lista = logsDaoService.obtenerLogs(tipo)
        if (lista.isEmpty()) {
            ui.mostrar("No hay logs guardados en la base de datos.")
        } else {
            ui.mostrarLista("Lista de logs", lista.map { it.toString() })
        }
    }


}

