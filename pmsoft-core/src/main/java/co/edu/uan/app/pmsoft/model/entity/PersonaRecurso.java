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
public class PersonaRecurso implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "persona_recurso_id", updatable = false, nullable = false)
	private Long id;
	
	@JoinColumn(name = "persona_recurso_persona_id", referencedColumnName = "persona_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Persona persona;
	
	@JoinColumn(name = "persona_recurso_recurso_id", referencedColumnName = "recurso_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Recurso recurso;
	
	
	
	@Column(name = "persona_recurso_estado", nullable = true)
	private Integer estado;
	
	@Column(name = "persona_recurso_editable", nullable = true)
	private Boolean editable;
	
	@Column(name = "persona_recurso_fechacreacion", nullable = true)
	private Date fechaCreacion;
	
	@Column(name = "persona_recurso_fechaultimocambio", nullable = true)
	private Date fechaUltimoCambio;
	
	@Column(name = "persona_recurso_usuariocreacion", nullable = true)
	private String usuarioCreacion;
	
	@Column(name = "persona_recurso_usuarioultimocambio", nullable = true)
	private String usuarioUltimoCambio;
	
	@Version
	@Column(name = "personaRecurso_version")
	private int version;
	
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

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Recurso getRecurso() {
		return recurso;
	}

	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}		
}

