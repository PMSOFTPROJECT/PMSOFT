-- Table: public.recurso

-- DROP TABLE public.recurso;

CREATE TABLE public.recurso
(
  recurso_id bigint NOT NULL,
  recurso_costo double precision NOT NULL,
  recurso_editable boolean NOT NULL,
  recurso_estado integer NOT NULL,
  recurso_fechacreacion timestamp without time zone NOT NULL,
  recurso_fechaultimocambio timestamp without time zone NOT NULL,
  recurso_nombre character varying(255) NOT NULL,
  recurso_usuariocreacion character varying(255) NOT NULL,
  recurso_usuarioultimocambio character varying(255) NOT NULL,
  recurso_version integer,
  CONSTRAINT recurso_pkey PRIMARY KEY (recurso_id),
  CONSTRAINT recurso_recurso_nombre_key UNIQUE (recurso_nombre)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.recurso
  OWNER TO postgres;
