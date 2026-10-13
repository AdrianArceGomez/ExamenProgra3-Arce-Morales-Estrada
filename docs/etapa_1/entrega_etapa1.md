# Etapa 1 — Análisis, diseño y arquitectura

> Complete todas las secciones. Elimine las indicaciones entre `< >` al terminar.

## 9.1 Identificación

```
Caso asignado: 10
Integrante 1: Octavio Morales
Integrantes 2:Adrian Arce
Integrante 3: Tadanory Estrada
URL del repositorio: https://pma.us.cloudlogin.co/ 
```

## 9.2 Descripción del problema

**Problema que se desea resolver:**

llevar un control sobre los herrmientas y estudiantes durante las prácticas.
Algunas herramientas no se devuelven o se devuelven dañadas y no queda registro de quién las tenía.

**Quién utilizaría el sistema:**

Los asistentes del laboratorio.

**Principales operaciones:**

Registrar la entrada y salida de herramientas del laboratorio.
listar las herramientas que estan siendo utilizadas.
actualizar en los estado y objetos herramientas sus datos.

## 9.3 Requerimientos funcionales

```
RF-01. El sistema debe permitir registrar, consultar, listar y actualizar la información de los estudiantes, incluyendo la opción de habilitarlos o inhabilitar de forma manual.
RF-02. El sistema debe permitir registrar, consultar, listar y actualizar la información del inventario de herramientas.
RF-03. El sistema debe permitir asignar una herramienta a un estudiante verificando que el estudiante esté habilitado, la herramienta esté en estado DISPONIBLE y que el estudiante no supere el límite máximo de 3 herramientas asignadas simultáneamente.
RF-04. El sistema debe cambiar automáticamente el estado de la herramienta a ASIGNADA y registrar la fecha/hora al realizar una asignación.
RF-05. El sistema debe permitir registrar la devolución de una herramienta previamente asignada, registrando la fecha/hora, la condición de entrega (BUENA o DANADA).
RF-06. El sistema debe actualizar el estado de la herramienta a DISPONIBLE si la devolución es BUENA, o a DANADA e inhabilitar automáticamente al estudiante si la devolución es DANADA.
RF-07. El sistema debe permitir consultar el historial completo de asignaciones de una herramienta específica y las herramientas actualmente asignadas a un estudiante determinado.
RF-08. El sistema debe generar un reporte detallado con las herramientas que se encuentran actualmente asignadas (asignaciones abiertas).
```

## 9.4 Reglas de negocio

| ID | Regla | Operación donde se aplica | Qué ocurre si se viola |
|----|-------|---------------------------|------------------------|
| RN-01 |Solo se asignan herramientas DISPONIBLES a estudiantes habilitados.  |Asignación de herramienta (`AsignacionService.asignarHerramienta`) |El sistema cancela la transacción y lanza una excepción impidiendo registrar el préstamo. |
| RN-02 |Un estudiante no puede tener más de 3 herramientas asignadas al mismo tiempo. |Asignación de herramienta (`AsignacionService.asignarHerramienta`) |Se bloquea la asignación actual arrojando un error de límite de préstamos excedido. |
| RN-03 |Solo se puede devolver una herramienta con una asignación abierta. |Devolución de herramienta (`AsignacionService.devolverHerramienta`) |Se rechaza la operación informando que la herramienta no posee un flujo de préstamo activo o vigente. |
| RN-04 |Una devolución en condición DAÑADA inhabilita al estudiante hasta que el asistente lo habilite de nuevo. |Devolución de herramienta (`AsignacionService.devolverHerramienta`) |La herramienta cambia automáticamente al estado DAÑADA y el estudiante muta su estado a INHABILITADO. |
## 9.5 Entidades, atributos y relaciones

| Clase | Responsabilidad | Atributos principales |
|--------|-----------------|-----------------------|
| Usuario | Entidad del modelo que representa y encapsula los datos físicos de un estudiante en el sistema. | `carnet: int`, `carrera: String`, `estado: String`, `nombre: String`, getters y setters. |
| Herramienta | Entidad del modelo que representa y encapsula las propiedades de un equipo del inventario. | `codigo: int`, `estado: EstadoHerramienta`, `nombre: String`, `tipo: String`, getters y setters. |
| Asignaciones | Entidad del modelo que registra la transacción histórica y el flujo de préstamo entre alumnos y equipos. | `id: int`, `herramientaid: int`, `usuarioid: int`, `horasolicitud: LocalDateTime`, `horadevolucion: LocalDateTime`, `condicionDevolucion`. |
| UsuarioHerramienta | Entidad intermedia que rompe físicamente la relación muchos a muchos en la persistencia. | `herramientaid: int`, `usuarioid: int`, getters y setters. |
| AsignacionController| Expone los endpoints de la API para procesar las solicitudes de préstamos y la entrega de equipos. | `postAsignar(carnet, codigoHerramienta)`, `putDevolver(codigoHerramienta, condicion)`. |
| UsuarioController | Controlador encargado de gestionar las peticiones externas relacionadas con las operaciones de los alumnos. | `registrarEstudiante(usuario)`, `consultarEstudiante(carnet)`, `listarEstudiantes()`, `actualizarEstudiante(usuario)`. |
| HerramientaController| Controlador encargado de gestionar las peticiones externas asociadas a la administración del inventario. | `registrarHerramienta(herramienta)`, `consultarHerramienta(codigo)`, `listarHerramientas()`, `actualizarHerramienta(herramienta)`. |

