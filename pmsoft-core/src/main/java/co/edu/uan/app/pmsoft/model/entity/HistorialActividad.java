package co.edu.uan.app.pmsoft.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class HistorialActividad implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "historial_actividad_id", updatable = false, nullable = false)
	private Long id;

	@Version
	@Column(name = "historial_actividad_version")
	private int version;

	@Column(name = "historial_actividad_usuarioCreacion", nullable = false)
	private String usuarioCreacion;

	@Column(name = "historial_actividad_fechaCreacion", nullable = false)
	private Date fechaCreacion;

	@Column(name = "historial_actividad_usuarioUltimoCambio", nullable = false)
	private String usuarioUltimoCambio;

	@Column(name = "historial_actividad_fechaUltimoCambio", nullable = false)
	private Date fechaUltimoCambio;

	@Column(name = "historial_actividad_estado", nullable = false)
	private Integer estado;

	@Column(name = "historial_actividad_editable", nullable = false)
	private Boolean editable;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}


	/**
	 * @return the usuarioCreacion
	 */
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	/**
	 * @param usuarioCreacion the usuarioCreacion to set
	 */
	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the usuarioUltimoCambio
	 */
	public String getUsuarioUltimoCambio() {
		return usuarioUltimoCambio;
	}

	/**
	 * @param usuarioUltimoCambio the usuarioUltimoCambio to set
	 */
	public void setUsuarioUltimoCambio(String usuarioUltimoCambio) {
		this.usuarioUltimoCambio = usuarioUltimoCambio;
	}

	/**
	 * @return the fechaUltimoCambio
	 */
	public Date getFechaUltimoCambio() {
		return fechaUltimoCambio;
	}

	/**
	 * @param fechaUltimoCambio the fechaUltimoCambio to set
	 */
	public void setFechaUltimoCambio(Date fechaUltimoCambio) {
		this.fechaUltimoCambio = fechaUltimoCambio;
	}

	/**
	 * @return the estado
	 */
	public Integer getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	/**
	 * @return the editable
	 */
	public Boolean getEditable() {
		return editable;
	}

	/**
	 * @param editable the editable to set
	 */
	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

}
