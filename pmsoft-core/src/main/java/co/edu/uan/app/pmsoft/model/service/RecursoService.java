package co.edu.uan.app.pmsoft.model.service;

import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import co.edu.uan.app.pmsoft.model.entity.Persona;
import co.edu.uan.app.pmsoft.model.entity.Recurso;
import co.edu.uan.app.pmsoft.model.pojo.Constantes;

@Remote
@Stateless
public class RecursoService {
	
	@PersistenceContext(unitName = "pmsoft-unit")
	private EntityManager em;
	
	public List<Recurso> getAll() {
		CriteriaQuery<Recurso> criteria = this.em.getCriteriaBuilder().createQuery(Recurso.class);
		
		List<Recurso> lista = this.em.createQuery(criteria.select(criteria.from(Recurso.class))).getResultList();
				
		return lista;
	}
	
	public Recurso getById(long id) throws Exception {
		
		Recurso recurso = null;
		recurso = this.em.find(Recurso.class, id);
		
		return recurso;
	}
	
	public Recurso save(Recurso recurso) {
		Recurso newRecurso = null;
		
		if (recurso == null) {
			throw new IllegalArgumentException("No hay objeto recurso para guardar");
		}
		
		newRecurso = this.em.merge(recurso);
		
		return newRecurso;		
	}
	
	public Recurso delete(Recurso recurso) {
		Recurso delRecurso = null;
		
		if (recurso == null) {
			throw new IllegalArgumentException("No hay objeto recurso para eliminar");
		}
		
		recurso.setEstado(Constantes.ESTADO_INACTIVO);
		delRecurso = this.em.merge(recurso);
		
		return delRecurso;
	}

}
