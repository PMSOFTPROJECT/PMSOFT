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
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "proyecto_id", updatable = false, nullable = false)
	private Long id;
	
	@Version
	@Column(name = "proyecto_version")
	private int version;
	
	@Column(name = "proyecto_nombre", nullable = false)
	private String nombre;
	
	@Column(name = "proyecto_objeto", nullable = false)
	private String objeto;
	
	@Column(name = "proyecto_personaresponsable", nullable = false)
	private String personaResponsable;
	
	@Column(name = "proyecto_porcentaje", nullable = false)
	private double porcentaje;
	
	@Column(name = "proyecto_fechaInicio", nullable = false)
	private String fechaInicio;
	
	@Column(name = "proyecto_fechafin", nullable = false)
	private String fechaFin;
	
	@Column(name = "proyecto_usuarioCreacion", nullable = false)
	private String usuarioCreacion;
	
	@Column(name = "proyecto_fechaCreacion", nullable = false)
	private String fechaCreacion;
	
	@Column(name = "proyecto_usuarioUltimoCambio", nullable = false)
	private String usuarioUltimoCambio;
	
	@Column(name = "proyecto_fechaUltimoCambio", nullable = false)
	private String fechaUltimoCambio;
	
	@Column(name = "proyecto_estado", nullable = false)
	private Integer estado;
	
	@Column(name = "proyecto_editable", nullable = false)
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
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the objeto
	 */
	public String getObjeto() {
		return objeto;
	}

	/**
	 * @param objeto the objeto to set
	 */
	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}

	/**
	 * @return the fechaInicio
	 */
	public String getFechaInicio() {
		return fechaInicio;
	}
	
	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getPersonaResponsable() {
		return personaResponsable;
	}

	public void setPersonaResponsable(String personaResponsable) {
		this.personaResponsable = personaResponsable;
	}

	public double getPorcentaje() {
		return porcentaje;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
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
	public String getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(String fechaCreacion) {
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
	public String getFechaUltimoCambio() {
		return fechaUltimoCambio;
	}

	/**
	 * @param fechaUltimoCambio the fechaUltimoCambio to set
	 */
	public void setFechaUltimoCambio(String fechaUltimoCambio) {
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

	/**
	 * @param porcentaje the porcentaje to set
	 */
	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
	}
	
	
		
}