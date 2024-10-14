SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `personabdd`
--
CREATE DATABASE IF NOT EXISTS `personabdd` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `personabdd`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ocupaciones`
--

DROP TABLE IF EXISTS `ocupaciones`;
CREATE TABLE IF NOT EXISTS `ocupaciones` (
  `id_ocupacion` int(11) NOT NULL AUTO_INCREMENT,
  `ocupacion` varchar(50) NOT NULL,
  PRIMARY KEY (`id_ocupacion`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `ocupaciones`
--

INSERT INTO `ocupaciones` (`id_ocupacion`, `ocupacion`) VALUES
(1, 'Doctor'),
(2, 'Emprendedor'),
(3, 'Profesor'),
(4, 'Informático'),
(5, 'Abogado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

DROP TABLE IF EXISTS `persona`;
CREATE TABLE IF NOT EXISTS `persona` (
  `id_persona` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_persona` varchar(100) NOT NULL,
  `edad_persona` int(11) DEFAULT NULL,
  `sexo_persona` varchar(50) NOT NULL,
  `id_ocupacion` int(11) NOT NULL,
  `fecha_nac` date NOT NULL,
  PRIMARY KEY (`id_persona`),
  KEY `id_ocupacion` (`id_ocupacion`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `persona`
--

INSERT INTO `persona` (`id_persona`, `nombre_persona`, `edad_persona`, `sexo_persona`, `id_ocupacion`, `fecha_nac`) VALUES
(1, 'Alejandro Pineda', 25, 'Masculino', 1, '1999-01-05'),
(2, 'Fernando Calderón', 23, 'Masculino', 2, '2001-05-07'),
(3, 'Emerson Torres', 23, 'Masculino', 3, '1999-08-03'),
(4, 'Wilber Chacón', 22, 'Masculino', 4, '2002-03-21'),
(5, 'Daniela Hernández', 26, 'Femenino', 5, '1998-05-24'),
(6, 'Sky Vizor', 20, 'Masculino', 3, '2004-03-17'),
(7, 'Jacynth Antcliff', 32, 'Femenino', 4, '1992-11-09'),
(8, 'Giraud Pughsley', 35, 'Masculino', 3, '1989-06-04'),
(9, 'Miguel Tather', 40, 'Masculino', 5, '1984-05-08'),
(10, 'Nerita Aireton', 23, 'Femenino', 2, '2001-06-01'),
(11, 'Adda Giacoppoli', 25, 'Femenino', 1, '1999-05-19'),
(12, 'Thoma Antonelli', 31, 'Masculino', 5, '1993-07-12'),
(13, 'Cobb Hurrell', 41, 'Masculino', 5, '1981-03-29'),
(14, 'Granger Mewburn', 34, 'Masculino', 2, '1990-12-13'),
(15, 'Valentine Hazelhurst', 39, 'Femenino', 5, '1985-12-25');


--
-- Filtros para la tabla `persona`
--
ALTER TABLE `persona`
  ADD CONSTRAINT `persona_ibfk_1` FOREIGN KEY (`id_ocupacion`) REFERENCES `ocupaciones` (`id_ocupacion`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
