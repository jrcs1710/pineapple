package br.senai.sp.cfp132.PineappleWS.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.sp.cfp132.PineappleWS.model.Usuario;

@Repository
public class UsuarioDao implements Dao {

	@PersistenceContext
	private EntityManager manager;

	public String gerarSenha() {
		String letrasM = "ABCDEFGHIJK";
		String charsEspeciais = "+-*<>?!@#$%&";
		int N = letrasM.length();
		int M = charsEspeciais.length();
		Random rd = new Random();
		StringBuilder sb = new StringBuilder();
		sb.append(letrasM.charAt(rd.nextInt(N)));
		sb.append(charsEspeciais.charAt(rd.nextInt(M)));


		return new BigInteger(30, rd).toString(32) + rd.nextInt(9)
				+ sb.toString();

	}

	@Override
	public void inserir(Object o) {

		manager.persist((Usuario) o);
	}

	@Override
	public void alterar(Object o) {
		manager.merge((Usuario) o);
	}

	public List<Usuario> buscarTodos() {
		Query lista = manager.createQuery("SELECT u FROM usuario u",
				Usuario.class);
		return lista.getResultList();
	}

	@Override
	public Object buscarId(Long id) {
		return manager.find(Usuario.class, id);
	}

	public Usuario buscarNomeSenha(String nome, String senha) {

		TypedQuery<Usuario> query = manager
				.createQuery(
						"SELECT u from Usuario u WHERE u.nomeUsuario = :nome AND u.senha = :senha",
						Usuario.class).setParameter("nome", nome)
				.setParameter("senha", senha);

		List<Usuario> u = query.getResultList();
		if (u.size() != 0) {
			Usuario user = u.get(0);
			return user;
		}

		return null;
	}

	public Usuario buscarNome(String nome) {
		TypedQuery<Usuario> query = manager.createQuery(
				"SELECT u from Usuario u WHERE u.nomeUsuario = :nome ",
				Usuario.class).setParameter("nome", nome);

		List<Usuario> u = query.getResultList();
		if (u.size() != 0) {
			Usuario user = u.get(0);
			return user;
		}

		return null;
	}

}
