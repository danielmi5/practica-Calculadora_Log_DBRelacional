package data.dao

interface IDao<T> {
    fun crear(objeto: T)
    fun obtenerPorFecha(fecha: String): List<T>
    fun obtenerPorHora(hora: String): List<T>
    fun obtenerPorFechaYHora(fecha: String, hora: String): T?
    fun actualizar(objeto: T)
    fun eliminar(fecha: String, hora: String): Boolean
    fun eliminarPorFecha(fecha: String): Boolean
    fun eliminarPorHora(hora: String): Boolean
    fun obtenerTodos(): List<T>
}