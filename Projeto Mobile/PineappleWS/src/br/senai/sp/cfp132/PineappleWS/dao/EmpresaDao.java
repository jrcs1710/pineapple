package br.senai.sp.cfp132.PineappleWS.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.senai.sp.cfp132.PineappleWS.model.Empresa;

@Repository
public class EmpresaDao implements Dao{

	@PersistenceContext
	private EntityManager manager;

	@Override
	public void inserir(Object o) {
		manager.persist((Empresa)o);
		
	}

	@Override
	public void alterar(Object o) {
		manager.merge((Empresa)o);
		
	}

	
	public List<Object> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object buscarId(Long id) {
		return manager.find(Empresa.class, id);
	}
	
}
