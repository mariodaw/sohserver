SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE `usuario` (
  `id` bigint NOT NULL,
  `username` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(512) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `correo` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `nombre` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `fnac` datetime NOT NULL,
  `cuenta` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `campeon` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `skin` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `id_tipoUsuario` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `tipousuario` (
  `id` bigint NOT NULL,
  `nombre` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `pertenece` (
  `id` bigint NOT NULL,
  `id_equipo` bigint NOT NULL,
  `id_usuario` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `equipo` (
  `id` bigint NOT NULL,
  `nombre` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `participa` (
  `id` bigint NOT NULL,
  `id_equipo` bigint NOT NULL,
  `id_evento` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `evento` (
  `id` bigint NOT NULL,
  `nombre` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `fini` datetime NOT NULL,
  `ffin` datetime NOT NULL,
  `id_tipoEvento` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `tipoevento` (
  `id` bigint NOT NULL,
  `nombre` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `premio` (
  `id` bigint NOT NULL,
  `premio` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
  `porcentaje` int NOT NULL,
  `id_evento` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


INSERT INTO `evento` (`id`, `nombre`, `fini`, `ffin`, `id_tipoEvento`) VALUES ('1', 'Jornada 8', '2022-12-25 05:05:05', '2022-12-25 05:06:05', '1')

INSERT INTO `tipoevento` (`id`, `nombre`) VALUES ('1', 'Jornada Torneo');

-- indice de la tabla usuario
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`);

-- Indices de la tabla `tipoUsuario`
ALTER TABLE `tipousuario`
  ADD PRIMARY KEY (`id`);

-- Indices de la tabla `pertenece`
ALTER TABLE `pertenece`
  ADD PRIMARY KEY (`id`);

  -- Indices de la tabla `participa`
ALTER TABLE `participa`
  ADD PRIMARY KEY (`id`);

-- Indices de la tabla `equipo`
ALTER TABLE `equipo`
  ADD PRIMARY KEY (`id`);

  -- Indices de la tabla `evento`
ALTER TABLE `evento`
  ADD PRIMARY KEY (`id`);

  -- Indices de la tabla `tipoEvento`
ALTER TABLE `evento`
  ADD PRIMARY KEY (`id`);

  -- indice de la tabla premio
ALTER TABLE `premio`
  ADD PRIMARY KEY (`id`);

    -- AUTO_INCREMENT de la tabla `premio`
ALTER TABLE `premio`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

  -- AUTO_INCREMENT de la tabla `tipoEvento`
ALTER TABLE `tipoEvento`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

  -- AUTO_INCREMENT de la tabla `evento`
ALTER TABLE `evento`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

-- AUTO_INCREMENT de la tabla `participa`
ALTER TABLE `participa`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

-- AUTO_INCREMENT de la tabla `pertenece`
ALTER TABLE `pertenece`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

-- AUTO_INCREMENT de la tabla `equipo`
ALTER TABLE `equipo`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

-- AUTO_INCREMENT de la tabla `tipousuario`
ALTER TABLE `tipoUsuario`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

-- AUTO_INCREMENT de la tabla `usuario`
ALTER TABLE `usuario`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;
COMMIT;