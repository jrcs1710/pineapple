package br.senai.sp.cfp132.PineappleSystems.Controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.senai.sp.cfp132.PineappleSystems.dao.ModeloDao;
import br.senai.sp.cfp132.PineappleSystems.model.Modelo;
import br.senai.sp.cfp132.PineappleSystems.util.Mensagens;
import br.senai.sp.cfp132.PineappleSystems.util.Util;

@Transactional
@Scope("session")
@Controller
public class ListaModeloController {

	@Autowired
	ModeloDao modDao;

	private List<Modelo> listModeloAtivo;
	private List<Modelo> listModeloInativo;
	private List<Modelo> listModelo;
	private List<Modelo> listModeloNome;

	private boolean todos;
	private boolean inativo;
	private boolean ativo;
	private boolean pesquisar;

	private String nomeModelo;

	private Modelo selectedModelo;

	
	
	
	public boolean isPesquisar() {
		return pesquisar;
	}

	public void setPesquisar(boolean pesquisar) {
		this.pesquisar = pesquisar;
	}

	public Modelo getSelectedModelo() {
		return selectedModelo;
	}

	public void setSelectedModelo(Modelo selectedModelo) {
		this.selectedModelo = selectedModelo;
	}

	public List<Modelo> getListModeloNome() {
		listModeloNome = modDao.buscarListaModelo_Nome(nomeModelo);
		return listModeloNome;
	}

	public void setListModeloNome(List<Modelo> listModeloNome) {
		this.listModeloNome = listModeloNome;
	}

	public String getNomeModelo() {
		return nomeModelo;
	}

	public void setNomeModelo(String nomeModelo) {
		this.nomeModelo = nomeModelo;
	}

	public List<Modelo> getListModeloAtivo() {
		listModeloAtivo = modDao.buscarModelo_Ativo();
		return listModeloAtivo;
	}

	public void setListModeloAtivo(List<Modelo> listModeloAtivo) {
		this.listModeloAtivo = listModeloAtivo;
	}

	public List<Modelo> getListModeloInativo() {
		listModeloInativo = modDao.buscarModelo_Inativo();
		return listModeloInativo;
	}

	public void setListModeloInativo(List<Modelo> listModeloInativo) {
		this.listModeloInativo = listModeloInativo;
	}

	public List<Modelo> getListModelo() {
		listModelo = modDao.buscarTodos();
		return listModelo;
	}

	public void setListModelo(List<Modelo> listModelo) {
		this.listModelo = listModelo;
	}

	public boolean isTodos() {
		return todos;
	}

	public void setTodos(boolean todos) {
		this.todos = todos;
	}

	public boolean isInativo() {
		return inativo;
	}

	public void setInativo(boolean inativo) {
		this.inativo = inativo;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public void desativar() {
		selectedModelo.setStatus(false);
		modDao.alterar(selectedModelo);
		Mensagens.informacao(selectedModelo.getNome()
				+ " desativado com sucesso!", null);
	}

	public void ativar() {
		selectedModelo.setStatus(true);
		modDao.alterar(selectedModelo);
		Mensagens.informacao(selectedModelo.getNome()
				+ " ativado com sucesso!", null);
	}

	public void todosListener() {
		ativo = false;
		inativo = false;
		nomeModelo = null;
	}

	public void ativosListener() {
		todos = false;
		inativo = false;
		nomeModelo = null;
	}

	public void inativosListener() {
		ativo = false;
		todos = false;
		nomeModelo = null;
	}

	public void nomeListener() {
		ativo = false;
		inativo = false;
		todos = false;
	}
	
	public void pesquisar(){
		pesquisar = true;
	}
	
	public String prepararAlteracao(){
		Util.passarObjeto("selectedModelo", selectedModelo);
		return "cadastro_modelo.xhtml";
	}
	
	
	
	@PostConstruct
	public void init(){
		ativo = false;
		inativo = false;
		todos = false;
		nomeModelo = null;
	}

}
