package data.dao

interface IDao<T> {
    fun crear(objeto: T)
    fun obtenerPorFecha(fecha: String): List<T>
    fun obtenerPorHora(hora: String): List<T>
    fun obtenerPorFechaYHora(fechaYHora: String): T?
    fun actualizar(objeto: T)
    fun eliminar(fecha: String, hora: String)
    fun obtenerTodos(): List<T>
}