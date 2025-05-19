package es.iesraprog2425.pruebaes.model

class LogError(private val msjError: String) {
    override fun toString(): String {
        return "${this::class.simpleName?.replace("Log","")} --> $msjError"
    }
}