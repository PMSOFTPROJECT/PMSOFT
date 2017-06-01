package co.edu.uan.app.pmsoft.model.service;

import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import co.edu.uan.app.pmsoft.model.entity.Observacion;
import co.edu.uan.app.pmsoft.model.pojo.Constantes;

@Remote
@Stateless
public class ObservacionService {

	@PersistenceContext(unitName = "pmsoft-unit")
	private EntityManager em;

	public List<Observacion> getAll() {

		CriteriaQuery<Observacion> criteria = this.em.getCriteriaBuilder().createQuery(Observacion.class);

		List<Observacion> lista = this.em.createQuery(criteria.select(criteria.from(Observacion.class))).getResultList();

		return lista;
	}

	public Observacion getById(long id) throws Exception {

		Observacion observacion = this.em.find(Observacion.class, id);

		if (observacion == null) {
			throw new Exception("No hay objeto con ese id");
		}

		return observacion;
	}

	public Observacion save(Observacion observacion) {
		Observacion newobservacion = null;

		if (observacion == null) {
			throw new IllegalArgumentException("No hay objeto Observacion para guardar");
		}

		newobservacion = this.em.merge(observacion);

		return newobservacion;

	}

	public Observacion delete(Observacion observacion) {
		Observacion delobservacion = null;

		if (observacion == null) {
			throw new IllegalArgumentException("No hay objeto Observacion para eliminar");
		}

		observacion.setEstado(Constantes.ESTADO_INACTIVO);
		delobservacion = this.em.merge(observacion);

		return delobservacion;
	}


}
