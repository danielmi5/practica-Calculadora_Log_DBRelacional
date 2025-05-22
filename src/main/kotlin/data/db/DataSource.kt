package data.db

import org.h2.jdbcx.JdbcDataSource
import javax.sql.DataSource
import com.zaxxer.hikari.HikariDataSource
import com.zaxxer.hikari.HikariConfig


/**
 * Objeto responsable de proveer configuraciones de DataSource para la conexión a la base de datos.
 *
 * Soporta dos modos de conexión:
 *  - [Modo.DRIVER]: Usa el driver H2 directamente sin pool de conexiones.
 *  - [Modo.HIKARI]: Usa HikariCP como pool de conexiones para mejor rendimiento.
 */
object DataSource {

    private const val JDBC_URL = "jdbc:h2:./data/logs"
    private const val USER = "user"
    private const val PASSWORD = ""
    private const val DRIVER = "org.h2.Driver"

    /**
     * Enum que define los modos de conexión soportados.
     */
    enum class Modo {
        /** Modo directo usando el driver JDBC de H2 */
        DRIVER,

        /** Modo con pool de conexiones usando HikariCP */
        HIKARI
    }

    /**
     * Obtiene un [DataSource] configurado según el [modo] especificado.
     *
     * @param modo Modo de conexión, por defecto [Modo.HIKARI].
     * @return DataSource configurado para la conexión a la base de datos H2.
     */
    fun obtenerDataSource(modo: Modo = Modo.HIKARI): DataSource {
        return when (modo) {
            Modo.DRIVER -> {
                // DataSource básico usando el driver H2 sin pool de conexiones.
                JdbcDataSource().apply {
                    setURL(JDBC_URL)
                    user = USER
                    password = PASSWORD
                }
            }
            Modo.HIKARI -> {
                // DataSource con pool de conexiones HikariCP para mejor performance.
                val config = HikariConfig().apply {
                    jdbcUrl = JDBC_URL
                    username = USER
                    password = PASSWORD
                    driverClassName = DRIVER
                    maximumPoolSize = 10
                }
                HikariDataSource(config)
            }
        }
    }
}