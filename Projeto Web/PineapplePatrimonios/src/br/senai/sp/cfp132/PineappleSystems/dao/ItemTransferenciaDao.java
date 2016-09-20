package br.senai.sp.cfp132.PineappleSystems.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.senai.sp.cfp132.PineappleSystems.model.ItemTransferencia;

@Repository
public class ItemTransferenciaDao implements Dao{

	@PersistenceContext
	EntityManager manager;

	@Override
	public void inserir(Object o) {
		manager.persist((ItemTransferencia)o);
		
	}

	@Override
	public void alterar(Object o) {
		manager.merge((ItemTransferencia)o);
		
	}

	public List<ItemTransferencia> buscarTodos() {
		Query lista = manager.createQuery("SELECT i FROM iteminconsistencia i", ItemTransferencia.class);
		return lista.getResultList();
	}

	@Override
	public Object buscarId(Long id) {
		return manager.find(ItemTransferencia.class, id);
	}
	
	
	
	
}
