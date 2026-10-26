# Etapa 2 — Implementación funcional y persistencia

## Cambios al diseño

| Cambio | Motivo |
|---|---|
| Se agregó el paquete `Dao` con `UsuarioDao`, `HerramientaDao` y `AsignacionesDao`, que implementan las interfaces de `repository`. | Separar la implementación JDBC de la interfaz que usa el `Service`. |
| La clase `Asignaciones` pasó a llamarse `Asignacion` y se volvió inmutable en sus datos de origen (`usuarioId`, `herramientaId`, `horasolicitud` son `final`); la devolución se registra con `devolver(...)`. | Evitar que una asignación cambie de estudiante o herramienta después de creada. |
| Se agregó la columna `CondicionDevolucion` a `Asignaciones`. Una asignación abierta tiene el valor `Pendiente`. | Saber qué asignaciones siguen abiertas (RN-02, RN-03). |
| Se agregó `DuracionMaximaHoras` a `Herramienta`. Al asignar, `Horadevolucion` se calcula como solicitud + duración máxima. | Registrar la hora límite de devolución. |
| Se agregó `PersistenciaException` (unchecked). | Envolver `SQLException` en la capa de datos sin obligar a las interfaces a declarar `throws`. |
| Se agregó el enum `UsuarioHabilitado`. | Representar el estado del estudiante. |
| Se eliminó la tabla `UsuarioHerramienta` de la base de datos. La clase `UsuarioHerramienta` sigue en `model`, pero no se usa. | La relación estudiante–herramienta ya queda registrada en `Asignaciones`, así que la tabla puente era redundante. |
| Se amplió `Usuario.Nombre` a `varchar(30)` y `Usuario.Estado` a `varchar(20)`. | Admitir nombres completos y el valor `DESHABILITADO`. |

Modelo relacional final (`SQL/gamabasis_ex1p3g4.sql`):

| Tabla | Columnas | Llaves |
|---|---|---|
| `Usuario` | `Carnet` int, `Nombre` varchar(30), `Carrera` varchar(20), `Estado` varchar(20) | PK `Carnet` |
| `Herramienta` | `Codigo` int, `Nombre` varchar(30), `Tipo` varchar(20), `Estado` varchar(20), `DuracionMaximaHoras` int | PK `Codigo` |
| `Asignaciones` | `Id` int AUTO_INCREMENT, `Usuarioid` int, `Herramientaid` int, `Horasolicitud` datetime, `Horadevolucion` datetime, `CondicionDevolucion` varchar(20) | PK `Id`; FK `Usuarioid → Usuario.Carnet`; FK `Herramientaid → Herramienta.Codigo` |

> El diagrama del modelo relacional de la Etapa 1 todavía muestra `UsuarioHerramienta` y no tiene `CondicionDevolucion` ni `DuracionMaximaHoras`. Hay que actualizarlo.

## Configuración y ejecución

```text
1. Crear la base de datos y cargar el script:
       mysql -u root -p -e "CREATE DATABASE gamabasis_ex1p3g4"
       mysql -u root -p gamabasis_ex1p3g4 < SQL/gamabasis_ex1p3g4.sql
   (o importar SQL/gamabasis_ex1p3g4.sql desde phpMyAdmin / MySQL Workbench)

2. Configurar la conexión:
       cd proyecto/src/main/resources
       cp db.properties.example db.properties
   En db.properties, cambiar la base de datos de db.url a gamabasis_ex1p3g4
   y completar db.usuario y db.clave.

3. Ejecutar:
       cd proyecto
       mvn clean compile exec:java
   Desde NetBeans: abrir la carpeta proyecto/ y ejecutar cr.ac.una.app.App
```

> ⚠️ El script que se usa es `SQL/gamabasis_ex1p3g4.sql`, exportado desde phpMyAdmin. La guía pide `database/schema.sql`, que sigue siendo la plantilla vacía, con `DROP/CREATE DATABASE` y datos de prueba. Ver D-09 en [`../etapa_3/mejoras.md`](../etapa_3/mejoras.md).

## Operaciones implementadas

