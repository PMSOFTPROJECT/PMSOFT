package co.edu.uan.app.pmsoft.model.service;

import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import co.edu.uan.app.pmsoft.model.entity.Documento;

@Remote
@Stateless
public class DocumentoService {
	
	@PersistenceContext(unitName = "pmsoft-unit")
	private EntityManager em;
	
	public List<Documento> getAll() {
		CriteriaQuery<Documento> criteria = this.em.getCriteriaBuilder().createQuery(Documento.class);
		
		List<Documento> lista = this.em.createQuery(criteria.select(criteria.from(Documento.class))).getResultList();
				
		return lista;
	}

	public Documento getById(long id) throws Exception {
		Documento doc = this.em.find(Documento.class, id);
		
		if (doc == null) {
			throw new Exception("No hay objeto con ese id");
		}
		
		return doc;
	}
	
	public Documento save(Documento doc) {
		Documento newDoc = null;
		
		if (doc == null) {
			throw new IllegalArgumentException("No hay objeto documento para guardar");
		}
		
		newDoc = this.em.merge(doc);
		
		return newDoc;		
	}
	
	public Documento delete(Documento doc) {
		Documento delDoc = null;
		
		if (doc == null) {
			throw new IllegalArgumentException("No hay objeto documento para eliminar");
		}
		
		doc.setEstado(Constantes.ESTADO_INACTIVO);
		delDoc = this.em.merge(doc);
		
		return delDoc;
	}	
}
