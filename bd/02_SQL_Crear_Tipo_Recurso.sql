-- Table: public.tiporecurso

-- DROP TABLE public.tiporecurso;

CREATE TABLE public.tiporecurso
(
  tiporecurso_id bigint NOT NULL,
  tiporecurso_editable boolean NOT NULL,
  tiporecurso_estado character varying(255) NOT NULL,
  tiporecurso_fechacreacion timestamp without time zone NOT NULL,
  tiporecurso_fechaultimocambio timestamp without time zone NOT NULL,
  tiporecurso_nombre character varying(255) NOT NULL,
  tiporecurso_usuariocreacion character varying(255) NOT NULL,
  tiporecurso_usuarioultimocambio character varying(255) NOT NULL,
  tiporecurso_version integer,
  CONSTRAINT tiporecurso_pkey PRIMARY KEY (tiporecurso_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tiporecurso
  OWNER TO postgres;
