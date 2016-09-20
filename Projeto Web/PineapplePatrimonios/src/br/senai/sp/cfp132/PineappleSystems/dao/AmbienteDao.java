package br.senai.sp.cfp132.PineappleSystems.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.senai.sp.cfp132.PineappleSystems.model.Ambiente;
import br.senai.sp.cfp132.PineappleSystems.model.Funcionario;

@Repository
public class AmbienteDao implements Dao {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public void inserir(Object o) {
		manager.persist((Ambiente) o);

	}

	@Override
	public void alterar(Object o) {
		manager.merge((Ambiente) o);

	}

	public List<Ambiente> buscarTodos() {
		return null;
	}

	@Override
	public Ambiente buscarId(Long id) {
		System.out.println("ID>" + id);
		return manager.find(Ambiente.class, id);
	}

	public List<Ambiente> buscarTodosNew() {
		Query lista = manager.createQuery("SELECT a FROM Ambiente a",
				Ambiente.class);
		return lista.getResultList();
	}

	public List<Ambiente> buscarTodosAtivos() {
		Query lista = manager.createQuery(
				"SELECT a FROM Ambiente a WHERE a.status = true",
				Ambiente.class);
		return lista.getResultList();
	}

	public List<Ambiente> buscarTodosInativos() {
		Query lista = manager.createQuery(
				"SELECT a FROM Ambiente a WHERE a.status = false",
				Ambiente.class);
		return lista.getResultList();
	}

	public void excluir(Long id) {
		Ambiente a = (Ambiente) buscarId(id);
		manager.remove(a);

	}

	public List<Ambiente> buscarAmbiente_Funcionario(Funcionario func) {
		Query lista = manager.createQuery(
				"SELECT a FROM Ambiente a WHERE a.responsavel = :funcionario",
				Ambiente.class).setParameter("funcionario", func);

		return lista.getResultList();
	}

	public List<Ambiente> buscarAmbienteStatus_Funcionario(Funcionario func,
			boolean status) {
		Query lista = manager
				.createQuery(
						"SELECT a FROM Ambiente a WHERE a.responsavel = :funcionario AND a.status = :status",
						Ambiente.class).setParameter("funcionario", func)
				.setParameter("status", status);

		return lista.getResultList();
	}

	public List<Ambiente> buscarAmbiente_Nome(String pesquisarAmbiente) {
		Query lista = manager
				.createQuery("SELECT a FROM Ambiente a WHERE a.nome = :nome",
						Ambiente.class).setParameter("nome", pesquisarAmbiente);

		return lista.getResultList();
	}

	public Ambiente buscarAmbiente_NomeU(String pesquisarAmbiente) {
		Query lista = manager
				.createQuery("SELECT a FROM Ambiente a WHERE a.nome = :nome",
						Ambiente.class).setParameter("nome", pesquisarAmbiente);
		if (lista.getResultList().size() != 0) {
			return (Ambiente) lista.getResultList().get(0);
		}

		return null;
	}

	public void destivar(Object o) {
		((Ambiente) o).setStatus(false);
		manager.merge((Ambiente) o);

	}

	public void ativar(Object o) {
		((Ambiente) o).setStatus(true);
		manager.merge((Ambiente) o);

	}

}
