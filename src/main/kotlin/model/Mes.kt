package es.iesraprog2425.pruebaes.model

enum class Mes(val num: Int) {
    ENERO(1),
    FEBRERO(2),
    MARZO(3),
    ABRIL(4),
    MAYO(5),
    JUNIO(6),
    JULIO(7),
    AGOSTO(8),
    SEPTIEMBRE(9),
    OCTUBRE(10),
    NOVIEMBRE(11),
    DICIEMBRE(12);

    companion object {
        fun obtenerPorNumero(numero: Int): Mes? {
            return entries.find { it.num == numero }
        }
    }
}

