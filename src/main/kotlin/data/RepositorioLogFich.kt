package es.iesraprog2425.pruebaes.data

import es.iesraprog2425.pruebaes.model.Log
import es.iesraprog2425.pruebaes.utils.GestionFicheros
import java.lang.IllegalArgumentException

class RepositorioLogFich(private val fich: GestionFicheros): RepositorioLog, FichLog {
    private var ruta: String? = null
    private var rutaLog: String? = null

    override fun obtenerRuta(): String?{
        return this.ruta
    }

    override fun crearLog(rutaLog: String){
        fich.crearFichero(rutaLog)
        this.rutaLog = rutaLog
    }

    override fun guardarRutaLog(ruta: String) {
        this.rutaLog = ruta
    }

    override fun subirLog(log: Log) {
        fich.aniadirLinea(rutaLog ?: throw kotlin.IllegalArgumentException(MSJ_ERROR_RUTA), log.toString())
    }

    override fun subirLogs(listaLogs: List<Log>) {
        for (log in listaLogs) {
            fich.aniadirLinea(rutaLog ?: throw kotlin.IllegalArgumentException(MSJ_ERROR_RUTA), log.toString())
        }
    }

    override fun obtenerLogs(): List<String> {
        return fich.leerFichero(rutaLog ?: throw kotlin.IllegalArgumentException(MSJ_ERROR_RUTA))
    }

    companion object{
        const val MSJ_ERROR_RUTA = "No hay una ruta existente"
    }

}