package br.senai.sp.cfp132.PineappleWS.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.senai.sp.cfp132.PineappleWS.model.Tipo;

@Repository
public class TipoDao implements Dao {

	@PersistenceContext
	EntityManager manager;

	@Override
	public void inserir(Object o) {
		manager.persist((Tipo) o);

	}

	@Override
	public void alterar(Object o) {
		manager.merge((Tipo) o);

	}

	public List<Tipo> buscarTodos() {
		Query lista = manager.createQuery("SELECT t FROM Tipo t", Tipo.class);

		return lista.getResultList();
	}

	@Override
	public Object buscarId(Long id) {
		return manager.find(Tipo.class, id);
	}

}
