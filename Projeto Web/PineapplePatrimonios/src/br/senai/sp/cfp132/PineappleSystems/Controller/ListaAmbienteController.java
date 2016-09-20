package br.senai.sp.cfp132.PineappleSystems.Controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Controller;

import br.senai.sp.cfp132.PineappleSystems.dao.AmbienteDao;
import br.senai.sp.cfp132.PineappleSystems.dao.ItemTransferenciaDao;
import br.senai.sp.cfp132.PineappleSystems.dao.MovimentacaoDao;
import br.senai.sp.cfp132.PineappleSystems.dao.PatrimonioDao;
import br.senai.sp.cfp132.PineappleSystems.model.Ambiente;
import br.senai.sp.cfp132.PineappleSystems.model.Funcionario;
import br.senai.sp.cfp132.PineappleSystems.model.ItemTransferencia;
import br.senai.sp.cfp132.PineappleSystems.model.Movimentacao;
import br.senai.sp.cfp132.PineappleSystems.model.Patrimonio;
import br.senai.sp.cfp132.PineappleSystems.model.StatusRequisicao;
import br.senai.sp.cfp132.PineappleSystems.model.TipoMovimentacao;
import br.senai.sp.cfp132.PineappleSystems.util.Mensagens;
import br.senai.sp.cfp132.PineappleSystems.util.Util;

@Controller
@Scope("session")
@Transactional
public class ListaAmbienteController {

	@Autowired
	AmbienteDao ambDao;
	@Autowired
	PatrimonioDao patDao;
	@Autowired
	MovimentacaoDao movDao;
	@Autowired
	ItemTransferenciaDao itemDao;

	private Ambiente ambiente = new Ambiente();
	private List<Ambiente> listAmbiente;
	private Ambiente selectedAmbiente = new Ambiente();
	private List<Patrimonio> listPatrimonio;
	private List<Patrimonio> selectedPatrimonios;
	private Movimentacao movimentacao;
	private ItemTransferencia itemTansferencia = new ItemTransferencia();
	private List<ItemTransferencia> itemList;
	private Long tAmbiente;
	private String observacoes;
	private boolean todos;
	private boolean ativo;
	private boolean inativo;
	private List<Ambiente> pAmbiente;
	private List<Ambiente> listAmbienteAtivo;
	private List<Ambiente> listAmbienteInativo;
	
	
	
	
	
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public boolean isInativo() {
		return inativo;
	}

	public void setInativo(boolean inativo) {
		this.inativo = inativo;
	}

	public List<Ambiente> getListAmbienteInativo() {
		listAmbienteInativo = ambDao.buscarTodosInativos();
		return listAmbienteInativo;
	}

	public void setListAmbienteInativo(List<Ambiente> listAmbienteInativo) {
		this.listAmbienteInativo = listAmbienteInativo;
	}

	public List<Ambiente> getListAmbienteAtivo() {
		listAmbienteAtivo =  ambDao.buscarTodosAtivos();
		return listAmbienteAtivo;
	}

	public void setListAmbienteAtivo(List<Ambiente> listAmbienteAtivo) {
		this.listAmbienteAtivo = listAmbienteAtivo;
	}

	public List<Ambiente> getpAmbiente() {
		pAmbiente = ambDao.buscarAmbiente_Nome(pesquisarAmbiente);
		return pAmbiente;
	}

	public void setpAmbiente(List<Ambiente> pAmbiente) {
		this.pAmbiente = pAmbiente;
	}
	private String pesquisarAmbiente;
	
	
	public String getPesquisarAmbiente() {
		
		return pesquisarAmbiente;
	}

	public void setPesquisarAmbiente(String pesquisarAmbiente) {
		this.pesquisarAmbiente = pesquisarAmbiente;
	}


	public boolean isTodos() {
		return todos;
	}

	public void setTodos(boolean todos) {
		this.todos = todos;
	}

	public Long gettAmbiente() {
		return tAmbiente;
	}

	public void settAmbiente(Long tAmbiente) {
		this.tAmbiente = tAmbiente;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Movimentacao getMovimentacao() {
		return movimentacao;
	}

	public void setMovimentacao(Movimentacao movimentacao) {
		this.movimentacao = movimentacao;
	}

	public ItemTransferencia getItemTansferencia() {
		return itemTansferencia;
	}

	public void setItemTansferencia(ItemTransferencia itemTansferencia) {
		this.itemTansferencia = itemTansferencia;
	}

	public List<ItemTransferencia> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemTransferencia> itemList) {
		this.itemList = itemList;
	}

	public List<Patrimonio> getSelectedPatrimonios() {
			
		return selectedPatrimonios;
	}

	public void setSelectedPatrimonios(List<Patrimonio> selectedPatrimonios) {

		this.selectedPatrimonios = selectedPatrimonios;
	}

