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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import co.edu.uan.app.pmsoft.model.entity.Documento;
import co.edu.uan.app.pmsoft.model.entity.Persona;
import co.edu.uan.app.pmsoft.model.entity.PersonaRecurso;
import co.edu.uan.app.pmsoft.model.entity.Recurso;
import co.edu.uan.app.pmsoft.model.pojo.Constantes;
import co.edu.uan.app.pmsoft.model.service.PersonaRecursoService;
import co.edu.uan.app.pmsoft.model.service.PersonaService;
import co.edu.uan.app.pmsoft.model.service.RecursoService;
import co.edu.uan.app.pmsoft.util.FacesUtils;

@ManagedBean(name = PersonaRecursoBean.BEAN_NAME)
@CustomScoped(value = "#{window}")
public class PersonaRecursoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String BEAN_NAME = "personaRecursoBean";
	public static final String PAGE_NAME = "gestionar_persona_recurso";
	private static final Logger logger = LoggerFactory.getLogger(PersonaRecursoBean.class);

	@EJB
	PersonaRecursoService personaRecursoService;
	
	@EJB
	PersonaService personaService;
	
	@EJB
	RecursoService recursoService;

	private PersonaRecurso personaRecurso;
	private List<PersonaRecurso> listaPersonaRecurso;
	private List<Persona> listaPersona;
	private List<Recurso> listaRecurso;
	private List<SelectItem> listSelectItemPersona;
	private List<SelectItem> listSelectItemRecurso;
	private SelectItem selectItem;
	private String headerDialog;
	private boolean visiblePopup;
	private boolean visibleEdit;
	private boolean visibleView;
	private boolean visibleDelete;
	private boolean ver;

	@Inject
	private SessionBean sessionBean;

	@PostConstruct
	public void init() {
		this.listaPersonaRecurso = null;
		this.personaRecurso = null;
		this.headerDialog = "";
		this.visiblePopup = false;
		this.visibleEdit = false;
		this.visibleView = false;
		this.visibleDelete = false;
		this.ver = false;
		this.listaPersona = null;
		this.listaRecurso = null;
		
		this.personaRecurso = new PersonaRecurso();
		this.personaRecurso.setPersona(new Persona());
		this.personaRecurso.setRecurso(new Recurso());
		this.personaRecurso.setEstado(Constantes.ESTADO_ACTIVO);
		this.personaRecurso.setEditable(true);
		this.personaRecurso.setFechaCreacion(new Date());
		this.personaRecurso.setFechaUltimoCambio(new Date());
		this.personaRecurso.setUsuarioCreacion(this.getSessionBean().getNombreCompletoUsuario());
		this.personaRecurso.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());	
		this.personaRecurso.setVersion(1);
	}

	private void openPopup() {
		this.visiblePopup = true;
	}

	private void closedPopup() {
		this.visiblePopup = false;
	}
	
	public List<Persona> getPersonaAll() {
		this.listaPersona = personaService.getAll();
		return this.listaPersona;
	}
	
	public List<Recurso> getRecursoAll() {
		this.listaRecurso = recursoService.getAll();
		return this.listaRecurso;
	}

	public List<PersonaRecurso> getPersonaRecursoAll() {
		this.listaPersonaRecurso = personaRecursoService.getAll();
		return this.listaPersonaRecurso;
	}

	public void agregarPersonaRecurso(ActionEvent event) {
		logger.info("Entro a addPersonaRecurso(event:" + event + ")");
		
		this.personaRecurso = new PersonaRecurso();
		this.personaRecurso.setPersona(new Persona());
		this.personaRecurso.setRecurso(new Recurso());
		this.personaRecurso.setEstado(Constantes.ESTADO_ACTIVO);
		this.personaRecurso.setEditable(true);
		this.personaRecurso.setFechaCreacion(new Date());
		this.personaRecurso.setFechaUltimoCambio(new Date());
		this.personaRecurso.setUsuarioCreacion(this.getSessionBean().getNombreCompletoUsuario());
		this.personaRecurso.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());	
		this.personaRecurso.setVersion(1);
		this.ver = false;

		this.headerDialog = "Nuevo PersonaRecurso";
		this.openPopup();
		this.visibleEdit = false;
		this.visibleView = true;
		this.visibleDelete = false;
		logger.info(this.personaRecurso.getUsuarioUltimoCambio());
		logger.info("Saliendo de addPersonaRecurso(PersonaRecurso:" + personaRecurso + ")");
	}

	public String saveAction(ActionEvent event) {
		logger.info("Entró a saveAction(ActionEvent event)");
        
		if (validateSaveAction(event)) {
			try {
				personaRecursoService.save(this.personaRecurso);
				this.getPersonaRecursoAll();
				this.closedPopup();

			} catch (Exception e) {
				FacesUtils.addMessageError("Guardar PersonaRecurso", "Error al guardar el PersonaRecurso", e.getMessage());
				logger.error("Error al guardar PersonaRecurso. "+e.getMessage());
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

		if (this.personaRecurso == null) {
			detail = "No existe un objeto PersonaRecurso inicializado";
			valid = false;

		} else if (this.personaRecurso.getPersona() == null || this.personaRecurso.getPersona().getId() == null) {
			detail = "Se debe ingresar la persona";	
			valid = false;
		} else if (this.personaRecurso.getRecurso() == null || this.personaRecurso.getRecurso().getId() == null) {
			detail = "Se debe ingresar el recurso";	
			valid = false;
		} 	

		if (!valid) {			
			FacesUtils.addMessageError("Guardar PersonaRecurso", "Error al guardar el PersonaRecurso", detail);
			logger.error("Error validando el PersonaRecurso a guardar. "+detail);
			
			UIComponent component = event.getComponent();
	        FacesMessage facesMessage = new FacesMessage((FacesMessage.Severity) FacesMessage.VALUES.get(2), detail, detail);
	        facesContext.addMessage(component.getClientId(), facesMessage);
		}
		logger.info("Saliendo de validateSaveAction()");
		return valid;
	}

	public void editarPersonaRecurso(ActionEvent event) {
		logger.info("Entro a editPersonaRecurso(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            
            if (tmpRowData instanceof Documento) {
            	this.personaRecurso = (PersonaRecurso) tmpRowData;
            	this.personaRecurso.setFechaUltimoCambio(new Date());
            	this.personaRecurso.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
            }
            
        }

		this.headerDialog = "Editar PersonaRecurso";
		this.openPopup();
		this.visibleEdit = false;
		this.visibleView = true;
		this.visibleDelete = false;

		logger.info("Saliendo de editPersonaRecurso(PersonaRecurso:" + personaRecurso + ")");

	}

	public void verPersonaRecurso(ActionEvent event) {
		logger.info("Entro a viewPersonaRecurso(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            if (tmpRowData instanceof Documento) {
            	this.personaRecurso = (PersonaRecurso) tmpRowData;
            }
        }
        
        this.ver = true;
        this.visibleEdit = true;
        this.visibleView = false;
        this.visibleDelete = false;
		this.headerDialog = "Información del PersonaRecurso";
		this.openPopup();

		logger.info("Saliendo de viewPersonaRecurso(personaRecurso:" + personaRecurso + ")");

	}

	public void popupClose() {
		this.visiblePopup = false;
	}
	
	public void eliminarPersonaRecurso(ActionEvent event) {
		logger.info("Entro a deletePersonaRecurso(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            if (tmpRowData instanceof PersonaRecurso) {
            	this.personaRecurso = (PersonaRecurso) tmpRowData;
            	this.personaRecurso.setFechaUltimoCambio(new Date());
            	this.personaRecurso.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
            }
        }
        
        this.closedPopup();
        
		logger.info("Saliendo de deletePersonaRecurso(PersonaRecurso:" + personaRecurso + ")");

	}

	public String deleteAction(ActionEvent event) {
		logger.info("Entró a deleteAction(ActionEvent event)");

		try {
			personaRecursoService.delete(this.personaRecurso);
			this.getPersonaRecursoAll();
			this.closedPopup();

		} catch (Exception e) {
			FacesUtils.addMessageError("Eliminar PersonaRecurso", "Error al eliminar el PersonaRecurso", e.getMessage());
			logger.error("Error al eliminar PersonaRecurso. "+e.getMessage());
		}

		logger.info("Saliendo de deleteAction()");
		return PAGE_NAME;
	}

	public String viewAction(ActionEvent event) {
		logger.info("Entró a viewAction(ActionEvent event)");

		if (validateSaveAction(event)) {

			try {
				personaRecursoService.delete(this.personaRecurso);
				this.getPersonaRecursoAll();
				this.closedPopup();

			} catch (Exception e) {
				FacesUtils.addMessageError("Ver PersonaRecurso", "Error al ver el PersonaRecurso", e.getMessage());
				logger.error("Error al ver PersonaRecurso. "+e.getMessage());
			}
		}

		logger.info("Saliendo de viewAction()");
		return PAGE_NAME;
	}
	

	public List<SelectItem> getListSelectItemPersona(){
		
		this.getPersonaAll();

		this.listSelectItemPersona = new ArrayList<SelectItem>();

		if(this.listaPersona != null){
			for (Persona per : listaPersona) {
				this.listSelectItemPersona.add(new SelectItem(per.getId(), per.getNombre()));
			}
		}

		return listSelectItemPersona;
	}
	
