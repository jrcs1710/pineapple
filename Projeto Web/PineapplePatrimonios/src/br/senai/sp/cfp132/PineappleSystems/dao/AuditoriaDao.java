package br.senai.sp.cfp132.PineappleSystems.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.sp.cfp132.PineappleSystems.model.Auditoria;

@Repository
public class AuditoriaDao implements Dao {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public void inserir(Object o) {
		manager.persist((Auditoria) o);

	}

	@Override
	public void alterar(Object o) {
		manager.merge((Auditoria) o);

	}

	
	public List<Auditoria> buscarTodos() {
		Query lista = manager.createQuery("SELECT a FROM Auditoria a",
				Auditoria.class);
		return lista.getResultList();
	}

	@Override
	public Object buscarId(Long id) {
		return manager.find(Auditoria.class, id);
	}

	public Auditoria buscarNrAuditoria(String nrAuditoria) {
		TypedQuery<Auditoria> lista = manager.createQuery(
				"SELECT a FROM auditoria a where a.nrAuditoria = :nrAuditoria",
				Auditoria.class).setParameter("nrAuditoria", nrAuditoria);
		List<Auditoria> auditoria = lista.getResultList();
		if (auditoria.size() != 0) {
			return auditoria.get(0);
		}
		return null;
	}

}
