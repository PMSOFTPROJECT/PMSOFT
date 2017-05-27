package co.edu.uan.app.pmsoft.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.edu.uan.app.pmsoft.model.entity.Recurso;
import co.edu.uan.app.pmsoft.model.entity.TipoRecurso;
import co.edu.uan.app.pmsoft.model.pojo.Constantes;
import co.edu.uan.app.pmsoft.model.service.RecursoService;
import co.edu.uan.app.pmsoft.model.service.TipoRecursoService;
import co.edu.uan.app.pmsoft.util.FacesUtils;

@ManagedBean(name = RecursoBean.BEAN_NAME)
@CustomScoped(value = "#{window}")
public class RecursoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final String BEAN_NAME = "recursoBean";
	public static final String PAGE_NAME = "gestionar_recursos";
	private static final Logger logger = LoggerFactory.getLogger(RecursoBean.class);
	
	@EJB
	RecursoService recursoService;
	
	@EJB
	TipoRecursoService tipoRecursoService;
	
	private Recurso recurso;
	private List<Recurso> listaRecursos;
	private List<TipoRecurso> listaTipoRecursos;
	private List<SelectItem> listSelectItem;
	private SelectItem selectItem;
	private String headerDialog;
	private boolean visiblePopup;
	private boolean ver;
	
	@Inject
	private SessionBean sessionBean;
	
	@PostConstruct
	public void init() {
		this.recurso = null;
		this.listaRecursos = null;
		this.visiblePopup = false;
		this.ver = false;
		this.listaTipoRecursos = null;
		
		this.recurso = new Recurso();
		this.recurso.setVersion(1);
		this.recurso.setNombre("");
		this.recurso.setTipoRecurso(new TipoRecurso()); // TODO
		this.recurso.setEstado(Constantes.ESTADO_ACTIVO);
		this.recurso.setCosto(0);
		this.recurso.setUsuarioCreacion(this.getSessionBean().getNombreCompletoUsuario());
		this.recurso.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
		this.recurso.setFechaUltimoCambio(new Date());
		this.recurso.setFechaCreacion(new Date());
		this.recurso.setEditable(true);
		
		logger.debug("RECURSO: " + this.recurso + "    " + this.recurso.getTipoRecurso());
	}
	
	public List<Recurso> getRecursoAll() {
		this.listaRecursos = recursoService.getAll();
		return this.listaRecursos;
	}
	
	public List<TipoRecurso> getTipoRecursoAll() {
		this.listaTipoRecursos = tipoRecursoService.getAll();
		return this.listaTipoRecursos;
	}

	public void agregarRecurso(ActionEvent event) {
		logger.info("Entro a agregarRecurso(event:" + event + ")");
		
		this.recurso = new Recurso();
		this.recurso.setVersion(1);
		this.recurso.setNombre("");
		this.recurso.setTipoRecurso(new TipoRecurso()); // TODO
		this.recurso.setEstado(Constantes.ESTADO_ACTIVO);
		this.recurso.setCosto(0);
		this.recurso.setUsuarioCreacion(this.getSessionBean().getNombreCompletoUsuario());
		this.recurso.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
		this.recurso.setFechaUltimoCambio(new Date());
		this.recurso.setFechaCreacion(new Date());
		this.recurso.setEditable(true);
		this.headerDialog = "Nuevo recurso";
		this.ver = false;
				
		this.openPopup();
	}
	
	public String saveAction(ActionEvent event) {
		logger.info("Entró a saveAction(ActionEvent event)");
        
		if (validateSaveAction(event)) {
			try {				
				logger.info("TIPOR: " + this.recurso.getTipoRecurso().getId());
				this.recurso.setTipoRecurso(tipoRecursoService.getById(this.recurso.getTipoRecurso().getId()));
				logger.info("TIPO RECURSO: " + this.recurso.getTipoRecurso().getNombre());
				recursoService.save(this.recurso);
				this.getRecursoAll();
				this.closedPopup();

			} catch (Exception e) {
				FacesUtils.addMessageError("Guardar Recurso", "Error al guardar el recurso", e.getMessage());
				logger.error("Error al guardar recurso. "+e.getMessage());
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
		
		if (this.recurso == null) {
			detail = "No existe un objeto PROYECTO inicializado";
			valid = false;
			
		} else if (StringUtils.isBlank(this.recurso.getNombre())) {
			detail = "Se debe ingresar el nombre del recurso";	
			valid = false;
			
		} else if (this.recurso.getTipoRecurso() == null || this.recurso.getTipoRecurso().getId() == null) { // TODO Validar el objeto tipo recurso
			detail = "Se debe ingresar el tipo del recurso";	
			valid = false;
			
		}
		
		if (!valid) {			
			FacesUtils.addMessageError("Guardar Recurso", "Error al guardar el Recurso", detail);
			logger.error("Error validando el recurso a guardar. "+detail);
			
			UIComponent component = event.getComponent();
	        FacesMessage facesMessage = new FacesMessage((FacesMessage.Severity) FacesMessage.VALUES.get(2), detail, detail);
	        facesContext.addMessage(component.getClientId(), facesMessage);
		}
		
		logger.info("Saliendo de validateSaveAction()");
		
		return valid;
	}
	
	public void editarRecurso(ActionEvent event) {
		logger.info("Entro a editarRecurso(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            
            if (tmpRowData instanceof Recurso) {
            	this.recurso = (Recurso) tmpRowData;
            	this.recurso.setFechaUltimoCambio(new Date());
            	this.recurso.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
            }
        }
        
        this.ver = false;
        this.headerDialog = "Editar Recurso";
		this.openPopup();

		logger.info("Saliendo de editarVersion(project:" + recurso + ")");
	}
	
	public void eliminarRecurso(ActionEvent event) {
		logger.info("Entro a deleteProject(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            if (tmpRowData instanceof Recurso) {
            	this.recurso = (Recurso) tmpRowData;
            	this.recurso.setFechaUltimoCambio(new Date());
            	this.recurso.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
            	deleteAction();
            }
        }
        
		logger.info("Saliendo de deleteProject(project:" + recurso + ")");

	}
	
	
	public String deleteAction() {
		logger.info("Entró a eliminarRecurso(ActionEvent event)");

		try {
			recursoService.delete(this.recurso);
			this.getRecursoAll();
			this.closedPopup();

		} catch (Exception e) {
			FacesUtils.addMessageError("Eliminar Recurso", "Error al eliminar el Recurso", e.getMessage());
			logger.error("Error al eliminar recurso. "+e.getMessage());
		}

		logger.info("Saliendo de deleteAction()");
		return PAGE_NAME;
	}
	
	public void verVersion(ActionEvent event) {
		logger.info("Entro a editarVersion(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            
            if (tmpRowData instanceof Recurso) {
            	this.recurso = (Recurso) tmpRowData;
            	this.recurso.setFechaUltimoCambio(new Date());
            	this.recurso.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
            }
        }
        
        this.ver = true;
        this.headerDialog = "Información del Recurso";
		this.openPopup();
	}
	
	public String cancelAction() {
		logger.info("Entró a cancelAction()");

		this.closedPopup();

		logger.info("Saliendo de cancelAction()");
		return PAGE_NAME;
	}
	
	public List<SelectItem> getListSelectItem(){
		
		// TODO
		
		this.getTipoRecursoAll();
		
		this.listSelectItem = new ArrayList<SelectItem>();
		
		if(this.listaTipoRecursos != null){
			for (TipoRecurso tipoRecurso : listaTipoRecursos) {
				this.listSelectItem.add(new SelectItem(tipoRecurso.getId(), tipoRecurso.getNombre()));
			}
		}
		                                               
		return listSelectItem;
	}

	public void popupClose() {
		this.visiblePopup = false;
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
	
	public void setNombreRecurso(String nombre) {
		if(this.recurso != null){
			this.recurso.setNombre(nombre);
		}
	}

	public String getNombreRecurso() {
		String nombre = "";
		
		if(this.recurso != null){
			nombre = this.recurso.getNombre();
		}

		return nombre;
	}
	
	public void setEstadoRecurso(Integer estado) {
		if (this.recurso != null) {
			this.recurso.setEstado(estado);
		}
	}
	
	public Integer getEstadoRecurso() {
		Integer estado = null;
		
		if (this.recurso != null) {
			estado = this.recurso.getEstado();
		}
		
		return estado;
	}
	
	public void setCostoRecurso(Integer costo) {
		if (this.recurso != null) {
			this.recurso.setCosto(costo);
		}
	}
	
	public Integer getCostoRecurso() {
		Integer costo = null;
		
		if (this.recurso != null) {
			costo = this.recurso.getCosto();
		}
		
		return costo;
	}
	
	public void setFechaCreacionRecurso(Date fecha) {
		if (this.recurso != null) {
			this.recurso.setFechaCreacion(fecha);
		}
	}
	
	public Date getFechaCreacionRecurso() {
		Date fecha = null;
		
		if (this.recurso != null) {
			fecha = this.recurso.getFechaCreacion();
		}
		
		return 	fecha;
	}
	
	public void setFechaUltimoCambioRecurso(Date fecha) {
		if (this.recurso != null) {
			this.recurso.setFechaUltimoCambio(fecha);
		}
	}
	
	public Date getFechaUltimoCambioRecurso() {
		Date fecha = null;
		
		if (this.recurso != null) {
			fecha = this.recurso.getFechaUltimoCambio();
		}
		
		return 	fecha;
	}
	
	public void setUsuarioCreacionRecurso(String usuario) {
		if(this.recurso != null){
			this.recurso.setUsuarioCreacion(usuario);
		}
	}

	public String getUsuarioCreacionRecurso() {
		String usuario = null;
		
		if(this.recurso != null){
			usuario = this.recurso.getUsuarioCreacion();
		}

		return usuario;
	}
	
	public void setUsuarioUltimoCambioRecurso(String usuario) {
		if(this.recurso != null){
			this.recurso.setUsuarioUltimoCambio(usuario);
		}
	}

	public String getUsuarioUltimoCambioRecurso() {
		String usuario = null;
		if(this.recurso != null){
			usuario = this.recurso.getUsuarioUltimoCambio();
		}

		return usuario;
	}

	public boolean isVer() {
		return ver;
	}

	public void setVer(boolean ver) {
		this.ver = ver;
	}
	
	public Recurso getRecurso() {
		return recurso;
	}

	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}

	public SelectItem getSelectItem() {
		return selectItem;
	}

	public void setSelectItem(SelectItem selectItem) {
		this.selectItem = selectItem;
	}

}