| Operación | Tipo | Menú | Controller | Service | Repository |
|---|---|---|---|---|---|
| Registrar estudiante | Registro | 2 → 1 | `UsuarioController.registrarEstudiante` | `UsuarioService.registrarUsuario` | `UsuarioRepository.save` |
| Consultar estudiante por carnet | Consulta | 2 → 2 | `UsuarioController.consultarEstudiante` | `UsuarioService.consultarUsuario` | `UsuarioRepository.findById` |
| Listar estudiantes | Consulta | 2 → 3 | `UsuarioController.listarEstudiantes` | `UsuarioService.listarUsuarios` | `UsuarioRepository.findAll` |
| Actualizar estudiante / habilitar | Actualización | 2 → 4 | `UsuarioController.actualizarEstudiante` | `UsuarioService.actualizarUsuario` | `UsuarioRepository.save` |
| Registrar herramienta | Registro | 3 → 1 | `HerramientaController.registrarHerramienta` | `HerramientaService.registrarHerramienta` | `HerramientaRepository.save` |
| Consultar herramienta por código | Consulta | 3 → 2 | `HerramientaController.consultarHerramienta` | `HerramientaService.ConsultarHerramienta` | `HerramientaRepository.findById` |
| Listar herramientas | Consulta | 3 → 3 | `HerramientaController.listarHerramientas` | `HerramientaService.listarHerramientas` | `HerramientaRepository.findAll` |
| Actualizar herramienta / estado | Actualización | 3 → 4 | `HerramientaController.actualizarHerramienta` | `HerramientaService.actualizarHerramienta` | `HerramientaRepository.save` |
| Asignar herramienta | Negocio | 4 | `AsignacionController.postAsignar` | `AsignacionService.registrarPrestamo` | `AsignacionRepository.save`, `UsuarioRepository.countActiveAsignaciones` |
| Devolver herramienta | Negocio | 5 | `AsignacionController.putDevolver` | `AsignacionService.registrarDevolucion` | `AsignacionRepository.findActiveByHerramientaId`, `save` |
| Herramientas asignadas a un estudiante | Consulta con JOIN | — | — | — | `AsignacionRepository.findHerramientasByUsuarioId` |
| Historial de una herramienta | Consulta con filtro | — | — | — | `AsignacionRepository.findHistorialByHerramientaId` |

> Las dos últimas consultas están implementadas en `AsignacionesDao`, pero todavía no tienen método en el `Service` ni opción en el menú.

## Reglas de negocio implementadas

| ID | Regla | Clase / método del Service | Excepción que se lanza |
|---|---|---|---|
| RN-01 | Solo se asignan herramientas DISPONIBLES a estudiantes habilitados. | `AsignacionService.validarDisponibilidad` | `ReglaNegocioException` |
| RN-02 | Máximo 3 herramientas asignadas por estudiante. | `AsignacionService.validarLimiteHerramientas` | `ReglaNegocioException` |
| RN-03 | Solo se devuelve una herramienta con asignación abierta. | `AsignacionService.registrarDevolucion` y `validarDevolucion` | `ReglaNegocioException` |
| RN-04 | Devolución DAÑADA inhabilita al estudiante. | `AsignacionService.aplicarSancionPorDanio` | — (cambia el estado) |
| — | Carnet y código no duplicados al registrar. | `UsuarioService.registrarUsuario`, `HerramientaService.registrarHerramienta` | `ReglaNegocioException` |
| — | Campos obligatorios y estado válido. | `UsuarioService.validarUsuario`, `HerramientaService.validarHerramienta`, `AsignacionService.validarAsignacion` | `ValidacionException` |

## JDBC

Método del repositorio con `PreparedStatement` y `try-with-resources` (`AsignacionesDao.findActiveByHerramientaId`):

```java
@Override
public Optional<Asignacion> findActiveByHerramientaId(int codigo) {
    String sql = "SELECT " + COLUMNAS + " FROM Asignaciones "
               + "WHERE Herramientaid = ? AND CondicionDevolucion = ? "
               + "ORDER BY Horasolicitud DESC LIMIT 1";

    try (Connection con = conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, codigo);
        ps.setString(2, PENDIENTE);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return Optional.of(mapear(rs));
            }
            return Optional.empty();
        }
    } catch (SQLException e) {
        throw new PersistenciaException(
            "No se pudo buscar la asignacion activa de la herramienta " + codigo, e);
    }
}
```

`COLUMNAS` es una constante con los nombres de columna: no contiene datos del usuario. Todos los valores se pasan con `?`.

Consulta con JOIN (`AsignacionesDao.findHerramientasByUsuarioId`):

```java
String sql = "SELECT h.Codigo, h.Nombre, h.Tipo, h.Estado, h.DuracionMaximaHoras "
           + "FROM Herramienta h "
           + "INNER JOIN Asignaciones a ON a.Herramientaid = h.Codigo "
           + "WHERE a.Usuarioid = ? AND a.CondicionDevolucion = ? "
           + "ORDER BY a.Horasolicitud";
```

