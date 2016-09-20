package br.senai.sp.cfp132.PineappleSystems.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;




import br.senai.sp.cfp132.PineappleSystems.model.ConferenciaGeral;

@Repository
public class ConferenciaGeralDao implements Dao {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public void inserir(Object o) {
		manager.persist((ConferenciaGeral)o);
		
	}

	@Override
	public void alterar(Object o) {
		manager.merge((ConferenciaGeral)o);
		
	}
	public ConferenciaGeral buscarUltima() {
		System.out.println("passou dao");
		TypedQuery<ConferenciaGeral> lista = manager.createQuery
				("SELECT c FROM ConferenciaGeral c order by c.id DESC", ConferenciaGeral.class);

		List<ConferenciaGeral> conferencia= lista.getResultList();
		if (conferencia.size() != 0) {
			System.out.println(conferencia.get(0).getNrConferencia());
			return conferencia.get(0);
		}
	
		return null;
	}

	public List<ConferenciaGeral> buscarTodos() {
		Query lista = manager.createQuery("SELECT c FROM ConferenciaGeral c", ConferenciaGeral.class);
		return lista.getResultList();
	}

	@Override
	public Object buscarId(Long id) {
		return manager.find(ConferenciaGeral.class, id);
	}
	
	public ConferenciaGeral buscarNrConferencia(String nrConferencia) {
		TypedQuery<ConferenciaGeral> lista = manager.createQuery(
				"SELECT c FROM Conferenciageral c where c.nrConferencia = :nrConferencia",
				ConferenciaGeral.class).setParameter("nrConferencia", nrConferencia);
		List<ConferenciaGeral> conferencia= lista.getResultList();
		if (conferencia.size() != 0) {
			return conferencia.get(0);
		}
		return null;
	}
	
	public List<ConferenciaGeral> buscarData(Calendar data) {
		TypedQuery<ConferenciaGeral> lista = manager.createQuery(
				"SELECT c FROM ConferenciaGeral c where c.dtInicio >= :data AND c.dtFim <= :data",
				ConferenciaGeral.class).setParameter("data", data);
		List<ConferenciaGeral> conferencia= lista.getResultList();
		if (conferencia.size() != 0) {
			return conferencia;
		}
		return null;
	}
}
