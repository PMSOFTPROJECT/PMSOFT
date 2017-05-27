package co.edu.uan.app.pmsoft.model.service;

import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import co.edu.uan.app.pmsoft.model.entity.Persona;
import co.edu.uan.app.pmsoft.model.pojo.Constantes;



@Remote
@Stateless
public class PersonaService {
	
	@PersistenceContext(unitName = "pmsoft-unit")
	private EntityManager em;
	
	public List<Persona> getAll() {
		CriteriaQuery<Persona> criteria = this.em.getCriteriaBuilder().createQuery(Persona.class);
		
		List<Persona> lista = this.em.createQuery(criteria.select(criteria.from(Persona.class))).getResultList();
				
		return lista;
	}

	public Persona getById(long id) throws Exception {
		Persona persona = this.em.find(Persona.class, id);
		
		if (persona == null) {
			throw new Exception("No hay objeto con ese id");
		}
		
		return persona;
	}
	
	public Persona save(Persona persona) {
		Persona newPersona = null;
		
		if (persona == null) {
			throw new IllegalArgumentException("No hay objeto persona para guardar");
		}
		
		newPersona = this.em.merge(persona);
		
		return newPersona;
		
	}
	
	public Persona delete(Persona persona) {
		Persona delPersona = null;
		
		if (persona == null) {
			throw new IllegalArgumentException("No hay objeto persona para eliminar");
		}
		
		persona.setEstado(Constantes.ESTADO_INACTIVO);
		delPersona = this.em.merge(persona);
		
		return delPersona;
	}
	
}
