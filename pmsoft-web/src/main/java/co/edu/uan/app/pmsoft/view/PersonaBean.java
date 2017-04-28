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

import co.edu.uan.app.pmsoft.model.entity.Persona;
import co.edu.uan.app.pmsoft.model.pojo.Constantes;
import co.edu.uan.app.pmsoft.model.service.PersonaService;
import co.edu.uan.app.pmsoft.util.FacesUtils;

@ManagedBean(name = PersonaBean.BEAN_NAME)
@CustomScoped(value = "#{window}")
public class PersonaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String BEAN_NAME = "personaBean";
	public static final String PAGE_NAME = "gestionar_personas";
	private static final Logger logger = LoggerFactory.getLogger(PersonaBean.class);

	@EJB
	PersonaService personaService;

	private Persona persona;
	private List<Persona> listaPersonas;
	private List<SelectItem> listSelectItem;
	private SelectItem selectItem;
	private String headerDialog;
	private boolean visiblePopup;
	private boolean visibleEdit;
	private boolean visibleView;
	private boolean visibleDelete;


	@Inject
	private SessionBean sessionBean;

	@PostConstruct
	public void init() {
		this.listaPersonas = null;
		this.persona = null;
		this.headerDialog = "";
		this.visiblePopup = false;
		this.visibleEdit = false;
		this.visibleView = false;
		this.visibleDelete = false;
	}

	private void openPopup() {
		this.visiblePopup = true;
	}

	private void closedPopup() {
		this.visiblePopup = false;
	}

	public List<Persona> getPersonaAll() {
		this.listaPersonas = personaService.getAll();
		return this.listaPersonas;
	}

	public void agregarPersona(ActionEvent event) {
		logger.info("Entro a addPersona(event:" + event + ")");

		this.persona = new Persona();
		this.persona.setNombre("");
		this.persona.setCostoHora(0);
		this.persona.setCargo("");
		this.persona.setTelefono("");
		this.persona.setDireccion("");
		this.persona.setEmail("");
		this.persona.setTipoDocumento("");
		this.persona.setIdentificacion("");
		this.persona.setNombreContactoEmergencia("");
		this.persona.setTelefonoContactoEmergencia("");
		this.persona.setUsuarioCreacion(this.getSessionBean().getNombreCompletoUsuario());
		this.persona.setFechaCreacion(new Date());
		this.persona.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
		this.persona.setFechaUltimoCambio(new Date());
		this.persona.setEstado(Constantes.ESTADO_ACTIVO);
		this.persona.setEditable(true);

		this.headerDialog = "Nueva Persona";
		this.openPopup();
		this.visibleEdit = false;
		this.visibleView = true;
		this.visibleDelete = false;
		logger.info("Saliendo de addPersona(rol:" + persona + ")");
	}

	public String saveAction(ActionEvent event) {
		logger.info("Entró a saveAction(ActionEvent event)");
        
		if (validateSaveAction(event)) {
			try {
				personaService.save(this.persona);
				this.getPersonaAll();
				this.closedPopup();

			} catch (Exception e) {
				FacesUtils.addMessageError("Guardar Persona", "Error al guardar la persona", e.getMessage());
				logger.error("Error al guardar persona. "+e.getMessage());
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

		if (this.persona == null) {
			detail = "No existe un objeto PERSONA inicializado";
			valid = false;

		} else if (StringUtils.isBlank(this.persona.getNombre())) {
			detail = "Se debe ingresar el nombre de la persona";	
			valid = false;
			
		} else if (persona.getCostoHora() == 0) {
			detail = "Se debe ingresar un costo de hora";
			valid = false;
			
		} else if (StringUtils.isBlank(this.persona.getCargo())) {
			detail = "Se debe ingresar un cargo";
			valid = false;
			
		} else if (StringUtils.isBlank(this.persona.getTelefono())) {
			detail = "Se debe ingresar un teléfono";
			valid = false;
			
		} else if (StringUtils.isBlank(persona.getDireccion())) {
			detail = "Se debe ingresar una dirección";
			valid = false;
			
		} else if (StringUtils.isBlank(persona.getEmail())) {
			detail = "Se debe ingresar un correo electrónico";
			valid = false;
			
		} else if (StringUtils.isBlank(persona.getTipoDocumento())) {
			detail = "Se debe ingresar un tipo de documento";
			valid = false;
			
		} else if (StringUtils.isBlank(persona.getIdentificacion())) {
			detail = "Se debe ingresar una identificación";
			valid = false;
			
		} else if (StringUtils.isBlank(persona.getIdentificacion())) {
			detail = "Se debe ingresar una identificación";
			valid = false;
			
		} else if (StringUtils.isBlank(persona.getNombreContactoEmergencia())) {
			detail = "Se debe ingresar un nombre de contacto de emergencia";
			valid = false;
			
		} else if (StringUtils.isBlank(persona.getTelefonoContactoEmergencia())) {
			detail = "Se debe ingresar un teléfono de contacto de emergencia";
			valid = false;
			
		}

		if (!valid) {			
			FacesUtils.addMessageError("Guardar Persona", "Error al guardar la persona", detail);
			logger.error("Error validando la persona a guardar. "+detail);
			
			UIComponent component = event.getComponent();
	        FacesMessage facesMessage = new FacesMessage((FacesMessage.Severity) FacesMessage.VALUES.get(2), detail, detail);
	        facesContext.addMessage(component.getClientId(), facesMessage);
		}
		logger.info("Saliendo de validateSaveAction()");
		return valid;
	}

	public void editarPersona(ActionEvent event) {
		logger.info("Entro a editPersona(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            
            if (tmpRowData instanceof Persona) {
            	this.persona = (Persona) tmpRowData;
            	this.persona.setFechaUltimoCambio(new Date());
            	this.persona.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
            }
            
        }

		this.headerDialog = "Editar Persona";
		this.openPopup();
		this.visibleEdit = false;
		this.visibleView = true;
		this.visibleDelete = false;

		logger.info("Saliendo de editPersona(persona:" + persona + ")");

	}

	public void eliminarPersona(ActionEvent event) {
		logger.info("Entro a deletePersona(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            if (tmpRowData instanceof Persona) {
            	this.persona = (Persona) tmpRowData;
            	this.persona.setFechaUltimoCambio(new Date());
            	this.persona.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
            }
        }
        
        
        this.visibleEdit = false;
        this.visibleView = true;
        this.visibleDelete = true;
        this.headerDialog = "Eliminar Persona";
        this.openPopup();
        
		logger.info("Saliendo de deletePersona(persona:" + persona + ")");

	}

	public void verPersona(ActionEvent event) {
		logger.info("Entro a viewPersona(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            if (tmpRowData instanceof Persona) {
            	this.persona = (Persona) tmpRowData;
            }
        }

        this.visibleEdit = true;
        this.visibleView = false;
        this.visibleDelete = false;
		this.headerDialog = "Información de la persona";
		this.openPopup();

		logger.info("Saliendo de viewPersona(Persona:" + persona + ")");

	}

	public void popupClose() {
		this.visiblePopup = false;
	}

	public String deleteAction(ActionEvent event) {
		logger.info("Entró a deleteAction(ActionEvent event)");

		try {
			personaService.delete(this.persona);
			this.getPersonaAll();
			this.closedPopup();

		} catch (Exception e) {
			FacesUtils.addMessageError("Eliminar Persona", "Error al eliminar la persona", e.getMessage());
			logger.error("Error al eliminar persona. "+e.getMessage());
		}


		logger.info("Saliendo de deleteAction()");
		return PAGE_NAME;
	}

	public String viewAction(ActionEvent event) {
		logger.info("Entró a viewAction(ActionEvent event)");

		if (validateSaveAction(event)) {

			try {
				personaService.delete(this.persona);
				this.getPersonaAll();
				this.closedPopup();

			} catch (Exception e) {
				FacesUtils.addMessageError("Ver Persona", "Error al ver la persona", e.getMessage());
				logger.error("Error al ver persona. "+e.getMessage());
			}
		}

		logger.info("Saliendo de viewAction()");
		return PAGE_NAME;
	}

	public List<SelectItem> getListSelectItem(){

		this.listSelectItem = new ArrayList<SelectItem>();

		if(this.listaPersonas != null){
			for (Persona persona : listaPersonas) {
				this.listSelectItem.add(new SelectItem(persona.getId(), persona.getNombre()));
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

	public Persona getPersona() {
		logger.info("this.persona = " + this.persona);
		if (this.persona != null)
			logger.info("this.persona.getNombre() = " + this.persona.getNombre());

		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public boolean isVisiblePopup() {
		return visiblePopup;
	}

	public void setVisiblePopup(boolean visiblePopup) {
		this.visiblePopup = visiblePopup;
	}
	
	public String getNombrePersona(){
		String nombre = "";
		if(this.persona != null){
			nombre = this.persona.getNombre();
		}

		return nombre;
	}

	public void setNombrePersona(String nombre){
		if(this.persona != null){
			this.persona.setNombre(nombre);
		}
	}	

	/*
     *  if closing with a client side api, ensure a listener is used to
     *  update the visible value on the server
     */
    public void closeFAjax(AjaxBehaviorEvent event){
        this.visiblePopup = false;
        this.visibleEdit = false;
    }   

    public int getCostoHoraPersona(){
		int costoHora = 0;
		if(this.persona != null){
			costoHora = this.persona.getCostoHora();
		}

		return costoHora;
	}

	public void setCostoHoraPersona(int costoHora) {
		if(this.persona != null){
			this.persona.setCostoHora(costoHora);
		}
	}
	
	public String getCargoPersona(){
		String cargo = "";
		if(this.persona != null){
			cargo = this.persona.getCargo();
		}

		return cargo;
	}

	public void setCargoPersona(String cargo){
		if(this.persona != null){
			this.persona.setCargo(cargo);
		}
	}
	
	public String getTelefonoPersona(){
		String telefono = "";
		if(this.persona != null){
			telefono = this.persona.getTelefono();
		}

		return telefono;
	}

	public void setTelefonoPersona(String telefono){
		if(this.persona != null){
			this.persona.setTelefono(telefono);
		}
	}
	
	public String getDireccionPersona(){
		String direccion = "";
		if(this.persona != null){
			direccion = this.persona.getDireccion();
		}

		return direccion;
	}

	public void setDireccionPersona(String direccion){
		if(this.persona != null){
			this.persona.setDireccion(direccion);
		}
	}
	
	public String getEmailPersona(){
		String email = "";
		if(this.persona != null){
			email = this.persona.getEmail();
		}

		return email;
	}

	public void setEmailPersona(String email){
		if(this.persona != null){
			this.persona.setEmail(email);
		}
	}
	
	public String getTipoDocumentoPersona(){
		String tipo = "";
		if(this.persona != null){
			tipo = this.persona.getTipoDocumento();
		}

		return tipo;
	}

	public void setTipoDocumentoPersona(String tipo){
		if(this.persona != null){
			this.persona.setTipoDocumento(tipo);
		}
	}
	
	public String getIdentificacionPersona(){
		String identificacion = "";
		if(this.persona != null){
			identificacion = this.persona.getIdentificacion();
		}

		return identificacion;
	}

	public void setIdentificacionPersona(String identificacion){
		if(this.persona != null){
			this.persona.setIdentificacion(identificacion);
		}
	}
	
	public String getNombreContactoEmergenciaPersona(){
		String nombreContacto = "";
		if(this.persona != null){
			nombreContacto = this.persona.getNombreContactoEmergencia();
		}

		return nombreContacto;
	}

	public void setNombreContactoEmergenciaPersona(String nombreContacto){
		if(this.persona != null){
			this.persona.setNombreContactoEmergencia(nombreContacto);
		}
	}
	
	public String getTelefonoContactoEmergenciaPersona(){
		String telefonoContacto = "";
		if(this.persona != null){
			telefonoContacto = this.persona.getTelefonoContactoEmergencia();
		}

		return telefonoContacto;
	}

	public void setTelefonoContactoEmergenciaPersona(String telefonoContacto){
		if(this.persona != null){
			this.persona.setTelefonoContactoEmergencia(telefonoContacto);
		}
	}
	
	public String getUsuarioCreacionPersona() {
		String user = null;
		if(this.persona != null){
			user = this.persona.getUsuarioCreacion();
		}

		return user;
	}

	public void setUsuarioCreacionPersona(String usuarioCreacion) {
		if(this.persona != null){
			this.persona.setUsuarioCreacion(usuarioCreacion);
		}
	}
	
	public Date getFechaCreacionPersona() {
		Date fechaCreacion = null;
		if(this.persona != null){
			fechaCreacion = this.persona.getFechaCreacion();
		}

		return fechaCreacion;
	}
	
	public void setFechaCreacionPersona(Date fechaCreacion) {
		if(this.persona != null){
			this.persona.setFechaCreacion(fechaCreacion);
		}
	}

	public String getUsuarioUltimoCambioPersona() {
		String user = null;
		if(this.persona != null){
			user = this.persona.getUsuarioUltimoCambio();
		}

		return user;
	}

	public void setUsuarioUltimoCambioPersona(String usuarioUltimoCambio) {
		if(this.persona != null){
			this.persona.setUsuarioUltimoCambio(usuarioUltimoCambio);
		}
	}
	
	public Date getFechaUltimoCambioPersona() {
		Date fechaUltimoCambio = null;
		if(this.persona != null){
			fechaUltimoCambio = this.persona.getFechaUltimoCambio();
		}

		return fechaUltimoCambio;
	}

	public void setFechaUltimoCambioPersona(Date fechaUltimoCambio) {
		if(this.persona != null){
			this.persona.setFechaUltimoCambio(fechaUltimoCambio);
		}
	}
	
	public Integer getEstadoPersona() {
		Integer estado = null;
		if(this.persona != null){
			estado = this.persona.getEstado();
		}

		return estado;
	}
	
	public void setEstadoPersona(Integer estado) {
		if(this.persona != null){
			this.persona.setEstado(estado);
		}
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
