package br.senai.sp.cfp132.PineappleSystems.Controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.senai.sp.cfp132.PineappleSystems.dao.MovimentacaoDao;
import br.senai.sp.cfp132.PineappleSystems.model.Movimentacao;

@Transactional
@Controller
@Scope("session")
public class ListaHistoricoRequisicoesController {

	@Autowired
	MovimentacaoDao movDao;
	
	private List<Movimentacao> listRequisicoes;


	
	

	public List<Movimentacao> getListRequisicoes() {
		listRequisicoes = movDao.buscarTodos();
		return listRequisicoes;
	}

	public void setListRequisicoes(List<Movimentacao> listRequisicoes) {
		this.listRequisicoes = listRequisicoes;
	}
	
	
	
	
	
	
}