**¿Alguna operación usa transacción?**

Todavía no. Asignar (insertar asignación y cambiar la herramienta a ASIGNADA) y devolver (actualizar asignación, herramienta y, si aplica, estudiante) modifican varias tablas y deben ejecutarse en una sola transacción con `setAutoCommit(false)`, `commit()` y `rollback()`. Hoy cada DAO abre su propia conexión, así que si falla un paso, los anteriores quedan guardados. Ver D-08 en [`../etapa_3/mejoras.md`](../etapa_3/mejoras.md).

## Service

Operación de negocio principal (`AsignacionService.registrarPrestamo`):

```java
public void registrarPrestamo(int carnet, int codigoHerramienta) throws Exception {
    validarDisponibilidad(carnet, codigoHerramienta);   // RN-01
    validarLimiteHerramientas(carnet);                  // RN-02

    Asignacion nuevaAsignacion = new Asignacion(carnet, codigoHerramienta, LocalDateTime.now());
    validarAsignacion(nuevaAsignacion);
    Herramienta herramienta = herramientaRepository.findById(codigoHerramienta).get();
    herramienta.setEstadoHerrramienta("ASIGNADA");

    asignacionRepository.save(nuevaAsignacion);
}
```

Regla RN-02 (`AsignacionService.validarLimiteHerramientas`):

```java
int cantidadActivas = usuarioRepository.countActiveAsignaciones(carnet);
if (cantidadActivas >= 3) {
    throw new ReglaNegocioException(
        "El usuario ya tiene el maximo de 3 herramientas asignadas");
}
```

El `Service` recibe las interfaces de `repository` por constructor:

```java
public AsignacionService(AsignacionRepository asignacionRepository,
                         HerramientaRepository herramientaRepository,
                         UsuarioRepository usuarioRepository) { ... }
```

## Excepciones

Excepción personalizada:

```java
public class ReglaNegocioException extends Exception {
    public ReglaNegocioException(String mensaje) { super(mensaje); }
    public ReglaNegocioException(String mensaje, Throwable causa) { super(mensaje, causa); }
}
```

Dónde se captura (`AsignacionController.postAsignar`). La aplicación muestra el mensaje y vuelve al menú:

```java
try {
    asignacionService.registrarPrestamo(carnet, codigoHerramienta);
    System.out.println("Herramienta (" + codigoHerramienta + ") asignada correctamente al carnet " + carnet + ".");
} catch (ReglaNegocioException | ValidacionException e) {
    System.out.println("Regla de negocio violada" + e.getMessage());
} catch (Exception e) {
    System.out.println("Error del sistema no se pudo procesar: " + e.getMessage());
}
```

`SQLException` se envuelve en `PersistenciaException` dentro de cada DAO.

## Evidencias de ejecución

**Datos en la base de datos** (exportados el 26-10-2026 en `SQL/gamabasis_ex1p3g4.sql`):

| Tabla | Registro | Qué demuestra |
|---|---|---|
| `Usuario` | `123`, Elkin Octavio Morales, Ing Sistemas, `DESHABILITADO` | Registro de estudiante y actualización de estado (inhabilitar). |
| `Herramienta` | `12`, Multimetro, Electrica, `EN_MANTENIMIENTO`, `DuracionMaximaHoras = 0` | Registro de herramienta y cambio de estado. La duración en 0 confirma el defecto D-07. |
| `Asignaciones` | sin registros | Todavía no hay asignaciones guardadas (ver D-01, D-02). |

> Pendiente: pegar la salida de consola (o capturas en `docs/etapa_2/img/`).

Capturas que se necesitan:

1. Registro de un estudiante y de una herramienta.
2. Consulta por carnet/código y listado.
3. Actualización de un estudiante o herramienta.
4. Asignación exitosa.
5. Asignación rechazada (estudiante inhabilitado, herramienta no disponible o límite de 3).

```text
<salida de consola>
```

## Distribución del trabajo

| Integrante | Aportes principales |
|---|---|
| Octavio Morales | Controllers, integración del menú en `App`, adaptación de Controller y Service |
| Adrián Arce | DAOs JDBC (`UsuarioDao`, `HerramientaDao`, `AsignacionesDao`), cambios al modelo, script SQL |
| Tadanory Estrada | Validaciones RN-01 a RN-04 en `AsignacionService`, integración de repositorios en services |
