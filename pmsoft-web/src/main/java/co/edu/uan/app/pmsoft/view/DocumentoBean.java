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

import co.edu.uan.app.pmsoft.model.entity.Documento;
import co.edu.uan.app.pmsoft.model.pojo.Constantes;
import co.edu.uan.app.pmsoft.model.service.DocumentoService;
import co.edu.uan.app.pmsoft.util.FacesUtils;

@ManagedBean(name = DocumentoBean.BEAN_NAME)
@CustomScoped(value = "#{window}")
public class DocumentoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String BEAN_NAME = "documentoBean";
	public static final String PAGE_NAME = "gestionar_documentos";
	private static final Logger logger = LoggerFactory.getLogger(DocumentoBean.class);

	@EJB
	DocumentoService documentoService;

	private Documento documento;
	private List<Documento> listaDocumento;
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
		this.listaDocumento = null;
		this.documento = null;
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

	public List<Documento> getDocumentoAll() {
		this.listaDocumento = documentoService.getAll();
		return this.listaDocumento;
	}

	public void agregarDocumento(ActionEvent event) {
		logger.info("Entro a addDocumento(event:" + event + ")");

		this.documento = new Documento();
		this.documento.setVersion(1);
		this.documento.setRuta("");
		this.documento.setNombre("");
		this.documento.setActividad(null);
		this.documento.setFechaCreacion(new Date());
		this.documento.setUsuarioCreacion(this.getSessionBean().getNombreCompletoUsuario());
		this.documento.setFechaCreacion(new Date());
		this.documento.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
		this.documento.setFechaUltimoCambio(new Date());
		this.documento.setEstado(Constantes.ESTADO_ACTIVO);
		this.documento.setEditable(true);

		this.headerDialog = "Nuevo documento";
		this.openPopup();
		this.visibleEdit = false;
		this.visibleView = true;
		this.visibleDelete = false;
		logger.info(this.documento.getUsuarioUltimoCambio());
		logger.info("Saliendo de addDocumento(rol:" + documento + ")");
	}

	public String saveAction(ActionEvent event) {
		logger.info("Entró a saveAction(ActionEvent event)");
        
		if (validateSaveAction(event)) {
			try {
				this.documentoService.save(this.documento);
				this.closedPopup();					
				
			} catch (Exception e) {
				FacesUtils.addMessageError("Guardar Documento", "Error al guardar el documento", e.getMessage());
				logger.error("Error al guardar documento. "+e.getMessage());
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

		if (this.documento == null) {
			detail = "No existe un objeto documento inicializado";
			valid = false;

		} else if (StringUtils.isBlank(this.documento.getNombre())) {
			detail = "Se debe ingresar el nombre del documento";	
			valid = false;
			
		} else if (StringUtils.isBlank(this.documento.getRuta())) {
			detail = "Se debe ingresar la ruta del documento";	
			valid = false;
			
		} else if (documento.getActividad() == null) {
			detail = "Se debe ingresar la actividad del documento";	
			valid = false;
			
		} 

		if (!valid) {			
			FacesUtils.addMessageError("Guardar documento", "Error al guardar documento", detail);
			logger.error("Error validando el documento a guardar. "+detail);
			
			UIComponent component = event.getComponent();
	        FacesMessage facesMessage = new FacesMessage((FacesMessage.Severity) FacesMessage.VALUES.get(2), detail, detail);
	        facesContext.addMessage(component.getClientId(), facesMessage);
		}
		logger.info("Saliendo de validateSaveAction()");
		return valid;
	}
	
	public String deleteAction(ActionEvent event) {
		logger.info("Entró a deleteAction(ActionEvent event)");

		try {
			documentoService.delete(this.documento);
			this.getDocumentoAll();
			this.closedPopup();

		} catch (Exception e) {
			FacesUtils.addMessageError("Eliminar Documento", "Error al eliminar el documento", e.getMessage());
			logger.error("Error al eliminar documento. "+e.getMessage());
		}


		logger.info("Saliendo de deleteAction()");
		return PAGE_NAME;
	}

	public void editarDocumento(ActionEvent event) {
		logger.info("Entro a editDocumento(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            
            if (tmpRowData instanceof Documento) {
            	this.documento = (Documento) tmpRowData;
            	this.documento.setFechaUltimoCambio(new Date());
            	this.documento.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
            }
            
        }

		this.headerDialog = "Editar documento";
		this.openPopup();
		this.visibleEdit = false;
		this.visibleView = true;
		this.visibleDelete = false;

		logger.info("Saliendo de editDocumento(Documento:" + documento + ")");

	}
	
	public void eliminarDocumento(ActionEvent event) {
		logger.info("Entro a deleteDocumento(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            if (tmpRowData instanceof Documento) {
            	this.documento = (Documento) tmpRowData;
            	this.documento.setFechaUltimoCambio(new Date());
            	this.documento.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
            }
        }
        
        
        this.visibleEdit = false;
        this.visibleView = true;
        this.visibleDelete = true;
        this.headerDialog = "Eliminar Documento";
        this.openPopup();
        
		logger.info("Saliendo de deleteDocumento(documento:" + documento + ")");

	}


	public void verDocumento(ActionEvent event) {
		logger.info("Entro a viewDocumento(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            if (tmpRowData instanceof Documento) {
            	this.documento = (Documento) tmpRowData;
            }
        }

        this.visibleEdit = true;
        this.visibleView = false;
        this.visibleDelete = false;
		this.headerDialog = "Información del tipo de Documento";
		this.openPopup();

		logger.info("Saliendo de viewDocumento(tipoDocumento:" + documento + ")");

	}

	public void popupClose() {
		this.visiblePopup = false;
	}


	public String viewAction(ActionEvent event) {
		logger.info("Entró a viewAction(ActionEvent event)");

		if (validateSaveAction(event)) {

			try {
				documentoService.delete(this.documento);
				this.getDocumentoAll();
				this.closedPopup();

			} catch (Exception e) {
				FacesUtils.addMessageError("Ver Documento", "Error al ver Documento", e.getMessage());
				logger.error("Error al ver Documento. "+e.getMessage());
			}
		}

		logger.info("Saliendo de viewAction()");
		return PAGE_NAME;
	}

	public List<SelectItem> getListSelectItem(){

		this.listSelectItem = new ArrayList<SelectItem>();

		if(this.listaDocumento != null){
			for (Documento documento : listaDocumento) {
				this.listSelectItem.add(new SelectItem(documento.getId(), documento.getNombre()));
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

	public Documento getDocumento() {
		logger.info("this.tipoDocumento = " + this.documento);
		if (this.documento != null)
			logger.info("this.documento.getNombre() = " + this.documento.getNombre());

		return this.documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public boolean isVisiblePopup() {
		return visiblePopup;
	}

	public void setVisiblePopup(boolean visiblePopup) {
		this.visiblePopup = visiblePopup;
	}
	
	public String getRutaDocumento(){
		String ruta = "";
		if(this.documento != null){
			ruta = this.documento.getRuta();
		}

		return ruta;
	}

	public void setRutaDocumento(String ruta){
		if(this.documento != null){
			this.documento.setRuta(ruta);
		}
	}
	
	public String getNombreDocumento(){
		String nombre = "";
		if(this.documento != null){
			nombre = this.documento.getNombre();
		}

		return nombre;
	}
	
	public void setNombreDocumento(String nombre){
		if(this.documento != null){
			this.documento.setNombre(nombre);
		}
	}
	
	public Long getActividadDocumento(){
		Long actividad = null;		
		if(this.documento != null){
			actividad = this.documento.getActividad();
		}

		return actividad;
	}

	public void setActividadDocumento(Long actividad){
		if(this.documento != null){
			this.documento.setActividad(actividad);
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

    public Integer getEstadoDocumento() {
		Integer estado = null;
		if(this.documento != null){
			estado = this.documento.getEstado();
		}

		return estado;
	}

	public void setEstadoDocumento(Integer estado) {
		if(this.documento != null){
			this.documento.setEstado(estado);
		}
	}
	
	public Date getFechaCreacionDocumento() {
		Date fechaCreacion = null;
		if(this.documento != null){
			fechaCreacion = this.documento.getFechaCreacion();
		}

		return fechaCreacion;
	}

	public void setFechaCreacionDocumento(Date fechaCreacion) {
		if(this.documento != null){
			this.documento.setFechaCreacion(fechaCreacion);
		}
	}

	public Date getFechaUltimoCambioDocumento() {
		Date fechaUltimoCambio = null;
		if(this.documento != null){
			fechaUltimoCambio = this.documento.getFechaUltimoCambio();
		}

		return fechaUltimoCambio;
	}

	public void setFechaUltimoCambioDocumento(Date fechaUltimoCambio) {
		if(this.documento != null){
			this.documento.setFechaUltimoCambio(fechaUltimoCambio);
		}
	}

	public String getUsuarioCreacionDocumento() {
		String user = null;
		if(this.documento != null){
			user = this.documento.getUsuarioCreacion();
		}

		return user;
	}

	public void setUsuarioCreacionDocumento(String usuarioCreacion) {
		if(this.documento != null){
			this.documento.setUsuarioCreacion(usuarioCreacion);
		}
	}

	public String getUsuarioUltimoCambioDocumento() {
		String user = null;
		if(this.documento != null){
			user = this.documento.getUsuarioUltimoCambio();
		}

		return user;
	}

	public void setUsuarioUltimoCambioDocumento(String usuarioUltimoCambio) {
		if(this.documento != null){
			this.documento.setUsuarioUltimoCambio(usuarioUltimoCambio);
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