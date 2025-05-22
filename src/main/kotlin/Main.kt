package es.iesraprog2425.pruebaes

import data.db.DataSource
import es.iesraprog2425.pruebaes.app.Aplicacion
import es.iesraprog2425.pruebaes.data.dao.ErrorDaoH2
import es.iesraprog2425.pruebaes.data.dao.OperacionDaoH2
import es.iesraprog2425.pruebaes.service.CalculadoraService
import es.iesraprog2425.pruebaes.service.ErrorDaoService
import es.iesraprog2425.pruebaes.service.GestorOperacionesService
import es.iesraprog2425.pruebaes.service.OperacionDaoService
import es.iesraprog2425.pruebaes.ui.Consola
import es.iesraprog2425.pruebaes.utils.BaseDatos


fun main(args: Array <String>) {
//TODO: LA OPCIÓN dEL MENÚ DE ELIMINAR, DAR LA OPCIÓN DE NO PASAR NADA POR EL VALOR - POR EJEMPLO -> SI NO QUIERE ELIMINAR POR FECHA, SE ELIMINARÍA POR HORA Y ASÍ
    val ui = Consola()
    val calculadora = CalculadoraService()
    val gestorOperaciones = GestorOperacionesService(ui,calculadora)
    val ds = DataSource.obtenerDataSource()
    val operacionDao = OperacionDaoH2(ds); val errorDao = ErrorDaoH2(ds)
    val operacionDaoService = OperacionDaoService(operacionDao); val errorDaoService = ErrorDaoService(errorDao)
    val app = Aplicacion(gestorOperaciones, ui, operacionDaoService, errorDaoService)

    try {
        if (ui.preguntar("¿Quieres borrar todos los datos de la base de datos (s/n)?")) BaseDatos.crearBaseDeDatos(true) else BaseDatos.crearBaseDeDatos()
        app.iniciar()
    } catch (e: Exception){
        ui.mostrar("ERROR CRÍTICO")
    }

    ui.mostrar("\nCALCULADORA SIN BATERÍA")
}

