package co.edu.uan.app.pmsoft.model.service;

import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.uan.app.pmsoft.model.entity.Proyecto;
import co.edu.uan.app.pmsoft.model.pojo.Constantes;

@Remote
@Stateless
public class ProyectoService {
	
	@PersistenceContext(unitName = "pmsoft-unit")
	private EntityManager em;
	
	private static final Logger logger = LoggerFactory.getLogger(ProyectoService.class);
	
	public List<Proyecto> getAll() {
		CriteriaQuery<Proyecto> criteria = this.em.getCriteriaBuilder().createQuery(Proyecto.class);
		
		List<Proyecto> lista = this.em.createQuery(criteria.select(criteria.from(Proyecto.class))).getResultList();
		
		logger.info("Lista = " + lista);
		
		return lista;
	}

	public Proyecto getById(long id) throws Exception {
		Proyecto project = this.em.find(Proyecto.class, id);
		
		if (project == null) {
			throw new Exception("No hay objeto con ese id");
		}
		
		return project;
	}
	
	public Proyecto save(Proyecto project) {
		Proyecto newProject = null;
		
		if (project == null) {
			throw new IllegalArgumentException("No hay objeto proyecto para guardar");
		}
		
		newProject = this.em.merge(project);
		
		return newProject;
		
	}
	
	public Proyecto delete(Proyecto project) {
		Proyecto delProject = null;
		
		if (project == null) {
			throw new IllegalArgumentException("No hay objeto proyecto para eliminar");
		}
		
		project.setEstado(Constantes.ESTADO_INACTIVO);
		delProject = this.em.merge(project);
		
		return delProject;
	}
	
}
