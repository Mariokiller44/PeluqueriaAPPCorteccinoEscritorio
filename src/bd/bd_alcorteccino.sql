-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 08-05-2023 a las 22:05:18
-- Versión del servidor: 10.4.27-MariaDB
-- Versión de PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bd_alcorteccino`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cita`
--

CREATE TABLE `cita` (
  `ID` int(11) NOT NULL,
  `ID_CLIENTE` int(11) NOT NULL,
  `ID_HORARIO` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cita`
--

INSERT INTO `cita` (`ID`, `ID_CLIENTE`, `ID_HORARIO`) VALUES
(1, 3, 1),
(2, 11, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `ID` int(11) NOT NULL,
  `DESCRIPCION` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`ID`, `DESCRIPCION`) VALUES
(2, 'Soy un seleccionador calvo'),
(3, 'Soy papadopoulos'),
(4, 'Soy un fontanero mayor'),
(2, NULL),
(3, NULL),
(4, NULL),
(9, NULL),
(11, NULL),
(13, NULL),
(15, NULL),
(16, NULL),
(16, 'Soy un piloto que conseguirá la 33');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `horario`
--

CREATE TABLE `horario` (
  `ID` int(11) NOT NULL,
  `FECHA` date DEFAULT NULL,
  `HORA` time DEFAULT NULL,
  `fecha_es` varchar(50) GENERATED ALWAYS AS (date_format(`FECHA`,'%d-%m-%Y')) VIRTUAL,
  `hora_es` varchar(50) GENERATED ALWAYS AS (date_format(`HORA`,'%H:%i:%s')) VIRTUAL,
  `ID_PERSONAL` int(11) NOT NULL,
  `ID_SERVICIO` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `horario`
--

INSERT INTO `horario` (`ID`, `FECHA`, `HORA`, `ID_PERSONAL`, `ID_SERVICIO`) VALUES
(1, '2023-05-10', '05:50:59', 7, 4),
(2, '2023-05-03', '20:14:59', 8, 3),
(3, '2023-05-24', '17:53:28', 12, 3),
(4, '2023-05-16', '12:53:28', 5, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personal`
--

CREATE TABLE `personal` (
  `ID` int(11) NOT NULL,
  `TIPO` varchar(20) NOT NULL,
  `SALARIO` float(7,2) DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `personal`
--

INSERT INTO `personal` (`ID`, `TIPO`, `SALARIO`) VALUES
(1, 'admin', 1700.80),
(5, 'Peluquero', 0.00),
(1, '', 0.00),
(5, '', 0.00),
(6, '', 0.00),
(7, '', 0.00),
(8, '', 0.00),
(10, '', 0.00),
(12, '', 0.00),
(14, '', 0.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `ID` int(11) NOT NULL,
  `NOMBRE` varchar(200) DEFAULT NULL,
  `STOCK` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`ID`, `NOMBRE`, `STOCK`) VALUES
(1, 'Peine', 20),
(2, 'Máquinadecortarelpelo', 4),
(3, 'Champú', 50),
(4, 'Gelacondicionador', 20),
(5, 'Secadora', 9),
(6, 'Tinterojo', 40),
(7, 'Tintenegro', 40),
(8, 'Tintemarron', 35),
(9, 'Papelaluminio', 10),
(10, 'Cera', 25),
(11, 'Laca', 20),
(12, 'Lima', 12);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `servicios`
--

CREATE TABLE `servicios` (
  `ID` int(11) NOT NULL,
  `DESCRIPCION` varchar(100) DEFAULT NULL,
  `PRODUCTO_ID` int(11) NOT NULL,
  `PRECIO` float(4,2) DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `servicios`
--

INSERT INTO `servicios` (`ID`, `DESCRIPCION`, `PRODUCTO_ID`, `PRECIO`) VALUES
(1, 'Secarelpelo', 5, 2.00),
(2, 'Peinar', 1, 15.00),
(3, 'Lavar', 3, 4.00),
(4, 'Limaruñas', 12, 21.00),
(5, 'Teñirdemarron', 8, 60.00),
(6, 'Teñirderojo', 6, 54.50);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `ID` int(11) NOT NULL,
  `NOMBRE` varchar(200) DEFAULT NULL,
  `APELLIDOS` varchar(300) DEFAULT NULL,
  `EMAIL` varchar(100) NOT NULL,
  `TELEFONO` int(9) DEFAULT NULL,
  `CUENTA` varchar(30) NOT NULL,
  `CONTRASENIA` varchar(30) NOT NULL,
  `TIPO_DE_USUARIO` varchar(8) NOT NULL DEFAULT 'Cliente'
) ;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`ID`, `NOMBRE`, `APELLIDOS`, `EMAIL`, `TELEFONO`, `CUENTA`, `CONTRASENIA`, `TIPO_DE_USUARIO`) VALUES
(1, 'Administrador', 'Mario', 'admin@peluac.com', 612231671, 'SuperUser', '1234sudosu', 'Personal'),
(2, 'Luis', 'De La Fuente', 'luis@delafu.es', 345678323, 'LuisSelEsp', '2026USA', 'Cliente'),
(3, 'Paco Antonio', 'Gimenez Fernandez', 'pacogimenez@gmail.com', 622442133, 'pjfernandez002', 'Sumamaconsupapa', 'Cliente'),
(4, 'Mario', 'Mario', 'mario@nintendo.jp', 192955627, 'MarioBrosRojo', 'Nintendo', 'Cliente'),
(5, 'Raluka', 'Rusa', 'ralukalarusa@gmail.com', 682243215, 'RalukitaPh', 'FerminHDP', 'Personal'),
(6, 'Carlos', 'Rodríguez Pérez', 'carlos.rodriguezperez@gmail.com', 666555444, 'carlosrp', 'Y4bF6kPdS9', 'Personal'),
(7, 'Ana', 'González Hernández', 'ana.gonzalezh@gmail.com', 551234567, 'agonzalezh', 'gZ8aX9cH2r', 'Personal'),
(8, 'Miguel', 'García López', 'miguel.garcialopez@hotmail.com', 212555212, 'miguelgl', 'D3eR5fT7gY', 'Personal'),
(9, 'Laura', 'Fernández Martínez', 'laura.fernandezm@gmail.com', 412345678, 'lfernandezm', 'Q1wE2rT3yU', 'Cliente'),
(10, 'José', 'Sánchez García', 'jose.sanchezg@hotmail.com', 55551212, 'josesg', 'F7gH8jK9lM', 'Personal'),
(11, 'Marta', 'Jiménez Pérez', 'marta.jimenezp@gmail.com', 666444333, 'martajp', 'Z2xC4vB6nM', 'Cliente'),
(12, 'Juan', 'Rodríguez Sánchez', 'juan.rodriguezs@gmail.com', 598765432, 'jrodriguezs', 'V5bN7mK9lP', 'Personal'),
(13, 'Sofía', 'Torres Hernández', 'sofia.torresh@gmail.com', 125551234, 'sofiath', 'X1cV2bN3mF', 'Cliente'),
(14, 'Daniel', 'Pérez Sánchez', 'daniel.perezs@hotmail.com', 87654321, 'danielps', 'R4tG5yH6uJ', 'Personal'),
(15, 'Andrea', 'García Martínez', 'andrea.garciam@gmail.com', 505551234, 'andreagm', 'L2kM4jH6gT', 'Cliente'),
(16, 'Fernando', 'Alonso Diaz', 'fernando@alo-.es', 622330332, 'MagicAlonso', 'AstonMartin', 'Cliente');

--
-- Disparadores `usuario`
--
DELIMITER $$
CREATE TRIGGER `insertar_usuario` AFTER INSERT ON `usuario` FOR EACH ROW BEGIN
  IF NEW.tipo_de_usuario = 'Cliente' THEN
    INSERT INTO cliente (id) VALUES (NEW.id);
  ELSEIF NEW.tipo_de_usuario = 'Personal' THEN
    INSERT INTO personal (id) VALUES (NEW.id);
  END IF;
END
$$
DELIMITER ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cita`
--
ALTER TABLE `cita`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_CLIENTE` (`ID_CLIENTE`),
  ADD KEY `ID_HORARIO` (`ID_HORARIO`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD KEY `ID` (`ID`);

--
-- Indices de la tabla `horario`
--
ALTER TABLE `horario`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_SERVICIO` (`ID_SERVICIO`),
  ADD KEY `ID_PERSONAL` (`ID_PERSONAL`);

--
-- Indices de la tabla `personal`
--
ALTER TABLE `personal`
  ADD KEY `ID` (`ID`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `servicios`
--
ALTER TABLE `servicios`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `PRODUCTO_ID` (`PRODUCTO_ID`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cita`
--
ALTER TABLE `cita`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `horario`
--
ALTER TABLE `horario`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `servicios`
--
ALTER TABLE `servicios`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cita`
--
ALTER TABLE `cita`
  ADD CONSTRAINT `cita_ibfk_1` FOREIGN KEY (`ID_CLIENTE`) REFERENCES `cliente` (`ID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `cita_ibfk_2` FOREIGN KEY (`ID_HORARIO`) REFERENCES `horario` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `usuario` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `horario`
--
ALTER TABLE `horario`
  ADD CONSTRAINT `horario_ibfk_1` FOREIGN KEY (`ID_SERVICIO`) REFERENCES `servicios` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `horario_ibfk_2` FOREIGN KEY (`ID_PERSONAL`) REFERENCES `personal` (`ID`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `personal`
--
ALTER TABLE `personal`
  ADD CONSTRAINT `personal_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `usuario` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `servicios`
--
ALTER TABLE `servicios`
  ADD CONSTRAINT `servicios_ibfk_1` FOREIGN KEY (`PRODUCTO_ID`) REFERENCES `productos` (`ID`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
