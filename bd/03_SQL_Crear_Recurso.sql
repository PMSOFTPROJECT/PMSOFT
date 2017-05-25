-- Table: public.recurso

-- DROP TABLE public.recurso;

CREATE TABLE public.recurso
(
  recurso_id bigint NOT NULL,
  recurso_costo integer NOT NULL,
  recurso_editable boolean NOT NULL,
  recurso_estado integer NOT NULL,
  recurso_fechacreacion timestamp without time zone NOT NULL,
  recurso_fechaultimocambio timestamp without time zone NOT NULL,
  recurso_nombre character varying(255) NOT NULL,
  recurso_usuariocreacion character varying(255) NOT NULL,
  recurso_usuarioultimocambio character varying(255) NOT NULL,
  recurso_version integer,
  recurso_tiporecurso_id bigint NOT NULL,
  CONSTRAINT recurso_pkey PRIMARY KEY (recurso_id),
  CONSTRAINT fkd124a7qyd4hqfpuo2fgfip6tv FOREIGN KEY (recurso_tiporecurso_id)
      REFERENCES public.tiporecurso (tiporecurso_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.recurso
  OWNER TO postgres;
