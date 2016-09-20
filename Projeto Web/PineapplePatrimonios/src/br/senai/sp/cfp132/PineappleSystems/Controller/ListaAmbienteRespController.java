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

@Transactional
@Scope("session")
@Controller
public class ListaAmbienteRespController {


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
	private List<Ambiente> pAmbiente;
	private Funcionario func;
	private List<Ambiente> listAmbienteAtivo;
	
	private boolean todos;
	
	
	
	
	
	
	public List<Ambiente> getListAmbienteAtivo() {
		listAmbienteAtivo = ambDao.buscarTodosAtivos();
		return listAmbienteAtivo;
	}

	public void setListAmbienteAtivo(List<Ambiente> listAmbienteAtivo) {
		this.listAmbienteAtivo = listAmbienteAtivo;
	}

	public Funcionario getFunc() {
		return func;
	}

	public void setFunc(Funcionario func) {
		this.func = func;
	}

	public boolean isTodos() {
		return todos;
	}

	public void setTodos(boolean todos) {
		this.todos = todos;
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
		listAmbiente = ambDao.buscarAmbienteStatus_Funcionario(func, true);
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

	public String solicitarMovimentacao() {
		

		if (selectedPatrimonios.size() == 0) {

			Mensagens.erro(
					"Erro: Selecione ao menos 1 patrimônio para a solicitação",
					null);
		} else {
			
			itemList = new ArrayList<ItemTransferencia>();
			
			ambiente = ambDao.buscarId(tAmbiente);
			
			
			for (int i = 0; i < selectedPatrimonios.size(); i++) {
				itemTansferencia = new ItemTransferencia();
				itemTansferencia.setStatus(true);
				itemTansferencia.setPatrimonio(selectedPatrimonios.get(i));
				itemTansferencia.setTipoMovimentacao(TipoMovimentacao.TRANSF);
				itemDao.inserir(itemTansferencia);
				itemList.add(itemTansferencia);
				
				
				
			

			}
			
			movimentacao = new Movimentacao();
		
			movimentacao.setObsSolicitante(observacoes);
			
			movimentacao.setDestino(ambiente);
			movimentacao.setSolicitante(func);
			movimentacao.setPatrimonios(itemList);
			movimentacao.setStatus(StatusRequisicao.ABERTO);
			movimentacao.setAtual(selectedAmbiente);
			movimentacao.setDtSolicitacao(Calendar.getInstance());
			
			movDao.inserir(movimentacao);
			
			
			
			
			
			Mensagens.informacao("Solicitação efetuada com sucesso!", null);
		}

		return "seus_ambientes.xhtml";
	}

	@PostConstruct
	public void init() {
		if (Util.carregarObjeto("usuario") != null) {
			func = (Funcionario) Util.carregarObjeto("usuario");
		}
		
		todos = false;
		listAmbiente = new ArrayList<Ambiente>();
		listPatrimonio = new ArrayList<Patrimonio>();
		itemList = new ArrayList<ItemTransferencia>();
		selectedPatrimonios = new ArrayList<Patrimonio>();
		
	}

	public String prepararMovimentacao(){
		if (selectedPatrimonios.size() != 0) {
			return "solicitacao_movimentacao.xhtml";
		}else {
			Mensagens.erro("Erro! Nenhum patrimônio selecionado", null);
			return null;
		}
		
	}
	public void clickCheckListener(){
		pesquisarAmbiente = null;
		
	}

	public void clickListener(){
		todos = false;
		
	}
public void verificarPatrimoniosSelecionados(){
		
		if (selectedPatrimonios.size() == 0) {
			Mensagens.erro("Selecione pelo menos um patrimônio!", null);
			
		}else {
			Util.passarObjeto("patrimonioBaixa", selectedPatrimonios);
			Util.redirecionarPagina("baixa_patrimonio.xhtml");
		}
	}
	
	
}
