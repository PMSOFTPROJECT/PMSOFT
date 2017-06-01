package co.edu.uan.app.pmsoft.model.service;

import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import co.edu.uan.app.pmsoft.model.entity.HistorialActividad;
import co.edu.uan.app.pmsoft.model.pojo.Constantes;

@Remote
@Stateless
public class HistorialActividadService {

	@PersistenceContext(unitName = "pmsoft-unit")
	private EntityManager em;

	public List<HistorialActividad> getAll() {

		CriteriaQuery<HistorialActividad> criteria = this.em.getCriteriaBuilder().createQuery(HistorialActividad.class);

		List<HistorialActividad> lista = this.em.createQuery(criteria.select(criteria.from(HistorialActividad.class))).getResultList();

		return lista;
	}

	public HistorialActividad getById(long id) throws Exception {

		HistorialActividad historialactividad = this.em.find(HistorialActividad.class, id);

		if (historialactividad == null) {
			throw new Exception("No hay objeto con ese id");
		}

		return historialactividad;
	}

	public HistorialActividad save(HistorialActividad historialactividad) {
		HistorialActividad newhistorialactividad = null;

		if (historialactividad == null) {
			throw new IllegalArgumentException("No hay objeto HistorialActividad para guardar");
		}

		newhistorialactividad = this.em.merge(historialactividad);

		return newhistorialactividad;

	}

	public HistorialActividad delete(HistorialActividad historialactividad) {
		HistorialActividad delhistorialactividad = null;

		if (historialactividad == null) {
			throw new IllegalArgumentException("No hay objeto HistorialActividad para eliminar");
		}

		historialactividad.setEstado(Constantes.ESTADO_INACTIVO);
		delhistorialactividad = this.em.merge(historialactividad);

		return delhistorialactividad;
	}


}
