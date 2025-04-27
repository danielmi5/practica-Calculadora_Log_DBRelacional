package es.iesraprog2425.pruebaes.data

import es.iesraprog2425.pruebaes.model.Log
import es.iesraprog2425.pruebaes.utils.GestionBaseDeDatos
import es.iesraprog2425.pruebaes.utils.Utils.mapearValores

class RepositorioLogBD(val baseDeDatos: GestionBaseDeDatos): RepositorioLog {
    init {
        baseDeDatos.conectarBD()
    }

    override fun subirLogs(listaLogs: List<Log>){
        baseDeDatos.insertarMultiplesDatosTabla("log", listaLogs.map { it.mapearValores() }, Log.obtenerPropiedades(), false)
    }

    override fun subirLog(log: Log){
        baseDeDatos.insertarDatosTabla("log", log.mapearValores(), Log.obtenerPropiedades())
    }

    override fun obtenerLogs(): List<String> {
        return baseDeDatos.obtenerDatosTabla("Log").map { it.toString() }
    }
}