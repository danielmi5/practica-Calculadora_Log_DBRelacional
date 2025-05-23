# README de la primera versión

## Dependencia necesaria para H2

Lo primero que hice al crear el repositorio e añadir el proyecto "calculadoraLog con txt", fue añadir la dependencia de la base de datos **H2** al archivo `build.gradle.kts`, lo que permite trabajar con una base de datos embebida durante el desarrollo:
```kotlin
implementation("com.h2database:h2:2.3.232")
```

## Conexión a una base de datos

Realicé manualmente la conexión a la base de datos H2 directamente en el método `main`. Para ello utilicé el **DriverManager** de JDBC, proporcionando la **URL JDBC**, el **usuario** y la **contraseña**.

En ese momento, aún no sabía que no era necesario especificar el driver manualmente en versiones recientes, por lo que incluí la línea `Class.forName(...)`. Todo el proceso estaba envuelto en un bloque `try-catch`, que imprimía mensajes en consola para saber si la conexión había sido exitosa o si se había producido un error.

**DriverManager** es una clase que gestiona los controladores JDBC y permite conectarse a bases de datos.  
El método `getConnection(url, usuario, contraseña)` establece la conexión con la base de datos usando la URL y credenciales proporcionadas, y devuelve un objeto `Connection` para interactuar con la base de datos.

```kotlin
fun main() {
    //Conexión Base de datos

    val url = "jdbc:h2:~/bdH2/test"
    val usuario = "user"
    val contraseña = ""

    try {
        Class.forName("org.h2.Driver")
        val conexion = DriverManager.getConnection(url, usuario, contraseña)
        println("Conexión exitosa")
        conexion.close()
    } catch (e: SQLException) {
        println("Error en la conexión: ${e.message}")
    } catch (e: ClassNotFoundException) {
        println("No se encontró el driver JDBC: ${e.message}")
    }
}
```

## Gestión de los procesos con la base de datos en una clase BaseDatos

En lugar de realizar la conexión con la base de datos en el main, decidí crear una clase llamada BaseDatos dentro del paquete utils. Esta clase implementa la interfaz GestiónBaseDeDatos y sus métodos:
- fun conectarBD(): Connection
- fun cerrarBD(conexion: Connection)

La clase BaseDatos se encarga de manejar la conexión y el cierre de la base de datos de forma centralizada. Para esto, recibe la configuración necesaria para el DriverManager: URL, usuario, contraseña y el driver JDBC.

```kotlin
class BaseDatos(private val url: String, private val driver: String, private val usuario: String, private val contra: String) : GestiónBaseDeDatos {
    override fun conectarBD(): Connection {
        try {
            Class.forName(driver)
            val conexion = DriverManager.getConnection(url, usuario, contra)
            println("Conexión exitosa")
            return conexion
        } catch (e: SQLException) {
            println("Error en la conexión: ${e.message}")
        } catch (e: ClassNotFoundException) {
            println("No se encontró el driver JDBC: ${e.message}")
        }
        throw IllegalArgumentException("ERROR CONECTAR BD")
    }

    override fun cerrarBD(conexion: Connection) {
        conexion.close()
        println("Conexión cerrada")
    }
}
```
Con esto mejoré la organización del proyecto, centralizando la gestión de la base de datos y facilitando la reutilización del código para abrir y cerrar conexiones.
[commit](https://github.com/danielmi5/practica-Calculadora_Log_DBRelacional/commit/cc5e1baa0bdd597fbeb6be83407dc62ce539cd87)


## Más funcionalidades para BaseDatos
[commit](https://github.com/danielmi5/practica-Calculadora_Log_DBRelacional/commit/6e0e3c5f516333cc9fad91572c0572bbb90456bd)

Añadí más funcionalidades como es: crear, borrar, borrar los datos, insertar datos a una tabla.
Ahora la clase de base de datos no solo gestiona la conexión, sino que también se encarga del acceso a los datos, lo que rompe el principio de responsabilidad única y dificulta el mantenimiento.


```kotlin
override fun crearTabla(
    nombreTabla: String,
    columnas: List<Pair<String, String>>,
    clavePrimaria: Pair<String, String>?
) {
    conexion?.prepareStatement("CREATE TABLE $nombreTabla (${Utils.obtenerColumansSql(columnas, clavePrimaria)});")?.execute() ?: throw SQLException("No se pudo crear la tabla")
}
```
Este método crea una tabla en la base de datos con el nombre indicado, definiendo sus columnas según una lista de pares (nombre de columna y tipo de dato) y opcionalmente una clave primaria. Construye y ejecuta dinámicamente una sentencia SQL CREATE TABLE usando un PreparedStatement. Si no puede ejecutar la operación, lanza una excepción SQLException.

```kotlin
override fun borrarTabla(nombreTabla: String) {
    conexion?.prepareStatement("DROP TABLE IF EXISTS $nombreTabla CASCADE")?.execute() ?: throw SQLException("No se pudo borrar la tabla ${nombreTabla.uppercase()}")
}
```
El método elimina la tabla especificada si existe, utilizando la sentencia SQL DROP TABLE IF EXISTS con la opción CASCADE para eliminar dependencias relacionadas. Si la tabla no se puede borrar o la conexión falla, se lanza una excepción con un mensaje.

```kotlin
override fun eliminarDatosTabla(nombreTabla: String){
    conexion?.prepareStatement("DELETE FROM $nombreTabla ")?.execute() ?: throw SQLException("No se pudo borrar los datos de la tabla ${nombreTabla.uppercase()}")
}
```

Este método elimina todos los registros existentes dentro de la tabla dada, sin borrar la tabla en sí, mediante la sentencia SQL DELETE FROM. En caso de error durante la ejecución, lanza una excepción.

```kotlin
override fun insertarDatosTabla(nombreTabla: String, valores: List<Any>, listaColumnasAInsertar: List<String>) {
    val stm = conexion?.prepareStatement("INSERT INTO $nombreTabla (${Utils.pasarColumnas(listaColumnasAInsertar)}) VALUES (${Utils.pasarValues(listaColumnasAInsertar)})") ?: throw SQLException("No se pudo insertar en la tabla $nombreTabla")
    var cont = 1
    for (valor in valores) {
        when (valor) {
            is String -> stm.setString(cont, valor)
            is Int -> stm.setInt(cont, valor)
            is Double -> stm.setDouble(cont, valor)
        }
        cont++
    }
    stm.executeUpdate()
}
```
Inserta una fila en la tabla indicada, asignando valores a las columnas especificadas. Recibe una lista de valores (que pueden ser String, Int o Double) y una lista de columnas donde esos valores se insertarán. Construye un PreparedStatement con la sentencia INSERT INTO y asigna cada valor a su parámetro según su tipo, luego ejecuta la inserción. Si la operación falla, lanza una excepción.


Estos métodos contienen varios errores: están expuestos a inyecciones SQL (más grave), ya que usan interpolación directa en las sentencias. Además, los recursos de PreparedStatement no se cierran correctamente, causando fugas de memoria.


## Implementación
[commit](https://github.com/danielmi5/practica-Calculadora_Log_DBRelacional/commit/2cb9d64ea71faa111ace8c22cae9eb08cfd26a7d)

Ahora modifiqué el código para que funcionase con una data class Log y añadí más metodos a BaseDatos.

[commit](https://github.com/danielmi5/practica-Calculadora_Log_DBRelacional/commit/9539fed66b81ffc3ccf8d965404863275de0bf45)

Por último implementé, ademas del manejo anterior de los logs, que los logs se guarden también en la base de datos.
