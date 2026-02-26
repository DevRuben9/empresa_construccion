-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 25-02-2026 a las 23:57:59
-- Versión del servidor: 8.0.31
-- Versión de PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `empresa_construccion`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `operador`
--

DROP TABLE IF EXISTS `operador`;
CREATE TABLE IF NOT EXISTS `operador` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `apellido` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `categoria_licencia` char(1) COLLATE utf8mb4_general_ci NOT NULL,
  `fecha_vencimiento` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `operador`
--

INSERT INTO `operador` (`id`, `nombre`, `apellido`, `categoria_licencia`, `fecha_vencimiento`) VALUES
(1, 'Marcos', 'Solis', 'A', '2026-05-12'),
(2, 'Elena', 'Ramos', 'B', '2027-08-20'),
(3, 'Jorge', 'Luna', 'C', '2025-12-01'),
(4, 'Lucía', 'Pérez', 'A', '2026-02-15'),
(5, 'Roberto', 'Gómez', 'B', '2028-03-10');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `operador_detalle`
--

DROP TABLE IF EXISTS `operador_detalle`;
CREATE TABLE IF NOT EXISTS `operador_detalle` (
  `id` int NOT NULL,
  `telefono` int NOT NULL,
  `direccion` varchar(40) COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(40) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `operador_detalle`
--

INSERT INTO `operador_detalle` (`id`, `telefono`, `direccion`, `email`) VALUES
(1, 71234567, 'Av. Siempre Viva 123', 'marcos@obra.com'),
(2, 72234568, 'Calle Los Pinos 45', 'elena@obra.com'),
(3, 73234569, 'Barrio Central s/n', 'jorge@obra.com'),
(4, 74234570, 'Urb. El Recreo 789', 'lucia@obra.com'),
(5, 75234571, 'Av. Blanco Galindo 5', 'roberto@obra.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `revision`
--

DROP TABLE IF EXISTS `revision`;
CREATE TABLE IF NOT EXISTS `revision` (
  `id` int NOT NULL AUTO_INCREMENT,
  `encargado` int NOT NULL,
  `vehiculo` varchar(8) COLLATE utf8mb4_general_ci NOT NULL,
  `chofer` int NOT NULL,
  `fecha_revision` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_revision_tecnico` (`encargado`),
  KEY `fk_revision_vehiculo` (`vehiculo`),
  KEY `fk_revision_operador` (`chofer`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `revision`
--

INSERT INTO `revision` (`id`, `encargado`, `vehiculo`, `chofer`, `fecha_revision`) VALUES
(1, 1, 'ABC-123', 1, '2024-02-10'),
(2, 2, 'XYZ-987', 2, '2024-02-11'),
(3, 3, 'JKL-456', 3, '2024-02-12'),
(4, 4, 'MNO-321', 4, '2024-02-13'),
(5, 5, 'PRQ-555', 5, '2024-02-14');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tecnico`
--

DROP TABLE IF EXISTS `tecnico`;
CREATE TABLE IF NOT EXISTS `tecnico` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `apellido` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `contacto` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tecnico`
--

INSERT INTO `tecnico` (`id`, `nombre`, `apellido`, `contacto`) VALUES
(1, 'Ricardo', 'Méndez', 60123456),
(2, 'Sofía', 'Castro', 60123457),
(3, 'Andrés', 'Silva', 60123458),
(4, 'Paola', 'Daza', 60123459),
(5, 'Hugo', 'Torres', 60123460);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculo`
--

DROP TABLE IF EXISTS `vehiculo`;
CREATE TABLE IF NOT EXISTS `vehiculo` (
  `placa` varchar(8) COLLATE utf8mb4_general_ci NOT NULL,
  `odometro` decimal(10,2) NOT NULL,
  `modelo` varchar(15) COLLATE utf8mb4_general_ci NOT NULL,
  `tipo` varchar(25) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`placa`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `vehiculo`
--

INSERT INTO `vehiculo` (`placa`, `odometro`, `modelo`, `tipo`) VALUES
('ABC-123', '1500.50', 'CAT 320', 'Excavadora'),
('XYZ-987', '5200.00', 'John Deere', 'Tractor'),
('JKL-456', '800.75', 'Volvo FMX', 'Volqueta'),
('MNO-321', '12500.20', 'Komatsu', 'Motoniveladora'),
('PRQ-555', '3400.00', 'Caterpillar', 'Retroexcavadora');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
