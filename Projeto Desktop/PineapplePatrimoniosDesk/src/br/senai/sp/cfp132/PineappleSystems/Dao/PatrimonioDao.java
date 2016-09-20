package br.senai.sp.cfp132.PineappleSystems.Dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.sp.cfp132.PineappleSystems.model.Ambiente;
import br.senai.sp.cfp132.PineappleSystems.model.Modelo;
import br.senai.sp.cfp132.PineappleSystems.model.Patrimonio;
import br.senai.sp.cfp132.PineappleSystems.model.Tipo;

@Repository
public class PatrimonioDao implements Dao {
	private EntityManager manager = Factory.getManager();
	@Override
	public void inserir(Object o) {
		manager.persist((Patrimonio) o);

	}

	@Override
	public void alterar(Object o) {
		manager.merge((Patrimonio) o);

	}

	public List<Patrimonio> buscarTodos() {
		Query lista = manager.createQuery("SELECT p FROM Patrimonio p",
				Patrimonio.class);
		return lista.getResultList();
	}

	@Override
	public Object buscarId(Long id) {
		
		return manager.find(Patrimonio.class, id);
	}

	public List<Patrimonio> buscarPatrimonio_Modelo(Modelo m) {
		TypedQuery<Patrimonio> lista = manager.createQuery(
				"SELECT p FROM Patrimonio p WHERE p.modelo = :modelo",
				Patrimonio.class).setParameter("modelo", m);

		List<Patrimonio> pat = lista.getResultList();
		if (pat.size() != 0) {
			return pat;
		}
		return null;
	}

	public List<Patrimonio> buscarPatrimonio_Tipo(Tipo t) {
		TypedQuery<Patrimonio> lista = manager.createQuery(
				"SELECT p FROM Patrimonio p WHERE p.modelo.tipo = :tipo",
				Patrimonio.class).setParameter("tipo", t);

		List<Patrimonio> pat = lista.getResultList();
		if (pat.size() != 0) {
			return pat;
		}
		return null;
	}

	public List<Patrimonio> buscarPatrimonio_DtEntrada(Calendar data) {
		TypedQuery<Patrimonio> lista = manager.createQuery(
				"SELECT p FROM Patrimonio p WHERE p.dtEntrada = :data",
				Patrimonio.class).setParameter("data", data);

		List<Patrimonio> pat = lista.getResultList();
		if (pat.size() != 0) {
			return pat;
		}
		return null;
	}

	public List<Patrimonio> buscarPatrimonio_DtSaida(Calendar data) {
		TypedQuery<Patrimonio> lista = manager.createQuery(
				"SELECT p FROM Patrimonio p WHERE p.dtSaida= :data",
				Patrimonio.class).setParameter("data", data);

		List<Patrimonio> pat = lista.getResultList();
		if (pat.size() != 0) {
			return pat;
		}
		return null;
	}

	public List<Patrimonio> buscarPatrimonio_Ambiente(Ambiente ambiente) {
		TypedQuery<Patrimonio> lista = manager.createQuery(
				"SELECT p FROM Patrimonio p WHERE p.ambiente = :ambiente",
				Patrimonio.class).setParameter("ambiente", ambiente);

		List<Patrimonio> pat = lista.getResultList();
		if (pat.size() != 0) {
			return pat;
		}
		return null;
	}
	public Patrimonio buscarPatrimonio_cdPatrimonio(String cdPatrimonio) {
		TypedQuery<Patrimonio> lista = manager.createQuery(
				"SELECT p FROM Patrimonio p WHERE p.cdPatrimonio = :cdPatrimonio",
				Patrimonio.class).setParameter("cdPatrimonio", cdPatrimonio);

		List<Patrimonio> pat = lista.getResultList();
		if (pat.size() != 0) {
			Patrimonio p = pat.get(0);
			return p;
		}
		return null;
	}

	public List<Patrimonio> buscarListPatrimonio_cdPatrimonio(String cdPatrimonio) {
		TypedQuery<Patrimonio> lista = manager.createQuery(
				"SELECT p FROM Patrimonio p WHERE p.cdPatrimonio = :cdPatrimonio",
				Patrimonio.class).setParameter("cdPatrimonio", cdPatrimonio);

		List<Patrimonio> pat = lista.getResultList();
		if (pat.size() != 0) {
			return pat;
		}
		return null;
	}

	public List<Patrimonio> buscarListPatrimonio_Ativo() {
		TypedQuery<Patrimonio> lista = manager.createQuery(
				"SELECT p FROM Patrimonio p WHERE p.dtSaida = null",
				Patrimonio.class);

		List<Patrimonio> pat = lista.getResultList();
		if (pat.size() != 0) {
			return pat;
		}
		return null;
	}
	public List<Patrimonio> buscarListPatrimonio_Baixa() {
		TypedQuery<Patrimonio> lista = manager.createQuery(
				"SELECT p FROM Patrimonio p WHERE p.dtSaida != null",
				Patrimonio.class);

		List<Patrimonio> pat = lista.getResultList();
		if (pat.size() != 0) {
			return pat;
		}
		return null;
	}
	
}
