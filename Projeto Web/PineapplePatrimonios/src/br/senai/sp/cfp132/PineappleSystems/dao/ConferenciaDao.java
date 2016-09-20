package br.senai.sp.cfp132.PineappleSystems.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import org.springframework.stereotype.Repository;

import br.senai.sp.cfp132.PineappleSystems.model.Conferencia;

@Repository
public class ConferenciaDao implements Dao {

	@PersistenceContext
	private EntityManager manager;
	
	
	@Override
	public void inserir(Object o) {
		manager.persist((Conferencia)o);
		
	}

	@Override
	public void alterar(Object o) {
		manager.merge((Conferencia)o);
		
	}

	
	public List<Conferencia> buscarTodos() {
		Query lista = manager.createQuery("SELECT c FROM conferencia c", Conferencia.class);
		return lista.getResultList();
	}

	@Override
	public Object buscarId(Long id) {
		return manager.find(Conferencia.class, id);
	}
	
	

}
