alter table rol
add constraint UQ_rol_nombre
unique(rol_nombre)

CREATE TABLE public.persona
(
  persona_id bigint NOT NULL,
  persona_nombre character varying(255) NOT NULL,
  persona_costohora bigint NOT NULL,
  persona_cargo character varying(255) NOT NULL,
  persona_telefono character varying(255) NOT NULL,
  persona_direccion character varying(255) NOT NULL,
  persona_email character varying(255) NOT NULL,
  persona_tipodocumento character varying(255) NOT NULL,
  persona_identificacion character varying(255) NOT NULL,
  persona_nombrecontactoemergencia character varying(255) NOT NULL,
  persona_telefonocontactoemergencia character varying(255) NOT NULL,  
  persona_usuariocreacion character varying(255) NOT NULL,
  persona_fechacreacion timestamp with time zone,
  persona_usuarioultimocambio character varying(255) NOT NULL,
  persona_fechaultimocambio timestamp with time zone,
  persona_estado integer NOT NULL,
  persona_editable boolean NOT NULL,
  CONSTRAINT persona_pkey PRIMARY KEY (persona_id),
  CONSTRAINT uq_persona_identificacion UNIQUE (persona_identificacion)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.persona
  OWNER TO postgres;