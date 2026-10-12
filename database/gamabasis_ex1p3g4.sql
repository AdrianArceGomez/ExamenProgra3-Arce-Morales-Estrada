-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 10.123.0.165:3306
-- Tiempo de generación: 12-10-2026 a las 02:13:09
-- Versión del servidor: 8.4.7
-- Versión de PHP: 8.2.34

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
  `Horadevolucion` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Herramienta`
--

CREATE TABLE `Herramienta` (
  `Codigo` int NOT NULL,
  `Nombre` varchar(30) NOT NULL,
  `Tipo` varchar(20) NOT NULL,
  `Estado` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Usuario`
--

CREATE TABLE `Usuario` (
  `Carnet` int NOT NULL,
  `Nombre` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Carrera` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Estado` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `UsuarioHerramienta`
--

CREATE TABLE `UsuarioHerramienta` (
  `Usuarioid` int NOT NULL,
  `Herramientaid` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
-- Indices de la tabla `UsuarioHerramienta`
--
ALTER TABLE `UsuarioHerramienta`
  ADD KEY `Carnet` (`Usuarioid`),
  ADD KEY `Codigo` (`Herramientaid`);

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

--
-- Filtros para la tabla `UsuarioHerramienta`
--
ALTER TABLE `UsuarioHerramienta`
  ADD CONSTRAINT `UsuarioHerramienta_ibfk_1` FOREIGN KEY (`Usuarioid`) REFERENCES `Usuario` (`Carnet`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `UsuarioHerramienta_ibfk_2` FOREIGN KEY (`Herramientaid`) REFERENCES `Herramienta` (`Codigo`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
