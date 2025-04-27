package es.iesraprog2425.pruebaes

import es.iesraprog2425.pruebaes.app.Aplicacion
import es.iesraprog2425.pruebaes.model.Calculadora
import es.iesraprog2425.pruebaes.app.GestorInicio
import es.iesraprog2425.pruebaes.data.RepositorioLogBD
import es.iesraprog2425.pruebaes.data.RepositorioLogFich
import es.iesraprog2425.pruebaes.model.Log
import es.iesraprog2425.pruebaes.service.GestorLogs
import es.iesraprog2425.pruebaes.service.GestorOperaciones
import es.iesraprog2425.pruebaes.ui.Consola
import es.iesraprog2425.pruebaes.utils.BaseDatos
import es.iesraprog2425.pruebaes.utils.Ficheros
import java.sql.Connection


fun main(args: Array<String>) {
    val url = "jdbc:h2:~/bdH2/test"
    val usuario = "user"
    val contra = ""
    val driver = "org.h2.Driver"

    val ui = Consola()
    val fich = Ficheros(ui)

    var bd: BaseDatos? = null
    try {
        bd = BaseDatos(url, driver, usuario, contra)
        val repoFich = RepositorioLogFich(fich)
        val repoBD = RepositorioLogBD(bd)
        val gestorLog = GestorLogs(repoFich, repoBD)
        val gestorInicio = GestorInicio(ui, fich, gestorLog)
        gestorInicio.comprobarRuta(args)
        val app = Aplicacion(GestorOperaciones(ui, Calculadora()), ui, gestorLog)
        if (args.size == 4){
            app.iniciar(args[1], args[3], args[2])
        } else app.iniciar()


    } catch (e: Exception){
        ui.mostrarError(e.message.toString())

    } finally {
        if (bd is BaseDatos) {
            bd.cerrarBD()
        }
    }


    ui.mostrar("FIN DEL PROGRAMA")

}

/*
*





    * */