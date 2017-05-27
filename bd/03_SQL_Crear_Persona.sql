CREATE TABLE public.persona
(
  persona_id bigint NOT NULL,
  persona_nombre character varying(255) NOT NULL,
  persona_costohora bigint NOT NULL,
  persona_cargo character varying(255) NOT NULL,
  persona_telefono character varying(255) NOT NULL,
  persona_direccion character varying(255) NOT NULL,
  persona_email character varying(255) NOT NULL,
  persona_nombrecontactoemergencia character varying(255) NOT NULL,
  persona_telefonocontactoemergencia character varying(255) NOT NULL,
  persona_estado integer NOT NULL,
  persona_identificacion character varying(255) NOT NULL,
  persona_usuariocreacion character varying(255) NOT NULL,
  persona_fechacreacion timestamp with time zone,
  persona_usuarioultimocambio character varying(255) NOT NULL,
  persona_fechaultimocambio timestamp with time zone,
  persona_editable boolean NOT NULL,
  persona_tipodocumento_id bigint,
  CONSTRAINT persona_pkey PRIMARY KEY (persona_id),
  CONSTRAINT fkd_tipodocumento FOREIGN KEY (persona_tipodocumento_id)
      REFERENCES public.tipodocumento (t_documento_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uq_persona_identificacion UNIQUE (persona_identificacion)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.persona
  OWNER TO postgres;