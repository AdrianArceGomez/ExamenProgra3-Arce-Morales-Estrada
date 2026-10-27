-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 10.123.0.165:3306
-- Tiempo de generación: 26-10-2026 a las 01:20:11
-- Versión del servidor: 8.4.7
-- Versión de PHP: 8.2.35

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gamabasis_ex1p3g4`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Asignaciones`
--

CREATE TABLE `Asignaciones` (
  `Id` int NOT NULL,
  `Usuarioid` int NOT NULL,
  `Herramientaid` int NOT NULL,
  `Horasolicitud` datetime NOT NULL,
  `Horadevolucion` datetime NOT NULL,
  `CondicionDevolucion` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Herramienta`
--

CREATE TABLE `Herramienta` (
  `Codigo` int NOT NULL,
  `Nombre` varchar(30) NOT NULL,
  `Tipo` varchar(20) NOT NULL,
  `Estado` varchar(20) NOT NULL,
  `DuracionMaximaHoras` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Herramienta`
--

INSERT INTO `Herramienta` (`Codigo`, `Nombre`, `Tipo`, `Estado`, `DuracionMaximaHoras`) VALUES
(12, 'Multimetro', 'Electrica', 'EN_MANTENIMIENTO', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Usuario`
--

CREATE TABLE `Usuario` (
  `Carnet` int NOT NULL,
  `Nombre` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Carrera` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Estado` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `Usuario`
--

INSERT INTO `Usuario` (`Carnet`, `Nombre`, `Carrera`, `Estado`) VALUES
(123, 'Elkin Octavio Morales', 'Ing Sistemas', 'DESHABILITADO');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `Asignaciones`
--
ALTER TABLE `Asignaciones`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `Usuarioid` (`Usuarioid`),
  ADD KEY `Herramientaid` (`Herramientaid`);

--
-- Indices de la tabla `Herramienta`
--
ALTER TABLE `Herramienta`
  ADD PRIMARY KEY (`Codigo`);

--
-- Indices de la tabla `Usuario`
--
ALTER TABLE `Usuario`
  ADD PRIMARY KEY (`Carnet`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `Asignaciones`
--
ALTER TABLE `Asignaciones`
  MODIFY `Id` int NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `Asignaciones`
--
ALTER TABLE `Asignaciones`
  ADD CONSTRAINT `Carnetfk_1` FOREIGN KEY (`Usuarioid`) REFERENCES `Usuario` (`Carnet`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `Codigofk_2` FOREIGN KEY (`Herramientaid`) REFERENCES `Herramienta` (`Codigo`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