| Clases relacionadas | Tipo de relación | Multiplicidad | Explicación |
|---|---|---|---|
| AsignacionController -> AsignacionService | Dependencia (`- - >`) | N/A | El controlador consume al servicio para delegar y ejecutar los flujos de préstamos y devoluciones con validaciones. |
| UsuarioController -> UsuarioService | Dependencia (`- - >`) | N/A | El controlador delega las acciones de control, persistencia y listado de alumnos a su capa lógica. |
| HerramientaController -> HerramientaService | Dependencia (`- - >`) | N/A | Envía las solicitudes de administración física del inventario hacia el servicio de herramientas. |
| Usuario <---> Asignaciones | Asociación | `1` a `0..*` | Una asignación contiene el ID de un único (**1**) estudiante, mientras que un usuario puede acumular cero o muchas (**0..***) asignaciones. |
| Herramienta <---> Asignaciones | Asociación | `1` a `0..*` | Un registro de asignación hace referencia a una única (**1**) herramienta, pero una herramienta puede tener cero o muchos (**0..***) préstamos. |
| Usuario <---> UsuarioHerramienta | Asociación | `1` a `0..*` | Un registro en la tabla intermedia pertenece a un (**1**) usuario, pero un usuario puede repetirse en cero o muchos (**0..***) enlaces de la tabla puente. |
| Herramienta <---> UsuarioHerramienta | Asociación | `1` a `0..*` | Un registro de la tabla puente apunta a una (**1**) herramienta, pero una herramienta puede registrarse en cero o muchos (**0..***) enlaces. |

**Enumeraciones:**

```java
public enum EstadoHerramienta {
DISPONIBLE,
ASIGNADA,
DAÑADA
}
public enum CondicionDevolucion {
BUENA,
DAÑADA
}
```

## 9.6 Diagrama de clases UML

Archivo: `docs/etapa_1/diagramas/UML_Examen.PDF

![UML](diagramas/UML_Examen.PDF)

## 9.7 Modelo relacional

https://lucid.app/lucidchart/7f89ef08-49b1-4ffd-804f-d6463a6f83a9/edit?invitationId=inv_583cd9c7-9380-48ed-b2a6-cd49cd6a0805
Archivo: `docs/etapa_1/diagramas/Modelo_Relacional.pdf`)

![Modelo relacional](diagramas/Modelo_Relacional.pdf)
https://lucid.app/lucidchart/4d1f1a12-0e94-41ad-a888-2a1ceab7cd53/edit?invitationId=inv_1e527c03-383e-45d1-b863-153220f13eeb
Script preliminar: (../../database/gamabasis_ex1p3g4.sql)

## 9.8 Arquitectura propuesta

| Capa | Clases | Responsabilidad |
|---|---|---|
| `model` | `Usuario`, `Herramienta`, `Asignaciones`, `UsuarioHerramienta`, `EstadoHerramienta`, `CondicionDevolucion` | Define los objetos de datos y entidades de persistencia que representan las tablas físicas de la base de datos, incluyendo sus variables y métodos básicos encapsulados (getters/setters). |
| `repository` | `UsuarioRepository`, `HerramientaRepository`, `AsignacionRepository` | Interfaces encargadas del acceso directo a la base de datos. Declaran las operaciones CRUD básicas y los métodos de consulta especializados con cláusulas JOIN. |
| `service` | `UsuarioService`, `HerramientaService`, `AsignacionService` | Contiene el núcleo lógico y las reglas de negocio del sistema (verificaciones de estados, límites de préstamos y orquestación de transacciones de asignación/devolución). |
| `controller` | `UsuarioController`, `HerramientaController`, `AsignacionController` | Capa más externa que expone los endpoints de la API (REST). Recibe las peticiones HTTP del usuario, valida sintácticamente los datos de entrada y delega el flujo a los servicios. |
| `exception` | `ReglaNegocioException`, `ValidacionException` | Alberga las excepciones personalizadas encargadas de capturar, interrumpir y reportar de forma clara los errores controlados cuando se viola alguna regla de negocio. |

**Interfaces Repository/DAO definidas:**

```java

AsignacionRepository`
HerramientaRepository`
UsuarioRepository`

```

## Código de la Etapa 1

- [X] Clases del modelo (atributos privados, constructores, getters/setters, `toString()`).
- [X] Enumeraciones.
- [ 1 / 2] Interfaces `Repository`/`DAO`.
- [X] Estructura de `Service` y `Controller`.
- [X] Excepciones personalizadas.
- [ ] El proyecto compila.

## Distribución del trabajo

| Integrante | Aportes principales |
|---|---|
| Octavio Morales |Clases Modelo, Diagrama UML, Controller  |
| Adrian Arce |Interface repository , Modelo Relacional, DAO |
| Tadanory Estrada|Estructura service, Excepciones  |