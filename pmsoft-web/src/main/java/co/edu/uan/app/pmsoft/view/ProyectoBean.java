package co.edu.uan.app.pmsoft.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.edu.uan.app.pmsoft.model.entity.Proyecto;
import co.edu.uan.app.pmsoft.model.pojo.Constantes;
import co.edu.uan.app.pmsoft.model.service.ProyectoService;
import co.edu.uan.app.pmsoft.util.FacesUtils;

@ManagedBean(name = ProyectoBean.BEAN_NAME)
@CustomScoped(value = "#{window}")
public class ProyectoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String BEAN_NAME = "proyectoBean";
	public static final String PAGE_NAME = "gestionar_proyectos";
	private static final Logger logger = LoggerFactory.getLogger(ProyectoBean.class);

	@EJB
	ProyectoService proyectoService;

	private Proyecto proyecto;
	private List<Proyecto> listaProyectos;
	private List<SelectItem> listSelectItem;
	private SelectItem selectItem;
	private String headerDialog;
	private boolean visiblePopup;
	private boolean visibleEdit;
	private boolean visibleView;
	private boolean visibleDelete;
	private boolean popupDateFechaInicio;
	private boolean popupDateFechaFin;


	@Inject
	private SessionBean sessionBean;

	@PostConstruct
	public void init() {
		this.listaProyectos = null;
		this.proyecto = null;
		this.headerDialog = "";
		this.visiblePopup = false;
		this.visibleEdit = false;
		this.visibleView = false;
		this.visibleDelete = false;
		this.popupDateFechaInicio = true;
		this.popupDateFechaFin = true;
	}

	private void openPopup() {
		this.visiblePopup = true;
	}

	private void closedPopup() {
		this.visiblePopup = false;
	}

	public List<Proyecto> getProjectAll() {
		this.listaProyectos = proyectoService.getAll();
		return this.listaProyectos;
	}

	public void agregarProyecto(ActionEvent event) {
		logger.info("Entro a addProject(event:" + event + ")");

		this.proyecto = new Proyecto();
		this.proyecto.setVersion(1);
		this.proyecto.setNombre("");
		this.proyecto.setObjeto("");
		this.proyecto.setFechaInicio(null);
		this.proyecto.setFechaFin(null);
		this.proyecto.setPersonaResponsable("");
		this.proyecto.setPorcentaje(0);
		this.proyecto.setUsuarioCreacion(this.getSessionBean().getNombreCompletoUsuario());
		this.proyecto.setFechaCreacion(new Date());
		this.proyecto.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
		this.proyecto.setFechaUltimoCambio(new Date());
		this.proyecto.setEstado(Constantes.ESTADO_ACTIVO);
		this.proyecto.setEditable(true);

		this.headerDialog = "Nuevo Proyecto";
		this.openPopup();
		this.visibleEdit = false;
		this.visibleView = true;
		this.visibleDelete = false;
		logger.info(this.proyecto.getUsuarioUltimoCambio());
		logger.info("Saliendo de addProject(rol:" + proyecto + ")");

	}

	public String saveAction() {
		logger.info("Entró a saveAction(ActionEvent event)");

		if (validateSaveAction()) {
			try {
				proyectoService.save(this.proyecto);
				this.getProjectAll();
				this.closedPopup();

			} catch (Exception e) {
				FacesUtils.addMessageError("Guardar Proyecto", "Error al guardar el Proyecto", e.getMessage());
				logger.error("Error al guardar proyecto. "+e.getMessage());
			}
		}

		logger.info("Saliendo de saveAction()");
		return PAGE_NAME;
	}

	private boolean validateSaveAction() {
		logger.info("Entró a validateSaveAction()");

		boolean valid = true;
		String detail = "";

		if (this.proyecto == null) {

			detail = "No existe un objeto PROYECTO inicializado";
			valid = false;

		} else if (StringUtils.isBlank(this.proyecto.getNombre())) {

			detail = "Se debe ingresar el nombre del proyecto";
			valid = false;
			
		} else if (proyecto.getFechaInicio() == null) {
			detail = "Se debe ingresar una fecha de inicio";
			valid = false;
			
		} else if (proyecto.getFechaFin() == null) {
			detail = "Se debe ingresar una fecha de fin";
			valid = false;
			
		} else if (proyecto.getFechaInicio().compareTo(proyecto.getFechaFin()) > 0) {
			detail = "La fecha fin no puede ser menor a la fecha inicio";
			valid = false;
			
		} else if (StringUtils.isBlank(proyecto.getObjeto())) {
			detail = "Se debe ingresar el objeto del proyecto";
			valid = false;
			
		} else if (StringUtils.isBlank(proyecto.getPersonaResponsable())) {
			detail = "Se debe ingresar la persona responsable del proyecto";
			valid = false;
			
		}

		if (!valid) {

			FacesUtils.addMessageError("Guardar Proyecto", "Error al guardar el Proyecto", detail);
			logger.error("Error validando el proyecto a guardar. "+detail);
		}

		logger.info("Saliendo de validateSaveAction()");
		return valid;
	}

	public void editarProyecto(ActionEvent event) {
		logger.info("Entro a editProject(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            
            if (tmpRowData instanceof Proyecto) {
            	this.proyecto = (Proyecto) tmpRowData;
            	this.proyecto.setFechaUltimoCambio(new Date());
            	this.proyecto.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
            }
            
        }

		this.headerDialog = "Editar Proyecto";
		this.openPopup();
		this.visibleEdit = false;
		this.visibleView = true;
		this.visibleDelete = false;

		logger.info("Saliendo de editProject(project:" + proyecto + ")");

	}

	public void eliminarProyecto(ActionEvent event) {
		logger.info("Entro a deleteProject(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            if (tmpRowData instanceof Proyecto) {
            	this.proyecto = (Proyecto) tmpRowData;
            	this.proyecto.setFechaUltimoCambio(new Date());
            	this.proyecto.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
            }
        }
        
        
        this.visibleEdit = false;
        this.visibleView = true;
        this.visibleDelete = true;
        this.headerDialog = "Eliminar Proyecto";
        this.openPopup();
        
		logger.info("Saliendo de deleteProject(project:" + proyecto + ")");

	}

	public void verProyecto(ActionEvent event) {
		logger.info("Entro a viewProject(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            if (tmpRowData instanceof Proyecto) {
            	this.proyecto = (Proyecto) tmpRowData;
            }
        }

        this.visibleEdit = true;
        this.visibleView = false;
        this.visibleDelete = false;
		this.headerDialog = "Información del Proyecto";
		this.openPopup();

		logger.info("Saliendo de viewProject(project:" + proyecto + ")");

	}

	public void popupClose() {
		this.visiblePopup = false;
	}

	public String deleteAction() {
		logger.info("Entró a deleteAction(ActionEvent event)");

		if (validateSaveAction()) {

			try {
				proyectoService.delete(this.proyecto);
				this.getProjectAll();
				this.closedPopup();

			} catch (Exception e) {
				FacesUtils.addMessageError("Eliminar Proyecto", "Error al eliminar el Proyecto", e.getMessage());
				logger.error("Error al eliminar proyecto. "+e.getMessage());
			}
		}

		logger.info("Saliendo de deleteAction()");
		return PAGE_NAME;
	}

	public String viewAction() {
		logger.info("Entró a viewAction(ActionEvent event)");

		if (validateSaveAction()) {

			try {
				proyectoService.delete(this.proyecto);
				this.getProjectAll();
				this.closedPopup();

			} catch (Exception e) {
				FacesUtils.addMessageError("Ver Proyecto", "Error al ver el Proyecto", e.getMessage());
				logger.error("Error al ver proyecto. "+e.getMessage());
			}
		}

		logger.info("Saliendo de viewAction()");
		return PAGE_NAME;
	}

	public List<SelectItem> getListSelectItem(){

		this.listSelectItem = new ArrayList<SelectItem>();

		if(this.listaProyectos != null){
			for (Proyecto project : listaProyectos) {
				this.listSelectItem.add(new SelectItem(project.getId(), project.getNombre()));
			}
		}

		return listSelectItem;
	}

	public String cancelAction() {
		logger.info("Entró a cancelAction()");

		this.closedPopup();

		logger.info("Saliendo de cancelAction()");
		return PAGE_NAME;
	}

	public String getHeaderDialog() {
		return this.headerDialog;
	}

	public void setHeaderDialog(String headerDialog) {
		this.headerDialog = headerDialog;
	}

	public Proyecto getProyecto() {

		logger.info("this.rol = " + this.proyecto);
		if (this.proyecto != null)
			logger.info("this.project.getNombre() = " + this.proyecto.getNombre());

		return this.proyecto;
	}

	public void setProyecto(Proyecto project) {
		this.proyecto = project;
	}

	public boolean isVisiblePopup() {
		return visiblePopup;
	}

	public void setVisiblePopup(boolean visiblePopup) {
		this.visiblePopup = visiblePopup;
	}

	public void setNombreProyecto(String nombre){
		if(this.proyecto != null){
			this.proyecto.setNombre(nombre);
		}
	}

	public String getNombreProyecto(){
		String nombre = "";
		if(this.proyecto != null){
			nombre = this.proyecto.getNombre();
		}

		return nombre;
	}

	/*
     *  if closing with a client side api, ensure a listener is used to
     *  update the visible value on the server
     */
    public void closeFAjax(AjaxBehaviorEvent event){
        this.visiblePopup = false;
        this.visibleEdit = false;
    }

    public String getObjetoProyecto(){
		String objeto = "";
		if(this.proyecto != null){
			objeto = this.proyecto.getObjeto();
		}

		return objeto;
	}

	public void setObjetoProyecto(String objeto) {
		if(this.proyecto != null){
			this.proyecto.setObjeto(objeto);
		}
	}

	public void setFechaInicioProyecto(Date fecha) {
		if(this.proyecto != null){
			this.proyecto.setFechaInicio(fecha);
		}
	}

	public Date getFechaInicioProyecto() {
		Date fecha = null;
		if(this.proyecto != null){
			fecha = this.proyecto.getFechaInicio();
		}

		return fecha;
	}

	public void setFechaFinProyecto(Date fecha) {
		if(this.proyecto != null){
			this.proyecto.setFechaFin(fecha);
		}
	}

	public Date getFechaFinProyecto() {
		Date fecha = null;
		if(this.proyecto != null){
			fecha = this.proyecto.getFechaFin();
		}

		return fecha;
	}

	public void setEstadoProyecto(Integer estado) {
		if(this.proyecto != null){
			this.proyecto.setEstado(estado);
		}
	}

	public Integer getEstadoProyecto() {
		Integer estado = null;
		if(this.proyecto != null){
			estado = this.proyecto.getEstado();
		}

		return estado;
	}

	public void setPorcentajeProyecto(Double porcentaje) {
		if(this.proyecto != null){
			this.proyecto.setPorcentaje(porcentaje);
		}
	}

	public double getPorcentajeProyecto() {
		double estado = 0;
		if(this.proyecto != null){
			estado = this.proyecto.getPorcentaje();
		}

		return estado;
	}

	public void setFechaCreacionProyecto(Date fechaCreacion) {
		if(this.proyecto != null){
			this.proyecto.setFechaCreacion(fechaCreacion);
		}
	}

	public Date getFechaCreacionProyecto() {
		Date fechaCreacion = null;
		if(this.proyecto != null){
			fechaCreacion = this.proyecto.getFechaCreacion();
		}

		return fechaCreacion;
	}

	public void setFechaUltimoCambioProyecto(Date fechaUltimoCambio) {
		if(this.proyecto != null){
			this.proyecto.setFechaUltimoCambio(fechaUltimoCambio);
		}
	}

	public Date getFechaUltimoCambioProyecto() {
		Date fechaUltimoCambio = null;
		if(this.proyecto != null){
			fechaUltimoCambio = this.proyecto.getFechaUltimoCambio();
		}

		return fechaUltimoCambio;
	}

	public void setUsuarioCreacionProyecto(String usuarioCreacion) {
		if(this.proyecto != null){
			this.proyecto.setUsuarioCreacion(usuarioCreacion);
		}
	}

	public String getUsuarioCreacionProyecto() {
		String user = null;
		if(this.proyecto != null){
			user = this.proyecto.getUsuarioCreacion();
		}

		return user;
	}

	public void setUsuarioUltimoCambioProyecto(String usuarioUltimoCambio) {
		if(this.proyecto != null){
			this.proyecto.setUsuarioUltimoCambio(usuarioUltimoCambio);
		}
	}

	public String getUsuarioUltimoCambioProyecto() {
		String user = null;
		if(this.proyecto != null){
			user = this.proyecto.getUsuarioUltimoCambio();
		}

		return user;
	}

	public void setPersonaResponsableProyecto(String persona) {
		if(this.proyecto != null){
			this.proyecto.setPersonaResponsable(persona);
		}
	}

	public String getPersonaResponsableProyecto() {
		String fecha = null;
		if(this.proyecto != null){
			fecha = this.proyecto.getPersonaResponsable();
		}

		return fecha;
	}


	public boolean isVisibleEdit() {
		return visibleEdit;
	}

	public void setVisibleEdit(boolean visibleEdit) {
		this.visibleEdit = visibleEdit;
	}

	public SelectItem getSelectItem() {
		return selectItem;
	}

	public void setSelectItem(SelectItem selectItem) {
		this.selectItem = selectItem;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public boolean isVisibleView() {
		return visibleView;
	}

	public void setVisibleView(boolean visibleView) {
		this.visibleView = visibleView;
	}
	
	public boolean isVisibleDelete() {
		return visibleDelete;
	}

	public void setVisibleDelete(boolean visibleDelete) {
		this.visibleDelete = visibleDelete;
	}

	public boolean isPopupDateFechaInicio() {
		return popupDateFechaInicio;
	}

	public void setPopupDateFechaInicio(boolean popupDateFechaInicio) {
		this.popupDateFechaInicio = popupDateFechaInicio;
	}

	public boolean isPopupDateFechaFin() {
		return popupDateFechaFin;
	}

	public void setPopupDateFechaFin(boolean popupDateFechaFin) {
		this.popupDateFechaFin = popupDateFechaFin;
	}
	
}
