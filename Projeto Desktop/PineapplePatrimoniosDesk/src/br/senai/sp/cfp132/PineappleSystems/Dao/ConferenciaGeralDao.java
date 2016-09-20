package br.senai.sp.cfp132.PineappleSystems.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;




import br.senai.sp.cfp132.PineappleSystems.model.ConferenciaGeral;

@Repository
public class ConferenciaGeralDao implements Dao {

	private EntityManager manager = Factory.getManager();

	@Override
	public void inserir(Object o) {
		manager.persist((ConferenciaGeral)o);
		
	}

	@Override
	public void alterar(Object o) {
		manager.merge((ConferenciaGeral)o);
		
	}


	public List<ConferenciaGeral> buscarTodos() {
		Query lista = manager.createQuery("SELECT c FROM conferenciageral c", ConferenciaGeral.class);
		return lista.getResultList();
	}

	@Override
	public Object buscarId(Long id) {
		return manager.find(ConferenciaGeral.class, id);
	}
	
	public ConferenciaGeral buscarNrConferencia(String nrConferencia) {
		TypedQuery<ConferenciaGeral> lista = manager.createQuery(
				"SELECT c FROM conferenciageral c where c.nrConferencia = :nrConferencia",
				ConferenciaGeral.class).setParameter("nrConferencia", nrConferencia);
		List<ConferenciaGeral> conferencia= lista.getResultList();
		if (conferencia.size() != 0) {
			return conferencia.get(0);
		}
		return null;
	}
	
	
}
