package co.edu.uan.app.pmsoft.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
public class Recurso implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "recurso_id", updatable = false, nullable = false)
	private Long id;
	
	@Version
	@Column(name = "recurso_version")
	private int version;
	
	@Column(name = "recurso_nombre", nullable = false)
	private String nombre;
	
	@JoinColumn(name = "recurso_tiporecurso_id", referencedColumnName = "tiporecurso_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER) // TODO EAGER preguntar al profesor
    private TipoRecurso tipoRecurso;
	
	@Column(name = "recurso_estado", nullable = false)
	private Integer estado;
	
	@Column(name = "recurso_costo", nullable = false)
	private Integer costo;
	
	@Column(name = "recurso_editable", nullable = false)
	private Boolean editable;
	
	@Column(name = "recurso_usuarioCreacion", nullable = false)
	private String usuarioCreacion;
	
	@Column(name = "recurso_fechaCreacion", nullable = false)
	private Date fechaCreacion;
	
	@Column(name = "recurso_usuarioUltimoCambio", nullable = false)
	private String usuarioUltimoCambio;
	
	@Column(name = "recurso_fechaUltimoCambio", nullable = false)
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

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getCosto() {
		return costo;
	}

	public void setCosto(Integer costo) {
		this.costo = costo;
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

	public TipoRecurso getTipoRecurso() {
		return tipoRecurso;
	}

	public void setTipoRecurso(TipoRecurso tipoRecurso) {
		this.tipoRecurso = tipoRecurso;
	}
			
}