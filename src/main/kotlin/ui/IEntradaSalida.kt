package es.iesraprog2425.pruebaes.ui

/**
 * Interfaz que define las operaciones para la interacción de entrada y salida con el usuario.
 *
 * Proporciona métodos para mostrar mensajes, errores, listas, solicitar diferentes tipos de datos,
 * realizar preguntas de confirmación, limpiar la pantalla y pausar la ejecución.
 */
interface IEntradaSalida {

    /**
     * Muestra un mensaje en la salida estándar.
     *
     * @param msj El mensaje a mostrar.
     * @param salto Indica si se debe agregar un salto de línea después del mensaje (por defecto `true`).
     */
    fun mostrar(msj: String, salto: Boolean = true)

    /**
     * Muestra un mensaje seguido de una lista de cadenas.
     *
     * @param msj Mensaje introductorio o título.
     * @param lista Lista de cadenas a mostrar.
     */
    fun mostrarLista(msj: String, lista: List<String>)

    /**
     * Muestra un mensaje de error.
     *
     * @param msj Mensaje de error a mostrar.
     * @param salto Indica si se debe agregar un salto de línea después del mensaje (por defecto `true`).
     */
    fun mostrarError(msj: String, salto: Boolean = true)

    /**
     * Solicita al usuario que introduzca una cadena de texto.
     *
     * @param msj Mensaje que se muestra para solicitar la información (opcional).
     * @return La cadena introducida por el usuario.
     */
    fun pedirInfo(msj: String = ""): String

    /**
     * Solicita al usuario que introduzca un número decimal.
     *
     * @param msj Mensaje que se muestra para solicitar la información (opcional).
     * @return El número decimal introducido o `null` si la entrada no es válida.
     */
    fun pedirDouble(msj: String = ""): Double?

    /**
     * Solicita al usuario que introduzca un número entero.
     *
     * @param msj Mensaje que se muestra para solicitar la información (opcional).
     * @return El número entero introducido o `null` si la entrada no es válida.
     */
    fun pedirEntero(msj: String = ""): Int?

    /**
     * Solicita al usuario que seleccione una opción numérica.
     *
     * @param msj Mensaje que se muestra para solicitar la opción.
     * @return El número entero correspondiente a la opción seleccionada.
     */
    fun pedirOpcion(msj: String): Int

    /**
     * Solicita al usuario que seleccione un tipo de log.
     *
     * @return El tipo de log seleccionado como cadena o `null` si no se selecciona ninguno.
     */
    fun pedirTipoLog(): String?

    /**
     * Realiza una pregunta al usuario y espera una respuesta afirmativa o negativa.
     *
     * @param msj Mensaje de la pregunta (por defecto "¿Deseas intentarlo de nuevo? (s/n): ").
     * @return `true` si la respuesta es afirmativa, `false` en caso contrario.
     */
    fun preguntar(msj: String = "¿Deseas intentarlo de nuevo? (s/n): "): Boolean

    /**
     * Limpia la pantalla simulando saltos de línea.
     *
     * @param numSaltos Número de saltos de línea para simular la limpieza (por defecto 20).
     */
    fun limpiarPantalla(numSaltos: Int = 20)

    /**
     * Pausa la ejecución hasta que el usuario presione una tecla o confirme para continuar.
     */
    fun pausar()
}