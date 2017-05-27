CREATE TABLE public.rol
(
  rol_id integer NOT NULL DEFAULT nextval('rol_rol_id_seq'::regclass), -- Llave primaria de la tabla
  rol_nombre character varying(50) NOT NULL, -- Nombre del rol
  rol_estado integer NOT NULL DEFAULT 1, -- Indica el estado actual del registro, de acuerdo a los siguientes valores:...
  rol_editable boolean NOT NULL DEFAULT true, -- Campo que indica si el registro es editable por el usuario
  rol_version integer NOT NULL DEFAULT 1, -- Campo para controlar la versión del registro desde el sistema java
  rol_usuariocreacion character varying(255),
  rol_usuarioultimocambio character varying(255),
  rol_fechacreacion timestamp with time zone,
  rol_fechaultimocambio timestamp with time zone,
  CONSTRAINT rol_pk PRIMARY KEY (rol_id),
  CONSTRAINT rol_nombre_uk UNIQUE (rol_nombre)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.rol
  OWNER TO postgres;
COMMENT ON COLUMN public.rol.rol_id IS 'Llave primaria de la tabla';
COMMENT ON COLUMN public.rol.rol_nombre IS 'Nombre del rol';
COMMENT ON COLUMN public.rol.rol_estado IS 'Indica el estado actual del registro, de acuerdo a los siguientes valores:
0 => Inactivo
1 => Activo
2 => Suspendido';
COMMENT ON COLUMN public.rol.rol_editable IS 'Campo que indica si el registro es editable por el usuario';
COMMENT ON COLUMN public.rol.rol_version IS 'Campo para controlar la versión del registro desde el sistema java';

