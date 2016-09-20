package br.senai.sp.cfp132.PineappleWS.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.sp.cfp132.PineappleWS.model.Modelo;
import br.senai.sp.cfp132.PineappleWS.model.Tipo;

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

	@Override
	public Object buscarId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Modelo> buscarModelo_Tipo(Tipo t) {
		TypedQuery<Modelo> lista = manager.createQuery(
				"SELECT m FROM Modelo m where m.tipo = :tipo", Modelo.class)
				.setParameter("tipo", t);
		List<Modelo> modelo = lista.getResultList();
		if (modelo.size() != 0) {
			return modelo;
		}
		return null;
	}

}