public List<SelectItem> getListSelectItemRecurso(){
		
		this.getRecursoAll();

		this.listSelectItemRecurso = new ArrayList<SelectItem>();

		if(this.listaRecurso != null){
			for (Recurso rec : listaRecurso) {
				this.listSelectItemRecurso.add(new SelectItem(rec.getId(), rec.getNombre()));
			}
		}

		return listSelectItemRecurso;
	}

	public String cancelAction() {
		logger.info("Entró a cancelAction()");

		this.closedPopup();

		logger.info("Saliendo de cancelAction()");
		return PAGE_NAME;
	}
	
	public boolean isVer() {
		return ver;
	}

	public void setVer(boolean ver) {
		this.ver = ver;
	}

	public String getHeaderDialog() {
		return this.headerDialog;
	}

	public void setHeaderDialog(String headerDialog) {
		this.headerDialog = headerDialog;
	}	

	public PersonaRecurso getPersonaRecurso() {

		logger.info("this.PersonaRecurso = " + this.personaRecurso);
		if (this.personaRecurso != null)
			logger.info("this.personaRecurso.getPersona() = " + this.personaRecurso.getPersona());

		return this.personaRecurso;
	}

	public void setPersonaRecurso(PersonaRecurso perRec) {
		this.personaRecurso = perRec;
	}
	
	public Date getFechaCreacionPersonaRecurso() {
		Date fechaCreacion = null;
		if(this.personaRecurso != null){
			fechaCreacion = this.personaRecurso.getFechaCreacion();
		}

		return fechaCreacion;
	}

	public void setFechaCreacionPersonaRecurso(Date fechaCreacion) {
		if(this.personaRecurso != null){
			this.personaRecurso.setFechaCreacion(fechaCreacion);
		}
	}

	public Date getFechaUltimoCambioPersonaRecurso() {
		Date fechaUltimoCambio = null;
		if(this.personaRecurso != null){
			fechaUltimoCambio = this.personaRecurso.getFechaUltimoCambio();
		}

		return fechaUltimoCambio;
	}

	public void setFechaUltimoCambioPersonaRecurso(Date fechaUltimoCambio) {
		if(this.personaRecurso != null){
			this.personaRecurso.setFechaUltimoCambio(fechaUltimoCambio);
		}
	}

	public String getUsuarioCreacionPersonaRecurso() {
		String user = null;
		if(this.personaRecurso != null){
			user = this.personaRecurso.getUsuarioCreacion();
		}

		return user;
	}

	public void setUsuarioCreacionPersonaRecurso(String usuarioCreacion) {
		if(this.personaRecurso != null){
			this.personaRecurso.setUsuarioCreacion(usuarioCreacion);
		}
	}

	public String getUsuarioUltimoCambioPersonaRecurso() {
		String user = null;
		if(this.personaRecurso != null){
			user = this.personaRecurso.getUsuarioUltimoCambio();
		}

		return user;
	}

	public void setUsuarioUltimoCambioPersonaRecurso(String usuarioUltimoCambio) {
		if(this.personaRecurso != null){
			this.personaRecurso.setUsuarioUltimoCambio(usuarioUltimoCambio);
		}
	}
	
	public Integer getEstadoPersonaRecurso() {
		Integer estado = null;
		if(this.personaRecurso != null){
			estado = this.personaRecurso.getEstado();
		}

		return estado;
	}

	public void setEstadoPersonaRecurso(Integer estado) {
		if(this.personaRecurso != null){
			this.personaRecurso.setEstado(estado);
		}
	}	

	public boolean isVisiblePopup() {
		return visiblePopup;
	}

	public void setVisiblePopup(boolean visiblePopup) {
		this.visiblePopup = visiblePopup;
	}
	
    public void closeFAjax(AjaxBehaviorEvent event){
        this.visiblePopup = false;
        this.visibleEdit = false;
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
}
