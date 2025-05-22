package es.iesraprog2425.pruebaes.model

/**
 * Enum que representa los distintos tipos de logs que se pueden registrar en el sistema.
 *
 * - [OPERACION]: Log relacionado con una operación matemática realizada con éxito.
 * - [ERROR]: Log relacionado con un error ocurrido durante la ejecución o entrada de datos.
 */
enum class TipoLog {
    /** Log generado por una operación matemática exitosa. */
    OPERACION,

    /** Log generado por un error en el sistema o entrada de datos no válida. */
    ERROR
}