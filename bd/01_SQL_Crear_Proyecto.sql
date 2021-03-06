﻿-- Table: public.proyecto

-- DROP TABLE public.proyecto;

CREATE TABLE public.proyecto
(
  proyecto_id bigint NOT NULL,
  proyecto_editable boolean NOT NULL,
  proyecto_estado integer NOT NULL,
  proyecto_nombre character varying(255) NOT NULL,
  proyecto_objeto character varying(255) NOT NULL,
  proyecto_usuariocreacion character varying(255) NOT NULL,
  proyecto_usuarioultimocambio character varying(255) NOT NULL,
  proyecto_version integer,
  proyecto_personaresponsable character varying(255) NOT NULL,
  proyecto_porcentaje character varying(255) NOT NULL,
  proyecto_fechacreacion timestamp with time zone,
  proyecto_fechainicio timestamp with time zone,
  proyecto_fechaultimocambio timestamp with time zone,
  proyecto_fechafin timestamp with time zone,
  CONSTRAINT proyecto_pkey PRIMARY KEY (proyecto_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.proyecto
  OWNER TO postgres;
