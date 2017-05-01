CREATE TABLE public.tipodocumento
(
  t_documento_id bigint NOT NULL,
  t_documento_editable boolean NOT NULL,
  t_documento_estado integer NOT NULL,
  t_documento_fechacreacion timestamp without time zone NOT NULL,
  t_documento_fechaultimocambio timestamp without time zone NOT NULL,
  t_documento_nombre character varying(255) NOT NULL,
  t_documento_usuariocreacion character varying(255) NOT NULL,
  t_documento_usuarioultimocambio character varying(255) NOT NULL,
  t_documento_version integer,
  CONSTRAINT tipodocumento_pkey PRIMARY KEY (t_documento_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tipodocumento
  OWNER TO postgres;
