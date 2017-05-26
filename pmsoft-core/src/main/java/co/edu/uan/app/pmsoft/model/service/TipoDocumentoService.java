package co.edu.uan.app.pmsoft.model.service;

import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import co.edu.uan.app.pmsoft.model.entity.TipoDocumento;
import co.edu.uan.app.pmsoft.model.pojo.Constantes;


@Remote
@Stateless
public class TipoDocumentoService {
	
	@PersistenceContext(unitName = "pmsoft-unit")
	private EntityManager em;
	
	public List<TipoDocumento> getAll() {
		
		CriteriaQuery<TipoDocumento> criteria = this.em.getCriteriaBuilder().createQuery(TipoDocumento.class);
		
		List<TipoDocumento> lista = this.em.createQuery(criteria.select(criteria.from(TipoDocumento.class))).getResultList();
				
		return lista;
	}

	public TipoDocumento getById(long id) throws Exception {
		
		TipoDocumento documento = this.em.find(TipoDocumento.class, id);
		
		if (documento == null) {
			throw new Exception("No hay objeto con ese id");
		}
		
		return documento;
	}
	
	public TipoDocumento save(TipoDocumento documento) {
		TipoDocumento newdocumento = null;
		
		if (documento == null) {
			throw new IllegalArgumentException("No hay objeto TipoDocumento para guardar");
		}
		
		newdocumento = this.em.merge(documento);
		
		return newdocumento;
		
	}
	
	public TipoDocumento delete(TipoDocumento documento) {
		TipoDocumento deldocumento = null;
		
		if (documento == null) {
			throw new IllegalArgumentException("No hay objeto TipoDocumento para eliminar");
		}
		
		documento.setEstado(Constantes.ESTADO_INACTIVO);
		deldocumento = this.em.merge(documento);
		
		return deldocumento;
	}
	
	public Boolean findDocumento(String nombre) throws Exception{
		Boolean result = false;
		CriteriaQuery<TipoDocumento> criteria = this.em.getCriteriaBuilder().createQuery(TipoDocumento.class);
		List<TipoDocumento> lista = this.em.createQuery("select t from TipoDocumento t where t.nombre LIKE :custName").setParameter("custName", nombre).getResultList();
		if(lista.size() == 0){
			result = true;
		}
		return result;
	}
	
}