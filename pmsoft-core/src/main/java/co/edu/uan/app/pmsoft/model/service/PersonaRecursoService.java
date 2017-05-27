package co.edu.uan.app.pmsoft.model.service;

import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import co.edu.uan.app.pmsoft.model.entity.PersonaRecurso;
import co.edu.uan.app.pmsoft.model.pojo.Constantes;

@Remote
@Stateless
public class PersonaRecursoService {
	
	@PersistenceContext(unitName = "pmsoft-unit")
	private EntityManager em;
	
	public List<PersonaRecurso> getAll() {
		CriteriaQuery<PersonaRecurso> criteria = this.em.getCriteriaBuilder().createQuery(PersonaRecurso.class);
		
		List<PersonaRecurso> lista = this.em.createQuery(criteria.select(criteria.from(PersonaRecurso.class))).getResultList();
				
		return lista;
	}

	public PersonaRecurso getById(long id) throws Exception {
		PersonaRecurso perRec = this.em.find(PersonaRecurso.class, id);
		
		if (perRec == null) {
			throw new Exception("No hay objeto con ese id");
		}
		
		return perRec;
	}
	
	public PersonaRecurso save(PersonaRecurso perRec) {
		PersonaRecurso newPerRec = null;
		
		if (perRec == null) {
			throw new IllegalArgumentException("No hay objeto PersonaRecurso para guardar");
		}
		
		newPerRec = this.em.merge(perRec);
		
		return newPerRec;		
	}
	
	public PersonaRecurso delete(PersonaRecurso perRec) {
		PersonaRecurso delPerRec = null;
		
		if (perRec == null) {
			throw new IllegalArgumentException("No hay objeto PersonaRecurso para eliminar");
		}
		
		perRec.setEstado(Constantes.ESTADO_INACTIVO);
		delPerRec = this.em.merge(perRec);
		
		return delPerRec;
	}	
}
