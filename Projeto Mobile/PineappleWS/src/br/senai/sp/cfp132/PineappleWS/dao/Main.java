package br.senai.sp.cfp132.PineappleWS.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.senai.sp.cfp132.PineappleWS.model.Movimentacao;
import br.senai.sp.cfp132.PineappleWS.model.StatusRequisicao;

public class Main {
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("pineapplepatrimonio");
		EntityManager manager = factory.createEntityManager();
		TypedQuery<Movimentacao> lista = manager.createQuery(
				"SELECT m FROM Movimentacao m where m.status = :status",
				Movimentacao.class).setParameter("status", StatusRequisicao.ABERTO);
		
		List<Movimentacao> mov = lista.getResultList();
		Movimentacao movi = mov.get(0);
		
	}
}
