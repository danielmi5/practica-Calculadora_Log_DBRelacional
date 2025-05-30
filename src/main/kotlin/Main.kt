package es.iesraprog2425.pruebaes

import data.db.DataSource
import es.iesraprog2425.pruebaes.app.Aplicacion
import es.iesraprog2425.pruebaes.data.dao.ErrorDaoH2
import es.iesraprog2425.pruebaes.data.dao.OperacionDaoH2
import es.iesraprog2425.pruebaes.service.CalculadoraService
import es.iesraprog2425.pruebaes.service.ErrorDaoService
import es.iesraprog2425.pruebaes.service.GestorOperacionesService
import es.iesraprog2425.pruebaes.service.LogDaoService
import es.iesraprog2425.pruebaes.service.OperacionDaoService
import es.iesraprog2425.pruebaes.ui.Consola
import es.iesraprog2425.pruebaes.utils.BaseDatos

/**
 * Punto de entrada principal de la aplicación.
 *
 * Esta función inicializa los componentes necesarios para la aplicación,
 * incluyendo la interfaz de usuario, los servicios de cálculo y gestión
 * de operaciones, acceso a datos y servicios de logs.
 *
 * El flujo principal realiza lo siguiente:
 * - Pregunta al usuario si desea borrar todos los datos de la base de datos.
 * - Según la respuesta, crea o resetea la base de datos.
 * - Inicia la aplicación.
 * - Captura excepciones críticas y las muestra por consola.
 * - Finalmente, muestra un mensaje indicando el cierre de la calculadora.
 */
fun main() {
    val ui = Consola()
    val calculadora = CalculadoraService()
    val gestorOperaciones = GestorOperacionesService(ui,calculadora)
    val ds = DataSource.obtenerDataSource()
    val operacionDao = OperacionDaoH2(ds); val errorDao = ErrorDaoH2(ds)
    val operacionDaoService = OperacionDaoService(operacionDao); val errorDaoService = ErrorDaoService(errorDao)
    val logsDaoService = LogDaoService(errorDaoService, operacionDaoService)
    val app = Aplicacion(gestorOperaciones, ui, logsDaoService)

    try {
        if (ui.preguntar("¿Quieres borrar todos los datos de la base de datos (s/n)?")){
            BaseDatos.crearBaseDeDatos(true)
            ui.mostrar("Datos de las tablas borradas")
        } else BaseDatos.crearBaseDeDatos()
        app.iniciar()
    } catch (e: Exception){
        ui.mostrar("ERROR CRÍTICO ${e.message}")
    }

    ui.mostrar("\nCALCULADORA SIN BATERÍA")
}

