package br.senai.sp.cfp132.PineappleSystems.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.sp.cfp132.PineappleSystems.model.Modelo;
import br.senai.sp.cfp132.PineappleSystems.model.Tipo;

@Repository
public class ModeloDao implements Dao {

	@PersistenceContext
	EntityManager manager;

	@Override
	public void inserir(Object o) {
		manager.persist((Modelo) o);

	}

	@Override
	public void alterar(Object o) {
		manager.merge((Modelo) o);

	}

	
	public List<Modelo> buscarTodos() {
		Query lista = manager.createQuery("SELECT m FROM Modelo m",
				Modelo.class);
		return lista.getResultList();
	}
	
	public List<Modelo> buscarModelo_Ativo() {
		Query lista = manager.createQuery("SELECT m FROM Modelo m WHERE m.status = true",
				Modelo.class);
		return lista.getResultList();
	}
	
	public List<Modelo> buscarModelo_Inativo() {
		Query lista = manager.createQuery("SELECT m FROM Modelo m WHERE m.status = false",
				Modelo.class);
		return lista.getResultList();
	}
	
	public Modelo buscarModelo_Nome(String nome) {
		Query lista = manager.createQuery("SELECT m FROM Modelo m WHERE m.nome = :nome",
				Modelo.class).setParameter("nome", nome);
		if (lista.getResultList().size() != 0) {
			Modelo m = (Modelo) lista.getResultList().get(0);
			return m;
		}
		return null;
	}

	public List<Modelo> buscarListaModelo_Nome(String nome) {
		Query lista = manager.createQuery("SELECT m FROM Modelo m WHERE m.nome = :nome",
				Modelo.class).setParameter("nome", nome);
		
		return lista.getResultList();
	}
	
	@Override
	public Object buscarId(Long id) {
		return manager.find(Modelo.class, id);
	}

	public List<Modelo> buscarModelo_Tipo(Tipo t) {
		TypedQuery<Modelo> lista = manager.createQuery(
				"SELECT m FROM Modelo m where m.tipo = :tipo", Modelo.class).setParameter("tipo", t);
		List<Modelo> modelo = lista.getResultList();
		if (modelo.size() != 0) {
			return modelo;
		}
		return null;
	}

	public List<Modelo> buscarModeloAtivo_Tipo(Tipo t) {
		TypedQuery<Modelo> lista = manager.createQuery(
				"SELECT m FROM Modelo m WHERE m.tipo = :tipo AND m.status = true", Modelo.class).setParameter("tipo", t);
		List<Modelo> modelo = lista.getResultList();
		if (modelo.size() != 0) {
			return modelo;
		}
		return null;
	}
	
}
