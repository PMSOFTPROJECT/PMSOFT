CREATE TABLE public.personarecurso
(
  persona_recurso_id bigint NOT NULL,
  persona_recurso_editable boolean,
  persona_recurso_estado integer,
  persona_recurso_fechacreacion timestamp without time zone,
  persona_recurso_fechaultimocambio timestamp without time zone,
  persona_recurso_recurso character varying(255) NOT NULL,
  persona_recurso_usuariocreacion character varying(255),
  persona_recurso_usuarioultimocambio character varying(255),
  persona_recurso_persona_id bigint,
  persona_recurso_recurso_id bigint,
  CONSTRAINT personarecurso_pkey PRIMARY KEY (persona_recurso_id),
  CONSTRAINT fkd_persona FOREIGN KEY (persona_recurso_persona_id)
      REFERENCES public.persona (persona_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkd_recurso FOREIGN KEY (persona_recurso_recurso_id)
      REFERENCES public.recurso (recurso_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.personarecurso
  OWNER TO postgres;
