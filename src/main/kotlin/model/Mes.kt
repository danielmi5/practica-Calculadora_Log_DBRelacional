package es.iesraprog2425.pruebaes.model

/**
 * Enum que representa los meses del año con su número correspondiente.
 *
 * @property num Número del mes en formato numérico (1 para enero, 12 para diciembre).
 */
enum class Mes(val num: Int) {
    /** Enero - Mes 1 */
    ENERO(1),

    /** Febrero - Mes 2 */
    FEBRERO(2),

    /** Marzo - Mes 3 */
    MARZO(3),

    /** Abril - Mes 4 */
    ABRIL(4),

    /** Mayo - Mes 5 */
    MAYO(5),

    /** Junio - Mes 6 */
    JUNIO(6),

    /** Julio - Mes 7 */
    JULIO(7),

    /** Agosto - Mes 8 */
    AGOSTO(8),

    /** Septiembre - Mes 9 */
    SEPTIEMBRE(9),

    /** Octubre - Mes 10 */
    OCTUBRE(10),

    /** Noviembre - Mes 11 */
    NOVIEMBRE(11),

    /** Diciembre - Mes 12 */
    DICIEMBRE(12);

    companion object {
        /**
         * Obtiene el mes correspondiente a un número dado.
         *
         * @param numero Número del mes (1 a 12).
         * @return Instancia del mes correspondiente, o `null` si el número no es válido.
         */
        fun obtenerPorNumero(numero: Int): Mes? {
            return entries.find { it.num == numero }
        }
    }
}
