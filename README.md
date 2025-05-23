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

Cada método de las clases DAO encapsula una consulta SQL específica. Por ejemplo, el método crear inserta una nueva operación en la base de datos, mientras que obtenerPorFecha, obtenerPorHora, y obtenerPorFechaYHora permiten recuperar operaciones filtradas según segun parámetros. Además, hay métodos como eliminar, eliminarPorFecha y eliminarPorHora que se encargan de borrar registros según parámetros dados. Todos los métodos manejan posibles errores SQL lanzando excepciones detalladas, lo que permite que el sistema controle adecuadamente los fallos durante el acceso a la base de datos.

Por ejemplo: 
https://github.com/danielmi5/practica-Calculadora_Log_DBRelacional/blob/7e465de5e6d21f88b035376b36da316cda696e97/src/main/kotlin/data/dao/OperacionDaoH2.kt#L24-L41

Está utilizando una sentencia SQL predefinida. Esta operación se realiza mediante un PreparedStatement que permite parametrizar la consulta SQL para evitar errores y vulnerabilidades como la inyección SQL. Se obtiene una conexión desde el DataSource utilizado, se prepara la sentencia con los campos necesarios (fecha, hora, num1, operador, num2, resultado) y se asignan los valores extraídos del objeto LogOperacion pasado como argumento. Luego, se ejecuta la inserción utilizando stmt.execute(), y gracias al uso de bloques use, tanto la conexión como el statement se cierran automáticamente al finalizar, gestionando adecuadamente los recursos. Y todo esto se controla mediante un try catch para enviar mensajes de error personalizados.
Todos los métodos DAO siguen más o menos la misma estructura, cambiando la lógica.

### Entidades arregladas (Log)

En la clase abstracta Log he añadido 2 constructores. El primer constructor genera la fecha y la hora de la creación del log y se lo asigna a fecha y hora, este caso es para cuando se crean los log a partir de la ejecución de la calculadora. El segundo constructor es cuando se pasa una fecha y una hora ya definida, este caso es para cuando se obtienen los log desde la base de datos.

```kotlin
abstract class Log {
    val fecha: String
    val hora: String

    constructor() {
        val fechaYHora = Time.obtenerFechayHoraActualF()
        this.fecha = fechaYHora.first
        this.hora = fechaYHora.second
    }

    constructor(fecha: String, hora: String) {
        this.fecha = fecha
        this.hora = hora
    }
}
```

En las subclases LogError y LogOperacion, las adapto al cambio anterior mencionado de su clase padre. Pasando por el constructor primario todas sus propiedades junto a una fecha y una hora (esto para el primer constructor de Log). También he creado un método estático crear() que recibe como parámetros los datos del Log específico y dentro mediante Time. genera la fecha y hora actuales, y devuelve una instancia de Log de ese tipo pasandole al constructor los parámetros, fecha y hora generadas. Tamién he personalizado más el método toString(). Más en adelante se mejoran estas clases.

### Dependencia HikariCP

Me faltaba la dependencia de HikariCP, para poder utilizarlo como pool de conexiones, así que la añadí al ``‎build.gradle.kts``:
```kotlin
implementation("com.zaxxer:HikariCP:5.1.0")
```

### Paquete utils más métodos

El object Time, tiene 3 métodos nuevos.

- obtenerFechaActual(): Devuelve un LocalDateTime.now()
- obtenerFechayHoraActualF(): Devuelve un par, siendo el primer valor la fecha y como segundo la hora.
- formatearFecha(fecha: String): Formatea una fecha con formato "dd-MM-yyyy" a "dd de "mes" sw "yyyy"".

También añadí una variable privada nueva (formateoFechaSql) --> dd-MM-yyyy

---

## 3. Commit

Me he encargado de arreglar la estructura del proyecto, la organización y el código. Las clases que ya estaban hechas. He hecho el main instanciando cada clase necesaria. He modificado mucho código disperso.

