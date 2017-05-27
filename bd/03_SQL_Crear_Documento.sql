CREATE TABLE public.documento
(
  documento_id bigint NOT NULL,
  documento_ruta character varying(255) NOT NULL,
  documento_nombre character varying(255) NOT NULL,
  documento_actividad bigint NOT NULL,
  documento_fechacreacion timestamp without time zone NOT NULL,
  documento_fechaultimocambio timestamp without time zone NOT NULL,
  documento_usuariocreacion character varying(255) NOT NULL,
  documento_usuarioultimocambio character varying(255) NOT NULL,
  documento_estado integer NOT NULL DEFAULT 1,
  documento_editable boolean NOT NULL,
  documento_version integer,
  CONSTRAINT documento_pkey PRIMARY KEY (documento_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.documento
  OWNER TO postgres;