	public List<Patrimonio> getListPatrimonio() {
		listPatrimonio = patDao.buscarTodos();
		return listPatrimonio;
	}

	public void setListPatrimonio(List<Patrimonio> listPatrimonio) {
		this.listPatrimonio = listPatrimonio;
	}

	public Ambiente getSelectedAmbiente() {

		return selectedAmbiente;
	}

	public void setSelectedAmbiente(Ambiente selecteAmbiente) {
		ambiente = selecteAmbiente;
		Util.passarObjeto("ambiente", ambiente);
		this.selectedAmbiente = selecteAmbiente;
	}

	public List<Ambiente> getListAmbiente() {
		listAmbiente = ambDao.buscarTodosNew();
		return listAmbiente;
	}

	public void setListAmbiente(List<Ambiente> listAmbiente) {
		this.listAmbiente = listAmbiente;
	}

	public Ambiente getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}

	public String alterar() {
		
		Util.passarObjeto("selectedAmbiente", selectedAmbiente);
		
	return "cadastro_ambiente.xhtml";
	}

	public void desativar() {
		

		if (selectedAmbiente.getPatrimonios().size() != 0) {
			Mensagens
					.erro("Não foi possível desativar o ambiente selecionado! Existem patrimonios no ambiente!",
							null);
		} else {
			ambDao.destivar(selectedAmbiente);
			Mensagens.informacao("Sucesso! Ambiente desativado com sucesso!",
					null);
		}

	}
	public void ativar(){
		ambDao.ativar(selectedAmbiente);
		Mensagens.informacao("Sucesso! Ambiente ativado com sucesso!",
				null);
	}

	public String efetuarMovimentacao() {
		

		if (selectedPatrimonios.size() == 0) {

			Mensagens.erro(
					"Erro: Selecione ao menos 1 patrimônio para a solicitação",
					null);
		} else {
			
			itemList = new ArrayList<ItemTransferencia>();
			Funcionario avaliador = (Funcionario) Util
					.carregarObjeto("usuario");
			ambiente = ambDao.buscarId(tAmbiente);
			Patrimonio p = new Patrimonio();
			List<Patrimonio> lista = selectedPatrimonios;
			
			for (int i = 0; i < selectedPatrimonios.size(); i++) {
				itemTansferencia = new ItemTransferencia();
				itemTansferencia.setStatus(true);
				itemTansferencia.setPatrimonio(selectedPatrimonios.get(i));
				itemTansferencia.setTipoMovimentacao(TipoMovimentacao.TRANSF);
				itemDao.inserir(itemTansferencia);
				itemList.add(itemTansferencia);
				p = lista.get(i);
				p.setAmbiente(ambiente);
				patDao.alterar(p);
				selectedAmbiente.getPatrimonios().remove(p);
				ambiente.getPatrimonios().add(p);
			

			}
			
			movimentacao = new Movimentacao();
			movimentacao.setAvaliador(avaliador);
			movimentacao.setObsSolicitante(observacoes);
			movimentacao.setObsAprovador(observacoes);
			movimentacao.setDestino(ambiente);
			movimentacao.setSolicitante(avaliador);
			movimentacao.setPatrimonios(itemList);
			movimentacao.setStatus(StatusRequisicao.APROV);
			movimentacao.setAtual(selectedAmbiente);
			movimentacao.setDtSolicitacao(Calendar.getInstance());
			movimentacao.setDataAprovacao(Calendar.getInstance());
			movDao.inserir(movimentacao);
			
			ambDao.alterar(selectedAmbiente);
			
			ambDao.alterar(ambiente);
			
			Mensagens.informacao("Movimentação efetuada com sucesso!", null);
		}

		return "lista_ambiente.xhtml";
	}

	@PostConstruct
	public void init() {
		listAmbiente = new ArrayList<Ambiente>();
		listPatrimonio = new ArrayList<Patrimonio>();
		itemList = new ArrayList<ItemTransferencia>();
		selectedPatrimonios = new ArrayList<Patrimonio>();
		todos = false;
		inativo = false;
		ativo = false;
	}

	public String prepararMovimentacao(){
		if (selectedPatrimonios.size() != 0) {
			return "movimentacao_ambiente_gerente.xhtml";
		}else {
			Mensagens.erro("Erro! Nenhum patrimônio selecionado", null);
			return null;
		}
		
	}
	public void clickCheckListener(){
		pesquisarAmbiente = null;
		ativo = false;
		inativo = false;
	}
	public void ativoListener(){
		pesquisarAmbiente = null;
		todos = false;
		inativo = false;
	}
	public void inativoListener(){
		pesquisarAmbiente = null;
		ativo = false;
		todos = false;
	}
	public void clickListener(){
		todos = false;
		ativo = false;
		inativo = false;
	}
}
