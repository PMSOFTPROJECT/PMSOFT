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

@Entity
public class Persona implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "persona_id", updatable = false, nullable = false)
	private Long id;	
	
//	@javax.persistence.Version
//	@Column(name = "persona_version")
//	private int version;
	
	@Column(name = "persona_nombre", nullable = false)
	private String nombre;
	
	@Column(name = "persona_costohora", nullable = false)
	private int costoHora;
	
	@Column(name = "persona_cargo", nullable = false)
	private String cargo;
	
	@Column(name = "persona_telefono", nullable = false)
	private String telefono;
	
	@Column(name = "persona_direccion", nullable = false)
	private String direccion;
	
	@Column(name = "persona_email", nullable = false)
	private String email;
	
	@JoinColumn(name = "persona_tipodocumento_id", referencedColumnName = "t_documento_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TipoDocumento tipoDocumento;
	
	@Column(name = "persona_identificacion", nullable = false)
	private String identificacion;
	
	@Column(name = "persona_nombrecontactoemergencia", nullable = false)
	private String nombreContactoEmergencia;
	
	@Column(name = "persona_telefonocontactoemergencia", nullable = false)
	private String telefonoContactoEmergencia;
	
	@Column(name = "persona_usuariocreacion", nullable = false)
	private String usuarioCreacion;
	
	@Column(name = "persona_fechacreacion", nullable = false)
	private Date fechaCreacion;
	
	@Column(name = "persona_usuarioultimocambio", nullable = false)
	private String usuarioUltimoCambio;
	
	@Column(name = "persona_fechaultimocambio", nullable = false)
	private Date fechaUltimoCambio;
	
	@Column(name = "persona_estado", nullable = false)
	private Integer estado;
	
	@Column(name = "persona_editable", nullable = false)
	private Boolean editable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCostoHora() {
		return costoHora;
	}

	public void setCostoHora(int costoHora) {
		this.costoHora = costoHora;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}	

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombreContactoEmergencia() {
		return nombreContactoEmergencia;
	}

	public void setNombreContactoEmergencia(String nombreContactoEmergencia) {
		this.nombreContactoEmergencia = nombreContactoEmergencia;
	}

	public String getTelefonoContactoEmergencia() {
		return telefonoContactoEmergencia;
	}

	public void setTelefonoContactoEmergencia(String telefonoContactoEmergencia) {
		this.telefonoContactoEmergencia = telefonoContactoEmergencia;
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
}