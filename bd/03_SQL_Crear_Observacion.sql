  CREATE TABLE public.observacion
  (
    observacion_id bigint NOT NULL,
    observacion_editable boolean NOT NULL,
    observacion_estado integer NOT NULL,
    observacion_fechacreacion timestamp with time zone,
    observacion_fechaultimocambio timestamp with time zone,
    observacion_descripcion character varying(255) NOT NULL,
    observacion_usuariocreacion character varying(255) NOT NULL,
    observacion_usuarioultimocambio character varying(255) NOT NULL,
    observacion_version integer,
    CONSTRAINT observacion_pkey PRIMARY KEY (observacion_id)
  )
  WITH (
    OIDS=FALSE
  );
  ALTER TABLE public.observacion
    OWNER TO postgres;
