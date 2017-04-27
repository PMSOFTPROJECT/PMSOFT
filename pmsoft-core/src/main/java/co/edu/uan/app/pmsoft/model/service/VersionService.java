package co.edu.uan.app.pmsoft.model.service;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import co.edu.uan.app.pmsoft.model.entity.Version;
import co.edu.uan.app.pmsoft.model.pojo.EstadosVersion;

@Remote
@Stateless
public class VersionService {
	
	@PersistenceContext(unitName = "pmsoft-unit")
	private EntityManager em;
	
	public List<Version> getAll() {
		CriteriaQuery<Version> criteria = this.em.getCriteriaBuilder().createQuery(Version.class);
		
		List<Version> lista = this.em.createQuery(criteria.select(criteria.from(Version.class))).getResultList();
				
		return lista;
	}
	
	public Version save(Version version) {
		Version newVersion = null;
		
		if (version == null) {
			throw new IllegalArgumentException("No hay objeto version para guardar");
		}
		
		newVersion = this.em.merge(version);
		
		return newVersion;		
	}
	
	public Version delete(Version version) {
		Version delVersion = null;
		
		if (version == null) {
			throw new IllegalArgumentException("No hay objeto proyecto para eliminar");
		}
		
		version.setEstado("INACTIVO");
		delVersion = this.em.merge(version);
		
		return delVersion;
	}
	
	public boolean getByNombreVersion(String nombre_version) {
		return false;
	}
	
}
