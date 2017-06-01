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
import co.edu.uan.app.pmsoft.model.entity.Observacion;
import co.edu.uan.app.pmsoft.model.pojo.Constantes;
import co.edu.uan.app.pmsoft.model.service.ObservacionService;
import co.edu.uan.app.pmsoft.util.FacesUtils;

@ManagedBean(name = ObservacionBean.BEAN_NAME)
@CustomScoped(value = "#{window}")
public class ObservacionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String BEAN_NAME = "observacionBean";
	public static final String PAGE_NAME = "gestionar_observaciones";
	private static final Logger logger = LoggerFactory.getLogger(ObservacionBean.class);

	@EJB
	ObservacionService observacionService;

	private Observacion observacion;
	private List<Observacion> listaObservacion;
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
		this.listaObservacion = null;
		this.observacion = null;
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

	public List<Observacion> getObservacionAll() {
		this.listaObservacion = observacionService.getAll();
		return this.listaObservacion;
	}

	public void agregarObservacion(ActionEvent event) {
		logger.info("Entro a addobservacion(event:" + event + ")");

		this.observacion = new Observacion();
		this.observacion.setVersion(1);
		this.observacion.setDescripcion("");
		this.observacion.setUsuarioCreacion(this.getSessionBean().getNombreCompletoUsuario());
		this.observacion.setFechaCreacion(new Date());
		this.observacion.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
		this.observacion.setFechaUltimoCambio(new Date());
		this.observacion.setEstado(Constantes.ESTADO_ACTIVO);
		this.observacion.setEditable(true);

		this.headerDialog = "Nueva observacion";
		this.openPopup();
		this.visibleEdit = false;
		this.visibleView = true;
		this.visibleDelete = false;
		logger.info(this.observacion.getUsuarioUltimoCambio());
		logger.info("Saliendo de addobservacion(rol:" + observacion + ")");
	}

	public String saveAction(ActionEvent event) {
		logger.info("Entró a saveAction(ActionEvent event)");

		if (validateSaveAction(event)) {
			try {
				observacionService.save(this.observacion);
				this.getObservacionAll();
				FacesUtils.addMessageError(event, "Guardado con éxito", "");
				this.closedPopup();
			} catch (Exception e) {
				FacesUtils.addMessageError(event, "Error al guardar la observacion", e.getMessage());
				logger.error("Error al guardar observacion. "+e.getMessage());
			}
		}

		logger.info("Saliendo de saveAction()");
		return PAGE_NAME;
	}

	private boolean validateSaveAction(ActionEvent event) {
		logger.info("Entró a validateSaveAction()");

		boolean valid = true;
		String detail = "";

		if (this.observacion == null) {
			FacesUtils.addMessageError(event,"No existe un objeto observacion inicializado", "");
			valid = false;

		} else if (StringUtils.isBlank(this.observacion.getDescripcion())) {
			FacesUtils.addMessageError(event, "Se debe ingresar la descripcion de la observacion", "");
			valid = false;

		}

		if (!valid) {
			FacesUtils.addMessageError(event, "Error al guardar la observacion", detail);
			logger.error("Error validando la observacion a guardar. "+detail);
		}
		logger.info("Saliendo de validateSaveAction()");
		return valid;
	}

	public void editarObservacion(ActionEvent event) {
		logger.info("Entro a editobservacion(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();

            if (tmpRowData instanceof Observacion) {
            	this.observacion = (Observacion) tmpRowData;
            	this.observacion.setFechaUltimoCambio(new Date());
            	this.observacion.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
            }

        }

		this.headerDialog = "Editar observacion";
		this.openPopup();
		this.visibleEdit = false;
		this.visibleView = true;
		this.visibleDelete = false;

		logger.info("Saliendo de editObservacion(observacion:" + observacion + ")");

	}


	public void verObservacion(ActionEvent event) {
		logger.info("Entro a viewobservacion(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            if (tmpRowData instanceof Observacion) {
            	this.observacion = (Observacion) tmpRowData;
            }
        }

        this.visibleEdit = true;
        this.visibleView = false;
        this.visibleDelete = false;
		this.headerDialog = "Información de la observacion";
		this.openPopup();

		logger.info("Saliendo de viewObservacion(observacion:" + observacion + ")");

	}

	public void popupClose() {
		this.visiblePopup = false;
	}


  public void eliminarObservacion(ActionEvent event) {
		logger.info("Entro a deleteProject(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            if (tmpRowData instanceof Observacion) {
            	this.observacion = (Observacion) tmpRowData;
            	this.observacion.setFechaUltimoCambio(new Date());
            	this.observacion.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
            }
        }

        this.closedPopup();

		logger.info("Saliendo de deleteProject(project:" + observacion + ")");

	}

	public String deleteAction(ActionEvent event) {
		logger.info("Entró a deleteAction(ActionEvent event)");

		try {
			observacionService.delete(this.observacion);
			this.getObservacionAll();
			this.closedPopup();

		} catch (Exception e) {
			FacesUtils.addMessageError(event, "Error al eliminar la Observacion", e.getMessage());
			logger.error("Error al eliminar observacion. "+e.getMessage());
		}

		logger.info("Saliendo de deleteAction()");
		return PAGE_NAME;
	}


	public String viewAction(ActionEvent event) {
		logger.info("Entró a viewAction(ActionEvent event)");

		if (validateSaveAction(event)) {

			try {
				observacionService.delete(this.observacion);
				this.getObservacionAll();
				this.closedPopup();

			} catch (Exception e) {
				FacesUtils.addMessageError(event, "Error al ver la observacion", e.getMessage());
				logger.error("Error al ver observacion. "+e.getMessage());
			}
		}

		logger.info("Saliendo de viewAction()");
		return PAGE_NAME;
	}

	public List<SelectItem> getListSelectItem(){

		this.listSelectItem = new ArrayList<SelectItem>();

		if(this.listaObservacion != null){
			for (Observacion observacion : listaObservacion) {
				this.listSelectItem.add(new SelectItem(observacion.getId(), observacion.getDescripcion()));
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

	public Observacion getObservacion() {

		logger.info("this.observacion = " + this.observacion);
		if (this.observacion != null)
			logger.info("this.observacion.getDescripcion() = " + this.observacion.getDescripcion());

		return this.observacion;
	}

	public void setObservacion(Observacion observacion) {
		this.observacion = observacion;
	}

	public boolean isVisiblePopup() {
		return visiblePopup;
	}

	public void setVisiblePopup(boolean visiblePopup) {
		this.visiblePopup = visiblePopup;
	}

	public void setDescripcionObservacion(String descripcion){
		if(this.observacion != null){
			this.observacion.setDescripcion(descripcion);
		}
	}

	public String getDescripcionObservacion(){
		String descripcion = "";
		if(this.observacion != null){
			descripcion = this.observacion.getDescripcion();
		}

		return descripcion;
	}

	/*
     *  if closing with a client side api, ensure a listener is used to
     *  update the visible value on the server
     */
    public void closeFAjax(AjaxBehaviorEvent event){
        this.visiblePopup = false;
        this.visibleEdit = false;
    }




	public void setEstadoObservacion(Integer estado) {
		if(this.observacion != null){
			this.observacion.setEstado(estado);
		}
	}

	public Integer getEstadoObservacion() {
		Integer estado = null;
		if(this.observacion != null){
			estado = this.observacion.getEstado();
		}

		return estado;
	}


	public void setFechaCreacionObservacion(Date fechaCreacion) {
		if(this.observacion != null){
			this.observacion.setFechaCreacion(fechaCreacion);
		}
	}

	public Date getFechaCreacionObservacion() {
		Date fechaCreacion = null;
		if(this.observacion != null){
			fechaCreacion = this.observacion.getFechaCreacion();
		}

		return fechaCreacion;
	}

	public void setFechaUltimoCambioObservacion(Date fechaUltimoCambio) {
		if(this.observacion != null){
			this.observacion.setFechaUltimoCambio(fechaUltimoCambio);
		}
	}

	public Date getFechaUltimoCambioObservacion() {
		Date fechaUltimoCambio = null;
		if(this.observacion != null){
			fechaUltimoCambio = this.observacion.getFechaUltimoCambio();
		}

		return fechaUltimoCambio;
	}

	public void setUsuarioCreacionObservacion(String usuarioCreacion) {
		if(this.observacion != null){
			this.observacion.setUsuarioCreacion(usuarioCreacion);
		}
	}

	public String getUsuarioCreacionObservacion() {
		String user = null;
		if(this.observacion != null){
			user = this.observacion.getUsuarioCreacion();
		}

		return user;
	}

	public void setUsuarioUltimoCambioObservacion(String usuarioUltimoCambio) {
		if(this.observacion != null){
			this.observacion.setUsuarioUltimoCambio(usuarioUltimoCambio);
		}
	}

	public String getUsuarioUltimoCambioObservacion() {
		String user = null;
		if(this.observacion != null){
			user = this.observacion.getUsuarioUltimoCambio();
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
