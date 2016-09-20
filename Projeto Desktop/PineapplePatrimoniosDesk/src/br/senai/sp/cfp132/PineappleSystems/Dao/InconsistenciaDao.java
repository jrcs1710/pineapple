package br.senai.sp.cfp132.PineappleSystems.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.sp.cfp132.PineappleSystems.model.Conferencia;
import br.senai.sp.cfp132.PineappleSystems.model.Inconsistencia;

@Repository
public class InconsistenciaDao implements Dao {

	private EntityManager manager = Factory.getManager();

	@Override
	public void inserir(Object o) {
		manager.persist((Inconsistencia) o);

	}

	@Override
	public void alterar(Object o) {
		manager.merge((Inconsistencia) o);

	}

	
	public List<Inconsistencia> buscarTodos() {
		Query lista = manager.createQuery("SELECT i FROM inconsistencia i",
				Inconsistencia.class);
		return lista.getResultList();
	}

	@Override
	public Object buscarId(Long id) {
		return manager.find(Inconsistencia.class, id);
	}

	public List<Inconsistencia> buscarInconsistencia_Conferencia(Conferencia c) {
		TypedQuery<Inconsistencia> lista = manager.createQuery(
				"SELECT i FROM inconsistencia i where i.conferencia = :conferencia",
				Inconsistencia.class).setParameter("conferencia", c);

		List<Inconsistencia> inc = lista.getResultList();
		if (inc.size() != 0) {
			return inc;
		}
		return null;
		
	}

}
