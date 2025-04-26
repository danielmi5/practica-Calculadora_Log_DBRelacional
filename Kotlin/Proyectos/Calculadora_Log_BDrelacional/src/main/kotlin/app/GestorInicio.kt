package es.iesraprog2425.pruebaes.app

import es.iesraprog2425.pruebaes.model.Operadores
import es.iesraprog2425.pruebaes.service.ServiceLog
import es.iesraprog2425.pruebaes.ui.IEntradaSalida
import es.iesraprog2425.pruebaes.utils.GestionFicheros

class GestorInicio(private val ui: IEntradaSalida, private val fich: GestionFicheros, private val gestorLog: ServiceLog) {

    fun comprobarRuta(args : Array <String>): String{
        when (args.size) {
            0 -> {
                val ruta = RUTA_PREDETERMINADA
                val rutaFichero = comprobarRuta(ruta)
                return if (rutaFichero == null){
                    gestorLog.crearFicheroLog(ruta)
                } else rutaFichero
            }
            1 -> {
                val ruta = args[0]
                val rutaFichero = comprobarRuta(ruta)
                return if (rutaFichero == null){
                    gestorLog.crearFicheroLog(ruta)
                } else rutaFichero
            }

            4 -> {
                val ruta = args[0]

                if (fich.existeFichero(ruta)) {
                    return ruta
                } else if (fich.existeDirectorio(ruta)){
                    return gestorLog.crearFicheroLog(ruta)
                } else throw IllegalArgumentException("Ruta no existente")
            }
            else -> {
                throw IllegalArgumentException("Número de argumentos no válido.")
            }
        }
    }


    private fun comprobarRuta(ruta: String): String?{
        if (!fich.existeDirectorio(ruta)) {
            fich.crearDirectorio(ruta)
            ui.mostrar("Ruta $ruta creada")
        } else {
            if (!fich.tieneFicheros(ruta)) {
                ui.mostrar("No existen ficheros de Log")
            } else {
                val ultimo = fich.obtenerRutaUltimoFicheroModificado(ruta)
                val lineas = fich.leerFichero(ultimo)
                if (lineas.isEmpty()) ui.mostrar("El último log está vacío...") else{
                    ui.mostrar("Lineas del último log realizado\n--------------------------------------")
                    ui.mostrarLista(lineas)
                }
                return ultimo
            }
        }
        return null
    }

    companion object{
        const val RUTA_PREDETERMINADA = "./log"
    }
}