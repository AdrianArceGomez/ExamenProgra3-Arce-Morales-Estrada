# Etapa 3 — Integración, pruebas y mejora

> Complete todas las secciones. Elimine las indicaciones entre `< >` al terminar.

## Integración

El sistema se integró en una arquitectura multicapa, la interfaz esta basada en consola y centraliza el flujo de control delegando en los respectivos controladores de forma desacoplada y resolviendo las reglas transaccionales complejas en la capa de servicios. El total de las operaciones CRUD y los flujos lógicos de negocio se ejecutan y persisten en tiempo real sobre el servidor remoto de MySQL en la nube.

| Opción del menú | Funcionalidad | Estado |
|---|---|---|
|1. Probar conexión | Diagnóstico de infraestructura y enlace de red con MySQL en la nube | ✅ |
|2. Módulo Estudiantes | Registro, consulta individual, listado completo y edición de estados | ✅ |
|3. Módulo Herramientas | Registro de inventario, consulta de fichas, listado general con horas | ✅ |
|4. Asignar Herramienta | Préstamo transaccional evaluando estados, límites de cantidad y horas vencidas | ✅ |
|5. Devolver Herramienta | Cierre de préstamos activos y aplicación automática de bloqueos por daño | ✅ |
|6. Asignaciones Vencidas | Reporte en tiempo real de préstamos abiertos que superaron el tiempo límite | ✅ |

## Cambio de requerimiento

Se solicitó agregar una duración máxima de asignación por horas a cada herramienta utilizando el atributo duracionMaximaHoras con valor por defecto de 4. Se requirió implementar las reglas de negocio RN-N1 una asignación está vencida si el tiempo transcurrido supera dicho límite y RN-N2 un estudiante con al menos un préstamo vencido queda bloqueado para solicitar nuevas herramientas, además de proveer una consulta especial para listar dichos retrasos con sus respectivas horas de exceso.



**Clases modificadas o agregadas:**

| Clase | Capa | Cambio realizado |
|---|---|---|
|AsignacionVencidaException.java|Excepciones |Clase nueva creada para modelar el rechazo de la regla de negocio RN-N2. |
|Herramienta.java |Modelo |Se agregó el atributo duracionMaximaHoras, su constructor actualizado y métodos accesores. |
|HerramientaDao.java |Repositorio DAO |Se actualizó la sentencia de inserción ON DUPLICATE KEY y el mapeador del ResultSet. |
|AsignacionRepository.java |Repositorio Interface |Se añadieron las firmas abstractas para las consultas hasOverdueAsignacion y findActiveAndOverdueAsignaciones |
|AsignacionDao.java|Repositorio DAO |Implementación JDBC con consultas utilizando TIMESTAMPDIFF y NOW() nativos de MySQL. |
|AsignacionService.java |Servicio |Se inyectó la validación preventiva de la regla RN-N2 al inicio del método registrarPrestamo. |
|AsignacionController.java |Repositorio DAO |Se añadió el catch específico de la nueva excepción y el método para listar el reporte en pantalla. |
|App.java |Presentación |Se extendió el menú principal a la opción 6 y se ajustó el rango del switch numérico. |



**Análisis:** 
¿qué partes del diseño facilitaron el cambio?
La programación orientada a interfaces en la capa de datos y el desacoplamiento, facilitaron la adición de los nuevos métodos. Al estar la lógica transaccional centralizada en el Service, incorporar el nuevo if de bloqueo preventivo tomó pocas líneas de código sin afectar las validaciones previas de disponibilidad o límites por cantidad.

¿qué fue necesario rehacer?
No se requirió reescribir ningún módulo existente. Únicamente fue necesario alterar el archivo schema.sql para añadir la columna física a la tabla de base de datos y actualizar el constructor de la entidad Herramienta para soportar el mapeo del nuevo atributo.

## Decisiones de diseño relevantes

• Cálculo de Tiempos Delegado en el RDBMS: Se decidió utilizar funciones nativas TIMESTAMPDIFF y NOW() directamente en las sentencias SQL de MySQL. Justificación: Evita que Java cargue datos masivos innecesarios a la memoria RAM y mitiga problemas de discrepancia horaria o sincronización manual entre la máquina del cliente y el servidor en la nube.
• Encapsulamiento en Cascada por Constructores: Se inyectaron las dependencias inferiores en los constructores vacíos de los controladores y servicios. Justificación: Mantiene a App.java 100% puro e independiente, impidiendo la fuga de importaciones de persistencia o DAOs en la capa de presentación.
• Control Temprano de la Regla de Negocio Fail-Fast: Se colocó la validación hasOverdueAsignacion en la primera línea ejecutable de registrarPrestamo. Justificación: Si el estudiante arrastra una infracción por tiempo de uso, la transacción aborta inmediatamente arrojando la excepción, ahorrando consultas y accesos redundantes a la base de datos para verificar la herramienta.

## Pruebas y mejoras

- Pruebas: [`pruebas.md`](pruebas.md)
- Mejoras y defectos corregidos: [`mejoras.md`](mejoras.md)

## UML final

si se le hagregaron las implementaciones del anuncio y esta en esta carpeta
(`docs/etapa_1/diagramas/uml_final.png`).>

## Aspectos pendientes



## Distribución del trabajo

| Integrante | Aportes principales |
|---|---|
|Octavio Morales |Construcción del flujo del menú interactivo en App.java, validación utilitaria de tipos en Consola.java |
|Adrian Arce|Modificación del archivo schema.sql, diseño actualizado del diagrama UML final, creación de las sentencias JDBC de inserción y lectura con el nuevo campo DuracionMaximaHoras en los DAOs. |
|Tadanory Estrada |Implementación de la validación de la regla RN-N2 en la capa Service |
