package br.senai.sp.cfp132.PineappleWS.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.senai.sp.cfp132.PineappleWS.model.Ambiente;
import br.senai.sp.cfp132.PineappleWS.model.Conferencia;
import br.senai.sp.cfp132.PineappleWS.model.ConferenciaGeral;

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
		Query lista = manager.createQuery("SELECT c FROM Conferencia c", Conferencia.class);
		return lista.getResultList();
	}
	
	public List<Conferencia> buscarConfAmbiente(Ambiente ambiente) {
		Query lista = manager.createQuery(
				"SELECT c FROM Conferencia c WHERE c.ambiente = :ambiente",
				Conferencia.class).setParameter("ambiente", ambiente);
		return lista.getResultList();
	}
	
	public List<Conferencia> buscarConf_ConfGeral(ConferenciaGeral confGeral) {
		Query lista = manager.createQuery(
				"SELECT c FROM Conferencia c WHERE c.dtInicio = :dtInicio",
				Conferencia.class).setParameter("dtInicio", confGeral.getDtInicio());
		return lista.getResultList();
	}

	@Override
	public Object buscarId(Long id) {
		return manager.find(Conferencia.class, id);
	}
	
	

}
