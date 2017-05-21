package co.edu.uan.app.pmsoft.view;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import co.edu.uan.app.pmsoft.model.entity.TipoRecurso;
import co.edu.uan.app.pmsoft.model.pojo.Constantes;
import co.edu.uan.app.pmsoft.model.service.TipoRecursoService;
import co.edu.uan.app.pmsoft.util.FacesUtils;

@ManagedBean(name = TipoRecursoBean.BEAN_NAME)
@CustomScoped(value = "#{window}")
public class TipoRecursoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final String BEAN_NAME = "tipoRecursoBean";
	public static final String PAGE_NAME = "gestionar_tipoRecurso";
	private static final Logger logger = LoggerFactory.getLogger(TipoRecursoBean.class);
	
	@EJB
	TipoRecursoService tipoRecursoService;
	
	private TipoRecurso tipoRecurso;
	private List<TipoRecurso> listaTipoRecursos;
	private String headerDialog;
	private String descripcionNotificacion;
	private boolean visiblePopup;
	private boolean editar;
	
	private boolean visibleNotificacion;
	
	@Inject
	private SessionBean sessionBean;
	
	public void init() {
		this.tipoRecurso = null;
		this.listaTipoRecursos = null;
		this.visiblePopup = false;
		this.editar = false;
	}
	
	public List<TipoRecurso> getTipoRecursoAll() {
		this.listaTipoRecursos = this.tipoRecursoService.getAll();
		return listaTipoRecursos;
	}
	
	public void agregarTipoRecurso(ActionEvent event) {
		logger.info("Entro a agregarTipoRecurso(event:" + event + ")");
		
		this.tipoRecurso = new TipoRecurso();
		this.tipoRecurso.setVersion(1);
		this.tipoRecurso.setNombre("");
		this.tipoRecurso.setEstado(Constantes.TIPO_RECURSO_ACTIVO);
		this.tipoRecurso.setEditable(true);
		this.tipoRecurso.setUsuarioCreacion(this.getSessionBean().getNombreCompletoUsuario());
		this.tipoRecurso.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
		this.tipoRecurso.setFechaUltimoCambio(new Date());
		this.tipoRecurso.setFechaCreacion(new Date());
		this.headerDialog = "Nuevo Tipo de Recurso";
		this.editar = true;
		this.openPopup();
	}
	
	public void editarTipoRecurso(ActionEvent event) {
		logger.info("Entro a editarTipoRecurso(event:" + event + ")");
		
        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            
            if (tmpRowData instanceof TipoRecurso) {
            	this.tipoRecurso = (TipoRecurso) tmpRowData;
            	this.tipoRecurso.setFechaUltimoCambio(new Date());
            	this.tipoRecurso.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
            }
        }
        
        this.headerDialog = "Editar Tipo Recurso";
        this.editar = false;
		this.openPopup();

		logger.info("Saliendo de editarVersion(project:" + tipoRecurso + ")");
	}
	
	public String saveAction(ActionEvent event) {
		logger.info("Entró a saveAction(ActionEvent event)");
		        
		if (validateSaveAction(event)) {
			try {
				this.tipoRecursoService.save(this.tipoRecurso);
				this.getListaTipoRecursos();
				this.visibleNotificacion = true;
				this.descripcionNotificacion = "Guardado con éxito";
				this.closedPopup();					
				
			} catch (Exception e) {
				FacesUtils.addMessageError("Guardar Tipo de recurso", "Error al guardar el tipo de recurso", e.getMessage());
				logger.error("Error al guardar tipo recurso. "+e.getMessage());
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
		
		if (this.tipoRecurso == null) {
			detail = "No existe un objeto TIPO RECURSO inicializado";
			valid = false;
			
		} else if (StringUtils.isBlank(this.tipoRecurso.getNombre())) {
			detail = "Se debe ingresar el nombre del tipo de recurso";	
			valid = false;

		} else if (this.tipoRecurso.getNombre().length() > 50) {
			detail = "El nombre del recurso no puede ser mayor a 50 caracteres";	
			valid = false;
			
		}
		
		if (!valid) {			
			FacesUtils.addMessageError("Guardar Versión", "Error al guardar el tipo de recurso", detail);
			logger.error("Error validando el tipo de recurso a guardar. "+detail);
			
			UIComponent component = event.getComponent();
	        FacesMessage facesMessage = new FacesMessage((FacesMessage.Severity) FacesMessage.VALUES.get(2), detail, detail);
	        facesContext.addMessage(component.getClientId(), facesMessage);
		}
		
		logger.info("Saliendo de validateSaveAction()");
		
		return valid;
	}
	
	public String cancelAction() {
		logger.info("Entró a cancelAction()");

		this.closedPopup();

		logger.info("Saliendo de cancelAction()");
		return PAGE_NAME;
	}
	
	private void openPopup() {
		this.visiblePopup = true;
	}

	private void closedPopup() {
		this.visiblePopup = false;
	}
	
	public void setNombreTipoRecurso(String nombre) {
		if(this.tipoRecurso != null){
			this.tipoRecurso.setNombre(nombre);
		}
	}

	public String getNombreTipoRecurso() {
		String nombre = "";
		
		if(this.tipoRecurso != null){
			nombre = this.tipoRecurso.getNombre();
		}

		return nombre;
	}

	public TipoRecurso getTipoRecurso() {
		return tipoRecurso;
	}

	public void setTipoRecurso(TipoRecurso tipoRecurso) {
		this.tipoRecurso = tipoRecurso;
	}

	public List<TipoRecurso> getListaTipoRecursos() {
		return listaTipoRecursos;
	}

	public void setListaTipoRecursos(List<TipoRecurso> listaTipoRecursos) {
		this.listaTipoRecursos = listaTipoRecursos;
	}

	public String getHeaderDialog() {
		return headerDialog;
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

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
		
	public String getDescripcionNotificacion() {
		return descripcionNotificacion;
	}

	public void setDescripcionNotificacion(String descripcionNotificacion) {
		this.descripcionNotificacion = descripcionNotificacion;
	}
	
	/*
     *  if closing with a client side api, ensure a listener is used to
     *  update the visible value on the server
     */
    public void closeFAjax(AjaxBehaviorEvent event){
        this.visiblePopup = false;
    }

	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
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