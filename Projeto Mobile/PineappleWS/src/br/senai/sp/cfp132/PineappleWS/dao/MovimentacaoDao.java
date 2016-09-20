package br.senai.sp.cfp132.PineappleWS.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.sp.cfp132.PineappleWS.model.Funcionario;
import br.senai.sp.cfp132.PineappleWS.model.Movimentacao;
import br.senai.sp.cfp132.PineappleWS.model.StatusRequisicao;

@Repository
public class MovimentacaoDao implements Dao {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public void inserir(Object o) {
		manager.persist((Movimentacao) o);

	}

	@Override
	public void alterar(Object o) {
		manager.merge((Movimentacao) o);

	}

	public List<Movimentacao> buscarTodos() {
		Query lista = manager.createQuery("SELECT m FROM Movimentacao m",
				Movimentacao.class);
		return lista.getResultList();
	}

	@Override
	public Object buscarId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Movimentacao> buscarMovimentacao_Data(Calendar data) {
		TypedQuery<Movimentacao> lista = manager.createQuery(
				"SELECT m FROM Movimentacao m where m.data = :data",
				Movimentacao.class).setParameter("data", data);

		List<Movimentacao> mov = lista.getResultList();
		if (mov.size() != 0) {
			return mov;
		}
		return null;
	}

	public List<Movimentacao> buscarMovimentacao_Solicitante(
			Funcionario solicitante) {
		TypedQuery<Movimentacao> lista = manager
				.createQuery(
						"SELECT m FROM Movimentacao m where m.solicitante = :solicitante",
						Movimentacao.class).setParameter("solicitante",
						solicitante);

		List<Movimentacao> mov = lista.getResultList();
		if (mov.size() != 0) {
			return mov;
		}
		return null;
	}

	public List<Movimentacao> buscarMovimentacao_Avaliador(Funcionario avaliador) {
		TypedQuery<Movimentacao> lista = manager.createQuery(
				"SELECT m FROM Movimentacao m where m.avaliador= :avaliador",
				Movimentacao.class).setParameter("avaliador", avaliador);

		List<Movimentacao> mov = lista.getResultList();
		if (mov.size() != 0) {
			return mov;
		}
		return null;
	}

	public List<Movimentacao> buscarMovimentacao_Aberta(StatusRequisicao status) {
		TypedQuery<Movimentacao> lista = manager.createQuery(
				"SELECT m FROM Movimentacao m where m.status = :status",
				Movimentacao.class).setParameter("status", status);

		List<Movimentacao> mov = lista.getResultList();
		if (mov.size() != 0) {
			return mov;
		}
		return null;
	}

}
