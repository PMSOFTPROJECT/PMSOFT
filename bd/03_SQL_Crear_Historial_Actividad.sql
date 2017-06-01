CREATE TABLE public.historialactividad
(
  historial_actividad_id bigint NOT NULL,
  historial_actividad_editable boolean NOT NULL,
  historial_actividad_estado integer NOT NULL,
  historial_actividad_fechacreacion timestamp with time zone,
  historial_actividad_fechaultimocambio timestamp with time zone,
  historial_actividad_usuariocreacion character varying(255) NOT NULL,
  historial_actividad_usuarioultimocambio character varying(255) NOT NULL,
  historial_actividad_version integer,
  CONSTRAINT historial_actividad_pkey PRIMARY KEY (historial_actividad_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.historialactividad
  OWNER TO postgres;
