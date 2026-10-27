# Informe de mejoras

## Mejora de calidad implementada

**Tipo de mejora:** Separación de responsabilidades y eliminación de duplicación de código de infraestructura.

**Problema identificado:**
Cada una de las clases de persistencia concreta (`UsuarioDao`, `HerramientaDao` y `AsignacionDao`) duplicaba la declaración de variables de configuración de credenciales (URL, usuario y contraseña) de forma estática dentro del código fuente. Esto violaba el principio de Responsabilidad Única, provocaba fallas críticas de comunicación por tiempo de espera agotado fuera del laboratorio, y obligaba a realizar modificaciones manuales en múltiples archivos ante cualquier cambio en el servidor MySQL.

<texto>

**Antes:**

```java
 Fragmento repetido en cada clase DAO (Ej: UsuarioDao.java)
private static final String URL = "jdbc:mysql://10.123.0.165:3306/gamabasis_ex1p3g4?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
private static final String USUARIO = "gamabasis_ex1p3g4";
private static final String CONTRASENA = "8GuO3r7cM%";

private Connection conectar() throws SQLException {
    return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
}
```

**Después:**

```java
 Fragmento mejorado: Reutilización de la clase centralizada de la cátedra
private Connection conectar() throws SQLException {
    // Delegación absoluta de la lectura del archivo seguro db.properties
    return cr.ac.una.config.ConexionBD.obtenerConexion();
}
```

**Beneficio obtenido:**

Se centralizó la gestión de la infraestructura en un único punto. Los DAOs se desentendieron por completo de saber en qué servidor físico o con qué contraseña opera el sistema, permitiendo cambiar de un entorno local a un entorno en la nube mediante el archivo externo `db.properties` sin tocar una sola línea de código Java. Se eliminó la duplicación de código y se eliminaron los errores de red.

**Commit:** Cambios de algunos archivos y agregacion del esquema

## Defectos encontrados y corregidos

| ID | Defecto | Cómo se detectó (prueba) | Corrección | Commit |
|---|---|---|---|---|
| **D-01** | El método `setCarnet(int)` de la entidad `Usuario` utilizaba `Objects.requireNonNull`, `.isBlank()` y `.length()`. Provocaba un error de compilación inmediato en NetBeans al intentar invocar métodos de objetos `String` sobre un tipo primitivo numérico `int`. | Durante la compilación de la Etapa 2 y Etapa 3 en NetBeans IDE al integrar las clases del modelo. | Se eliminaron las llamadas de cadena y se reestructuró la validación utilizando operadores matemáticos lógicos puros (`Carnet <= 0 \|\| Carnet > 9999999`), lanzando un `IllegalArgumentException` controlado ante datos inválidos. | cambios en la calses modelo |

