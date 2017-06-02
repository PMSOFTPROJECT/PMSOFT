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
import co.edu.uan.app.pmsoft.model.entity.TipoDocumento;
import co.edu.uan.app.pmsoft.model.pojo.Constantes;
import co.edu.uan.app.pmsoft.model.service.TipoDocumentoService;
import co.edu.uan.app.pmsoft.util.FacesUtils;

@ManagedBean(name = TipoDocumentoBean.BEAN_NAME)
@CustomScoped(value = "#{window}")
public class TipoDocumentoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String BEAN_NAME = "tipoDocumentoBean";
	public static final String PAGE_NAME = "gestionar_tipo_documentos";
	private static final Logger logger = LoggerFactory.getLogger(TipoDocumentoBean.class);

	@EJB
	TipoDocumentoService tipoDocumentoService;

	private TipoDocumento tipoDocumento;
	private List<TipoDocumento> listaTipoDocumento;
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
		this.listaTipoDocumento = null;
		this.tipoDocumento = null;
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

	public List<TipoDocumento> getTipoDocumentoAll() {
		this.listaTipoDocumento = tipoDocumentoService.getAll();
		return this.listaTipoDocumento;
	}

	public void agregarTipoDocumento(ActionEvent event) {
		logger.info("Entro a addtipoDocumento(event:" + event + ")");

		this.tipoDocumento = new TipoDocumento();
		this.tipoDocumento.setVersion(1);
		this.tipoDocumento.setNombre("");
		this.tipoDocumento.setUsuarioCreacion(this.getSessionBean().getNombreCompletoUsuario());
		this.tipoDocumento.setFechaCreacion(new Date());
		this.tipoDocumento.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
		this.tipoDocumento.setFechaUltimoCambio(new Date());
		this.tipoDocumento.setEstado(Constantes.ESTADO_ACTIVO);
		this.tipoDocumento.setEditable(true);

		this.headerDialog = "Nuevo tipo de documento";
		this.openPopup();
		this.visibleEdit = false;
		this.visibleView = true;
		this.visibleDelete = false;
		logger.info(this.tipoDocumento.getUsuarioUltimoCambio());
		logger.info("Saliendo de addtipoDocumento(rol:" + tipoDocumento + ")");
	}

	public String saveAction(ActionEvent event) {
		logger.info("Entró a saveAction(ActionEvent event)");

		if (validateSaveAction(event)) {
			try {

				if(tipoDocumentoService.findDocumento(getNombreTipoDocumento()) == true){
				tipoDocumentoService.save(this.tipoDocumento);
				this.getTipoDocumentoAll();
				FacesUtils.addMessageInfo(event, "Almacenado con éxito","");
				this.closedPopup();}
				else{
					FacesUtils.addMessageWarn(event, "El tipo de documento ya fue agregado", "");
				}

			} catch (Exception e) {
				FacesUtils.addMessageError(event, "Error al guardar el tipo de documento", e.getMessage());
				logger.error("Error al guardar tipo de documento. "+e.getMessage());
			}
		}

		logger.info("Saliendo de saveAction()");
		return PAGE_NAME;
	}

	private boolean validateSaveAction(ActionEvent event) {
		logger.info("Entró a validateSaveAction()");

		boolean valid = true;
		String detail = "";

		if (this.tipoDocumento == null) {
			 FacesUtils.addMessageError(event, "No existe un objeto tipo de documento inicializado","");
			 valid = false;
		} if (StringUtils.isBlank(this.tipoDocumento.getNombre())) {
			 FacesUtils.addMessageError(event, "Se debe ingresar el nombre del tipo de documento","");
			 valid = false;
		}

		if (!valid) {
			FacesUtils.addMessageError(event, "Error al guardar el tipo de documento", detail);
			logger.error("Error validando el tipo de documento a guardar. "+detail);

		}
		logger.info("Saliendo de validateSaveAction()");
		return valid;
	}

	public void editarTipoDocumento(ActionEvent event) {
		logger.info("Entro a edittipoDocumento(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();

            if (tmpRowData instanceof TipoDocumento) {
            	this.tipoDocumento = (TipoDocumento) tmpRowData;
            	this.tipoDocumento.setFechaUltimoCambio(new Date());
            	this.tipoDocumento.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
            }

        }

		this.headerDialog = "Editar tipo de documento";
		this.openPopup();
		this.visibleEdit = false;
		this.visibleView = true;
		this.visibleDelete = false;

		logger.info("Saliendo de editTipoDocumento(tipoDocumento:" + tipoDocumento + ")");

	}


	public void verTipoDocumento(ActionEvent event) {
		logger.info("Entro a viewtipoDocumento(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            if (tmpRowData instanceof TipoDocumento) {
            	this.tipoDocumento = (TipoDocumento) tmpRowData;
            }
        }

        this.visibleEdit = true;
        this.visibleView = false;
        this.visibleDelete = false;
		this.headerDialog = "Información del tipo de Documento";
		this.openPopup();

		logger.info("Saliendo de viewTipoDocumento(tipoDocumento:" + tipoDocumento + ")");

	}

	public void popupClose() {
		this.visiblePopup = false;
	}
	
	
	 public void eliminarTipoDocumento(ActionEvent event) {
			logger.info("Entro a deleteProject(event:" + event + ")");

	        UIComponent tmpComponent = event.getComponent();
	        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
	            tmpComponent = tmpComponent.getParent();
	        }
	        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
	            Object tmpRowData = ((UIData) tmpComponent).getRowData();
	            if (tmpRowData instanceof TipoDocumento) {
	            	this.tipoDocumento = (TipoDocumento) tmpRowData;
	            	this.tipoDocumento.setFechaUltimoCambio(new Date());
	            	this.tipoDocumento.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
	            }
	        }

	        this.closedPopup();

			logger.info("Saliendo de deleteProject(project:" + tipoDocumento + ")");

		}

		public String deleteAction(ActionEvent event) {
			logger.info("Entró a deleteAction(ActionEvent event)");

			try {
				tipoDocumentoService.delete(this.tipoDocumento);
				this.getTipoDocumentoAll();
				FacesUtils.addMessageInfo(event, "El tipo de documento fue cambiado a estado de inactividad","");
				this.closedPopup();

			} catch (Exception e) {
				FacesUtils.addMessageError(event, "Error al eliminar el tipo de documento", e.getMessage());
				logger.error("Error al eliminar el tipo de documento. "+e.getMessage());
			}

			logger.info("Saliendo de deleteAction()");
			return PAGE_NAME;
		}


	public String viewAction(ActionEvent event) {
		logger.info("Entró a viewAction(ActionEvent event)");

		if (validateSaveAction(event)) {

			try {
				tipoDocumentoService.delete(this.tipoDocumento);
				this.getTipoDocumentoAll();
				this.closedPopup();

			} catch (Exception e) {
				FacesUtils.addMessageError(event, "Error al ver el tipo de Documento", e.getMessage());
				logger.error("Error al ver tipo de Documento. "+e.getMessage());
			}
		}

		logger.info("Saliendo de viewAction()");
		return PAGE_NAME;
	}

	public List<SelectItem> getListSelectItem(){

		this.listSelectItem = new ArrayList<SelectItem>();

		if(this.listaTipoDocumento != null){
			for (TipoDocumento tipoDocumento : listaTipoDocumento) {
				this.listSelectItem.add(new SelectItem(tipoDocumento.getId(), tipoDocumento.getNombre()));
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

	public TipoDocumento getTipoDocumento() {

		logger.info("this.tipoDocumento = " + this.tipoDocumento);
		if (this.tipoDocumento != null)
			logger.info("this.tipoDocumento.getNombre() = " + this.tipoDocumento.getNombre());

		return this.tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public boolean isVisiblePopup() {
		return visiblePopup;
	}

	public void setVisiblePopup(boolean visiblePopup) {
		this.visiblePopup = visiblePopup;
	}

	public void setNombreTipoDocumento(String nombre){
		if(this.tipoDocumento != null){
			this.tipoDocumento.setNombre(nombre);
		}
	}

	public String getNombreTipoDocumento(){
		String nombre = "";
		if(this.tipoDocumento != null){
			nombre = this.tipoDocumento.getNombre();
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




	public void setEstadoTipoDocumento(Integer estado) {
		if(this.tipoDocumento != null){
			this.tipoDocumento.setEstado(estado);
		}
	}

	public Integer getEstadoTipoDocumento() {
		Integer estado = null;
		if(this.tipoDocumento != null){
			estado = this.tipoDocumento.getEstado();
		}

		return estado;
	}


	public void setFechaCreacionTipoDocumento(Date fechaCreacion) {
		if(this.tipoDocumento != null){
			this.tipoDocumento.setFechaCreacion(fechaCreacion);
		}
	}

	public Date getFechaCreacionTipoDocumento() {
		Date fechaCreacion = null;
		if(this.tipoDocumento != null){
			fechaCreacion = this.tipoDocumento.getFechaCreacion();
		}

		return fechaCreacion;
	}

	public void setFechaUltimoCambioTipoDocumento(Date fechaUltimoCambio) {
		if(this.tipoDocumento != null){
			this.tipoDocumento.setFechaUltimoCambio(fechaUltimoCambio);
		}
	}

	public Date getFechaUltimoCambioTipoDocumento() {
		Date fechaUltimoCambio = null;
		if(this.tipoDocumento != null){
			fechaUltimoCambio = this.tipoDocumento.getFechaUltimoCambio();
		}

		return fechaUltimoCambio;
	}

	public void setUsuarioCreacionTipoDocumento(String usuarioCreacion) {
		if(this.tipoDocumento != null){
			this.tipoDocumento.setUsuarioCreacion(usuarioCreacion);
		}
	}

	public String getUsuarioCreacionTipoDocumento() {
		String user = null;
		if(this.tipoDocumento != null){
			user = this.tipoDocumento.getUsuarioCreacion();
		}

		return user;
	}

	public void setUsuarioUltimoCambioTipoDocumento(String usuarioUltimoCambio) {
		if(this.tipoDocumento != null){
			this.tipoDocumento.setUsuarioUltimoCambio(usuarioUltimoCambio);
		}
	}

	public String getUsuarioUltimoCambioTipoDocumento() {
		String user = null;
		if(this.tipoDocumento != null){
			user = this.tipoDocumento.getUsuarioUltimoCambio();
		}

		return user;
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
