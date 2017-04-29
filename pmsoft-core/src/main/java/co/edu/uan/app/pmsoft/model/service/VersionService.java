package co.edu.uan.app.pmsoft.model.service;

import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.uan.app.pmsoft.model.entity.Version;

@Remote
@Stateless
public class VersionService {
	
	@PersistenceContext(unitName = "pmsoft-unit")
	private EntityManager em;
	
	private static final Logger logger = LoggerFactory.getLogger(VersionService.class);
	
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
	
	@SuppressWarnings("unchecked")
	public boolean getByNombreVersion(String nombre_version) throws Exception {
		boolean result = false;
		
		List<Version> lista = this.em.createNamedQuery("Version.findByNombre").setParameter("nombre", nombre_version).getResultList();
				
		if(lista.size() == 0){
			
			return result;
			
		}
		
		result = true;
		
		return result;
	}
	
}
