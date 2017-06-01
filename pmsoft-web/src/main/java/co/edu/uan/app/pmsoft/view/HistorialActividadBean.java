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
import co.edu.uan.app.pmsoft.model.entity.HistorialActividad;
import co.edu.uan.app.pmsoft.model.pojo.Constantes;
import co.edu.uan.app.pmsoft.model.service.HistorialActividadService;
import co.edu.uan.app.pmsoft.util.FacesUtils;

@ManagedBean(name = HistorialActividadBean.BEAN_NAME)
@CustomScoped(value = "#{window}")
public class HistorialActividadBean implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String BEAN_NAME = "historialActividadBean";
	public static final String PAGE_NAME = "gestionar_historial_actividades";
	private static final Logger logger = LoggerFactory.getLogger(HistorialActividadBean.class);

	@EJB
	HistorialActividadService historialActividadService;

	private HistorialActividad historialActividad;
	private List<HistorialActividad> listaHistorialActividad;
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
		this.listaHistorialActividad = null;
		this.historialActividad = null;
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

	public List<HistorialActividad> getHistorialActividadAll() {
		this.listaHistorialActividad = historialActividadService.getAll();
		return this.listaHistorialActividad;
	}

	public void agregarHistorialActividad(ActionEvent event) {
		logger.info("Entro a addhistorialActividad(event:" + event + ")");

		this.historialActividad = new HistorialActividad();
		this.historialActividad.setVersion(1);
		this.historialActividad.setUsuarioCreacion(this.getSessionBean().getNombreCompletoUsuario());
		this.historialActividad.setFechaCreacion(new Date());
		this.historialActividad.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
		this.historialActividad.setFechaUltimoCambio(new Date());
		this.historialActividad.setEstado(Constantes.ESTADO_ACTIVO);
		this.historialActividad.setEditable(true);

		this.headerDialog = "Nueva historialActividad";
		this.openPopup();
		this.visibleEdit = false;
		this.visibleView = true;
		this.visibleDelete = false;
		logger.info(this.historialActividad.getUsuarioUltimoCambio());
		logger.info("Saliendo de addhistorialActividad(rol:" + historialActividad + ")");
	}

	public String saveAction(ActionEvent event) {
		logger.info("Entró a saveAction(ActionEvent event)");

		if (validateSaveAction(event)) {
			try {
				historialActividadService.save(this.historialActividad);
				this.getHistorialActividadAll();
				FacesUtils.addMessageError(event, "Guardado con éxito", "");
				this.closedPopup();
			} catch (Exception e) {
				FacesUtils.addMessageError(event, "Error al guardar el historial de actividad", e.getMessage());
				logger.error("Error al guardar historialActividad. "+e.getMessage());
			}
		}

		logger.info("Saliendo de saveAction()");
		return PAGE_NAME;
	}

	private boolean validateSaveAction(ActionEvent event) {
		logger.info("Entró a validateSaveAction()");

		boolean valid = true;
		String detail = "";

		if (this.historialActividad == null) {
			FacesUtils.addMessageError(event, "No existe un objeto historialActividad inicializado", "");
			valid = false;
		}

		if (!valid) {
			FacesUtils.addMessageError(event, "Error al guardar el historial de actividad", detail);
			logger.error("Error validando el historial de actividad a guardar. "+detail);
		}
		logger.info("Saliendo de validateSaveAction()");
		return valid;
	}

	public void editarHistorialActividad(ActionEvent event) {
		logger.info("Entro a edithistorialActividad(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();

            if (tmpRowData instanceof HistorialActividad) {
            	this.historialActividad = (HistorialActividad) tmpRowData;
            	this.historialActividad.setFechaUltimoCambio(new Date());
            	this.historialActividad.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
            }

        }

		this.headerDialog = "Editar historialActividad";
		this.openPopup();
		this.visibleEdit = false;
		this.visibleView = true;
		this.visibleDelete = false;

		logger.info("Saliendo de editHistorialActividad(historialActividad:" + historialActividad + ")");

	}


	public void verHistorialActividad(ActionEvent event) {
		logger.info("Entro a viewhistorialActividad(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            if (tmpRowData instanceof HistorialActividad) {
            	this.historialActividad = (HistorialActividad) tmpRowData;
            }
        }

        this.visibleEdit = true;
        this.visibleView = false;
        this.visibleDelete = false;
		this.headerDialog = "Información de el historial de actividad";
		this.openPopup();

		logger.info("Saliendo de viewHistorialActividad(historialActividad:" + historialActividad + ")");

	}

	public void popupClose() {
		this.visiblePopup = false;
	}


  public void eliminarHistorialActividad(ActionEvent event) {
		logger.info("Entro a deleteProject(event:" + event + ")");

        UIComponent tmpComponent = event.getComponent();
        while (null != tmpComponent && !(tmpComponent instanceof UIData)) {
            tmpComponent = tmpComponent.getParent();
        }
        if (tmpComponent != null && (tmpComponent instanceof UIData)) {
            Object tmpRowData = ((UIData) tmpComponent).getRowData();
            if (tmpRowData instanceof HistorialActividad) {
            	this.historialActividad = (HistorialActividad) tmpRowData;
            	this.historialActividad.setFechaUltimoCambio(new Date());
            	this.historialActividad.setUsuarioUltimoCambio(this.getSessionBean().getNombreCompletoUsuario());
            }
        }

        this.closedPopup();

		logger.info("Saliendo de deleteProject(project:" + historialActividad + ")");

	}

	public String deleteAction(ActionEvent event) {
		logger.info("Entró a deleteAction(ActionEvent event)");

		try {
			historialActividadService.delete(this.historialActividad);
			this.getHistorialActividadAll();
			this.closedPopup();

		} catch (Exception e) {
			FacesUtils.addMessageError(event, "Error al eliminar el historial de actividad", e.getMessage());
			logger.error("Error al eliminar historialActividad. "+e.getMessage());
		}

		logger.info("Saliendo de deleteAction()");
		return PAGE_NAME;
	}


	public String viewAction(ActionEvent event) {
		logger.info("Entró a viewAction(ActionEvent event)");

		if (validateSaveAction(event)) {

			try {
				historialActividadService.delete(this.historialActividad);
				this.getHistorialActividadAll();
				this.closedPopup();

			} catch (Exception e) {
				FacesUtils.addMessageError(event, "Error al ver el historial de actividad", e.getMessage());
				logger.error("Error al ver historialActividad. "+e.getMessage());
			}
		}

		logger.info("Saliendo de viewAction()");
		return PAGE_NAME;
	}

	public List<SelectItem> getListSelectItem(){

		this.listSelectItem = new ArrayList<SelectItem>();

		if(this.listaHistorialActividad != null){
			for (HistorialActividad historialActividad : listaHistorialActividad) {
				this.listSelectItem.add(new SelectItem(historialActividad.getId(), historialActividad.getUsuarioUltimoCambio()));
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

	public HistorialActividad getHistorialActividad() {

		logger.info("this.historialActividad = " + this.historialActividad);
		if (this.historialActividad != null)
			logger.info("this.historialActividad.getId() = " + this.historialActividad.getId());

		return this.historialActividad;
	}

	public void setHistorialActividad(HistorialActividad historialActividad) {
		this.historialActividad = historialActividad;
	}

	public boolean isVisiblePopup() {
		return visiblePopup;
	}

	public void setVisiblePopup(boolean visiblePopup) {
		this.visiblePopup = visiblePopup;
	}


	/*
     *  if closing with a client side api, ensure a listener is used to
     *  update the visible value on the server
     */
    public void closeFAjax(AjaxBehaviorEvent event){
        this.visiblePopup = false;
        this.visibleEdit = false;
    }




	public void setEstadoHistorialActividad(Integer estado) {
		if(this.historialActividad != null){
			this.historialActividad.setEstado(estado);
		}
	}

	public Integer getEstadoHistorialActividad() {
		Integer estado = null;
		if(this.historialActividad != null){
			estado = this.historialActividad.getEstado();
		}

		return estado;
	}


	public void setFechaCreacionHistorialActividad(Date fechaCreacion) {
		if(this.historialActividad != null){
			this.historialActividad.setFechaCreacion(fechaCreacion);
		}
	}

	public Date getFechaCreacionHistorialActividad() {
		Date fechaCreacion = null;
		if(this.historialActividad != null){
			fechaCreacion = this.historialActividad.getFechaCreacion();
		}

		return fechaCreacion;
	}

	public void setFechaUltimoCambioHistorialActividad(Date fechaUltimoCambio) {
		if(this.historialActividad != null){
			this.historialActividad.setFechaUltimoCambio(fechaUltimoCambio);
		}
	}

	public Date getFechaUltimoCambioHistorialActividad() {
		Date fechaUltimoCambio = null;
		if(this.historialActividad != null){
			fechaUltimoCambio = this.historialActividad.getFechaUltimoCambio();
		}

		return fechaUltimoCambio;
	}

	public void setUsuarioCreacionHistorialActividad(String usuarioCreacion) {
		if(this.historialActividad != null){
			this.historialActividad.setUsuarioCreacion(usuarioCreacion);
		}
	}

	public String getUsuarioCreacionHistorialActividad() {
		String user = null;
		if(this.historialActividad != null){
			user = this.historialActividad.getUsuarioCreacion();
		}

		return user;
	}

	public void setUsuarioUltimoCambioHistorialActividad(String usuarioUltimoCambio) {
		if(this.historialActividad != null){
			this.historialActividad.setUsuarioUltimoCambio(usuarioUltimoCambio);
		}
	}

	public String getUsuarioUltimoCambioHistorialActividad() {
		String user = null;
		if(this.historialActividad != null){
			user = this.historialActividad.getUsuarioUltimoCambio();
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
