package co.edu.uan.app.pmsoft.model.service;

import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.uan.app.pmsoft.model.entity.Project;
import co.edu.uan.app.pmsoft.model.pojo.Constantes;

@Remote
@Stateless
public class ProjectService {
	
	@PersistenceContext(unitName = "pmsoft-unit")
	private EntityManager em;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
	
	public List<Project> getAll() {
		CriteriaQuery<Project> criteria = this.em.getCriteriaBuilder().createQuery(Project.class);
		
		List<Project> lista = this.em.createQuery(criteria.select(criteria.from(Project.class))).getResultList();
		
		logger.info("Lista = " + lista);
		
		return lista;
	}

	public Project getById(long id) throws Exception {
		Project project = this.em.find(Project.class, id);
		
		if (project == null) {
			throw new Exception("No hay objeto con ese id");
		}
		
		return project;
	}
	
	public Project save(Project project) {
		Project newProject = null;
		
		if (project == null) {
			throw new IllegalArgumentException("No hay objeto proyecto para guardar");
		}
		
		newProject = this.em.merge(project);
		
		return newProject;
		
	}
	
	public Project delete(Project project) {
		Project delProject = null;
		
		if (project == null) {
			throw new IllegalArgumentException("No hay objeto proyecto para eliminar");
		}
		
		project.setEstado(Constantes.ESTADO_INACTIVO);
		delProject = this.em.merge(project);
		
		return delProject;
	}
	
}
