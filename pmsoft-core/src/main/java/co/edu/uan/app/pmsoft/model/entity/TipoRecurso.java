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
public class TipoRecurso implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "tipoRecurso_id", updatable = false, nullable = false)
	private Long id;
	
	@Version
	@Column(name = "tipoRecurso_version")
	private int version;
	
	@Column(name = "tipoRecurso_nombre", nullable = false)
	private String nombre;
	
	@Column(name = "tipoRecurso_estado", nullable = false)
	private String estado;
	
	@Column(name = "tipoRecurso_editable", nullable = false)
	private Boolean editable;
	
	@Column(name = "tipoRecurso_usuarioCreacion", nullable = false)
	private String usuarioCreacion;
	
	@Column(name = "tipoRecurso_fechaCreacion", nullable = false)
	private Date fechaCreacion;
	
	@Column(name = "tipoRecurso_usuarioUltimoCambio", nullable = false)
	private String usuarioUltimoCambio;
	
	@Column(name = "tipoRecurso_fechaUltimoCambio", nullable = false)
	private Date fechaUltimoCambio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getUsuarioUltimoCambio() {
		return usuarioUltimoCambio;
	}

	public void setUsuarioUltimoCambio(String usuarioUltimoCambio) {
		this.usuarioUltimoCambio = usuarioUltimoCambio;
	}

	public Date getFechaUltimoCambio() {
		return fechaUltimoCambio;
	}

	public void setFechaUltimoCambio(Date fechaUltimoCambio) {
		this.fechaUltimoCambio = fechaUltimoCambio;
	}

}