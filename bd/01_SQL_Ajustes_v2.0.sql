ALTER TABLE public.project
  DROP COLUMN proyecto_fechacreacion;
ALTER TABLE public.project
  DROP COLUMN proyecto_fechainicio;
ALTER TABLE public.project
  DROP COLUMN proyecto_fechaultimocambio;
ALTER TABLE public.project
  DROP COLUMN proyecto_fechafin;
ALTER TABLE public.project
  ADD COLUMN proyecto_fechacreacion timestamp with time zone;
ALTER TABLE public.project
  ADD COLUMN proyecto_fechainicio timestamp with time zone;
ALTER TABLE public.project
  ADD COLUMN proyecto_fechaultimocambio timestamp with time zone;
ALTER TABLE public.project
  ADD COLUMN proyecto_fechafin timestamp with time zone;