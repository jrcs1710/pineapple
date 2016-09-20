package br.senai.sp.cfp132.PineappleSystems.Controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.senai.sp.cfp132.PineappleSystems.dao.MovimentacaoDao;
import br.senai.sp.cfp132.PineappleSystems.model.Funcionario;
import br.senai.sp.cfp132.PineappleSystems.model.Movimentacao;
import br.senai.sp.cfp132.PineappleSystems.model.StatusRequisicao;
import br.senai.sp.cfp132.PineappleSystems.util.Util;

@Transactional
@Scope("session")
@Controller
public class ListaRequisicaoRespController {

	
	
	@Autowired
	MovimentacaoDao movDao;
	
	private boolean aprovado;
	private boolean recusado;
	
	private Funcionario func;
	
	private List<Movimentacao> listSolicitacaoAberta;
	private List<Movimentacao> listSolicitacaoRecusada;
	private List<Movimentacao> listSolicitacaoAprovada;
	
	

	public boolean isAprovado() {
		return aprovado;
	}


	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}


	public boolean isRecusado() {
		return recusado;
	}


	public void setRecusado(boolean recusado) {
		this.recusado = recusado;
	}


	public List<Movimentacao> getListSolicitacaoRecusada() {
		listSolicitacaoRecusada = movDao.buscarMovimentacaoStatus_Solicitante(func, StatusRequisicao.RECUS);
		return listSolicitacaoRecusada;
	}


	public void setListSolicitacaoRecusada(
			List<Movimentacao> listSolicitacaoRecusada) {
		this.listSolicitacaoRecusada = listSolicitacaoRecusada;
	}


	public List<Movimentacao> getListSolicitacaoAprovada() {
		listSolicitacaoAprovada = movDao.buscarMovimentacaoStatus_Solicitante(func, StatusRequisicao.APROV);
		return listSolicitacaoAprovada;
	}


	public void setListSolicitacaoAprovada(
			List<Movimentacao> listSolicitacaoAprovada) {
		this.listSolicitacaoAprovada = listSolicitacaoAprovada;
	}


	public Funcionario getFunc() {
		return func;
	}


	public void setFunc(Funcionario func) {
		this.func = func;
	}


	public List<Movimentacao> getListSolicitacaoAberta() {
		listSolicitacaoAberta = movDao.buscarMovimentacaoStatus_Solicitante(func, StatusRequisicao.ABERTO);
		return listSolicitacaoAberta;
	}


	public void setListSolicitacaoAberta(List<Movimentacao> listSolicitacaoAberta) {
		this.listSolicitacaoAberta = listSolicitacaoAberta;
	}
	
	
	
	public void aprovadoListener(){
		recusado = false;
	}
	
	public void recusadoListener(){
		aprovado = false;
	}

	@PostConstruct
	public void init(){
		if (Util.carregarObjeto("usuario") != null) {
			func = (Funcionario) Util.carregarObjeto("usuario");
		}
		aprovado = false;
		recusado = false;
	}
	
	
	
}