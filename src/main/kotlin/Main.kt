package es.iesraprog2425.pruebaes

import es.iesraprog2425.pruebaes.app.Aplicacion
import es.iesraprog2425.pruebaes.model.Calculadora
import es.iesraprog2425.pruebaes.app.GestorInicio
import es.iesraprog2425.pruebaes.service.GestorLogs
import es.iesraprog2425.pruebaes.service.GestorOperaciones
import es.iesraprog2425.pruebaes.ui.Consola
import es.iesraprog2425.pruebaes.utils.Ficheros
import java.sql.*


fun main() {
    //Conexión Base de datos

    val url = "jdbc:h2:~/bdH2/test"
    val usuario = "user"
    val contraseña = ""

    try {
        Class.forName("org.h2.Driver")
        val conexion = DriverManager.getConnection(url, usuario, contraseña)
        println("Conexión exitosa")
        conexion.close()
    } catch (e: SQLException) {
        println("Error en la conexión: ${e.message}")
    } catch (e: ClassNotFoundException) {
        println("No se encontró el driver JDBC: ${e.message}")
    }
}

/*
* val ui = Consola()
    val fich = Ficheros(ui)
    val gestorLog = GestorLogs(fich)
    val gestorInicio = GestorInicio(ui, fich, gestorLog)

    try {
        val ruta = gestorInicio.comprobarRuta(args)
        val app = Aplicacion(ruta, GestorOperaciones(ui, Calculadora()), ui, gestorLog)
        if (args.size == 4){
            app.iniciar(args[1], args[3], args[2])
        } else app.iniciar()
    } catch (e: Exception){
        ui.mostrarError(e.message.toString())
    }


    ui.mostrar("FIN DEL PROGRAMA")
    * */