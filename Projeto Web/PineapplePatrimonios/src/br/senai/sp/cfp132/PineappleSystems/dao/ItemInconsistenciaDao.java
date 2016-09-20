package br.senai.sp.cfp132.PineappleSystems.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.senai.sp.cfp132.PineappleSystems.model.ItemInconsistencia;

@Repository
public class ItemInconsistenciaDao implements Dao{

	@PersistenceContext
	EntityManager manager;

	@Override
	public void inserir(Object o) {
		manager.persist((ItemInconsistencia)o);
		
	}

	@Override
	public void alterar(Object o) {
		manager.merge((ItemInconsistencia)o);
		
	}

	
	public List<ItemInconsistencia> buscarTodos() {
		Query lista = manager.createQuery("SELECT i FROM iteminconsistencia i", ItemInconsistencia.class);
		return lista.getResultList();
	}

	@Override
	public Object buscarId(Long id) {
		return manager.find(ItemInconsistencia.class, id);
	}
	
	
	
	
	
	
}
