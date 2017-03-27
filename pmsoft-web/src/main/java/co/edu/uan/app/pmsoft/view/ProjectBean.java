package co.edu.uan.app.pmsoft.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
import co.edu.uan.app.pmsoft.model.entity.Project;
import co.edu.uan.app.pmsoft.model.pojo.Constantes;
import co.edu.uan.app.pmsoft.model.service.ProjectService;
import co.edu.uan.app.pmsoft.util.FacesUtils;

@ManagedBean(name = ProjectBean.BEAN_NAME)
@CustomScoped(value = "#{window}")
public class ProjectBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final String BEAN_NAME = "projectBean";
	public static final String PAGE_NAME = "gestionar_proyectos";
	private static final Logger logger = LoggerFactory.getLogger(ProjectBean.class);

	@EJB
	ProjectService projectService;
	
	private Project project;
	private List<Project> listProject;
	private List<SelectItem> listSelectItem;
	private SelectItem selectItem;
	private String headerDialog;
	private boolean visiblePopup;
	private boolean visibleDelete;
	private boolean visibleView;
	
	
	@Inject
	private SessionBean sessionBean;
	
	@PostConstruct
	public void init() {
		this.listProject = null;
		this.project = null;
		this.headerDialog = "";
		this.visiblePopup = false;
		this.visibleDelete = false;
		this.visibleView = false;
	}
	
	private void openPopup() {
		this.visiblePopup = true;
	}
	
	private void closedPopup() {
		this.visiblePopup = false;
	}
	
	public List<Project> getProjectAll() {
		this.listProject = projectService.getAll();
		return this.listProject;
	}
	
	public void addProject(ActionEvent event) {
		logger.info("Entro a addProject(event:" + event + ")");
		
		String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));	
		String hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
		String minute = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
		String second = String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
		
		this.project = new Project();
		this.project.setVersion(1);
		this.project.setNombre("");
		this.project.setObjeto("");
		this.project.setFechaInicio("");
		this.project.setFechaFin("");
		this.project.setPersonaResponsable("");
		this.project.setPorcentaje(0);
		this.project.setUsuarioCreacion(this.getSessionBean().getNombreCompletoUsuario());
		this.project.setFechaCreacion(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);
		this.project.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
		this.project.setFechaUltimoCambio(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);
		this.project.setEstado(Constantes.ESTADO_ACTIVO);
		this.project.setEditable(true);
		
		this.headerDialog = "Nuevo Proyecto";
		this.openPopup();
		this.visibleDelete = false;
		this.visibleView = true;
		logger.info(this.project.getUsuarioUltimoCambio());
		logger.info("Saliendo de addProject(rol:" + project + ")");

	}
	
	public String saveAction() {
		logger.info("Entró a saveAction(ActionEvent event)");

		if (validateSaveAction()) {
			
			try {
				projectService.save(this.project);
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

		if (this.project == null) {
			
			detail = "No existe un objeto PROJECT inicializado";
			valid = false;
			
		} else if (StringUtils.isBlank(this.project.getNombre())) {
			
			detail = "Se debe ingresar el nombre del proyecto";
			valid = false;
		}

		if (!valid) {
			
			FacesUtils.addMessageError("Guardar Proyecto", "Error al guardar el Proyecto", detail);
			logger.error("Error validando el proyecto a guardar. "+detail);
		}

		logger.info("Saliendo de validateSaveAction()");
		return valid;
	}
	
	public void editProject(ActionEvent event) {
		logger.info("Entro a editProject(event:" + event + ")");
		
        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            if (tmpRowData instanceof Project) {
            
        		String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 1);
        		String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));	
        		String hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
        		String minute = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
        		String second = String.valueOf(Calendar.getInstance().get(Calendar.SECOND));         	
            	
            	this.project = (Project) tmpRowData;
            	this.project.setFechaUltimoCambio(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);
            	this.project.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
            }
        }

		this.headerDialog = "Editar Proyecto";
		this.openPopup();
		this.visibleDelete = false;
		this.visibleView = true;

		logger.info("Saliendo de editProject(project:" + project + ")");

	}
	
	public void deleteProject(ActionEvent event) {
		logger.info("Entro a deleteProject(event:" + event + ")");
		
        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            if (tmpRowData instanceof Project) {
            	this.project = (Project) tmpRowData;
            	deleteAction();
            }
        }
		
		logger.info("Saliendo de deleteProject(project:" + project + ")");

	}
	
	public void viewProject(ActionEvent event) {
		logger.info("Entro a viewProject(event:" + event + ")");
		
        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            if (tmpRowData instanceof Project) {
            	this.project = (Project) tmpRowData;
            }
        }
        
        this.visibleDelete = true;
        this.visibleView = false;
		this.headerDialog = "Información del Proyecto";
		this.openPopup();
		
		logger.info("Saliendo de viewProject(project:" + project + ")");

	}
	
	public void popupClose() {
		this.visiblePopup = false;
	}
	
	public String deleteAction() {
		logger.info("Entró a deleteAction(ActionEvent event)");

		if (validateSaveAction()) {

			try {
				projectService.delete(this.project);
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
				projectService.delete(this.project);
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
		
		if(this.listProject != null){
			for (Project project : listProject) {
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
	
	public Project getProject() {

		logger.info("this.rol = " + this.project);
		if (this.project != null)
			logger.info("this.project.getNombre() = " + this.project.getNombre());

		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public boolean isVisiblePopup() {
		return visiblePopup;
	}

	public void setVisiblePopup(boolean visiblePopup) {
		this.visiblePopup = visiblePopup;
	}
	
	public void setNombreProject(String nombre){
		if(this.project != null){
			this.project.setNombre(nombre);
		}
	}
	
	public String getNombreProject(){
		String nombre = "";
		if(this.project != null){
			nombre = this.project.getNombre();
		}
		
		return nombre;
	}
	
	/*
     *  if closing with a client side api, ensure a listener is used to
     *  update the visible value on the server
     */
    public void closeFAjax(AjaxBehaviorEvent event){
        this.visiblePopup = false;
        this.visibleDelete = false;
    }
    
    public String getObjetoProject(){
		String objeto = "";
		if(this.project != null){
			objeto = this.project.getObjeto();
		}
		
		return objeto;
	}

	public void setObjetoProject(String objeto) {
		if(this.project != null){
			this.project.setObjeto(objeto);
		}
	}

	public void setFechaInicioProject(String fecha) {
		if(this.project != null){
			this.project.setFechaInicio(fecha);
		}
	}
	
	public String getFechaInicioProject() {
		String fecha = null;
		if(this.project != null){
			fecha = this.project.getFechaInicio();
		}
		
		return fecha;
	}	
	
	public void setFechaFinProject(String fecha) {
		if(this.project != null){
			this.project.setFechaFin(fecha);
		}
	}
	
	public String getFechaFinProject() {
		String fecha = null;
		if(this.project != null){
			fecha = this.project.getFechaFin();
		}
		
		return fecha;
	}
	
	public void setEstadoProject(Integer estado) {
		if(this.project != null){
			this.project.setEstado(estado);
		}
	}
	
	public Integer getEstadoProject() {
		Integer estado = null;
		if(this.project != null){
			estado = this.project.getEstado();
		}
		
		return estado;
	}
	
	public void setPorcentajeProject(Double porcentaje) {
		if(this.project != null){
			this.project.setPorcentaje(porcentaje);
		}
	}
	
	public double getPorcentajeProject() {
		double estado = 0;
		if(this.project != null){
			estado = this.project.getPorcentaje();
		}
		
		return estado;
	}
	
	public void setFechaCreacionProject(String fechaCreacion) {
		if(this.project != null){
			this.project.setFechaCreacion(fechaCreacion);
		}
	}
	
	public String getFechaCreacionProject() {
		String estado = null;
		if(this.project != null){
			estado = this.project.getFechaCreacion();
		}
		
		return estado;
	}
	
	public void setFechaUltimoCambioProject(String fechaUltimoCambio) {
		if(this.project != null){
			this.project.setFechaUltimoCambio(fechaUltimoCambio);
		}
	}
	
	public String getFechaUltimoCambioProject() {
		String fechaUltimoCambio = null;
		if(this.project != null){
			fechaUltimoCambio = this.project.getFechaUltimoCambio();
		}
		
		return fechaUltimoCambio;
	}
	
	public void setUsuarioCreacionProject(String usuarioCreacion) {
		if(this.project != null){
			this.project.setUsuarioCreacion(usuarioCreacion);
		}
	}
	
	public String getUsuarioCreacionProject() {
		String user = null;
		if(this.project != null){
			user = this.project.getUsuarioCreacion();
		}
		
		return user;
	}
	
	public void setUsuarioUltimoCambioProject(String usuarioUltimoCambio) {
		if(this.project != null){
			this.project.setUsuarioUltimoCambio(usuarioUltimoCambio);
		}
	}
	
	public String getUsuarioUltimoCambioProject() {
		String user = null;
		if(this.project != null){
			user = this.project.getUsuarioUltimoCambio();
		}
		
		return user;
	}
	
	public void setPersonaResponsableProject(String persona) {
		if(this.project != null){
			this.project.setPersonaResponsable(persona);
		}
	}
	
	public String getPersonaResponsableProject() {
		String fecha = null;
		if(this.project != null){
			fecha = this.project.getPersonaResponsable();
		}
		
		return fecha;
	}
	
	
	public boolean isVisibleDelete() {
		return visibleDelete;
	}

	public void setVisibleDelete(boolean visibleDelete) {
		this.visibleDelete = visibleDelete;
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
	
}

