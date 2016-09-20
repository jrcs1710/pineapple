package br.senai.sp.cfp132.PineappleSystems.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.sp.cfp132.PineappleSystems.model.Empresa;

@Repository
public class EmpresaDao implements Dao{
	private EntityManager manager = Factory.getManager();

	@Override
	public void inserir(Object o) {
		manager.persist((Empresa)o);
		
	}

	@Override
	public void alterar(Object o) {
		manager.merge((Empresa)o);
		
	}

	
	public List<Empresa> buscarTodos() {

TypedQuery<Empresa> query = manager.createQuery("Select e FROM Empresa e", Empresa.class);
		return query.getResultList();
	}

	@Override
	public Object buscarId(Long id) {
		return manager.find(Empresa.class, id);
	}
	
}
