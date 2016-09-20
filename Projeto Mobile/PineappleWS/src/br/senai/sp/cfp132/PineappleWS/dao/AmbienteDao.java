package br.senai.sp.cfp132.PineappleWS.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.senai.sp.cfp132.PineappleWS.model.Ambiente;
import br.senai.sp.cfp132.PineappleWS.model.Conferencia;
import br.senai.sp.cfp132.PineappleWS.model.Funcionario;

@Repository
public class AmbienteDao implements Dao{

	@PersistenceContext
	private EntityManager manager;
	
	
	@Override
	public void inserir(Object o) {
		manager.persist((Ambiente) o);
		
	}

	@Override
	public void alterar(Object o) {
		manager.merge((Ambiente)o);
		
	}


	public List<Ambiente> buscarTodos() {
		return null;
	}

	@Override
	public Object buscarId(Long id) {
		return manager.find(Ambiente.class, id);
	}

	public List<Ambiente> buscarTodosNew() {
		Query lista = manager.createQuery("SELECT a FROM Ambiente a", Ambiente.class);
		return lista.getResultList();
	}
	
	public void excluir(Long id){
		Ambiente a = (Ambiente)buscarId(id);
		manager.remove(a);
		
	}

	public List<Ambiente> buscarAmbiente_Funcionario(Funcionario func) {
		Query lista = manager.createQuery(
				"SELECT a FROM Ambiente a WHERE a.responsavel = :funcionario",
				Ambiente.class).setParameter("funcionario", func);

		return lista.getResultList();
	}

	public List<Ambiente> buscarAmbiente_Conferencia(Conferencia conf) {
		Query lista = manager.createQuery(
				"SELECT a FROM Ambiente a WHERE a = :confAmbiente",
				Ambiente.class).setParameter("confAmbiente", conf.getAmbiente());

		return lista.getResultList();
	}
	
	
}
