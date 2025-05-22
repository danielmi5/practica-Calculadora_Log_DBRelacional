package data.dao

/**
 * Interfaz genérica para las operaciones básicas de acceso a datos (DAO).
 *
 * @param T Tipo del objeto que maneja el DAO.
 */
interface IDao<T> {

    /**
     * Crea un nuevo objeto en la fuente de datos.
     *
     * @param objeto Objeto a crear.
     */
    fun crear(objeto: T)

    /**
     * Obtiene una lista de objetos filtrados por la fecha.
     *
     * @param fecha Fecha para filtrar, en formato esperado por la implementación.
     * @return Lista de objetos correspondientes a la fecha.
     */
    fun obtenerPorFecha(fecha: String): List<T>

    /**
     * Obtiene una lista de objetos filtrados por la hora.
     *
     * @param hora Hora para filtrar, en formato esperado por la implementación.
     * @return Lista de objetos correspondientes a la hora.
     */
    fun obtenerPorHora(hora: String): List<T>

    /**
     * Obtiene un objeto que corresponde a la fecha y hora indicadas.
     *
     * @param fecha Fecha del objeto.
     * @param hora Hora del objeto.
     * @return Objeto que coincide con fecha y hora, o null si no existe.
     */
    fun obtenerPorFechaYHora(fecha: String, hora: String): T?

    /**
     * Actualiza un objeto existente en la fuente de datos.
     *
     * @param objeto Objeto con los datos actualizados.
     */
    fun actualizar(objeto: T)

    /**
     * Elimina un objeto identificado por fecha y hora.
     *
     * @param fecha Fecha del objeto a eliminar.
     * @param hora Hora del objeto a eliminar.
     * @return True si se eliminó correctamente, false si no se encontró.
     */
    fun eliminar(fecha: String, hora: String): Boolean

    /**
     * Elimina todos los objetos que correspondan a una fecha dada.
     *
     * @param fecha Fecha para filtrar los objetos a eliminar.
     * @return Número de objetos eliminados.
     */
    fun eliminarPorFecha(fecha: String): Int

    /**
     * Elimina todos los objetos que correspondan a una hora dada.
     *
     * @param hora Hora para filtrar los objetos a eliminar.
     * @return Número de objetos eliminados.
     */
    fun eliminarPorHora(hora: String): Int

    /**
     * Obtiene todos los objetos disponibles en la fuente de datos.
     *
     * @return Lista con todos los objetos.
     */
    fun obtenerTodos(): List<T>
}