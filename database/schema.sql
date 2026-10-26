-- =====================================================================
-- Examen Práctico Integrador — Programación III
-- Caso 10: Control de herramientas de laboratorio
--
-- Crea la base de datos desde cero, con tablas, restricciones y datos
-- de prueba.
--
-- Ejecutar:  mysql -u root -p < database/schema.sql
-- El nombre de la base de datos debe coincidir con db.url en db.properties.
-- Requiere MySQL 8.0.16 o superior (para que se apliquen los CHECK).
-- =====================================================================

DROP DATABASE IF EXISTS examen_progra3;
CREATE DATABASE examen_progra3
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

USE examen_progra3;

-- ---------------------------------------------------------------------
-- Tablas
-- ---------------------------------------------------------------------

-- Estudiantes del laboratorio
CREATE TABLE Usuario (
    Carnet   INT          NOT NULL,
    Nombre   VARCHAR(30)  NOT NULL,
    Carrera  VARCHAR(20)  NOT NULL,
    Estado   VARCHAR(20)  NOT NULL DEFAULT 'HABILITADO',
    CONSTRAINT pk_usuario PRIMARY KEY (Carnet),
    CONSTRAINT chk_usuario_carnet CHECK (Carnet > 0),
    CONSTRAINT chk_usuario_estado CHECK (Estado IN ('HABILITADO', 'DESHABILITADO'))
);

-- Inventario de herramientas
CREATE TABLE Herramienta (
    Codigo               INT          NOT NULL,
    Nombre               VARCHAR(30)  NOT NULL,
    Tipo                 VARCHAR(20)  NOT NULL,
    Estado               VARCHAR(20)  NOT NULL DEFAULT 'DISPONIBLE',
    DuracionMaximaHoras  INT          NOT NULL DEFAULT 0,
    CONSTRAINT pk_herramienta PRIMARY KEY (Codigo),
    CONSTRAINT chk_herramienta_codigo CHECK (Codigo > 0),
    CONSTRAINT chk_herramienta_estado
        CHECK (Estado IN ('DISPONIBLE', 'ASIGNADA', 'DANADA', 'DAÑADA', 'EN_MANTENIMIENTO')),
    CONSTRAINT chk_herramienta_duracion CHECK (DuracionMaximaHoras >= 0)
);

-- Asignaciones (préstamos) de herramientas a estudiantes.
-- Una asignación abierta tiene CondicionDevolucion = 'Pendiente' y
-- Horadevolucion = hora límite (solicitud + DuracionMaximaHoras).
CREATE TABLE Asignaciones (
    Id                   INT          NOT NULL AUTO_INCREMENT,
    Usuarioid            INT          NOT NULL,
    Herramientaid        INT          NOT NULL,
    Horasolicitud        DATETIME     NOT NULL,
    Horadevolucion       DATETIME     NOT NULL,
    CondicionDevolucion  VARCHAR(20)  NOT NULL DEFAULT 'Pendiente',
    CONSTRAINT pk_asignaciones PRIMARY KEY (Id),
    CONSTRAINT fk_asignaciones_usuario FOREIGN KEY (Usuarioid)
        REFERENCES Usuario (Carnet)
        ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_asignaciones_herramienta FOREIGN KEY (Herramientaid)
        REFERENCES Herramienta (Codigo)
        ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT chk_asignaciones_condicion
        CHECK (CondicionDevolucion IN ('Pendiente', 'Bueno', 'Buena', 'Dañado', 'Dañada', 'Danado', 'Danada')),
    CONSTRAINT chk_asignaciones_fechas CHECK (Horadevolucion >= Horasolicitud)
);

-- Acelera la búsqueda de asignaciones abiertas por herramienta y por usuario
CREATE INDEX idx_asignaciones_herramienta ON Asignaciones (Herramientaid, CondicionDevolucion);
CREATE INDEX idx_asignaciones_usuario     ON Asignaciones (Usuarioid, CondicionDevolucion);

-- ---------------------------------------------------------------------
-- Datos de prueba
-- ---------------------------------------------------------------------

INSERT INTO Usuario (Carnet, Nombre, Carrera, Estado) VALUES
    (123,       'Elkin Octavio Morales', 'Ing Sistemas', 'DESHABILITADO'),
    (402220333, 'Ana Mora',              'Electronica',  'HABILITADO'),
    (402220444, 'Luis Soto',             'Electronica',  'HABILITADO'),
    (402220666, 'Carla Jimenez',         'Ing Sistemas', 'HABILITADO');

INSERT INTO Herramienta (Codigo, Nombre, Tipo, Estado, DuracionMaximaHoras) VALUES
    (12,  'Multimetro',        'Electrica',    'EN_MANTENIMIENTO', 4),
    (101, 'Multimetro Fluke',  'Medicion',     'DISPONIBLE',       4),
    (102, 'Osciloscopio',      'Medicion',     'DISPONIBLE',       2),
    (103, 'Cautin',            'Soldadura',    'DISPONIBLE',       3),
    (104, 'Kit Arduino',       'Kit',          'DISPONIBLE',       6),
    (105, 'Fuente de poder',   'Alimentacion', 'DISPONIBLE',       4),
    (106, 'Generador senales', 'Medicion',     'ASIGNADA',         2);

-- Historial: una asignación ya devuelta (105) y una abierta (106)
INSERT INTO Asignaciones (Usuarioid, Herramientaid, Horasolicitud, Horadevolucion, CondicionDevolucion) VALUES
    (402220444, 105, '2026-10-20 08:00:00', '2026-10-20 11:30:00', 'Bueno'),
    (402220666, 106, '2026-10-25 09:00:00', '2026-10-25 11:00:00', 'Pendiente');