[commit](https://github.com/danielmi5/practica-Calculadora_Log_DBRelacional/commit/b088de171ca6cc1ab490d339e36addffab6a1165)

### Main preparado

```kotlin
    val ui = Consola()
    val calculadora = CalculadoraService()
    val gestorOperaciones = GestorOperacionesService(ui,calculadora)
    val ds = DataSource.obtenerDataSource()
    val operacionDao = OperacionDaoH2(ds); val errorDao = ErrorDaoH2(ds)
    val operacionDaoService = OperacionDaoService(operacionDao); val errorDaoService = ErrorDaoService(errorDao)
    val app = Aplicacion(gestorOperaciones, ui, operacionDaoService, errorDaoService)

    try {
        app.iniciar()
    } catch (e: Exception){
        ui.mostrar("ERROR CRÍTICO")
    }

    ui.mostrar("\nCALCULADORA SIN BATERÍA")
```

El main instancia y conecta los componentes principales de la aplicación siguiendo una arquitectura modular. Primero se crean la interfaz de usuario (Consola), el servicio de cálculo (CalculadoraService) y el gestor de operaciones que los une. Luego se configura la conexión a la base de datos mediante DataSource, y se instancian los DAOs (OperacionDaoH2 y ErrorDaoH2) usando dicha conexión. Estos DAOs se encapsulan en servicios (OperacionDaoService y ErrorDaoService) que se encargan de aplicar la lógica de negocio antes de acceder a los datos. Finalmente, se construye la clase principal Aplicacion, inyectando todas las dependencias necesarias, y se inicia su ejecución con app.iniciar(). Si ocurre una excepción crítica durante la ejecución, se muestra un mensaje de error y finaliza el programa.

### Nueva estructura

Para mejorar la organización del proyecto y seguir una arquitectura más limpia y modular, he realizado una reestructuración: la clase ``Calculadora`` ha sido convertida en un servicio, siguiendo el patrón de diseño de capas. Originalmente, esta clase se encontraba dentro del paquete model, lo cual no era buena idea ya que el paquete model debe contener únicamente representaciones de datos o entidades, no lógica de negocio. Entonces, he convertido calculadora en un servicio, que implementa su anterior interfaz pero como servicio también. De esta manera el código se vuelve más escalable y coherente.

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

```kotlin
    fun iniciar(){
        var salir = false
        while (!salir) {
            ui.mostrar("""
                <<< Menú Principal >>>
                1. Ejecutar Calculadora
                2. Gestionar Logs
                3. Salir
            """.trimIndent())
            when (ui.pedirOpcion("Elige una opción >> ")) {
                1 -> ejecutarCalculadora()
                2 -> menuGestionarLogs()
                3 -> salir = true
                else -> ui.mostrarError("Opción equivocada")
            }
        }
    }

```

Esta es la función principal de la aplicación. Muestra un menú y da a elegir 3 opciones: Ejecutar la calculadora (llama a ejecutarCalculadora()), gestionar los logs (llama a menuGestionarLogs()) y salir de la aplicación.

```kotlin
    private fun ejecutarCalculadora() {
        ui.pausar()
        var logActual: Log? = null
        do {
            try {
                ui.limpiarPantalla()
                val logResultado = gestorOperaciones.realizarOperacion()
                ui.mostrar(logResultado.obtenerLog())
                logActual = logResultado
            } catch (e: NumberFormatException) {
                ui.mostrarError(e.message ?: "Se ha producido un error!")
                logActual = LogError.crear(e.message.toString())
            } catch (e: InfoCalcException) {
                ui.mostrarError(e.message ?: "Se ha producido un error!")
                logActual = LogError.crear(e.message.toString())

            } catch (e: Exception) {
                ui.mostrarError(e.message ?: "Se ha producido un error!")
                logActual = LogError.crear(e.message.toString())
            } finally {
                when(logActual){
                    is LogOperacion -> operacionDaoService.crearOperacion(logActual)
                    is LogError -> errorDaoService.crearError(logActual)
                }
            }
        } while (ui.preguntar())
        ui.limpiarPantalla()
    }
```

Esto es la función iniciar() anterior a la actualización a base de datos. Ahora está actualizada con el menú y lista para la base de datos. En ``ejecutarCalculadora()``, se encapsula la lógica para realizar operaciones matemáticas usando el componente gestorOperaciones. Cualquier resultado que se complete se convierte en un objeto LogOperacion, mientras que los errores generados durante la operación se convierten en LogError. Ahora en vez de una linea de mensaje, se utilizan las entidades Log (sus métodos tambien) y la lógica respecto a la base de datos (llamadas a DaoServices).

```kotlin

    private fun menuGestionarLogs() {
        var salir = false
        while (!salir) {
            ui.mostrar("""
                <<< Gestión Logs >>>
                1. Obtener logs por fecha
                2. Obtener logs por hora
                3. Obtener log por fecha y hora
                4. Eliminar log por fecha y hora
                5. Eliminar logs por fecha
                6. Eliminar logs por hora
                7. Obtener todos los logs
                8. Salir
            """.trimIndent())
            when (ui.pedirOpcion("Elige una opción")) {
                1 -> mostrarLogPorFecha()
                2 -> mostrarLogPorHora()
                3 -> mostrarLogPorFechaYHora()
                4 -> eliminarLogPorFechaYHora()
                5 -> eliminarLogPorFecha()
                6 -> eliminarLogPorHora()
                7 -> mostrarLogs()
                8 -> salir = true
                else -> ui.mostrarError("Opción equivocada")
            }
            ui.limpiarPantalla()
        }
    }

    private fun mostrarLogPorFecha() {}
    private fun mostrarLogPorHora() {}
    private fun mostrarLogPorFechaYHora() {}
    private fun eliminarLogPorFechaYHora() {}
    private fun eliminarLogPorFecha() {}
    private fun eliminarLogPorHora() {}
    private fun mostrarLogs() {}


}
```

El método ``menuGestionarLogs()`` ofrece un menú secundario dedicado exclusivamente a la consulta y eliminación de registros. A través de opciones enumeradas, permite al usuario buscar logs por fecha u/y hora, además de poder eliminar registros bajo esos mismos criterios. También permite mostrar todos los logs almacenados. Estas opciones están delegadas a métodos auxiliares, esots métodos están declarados (todavía sin lógica).


## Último Commit

Hice la lógica del submenú de logs de cada opción. Y con estos cambios tuve que moficar los métodos de los DAO y de sus servicios para cambiar su lógica.

[Commit](https://github.com/danielmi5/practica-Calculadora_Log_DBRelacional/commit/9aaf2a5db9cf2afff00f41a3155ed8019887481d#diff-1b61b698d955cdd97fc1a49065498c377522316a6e63b10770a8a689977fb316)

### Cambios a la aplicación

Modifiqué algunos fallos de sintaxis.

En ``ejecutar calculadora`` la lógica en el finally de comprobar la eliminé y ahora, llama a crearLog si es nula (nuevo método creado).

Añadí un bloque de try catch en el submenú de gestionar los logs para controlar las posibles excepciones.

Completé la lógica de los métodos del submenú. En caso de necesitar fecha y/u hora, añadí pedir datos por consola para la fecha y hora, además de la lógica pertinente del método que se pida. Se verifica con require si los datos obtenidos (fecha / hora) cumplen con el formato pedido. 
Y también, pero en todos los métodos, añadi la opción de pedir por tipo del Log (método nuevo de IEntradaSalida). Dando la opción de no pasar nada si no se quiere filtrar.

```kotlin
override fun pedirTipoLog(): String? {
        val opciones = listOf("ERROR", "OPERACION")
        val seleccion = pedirInfo("Selecciona el tipo de log (ERROR o OPERACION | '' Si no quieres 'filtrar')").uppercase().replace("Ó","O")
        return if (seleccion in opciones) seleccion else null
    }
```

### Cambios a los DAO

En el caso de ``ErrorDao`` cambié fallos de sintaxis y, modifiqué el tipo del return y la lógica de los métodos eliminarPorFecha/Hora. Ahora simplemente en vez de devolver un booleano si había eliminado algo, ahora devuelve el número de eliminaciones realizadas para poder manejar este dato desde la app.

Lo mismo ha ocurrido con ``OperacionDao`` modifiqué el tipo del return y la lógica de los métodos eliminarPorFecha/Hora (siguiendo lo mismo que con ErrorDao), además de modificar el tipo del return en la interfaz genérica ``IDao``.

### Cambios a Service

El cambio anterior ha provocado que cambie todos los return de esos métodos eliminar de los serviciosDao y sus interfaces a Int. No ha afectado a la lógica de los servicios espécificos (ErrorDaoService y OperacionDaoService). Sin embargo, he tenido que cambiar un poco la lógica de los métodos de ``LogDaoService`` (simplemente sumar correctamente los números de lo que devuelven) y sintaxis.

También, por añadir la posibilidad de filtrar las operaciones de consulta y eliminación, he tenido que añadir lógica que verifique antes de realizar la llamada al servicio DAO específico, si coincide con el tipo buscado (en caso de nulo, lo es).

- Por ejemplo:

```kotlin
override fun eliminarLogsPorHora(hora: String, tipo: String?): Int {
        val eliminaOperacion = if (tipo == null || tipo == "OPERACION") operacionDao.eliminarOperacionPorHora(hora) else 0
        val eliminaError = if (tipo == null || tipo == "ERROR") errorDao.eliminarErrorPorHora(hora) else 0
        return eliminaOperacion + eliminaError
```
En este caso, antes de eliminar comprueba si el tipo es nulo o si el tipo coincide con el Log que maneja. Si coincide llama al método de su serviceDao, sino devuelve 0 directamente. Y devuelve la suma del número de ambos.

Añadí a su vez un método para crear Logs, dependiendo del tipo de Log que reciba ejecuta una llamada u otra.

```kotlin
override fun crearLog(log: Log) {
        when(log){
            is LogOperacion -> operacionDao.crearOperacion(log)
            is LogError -> errorDao.crearError(log)
        }
    }
```

---

Tras este commit se realizó la documentación del código.
