-- Table: public.version

-- DROP TABLE public.version;

CREATE TABLE public.version
(
  version_id bigint NOT NULL,
  version_comentario character varying(255) NOT NULL,
  version_editable boolean NOT NULL,
  version_estado character varying(255) NOT NULL,
  version_fechacreacion timestamp without time zone NOT NULL,
  version_fechafin timestamp without time zone NOT NULL,
  version_fechainicio timestamp without time zone NOT NULL,
  version_fechaultimocambio timestamp without time zone NOT NULL,
  version_nombre character varying(255) NOT NULL,
  version_usuariocreacion character varying(255) NOT NULL,
  version_usuarioultimocambio character varying(255) NOT NULL,
  version_version integer,
  CONSTRAINT version_pkey PRIMARY KEY (version_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.version
  OWNER TO postgres;
