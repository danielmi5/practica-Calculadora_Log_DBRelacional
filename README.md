# README DEL PROYECTO CALCULADORA

Esta rama es la version definitiva de la calculadora con la integración de una base de datos H2. 

Para ver como lo hice antes de saber sobre el patrón DAO, dataSource, etc. Enlace a la rama --> [basededatos](https://github.com/danielmi5/practica-Calculadora_Log_DBRelacional/tree/basededatos)

Se realiza el uso del patrón DAO y dataSource con el pool de conexiones de HikariCP. Cumpliendo correctamente con los principios SOLID.

## 1. Commit

Capa data creada con el object dataSource que gestiona la conexión con la base de datos y patrón DAO. Los servicios DAO declarados. Object Time gestiona la fecha y hora actual. Falta Lógica

[commit](https://github.com/danielmi5/practica-Calculadora_Log_DBRelacional/commit/6b6432a03c32633119bc2ec694f584548bca5963#diff-1dafba550b2f9f0c5fc11a1a83c59e095f18d79627034c67b1e78fba9325397c)

---

### Creación del paquete data con dao y db

He creado el paquete data, cuyo propósito es manejar todo el acceso y gestión de datos provenientes de la base de datos. Este paquete está dividido en dos subcarpetas: dao y db.

#### DAO 

Dentro de dao se implementan los Data Access Objects (DAO), que son las clases responsables de interactuar con la base de datos para realizar operaciones sobre las entidades del dominio.

Diseñé una interfaz genérica IDao<T> que define métodos básicos para realizar las operaciones CRUD. Además, incluye métodos especializadas para filtrar por fecha, hora o ambas. Esto permite que cualquier entidad que implemente esta interfaz pueda gestionar estas operaciones de forma homogénea.
```kotlin
interface IDao<T> {
    fun crear(entity: T): Boolean
    fun leer(id: Int): T?
    fun actualizar(entity: T): Boolean
    fun eliminar(id: Int): Boolean
    fun consultarPorFecha(fecha: LocalDate): List<T>
    fun consultarPorHora(hora: LocalTime): List<T>
    fun consultarPorFechaYHora(fecha: LocalDate, hora: LocalTime): List<T>
}
```
A partir de esta interfaz, creé dos clases concretas que implementan IDao<T> para manejar dos tipos de datos diferentes:
- ErrorDaoH2: Gestiona los datos relacionados con los errores (la entidad LogError).
- OperacionDaoH2: Gestiona los datos relacionados con operaciones (la entidad LogOperacion).

En ErrorDaoH2 definí todos los métodos de la interfaz, pero aún no los he implementado.

En OperacionDaoH2 comencé a implementar el método crear, que inserta un registro en la tabla OPERACION usando un PreparedStatement parametrizado para evitar inyección SQL y manejando excepciones de SQL y timeout. Los demás métodos siguen sin implementación.

#### DB

En db he creado un object DataSource que centraliza la configuración y creación de conexiones a la base de datos, ofreciendo dos modos de operación: uno basado en el driver JDBC nativo de H2 (JdbcDataSource), que proporciona conexiones directas y simples; y otro basado en HikariCP, un pool de conexiones eficiente que gestiona y reutiliza un conjunto limitado de conexiones para optimizar el rendimiento y la escalabilidad en aplicaciones con alta concurrencia. Esta abstracción permite cambiar fácilmente el mecanismo de conexión sin afectar el resto de la capa de acceso a datos.

```kotlin
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
```

### Declaración de los servicios DAO

Para mantener una arquitectura limpia, escalable y fácil de mantener, he creado servicios DAO para cada DAO, inyectando en ellos el DAO correspondiente. Cada servicio implementa su propia interfaz, la cual define los métodos que estarán disponibles para la clase Aplicacion. Estos métodos en el servicio corresponden directamente con los métodos del DAO, pero el servicio tiene la responsabilidad adicional de manejar la lógica de negocio. Además, estos servicios mantienen una comunicación bidireccional: reciben solicitudes y parámetros de la clase Aplicación, ejecutan la lógica requerida y luego invocan al DAO para acceder a la base de datos. Posteriormente, procesan la respuesta del DAO y la entregan de forma adecuada a la Aplicación. Este diseño favorece un desacoplamiento claro entre las dos capas, facilitando la evolución independiente de cada capa. 

Los métodos de OperacionDAOService y ErrorDAOService solo los he declarado, no tienen todavía la lógica.


### Creacion del model

He definido las entidades LogError (respresenta un log de error) y LogOperacion (representa un log de una operación).

- Clase abstracta Log: Es una clase abstracta que funciona como base para los registros que guardan información con fecha y hora. Incluye dos propiedades que automáticamente asignan la fecha y la hora actuales al crear cualquier log que herede de ella, facilitando así el control.

- Subclase LogError:  Representa un registro de error simple que almacena un mensaje del error ocurrido. Hereda de Log, por lo que automáticamente incluye la fecha y hora del registro y sobrescribe el método toString() para mostrar de forma clara el tipo de log y el mensaje, facilitando su lectura en los reportes o en la consola. Esto lo mejoro en un futuro.

- Subclase LogOperacion: Modela un registro de una operación matemática, guardando los dos números involucrados, el operador utilizado y el resultado obtenido. Hereda de Log, por lo que automáticamente incluye la fecha y hora del registro, y sobrescribe toString() para mostrar la operación de manera legible y clara. Esto lo mejoro en un futuro.

### Paquete utils

El objeto Time es una utilidad que proporciona métodos para obtener la fecha y la hora actuales en formatos legibles y consistentes para la aplicación. Utiliza la clase LocalDateTime de Kotlin para capturar el momento actual y luego lo formatea según patrones definidos con DateTimeFormatter. El método obtenerFechaActual() devuelve la fecha en un formato tipo “23 de mayo de 2025”, mientras que obtenerHoraActual() devuelve la hora en formato de 24 horas como “14:35:12”. Esto centraliza y estandariza el formato de fecha y hora en toda la aplicación, facilitando su uso en los registros.

```kotlin
object Time {

    fun obtenerFechaActual(): String{
        val fechaActual = LocalDateTime.now().format(formateoFecha)
        return fechaActual
    }

    fun obtenerHoraActual(): String{
        val horaActual = LocalDateTime.now().format(formateoHora)
        return horaActual
    }

    private val formateoFecha = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy")
    private val formateoHora = DateTimeFormatter.ofPattern("HH:mm:ss")
}
```
---

## 2. Commit

Patrón DAO aplicado y sus servicos también. Arregladas las clases LOG. Enum class Mes creada y Operadores mejorada. En el object Time métodos añadidos

[commit](https://github.com/danielmi5/practica-Calculadora_Log_DBRelacional/commit/59b67280ce98206eb711fe3cad5755a55f73e2a9)

---

### Patrón DAO implementado

### Lógica de los servicios DAO

### Entidades arregladas (Log)

### Paquete utils más métodos

---

## 3. Commit

Me he encargado de arreglar la estructura del proyecto, la organización y el código. Las clases que ya estaban hechas. He hecho el main instanciando cada clase necesaria. He modificado mucho código disperso.

[commit](https://github.com/danielmi5/practica-Calculadora_Log_DBRelacional/commit/b088de171ca6cc1ab490d339e36addffab6a1165)

### Nueva estructura

He convertido calculadora en un servicio, que implementa su anterior interfaz pero como servicio también.

---

## 4. Commit

Object BaseDatos implementado que se encarga de inicializar la base de datos. Errores arreglados y base de datos correcta.

[commit](https://github.com/danielmi5/practica-Calculadora_Log_DBRelacional/commit/77e86a320fe4a5729cd17c2a312a0277d3bd51ee)

### Object BaseDatos

```kotlin
import data.db.DataSource
import java.io.File

object BaseDatos {
    private const val SCRIPT_SQL = "./src/main/resources/scriptBD.sql"
    private const val SCRIPT_BORRADO_SQL = "./src/main/resources/scriptBorradoBD.sql"

    fun crearBaseDeDatos(borrarDatos: Boolean = false) {
        if (borrarDatos) {
            val script = File(SCRIPT_BORRADO_SQL).readText()
            DataSource.obtenerDataSource().connection.use {
                it.createStatement().use {
                    it.execute(script)
                }
            }
        }


        val script = File(SCRIPT_SQL).readText()
        DataSource.obtenerDataSource().connection.use {
            it.createStatement().use {
                it.execute(script)

            }
        }


    }
}
```

El objeto BaseDatos se encarga de inicializar y preparar la base de datos para la aplicación. Tiene dos scripts SQL definidos: uno para crear la estructura necesaria (scriptBD.sql) y otro para limpiar la base de datos (scriptBorradoBD.sql). El método crearBaseDeDatos acepta un parámetro opcional borrarDatos que, si es verdadero, primero ejecuta el script de borrado para limpiar la base de datos antes de crear la base de datos nuevamente.

Para ejecutar los scripts, el método lee el contenido de los archivos SQL como texto y luego obtiene una conexión a la base de datos a través del DataSource. Usa use para asegurarse de que la conexión y los statements se cierren automáticamente una vez ejecutados, evitando fugas de recursos. De esta forma, garantiza que la base de datos quede siempre en el estado esperado, ya sea limpia o solo creada, antes de que la aplicación comience a trabajar con ella.

Los scripts se encuentran en src/main/resources.


## 5. Commit 

He creado diferentes métodos en Aplicación para la gestión del menú y submenú. Y he declarado los métodos utilizados en el submenú. También he creado un nuevo servicio global `LogDaoService` que implementa una interfaz para los servicios DAO, que inyecta estos dos (IOperacionDaoService y IErrorDaoService). Y estos los he eliminado de la inyección en Aplicación y he añadido a LogDaoService.

[commit](https://github.com/danielmi5/practica-Calculadora_Log_DBRelacional/commit/4fcdfd696df5417044f95c319833c5dc1df3b7c4)

### Servicio global para los DAO

He implementado la interfaz ``ILogDaoService``. Esta interfaz define un conjunto de métodos para trabajar con registros de logs en general, sin importar si son de errores o de operaciones.

Esta clase implementa la interfaz ILogDaoService y funciona como un servicio que unifica los accesos a dos DAOs específicos: uno para errores (errorDao) y otro para operaciones (operacionDao). Estos son inyectados en él, y esto le permite delegar las operaciones específicas a cada DAO correspondiente, mientras se encarga de combinar y ordenar los resultados antes de entregarlos a la Aplicacion.

```kotlin
class LogDaoService(private val errorDao: IErrorDaoService, private val operacionDao: IOperacionDaoService) : ILogDaoService {
    override fun obtenerLogsPorFecha(fecha: String): List<Log> {
        val errores = errorDao.obtenerErrorPorFecha(fecha)
        val operaciones = operacionDao.obtenerOperacionPorFecha(fecha)
        return (errores + operaciones).sortedBy { it.hora }
    }

    override fun obtenerLogsPorHora(hora: String): List<Log> {
        val errores = errorDao.obtenerErrorPorHora(hora)
        val operaciones = operacionDao.obtenerOperacionPorHora(hora)
        return (errores + operaciones).sortedBy { it.fecha }
    }

    override fun obtenerLogPorFechaYHora(fecha: String, hora: String): Log? {
        val operacion = operacionDao.obtenerOperacionPorFechaYHora(fecha, hora)
        if (operacion != null) return operacion
        val error = errorDao.obtenerErrorPorFechaYHora(fecha, hora)
        return error
    }

    override fun eliminarLog(fecha: String, hora: String): Boolean {
        val eliminaOperacion = operacionDao.eliminarOperacion(fecha, hora)
        val eliminaError = errorDao.eliminarError(fecha, hora)
        return eliminaOperacion || eliminaError
    }

    override fun eliminarLogsPorFecha(fecha: String): Boolean {
        val eliminaOperacion = operacionDao.eliminarOperacionPorFecha(fecha)
        val eliminaError = errorDao.eliminarErrorPorFecha(fecha)
        return eliminaOperacion || eliminaError
    }

    override fun eliminarLogsPorHora(hora: String): Boolean {
        val eliminaOperacion = operacionDao.eliminarOperacionPorHora(hora)
        val eliminaError = errorDao.eliminarErrorPorHora(hora)
        return eliminaOperacion || eliminaError
    }

    override fun obtenerLogs(): List<Log> {
        val errores = errorDao.obtenerErrores()
        val operaciones = operacionDao.obtenerOperaciones()
        return (errores + operaciones).sortedBy { it.fecha }
    }

}
```

### Menú Aplicación

