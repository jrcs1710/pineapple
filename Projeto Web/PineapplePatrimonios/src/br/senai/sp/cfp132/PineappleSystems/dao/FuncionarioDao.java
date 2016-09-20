package br.senai.sp.cfp132.PineappleSystems.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.sp.cfp132.PineappleSystems.model.Cargo;
import br.senai.sp.cfp132.PineappleSystems.model.Funcionario;
import br.senai.sp.cfp132.PineappleSystems.model.Usuario;

@Repository
public class FuncionarioDao implements Dao {

	@PersistenceContext
	private EntityManager manager;

	@Override
	
	public void inserir(Object o) {
	
			
			manager.persist((Funcionario) o);
			
	

	}

	@Override
	public void alterar(Object o) {

		manager.merge((Funcionario) o);

	}

	
	public List<Funcionario> buscarTodos() {
		Query lista = manager.createQuery("SELECT f FROM Funcionario f");

		return lista.getResultList();
	}

	@Override
	public Object buscarId(Long id) {
		return manager.find(Funcionario.class, id);
	}

	public Funcionario buscarUsuario(Usuario u) {

		TypedQuery<Funcionario> query = manager.createQuery(
				"SELECT f FROM Funcionario f WHERE f.usuario = :usuario",
				Funcionario.class).setParameter("usuario", u);
		List<Funcionario> lista = query.getResultList();

		if (lista.size() != 0) {
			return lista.get(0);
		}
		return null;

	}

	public List<Funcionario> buscarFuncCargo(Cargo c, boolean status) {
		TypedQuery<Funcionario> query = manager
				.createQuery(
						"SELECT f FROM Funcionario f WHERE f.cargo = :cargo AND f.status = :status",
						Funcionario.class).setParameter("cargo", c)
				.setParameter("status", status);

		return query.getResultList();
	}

	public Funcionario buscarFuncEmail(String email){
		TypedQuery<Funcionario> query = manager.createQuery("SELECT f FROM Funcionario f WHERE f.email = :email", Funcionario.class).setParameter("email", email); 
		Funcionario f;
		List<Funcionario> lista = query.getResultList();
		if (lista.size() != 0) {
			f = lista.get(0);
			return f;
		}
		return null;
	}
	
	public List<Funcionario> buscarFuncStatus(boolean status){
		TypedQuery<Funcionario> query = manager.createQuery("SELECT f FROM Funcionario f WHERE f.status = :status", Funcionario.class).setParameter("status", status); 
		
		
		return query.getResultList();
	}
	
	public List<Funcionario> buscarFuncNome(String nome){
		TypedQuery<Funcionario> query = manager.createQuery("SELECT f FROM Funcionario f WHERE f.nome = :nome", Funcionario.class).setParameter("nome", nome); 
		return query.getResultList();
	}
	
	
	
}
