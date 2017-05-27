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
public class Documento implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "documento_id", updatable = false, nullable = false)
	private Long id;	
	
	@Version
	@Column(name = "personaRecurso_version")
	private int version;
	
	@Column(name = "documento_ruta", nullable = false)
	private String ruta;
	
	@Column(name = "documento_nombre", nullable = false)
	private String nombre;
	
	@Column(name = "documento_actividad", nullable = false)
	private Long actividad;
	
	@Column(name = "documento_fechacreacion", nullable = true)
	private Date fechaCreacion;
	
	@Column(name = "documento_fechaultimocambio", nullable = true)
	private Date fechaUltimoCambio;
	
	@Column(name = "documento_usuariocreacion", nullable = true)
	private String usuarioCreacion;
	
	@Column(name = "documento_usuarioultimocambio", nullable = true)
	private String usuarioUltimoCambio;
	
	@Column(name = "documento_estado", nullable = true)
	private Integer estado;
	
	@Column(name = "documento_editable", nullable = true)
	private Boolean editable;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getActividad() {
		return actividad;
	}

	public void setActividad(Long actividad) {
		this.actividad = actividad;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaUltimoCambio() {
		return fechaUltimoCambio;
	}

	public void setFechaUltimoCambio(Date fechaUltimoCambio) {
		this.fechaUltimoCambio = fechaUltimoCambio;
	}

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public String getUsuarioUltimoCambio() {
		return usuarioUltimoCambio;
	}

	public void setUsuarioUltimoCambio(String usuarioUltimoCambio) {
		this.usuarioUltimoCambio = usuarioUltimoCambio;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
}

