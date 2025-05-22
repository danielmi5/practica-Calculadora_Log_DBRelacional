package es.iesraprog2425.pruebaes.ui

import java.util.Scanner

/**
 * Implementación de [IEntradaSalida] para la consola estándar.
 *
 * Proporciona métodos para mostrar mensajes, errores, listas, solicitar entradas
 * de diferentes tipos, hacer preguntas, limpiar la pantalla y pausar la ejecución
 * utilizando la entrada/salida estándar del sistema.
 */
class Consola : IEntradaSalida {
    private val scanner = Scanner(System.`in`)

    /**
     * Muestra un mensaje en la consola.
     *
     * @param msj Mensaje a mostrar.
     * @param salto Indica si se debe agregar un salto de línea después del mensaje.
     */
    override fun mostrar(msj: String, salto: Boolean) {
        print("$msj${if (salto) "\n" else ""}")
    }

    /**
     * Muestra un mensaje de error en la consola.
     *
     * @param msj Mensaje de error a mostrar.
     * @param salto Indica si se debe agregar un salto de línea después del mensaje.
     */
    override fun mostrarError(msj: String, salto: Boolean) {
        mostrar("ERROR - $msj", salto)
    }

    /**
     * Solicita al usuario que introduzca una cadena de texto.
     *
     * @param msj Mensaje que se muestra para solicitar la información.
     * @return La cadena introducida por el usuario, sin espacios al inicio o final.
     */
    override fun pedirInfo(msj: String): String {
        if (msj.isNotEmpty()) mostrar(msj + " >>", false)
        return scanner.nextLine().trim()
    }

    /**
     * Solicita al usuario que introduzca un número decimal.
     *
     * @param msj Mensaje que se muestra para solicitar la información.
     * @return El número decimal introducido o `null` si la entrada no es válida.
     */
    override fun pedirDouble(msj: String): Double? {
        return pedirInfo(msj).replace(',', '.').toDoubleOrNull()
    }

    /**
     * Solicita al usuario que introduzca un número entero.
     *
     * @param msj Mensaje que se muestra para solicitar la información.
     * @return El número entero introducido o `null` si la entrada no es válida.
     */
    override fun pedirEntero(msj: String): Int? {
        return pedirInfo(msj).toIntOrNull()
    }

    /**
     * Solicita al usuario que seleccione una opción numérica.
     *
     * @param msj Mensaje que se muestra para solicitar la opción.
     * @return El número entero correspondiente a la opción seleccionada, o 0 si no es válido.
     */
    override fun pedirOpcion(msj: String): Int {
        return pedirInfo(msj).toIntOrNull() ?: 0
    }

    /**
     * Solicita al usuario que seleccione un tipo de log.
     *
     * Las opciones válidas son "ERROR" y "OPERACION" (no sensible a mayúsculas).
     * Si la entrada no coincide, devuelve `null`.
     *
     * @return El tipo de log seleccionado o `null` si no se selecciona ninguno válido.
     */
    override fun pedirTipoLog(): String? {
        val opciones = listOf("ERROR", "OPERACION")
        val seleccion = pedirInfo("Selecciona el tipo de log (ERROR o OPERACION | '' Si no quieres 'filtrar')").uppercase().replace("Ó","O")
        return if (seleccion in opciones) seleccion else null
    }

    /**
     * Realiza una pregunta al usuario esperando una respuesta afirmativa o negativa.
     *
     * Solo acepta "s", "si", "n" o "no" (insensible a mayúsculas).
     * En caso de respuesta inválida, muestra un error y vuelve a preguntar.
     *
     * @param msj Mensaje de la pregunta.
     * @return `true` si la respuesta es afirmativa, `false` en caso contrario.
     */
    override fun preguntar(msj: String): Boolean {
        do {
            val respuesta = pedirInfo(msj).lowercase()
            when (respuesta) {
                "s", "si" -> return true
                "n", "no" -> return false
                else -> mostrarError("Respuesta no válida. Responde con s, n, si o no.")
            }
        } while (true)
    }

    /**
     * Limpia la pantalla de la consola.
     *
     * Si la consola soporta secuencias ANSI, las usa para limpiar la pantalla,
     * si no, simula limpieza con saltos de línea.
     *
     * @param numSaltos Número de saltos de línea para simular la limpieza (por defecto 20).
     */
    override fun limpiarPantalla(numSaltos: Int) {
        if (System.console() != null) {
            mostrar("\u001b[H\u001b[2J", false)
            System.out.flush()
        } else {
            repeat(numSaltos) {
                mostrar("")
            }
        }
    }

    /**
     * Pausa la ejecución hasta que el usuario presione ENTER.
     */
    override fun pausar() {
        pedirInfo("Introduce ENTER para continuar...")
    }

    /**
     * Muestra un mensaje seguido de una lista de cadenas.
     *
     * @param msj Mensaje introductorio o título.
     * @param lista Lista de cadenas a mostrar.
     */
    override fun mostrarLista(msj: String, lista: List<String>) {
        mostrar(msj + "\n" + "-".repeat(msj.length * 2))
        for (s in lista) {
            mostrar(s)
        }
    }
}