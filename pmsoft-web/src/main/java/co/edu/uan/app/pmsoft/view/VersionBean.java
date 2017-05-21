package co.edu.uan.app.pmsoft.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.edu.uan.app.pmsoft.model.entity.Version;
import co.edu.uan.app.pmsoft.model.service.VersionService;
import co.edu.uan.app.pmsoft.util.FacesUtils;

@ManagedBean(name = VersionBean.BEAN_NAME)
@CustomScoped(value = "#{window}")
public class VersionBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final String BEAN_NAME = "versionBean";
	public static final String PAGE_NAME = "gestionar_versiones";
	private static final Logger logger = LoggerFactory.getLogger(VersionBean.class);
	
	@EJB
	VersionService versionService;
	
	private Version version;
	private List<Version> listaVersiones;
	private List<SelectItem> listSelectItem;
	private String headerDialog;
	private String descripcionNotificacion;
	private boolean visiblePopup;
	private boolean editar;
	private boolean ver;
	private boolean visibleCombo;
	private int width;
	private int height;
	private boolean visibleNotificacion;
		
	@Inject
	private SessionBean sessionBean;
	
	@PostConstruct
	public void init() {
		this.version = null;
		this.listaVersiones = null;
		this.visiblePopup = false;
		this.editar = false;
		this.ver = false;
		this.visibleCombo = false;
		this.width = 0;
		this.height = 0;
	}
	
	public List<Version> getVersionAll() {
		this.listaVersiones = versionService.getAll();
		return this.listaVersiones;
	}
	
	public void agregarVersion(ActionEvent event) {
		logger.info("Entro a agregarVersion(event:" + event + ")");
		
		this.version = new Version();
		this.version.setVersion(1);
		this.version.setNombre("");
		this.version.setFechaInicio(null);
		this.version.setFechaFin(null);
		this.version.setComentario("");
		this.version.setEstado("");
		this.version.setEditable(true);
		this.version.setUsuarioCreacion(this.getSessionBean().getNombreCompletoUsuario());
		this.version.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
		this.version.setFechaUltimoCambio(new Date());
		this.version.setFechaCreacion(new Date());
		this.headerDialog = "Nueva versión";
		
		this.editar = false;
		this.visibleCombo = false;
		this.width = 400;
		this.height = 300;
		this.openPopup();
		
	}
	
	public String saveAction(ActionEvent event) {
		logger.info("Entró a saveAction(ActionEvent event)");
        
		if (validateSaveAction(event)) {
			try {				
				versionService.save(this.version);
				this.getVersionAll();
				this.descripcionNotificacion = "Almacenado con éxito";
				this.visibleNotificacion = true;
				this.closedPopup();

			} catch (Exception e) {
				FacesUtils.addMessageError("Guardar Version", "Error al guardar la versión", e.getMessage());
				logger.error("Error al guardar version. "+e.getMessage());
			}
		}

		logger.info("Saliendo de saveAction()");
		return PAGE_NAME;
	}
	
	private boolean validateSaveAction(ActionEvent event) {
		logger.info("Entró a validateSaveAction()");
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
        
        // remove existing messages
        Iterator<FacesMessage> i = facesContext.getMessages();
        while (i.hasNext()) {
            i.next();
            i.remove();
        }
		
		boolean valid = true;
		String detail = "";
		
		if (this.version == null) {
			detail = "No existe un objeto PROYECTO inicializado";
			valid = false;
			
		} else if (StringUtils.isBlank(this.version.getNombre())) {
			detail = "Se debe ingresar el nombre de la versión";	
			valid = false;
			
		} else if (this.version.getFechaInicio() == null) {
			detail = "Se debe ingresar una fecha de inicio";
			valid = false;
			
		} else if (this.version.getFechaFin() == null) {
			detail = "Se debe ingresar una fecha de fin";
			valid = false;
			
		} else if (this.version.getFechaInicio().compareTo(this.version.getFechaFin()) > 0) {
			detail = "La fecha fin no puede ser menor a la fecha inicio";
			valid = false;
			
		} else if (StringUtils.isBlank(this.version.getComentario())) {
			detail = "Se debe ingresar el comentario de la versión";	
			valid = false;
			
		} else if (StringUtils.isBlank(this.version.getEstado())) {
			detail = "Se debe ingresar el estado de la versión";	
			valid = false;
		}
		
		if (!valid) {			
			FacesUtils.addMessageError("Guardar Versión", "Error al guardar la Versión", detail);
			logger.error("Error validando la versión a guardar. "+detail);
			
			UIComponent component = event.getComponent();
	        FacesMessage facesMessage = new FacesMessage((FacesMessage.Severity) FacesMessage.VALUES.get(2), detail, detail);
	        facesContext.addMessage(component.getClientId(), facesMessage);
		}
		
		logger.info("Saliendo de validateSaveAction()");
		
		return valid;
	}
	
	public void editarVersion(ActionEvent event) {
		logger.info("Entro a editarVersion(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            
            if (tmpRowData instanceof Version) {
            	this.version = (Version) tmpRowData;
            	this.version.setFechaUltimoCambio(new Date());
            	this.version.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
            }
        }
        
        this.headerDialog = "Editar Version";
        this.editar = true;
        this.ver = false;
        this.visibleCombo = true;
        this.width = 400;
		this.height = 300;
		this.openPopup();

		logger.info("Saliendo de editarVersion(project:" + version + ")");
	}
	
	public void verVersion(ActionEvent event) {
		logger.info("Entro a editarVersion(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            
            if (tmpRowData instanceof Version) {
            	this.version = (Version) tmpRowData;
            	this.version.setFechaUltimoCambio(new Date());
            	this.version.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
            }
        }
        
        this.headerDialog = "Información de la Version";
        this.editar = true;
        this.ver = true;
        this.width = 400;
        this.height = 410;
		this.openPopup();
	}
	
	public void eliminarVersion(ActionEvent event) {
		logger.info("Entro a eliminarVersion(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            
            if (tmpRowData instanceof Version) {
            	this.version = (Version) tmpRowData;
            	this.version.setFechaUltimoCambio(new Date());
            	this.version.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
            }
        }
        
        this.headerDialog = "Editar Version";
        this.editar = true;
        this.ver = false;
        this.closedPopup();

		logger.info("Saliendo de eliminarVersion(project:" + version + ")");
	}
	
	public String deleteAction(ActionEvent event) {
		logger.info("Entró a deleteAction(ActionEvent event)");

		try {
			versionService.delete(this.version);
			this.getVersionAll();
			this.closedPopup();

		} catch (Exception e) {
			FacesUtils.addMessageError("Eliminar Version", "Error al eliminar la versión", e.getMessage());
			logger.error("Error al eliminar versión. "+e.getMessage());
		}


		logger.info("Saliendo de deleteAction()");
		return PAGE_NAME;
	}
	
	public void closeConfirmDialog(ActionEvent event) {
		this.closedPopup();
	}	
		
	public String cancelAction() {
		logger.info("Entró a cancelAction()");

		this.closedPopup();
		this.ver = false;

		logger.info("Saliendo de cancelAction()");
		return PAGE_NAME;
	}
	
	public SessionBean getSessionBean() {
		return sessionBean;
	}	
		
	public String getHeaderDialog() {
		return this.headerDialog;
	}

	public void setHeaderDialog(String headerDialog) {
		this.headerDialog = headerDialog;
	}

	public boolean isVisiblePopup() {
		return visiblePopup;
	}
	
	public void setVisiblePopup(boolean visiblePopup) {
		this.visiblePopup = visiblePopup;
	}
	
	private void openPopup() {
		this.visiblePopup = true;
	}

	private void closedPopup() {
		this.visiblePopup = false;
	}
	
	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}
	
	public void setNombreVersion(String nombre) {
		if(this.version != null){
			this.version.setNombre(nombre);
		}
	}

	public String getNombreVersion() {
		String nombre = "";
		
		if(this.version != null){
			nombre = this.version.getNombre();
		}

		return nombre;
	}
	
	public void setFechaInicioVersion(Date fecha) {
		if (this.version != null) {
			this.version.setFechaInicio(fecha);
		}
	}
	
	public Date getFechaInicioVersion() {
		Date fecha = null;
		
		if (this.version != null) {
			fecha = this.version.getFechaInicio();
		}
		
		return 	fecha;
	}
	
	public void setFechaFinVersion(Date fecha) {
		if (this.version != null) {
			this.version.setFechaFin(fecha);
		}
	}
	
	public Date getFechaFinVersion() {
		Date fecha = null;
		
		if (this.version != null) {
			fecha = this.version.getFechaFin();
		}
		
		return 	fecha;
	}
	
	public void setFechaCreacionVersion(Date fecha) {
		if (this.version != null) {
			this.version.setFechaCreacion(fecha);
		}
	}
	
	public Date getFechaCreacionVersion() {
		Date fecha = null;
		
		if (this.version != null) {
			fecha = this.version.getFechaCreacion();
		}
		
		return 	fecha;
	}
	
	public void setFechaUltimoCambioVersion(Date fecha) {
		if (this.version != null) {
			this.version.setFechaUltimoCambio(fecha);
		}
	}
	
	public Date getFechaUltimoCambioVersion() {
		Date fecha = null;
		
		if (this.version != null) {
			fecha = this.version.getFechaUltimoCambio();
		}
		
		return 	fecha;
	}
	
	public void setUsuarioCreacionVersion(String usuario) {
		if(this.version != null){
			this.version.setUsuarioCreacion(usuario);
		}
	}

	public String getUsuarioCreacionVersion() {
		String usuario = null;
		
		if(this.version != null){
			usuario = this.version.getUsuarioCreacion();
		}

		return usuario;
	}
	
	public void setUsuarioUltimoCambioVersion(String usuario) {
		if(this.version != null){
			this.version.setUsuarioUltimoCambio(usuario);
		}
	}

	public String getUsuarioUltimoCambioVersion() {
		String usuario = null;
		if(this.version != null){
			usuario = this.version.getUsuarioUltimoCambio();
		}

		return usuario;
	}
	
	public void setComentarioVersion(String comentario) {
		if (this.version != null) {
			this.version.setComentario(comentario);
		}
	}
	
	public String getComentarioVersion() {
		String comentario = "";
		
		if (this.version != null) {
			comentario = this.version.getComentario();
		}
		
		return comentario;
	}
	
	public void setEstadoVersion(String estado) {
		if (this.version != null) {
			this.version.setEstado(estado);
		}
	}
	
	public String getEstadoVersion() {
		String estado = null;
		
		if (this.version != null) {
			estado = this.version.getEstado();
		}
		
		return estado;
	}
	
	
	
	
	public String getDescripcionNotificacion() {
		return descripcionNotificacion;
	}

	public void setDescripcionNotificacion(String descripcionNotificacion) {
		this.descripcionNotificacion = descripcionNotificacion;
	}
		
	public boolean isVer() {
		return ver;
	}

	public void setVer(boolean ver) {
		this.ver = ver;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isVisibleCombo() {
		return visibleCombo;
	}

	public void setVisibleCombo(boolean visibleCombo) {
		this.visibleCombo = visibleCombo;
	}
	
	public List<SelectItem> getEstados() {
		this.listSelectItem = new ArrayList<SelectItem>();
		
		this.listSelectItem.add(new SelectItem("ACTIVO"));
		this.listSelectItem.add(new SelectItem("INACTIVO"));
		this.listSelectItem.add(new SelectItem("PROCESO"));
		this.listSelectItem.add(new SelectItem("PRUEBA"));
		this.listSelectItem.add(new SelectItem("PRODUCCIÓN"));
		this.listSelectItem.add(new SelectItem("TERMINADO"));
		this.listSelectItem.add(new SelectItem("ESTANCADO"));
		
		return this.listSelectItem;
	}
		
	/*
     *  if closing with a client side api, ensure a listener is used to
     *  update the visible value on the server
     */
    public void closeFAjax(AjaxBehaviorEvent event){
        this.visiblePopup = false;
    }

	public boolean isVisibleNotificacion() {
		return visibleNotificacion;
	}

	public void setVisibleNotificacion(boolean visibleNotificacion) {
		this.visibleNotificacion = visibleNotificacion;
	}
	
	public void closeListener(AjaxBehaviorEvent event) {
		visibleNotificacion = false;
	}

}