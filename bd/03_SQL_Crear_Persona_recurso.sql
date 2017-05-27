CREATE TABLE public.personarecurso
(
  persona_recurso_id bigint NOT NULL,
  persona_recurso_editable boolean,
  persona_recurso_estado integer,
  persona_recurso_fechacreacion timestamp without time zone,
  persona_recurso_fechaultimocambio timestamp without time zone,
  persona bytea,
  persona_recurso_recurso character varying(255) NOT NULL,
  persona_recurso_usuariocreacion character varying(255),
  persona_recurso_usuarioultimocambio character varying(255),
  personarecurso_version integer,
  CONSTRAINT personarecurso_pkey PRIMARY KEY (persona_recurso_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.personarecurso
  OWNER TO postgres;
