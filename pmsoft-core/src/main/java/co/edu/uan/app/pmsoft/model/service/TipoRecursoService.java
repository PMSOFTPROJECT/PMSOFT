package co.edu.uan.app.pmsoft.model.service;

import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import co.edu.uan.app.pmsoft.model.entity.TipoRecurso;
import co.edu.uan.app.pmsoft.model.entity.Version;

@Remote
@Stateless
public class TipoRecursoService {
	
	@PersistenceContext(unitName = "pmsoft-unit")
	private EntityManager em;
	
	public List<TipoRecurso> getAll() {
		CriteriaQuery<TipoRecurso> criteria = this.em.getCriteriaBuilder().createQuery(TipoRecurso.class);
		return this.em.createQuery(criteria.select(criteria.from(TipoRecurso.class))).getResultList();

	}
	
	public TipoRecurso save(TipoRecurso tipoRecurso) {
		TipoRecurso newTipoRecurso = null;
		
		if (tipoRecurso == null) {
			throw new IllegalArgumentException("No hay objeto TipoRecurso para guardar");
		}
		
		newTipoRecurso = this.em.merge(tipoRecurso);
		
		return newTipoRecurso;		
	}

}
