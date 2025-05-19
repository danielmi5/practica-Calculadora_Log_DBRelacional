package data.db

import org.h2.jdbcx.JdbcDataSource
import javax.sql.DataSource
import com.zaxxer.hikari.HikariDataSource
import com.zaxxer.hikari.HikariConfig

object DataSource {

    private const val JDBC_URL = "jdbc:h2:./data/tienda"
    private const val USER = "user"
    private const val PASSWORD = ""
    private const val DRIVER = "org.h2.Driver"

    enum class Modo {
        DRIVER,
        HIKARI
    }

    fun obtenerDataSource(modo: Modo = Modo.HIKARI): DataSource {
        return when (modo) {
            Modo.DRIVER -> {
                JdbcDataSource().apply {
                    setURL(JDBC_URL)
                    user = USER
                    password = PASSWORD
                }
            }
            Modo.HIKARI -> {
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