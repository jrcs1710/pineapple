package br.senai.sp.cfp132.PineappleSystems.Controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import br.senai.sp.cfp132.PineappleSystems.dao.PatrimonioDao;

import br.senai.sp.cfp132.PineappleSystems.model.Patrimonio;
import br.senai.sp.cfp132.PineappleSystems.util.Mensagens;
import br.senai.sp.cfp132.PineappleSystems.util.Util;

@Controller
@Transactional
@Scope("session")
public class ListaPatrimonioGerenteController {

	@Autowired
	PatrimonioDao patDao;
	
	

	private List<Patrimonio> listPatrimonio;
	private List<Patrimonio> listPatrimonioTodos;
	private List<Patrimonio> selectedPatrimonios;
	private List<Patrimonio> listPatrimonioAtivo;
	private List<Patrimonio> listPatrimonioInativo;

	private boolean pesquisar;

	private String cdPatrimonio;
	private boolean todos;
	private boolean inativo;
	private boolean ativo;
	private Patrimonio selectedPatrimonioEdit;
	
	

	
	public List<Patrimonio> getListPatrimonioAtivo() {
		listPatrimonioAtivo = patDao.buscarListPatrimonio_Ativo();
		return listPatrimonioAtivo;
	}

	public void setListPatrimonioAtivo(List<Patrimonio> listPatrimonioAtivo) {
		this.listPatrimonioAtivo = listPatrimonioAtivo;
	}

	public List<Patrimonio> getListPatrimonioInativo() {
		listPatrimonioInativo = patDao.buscarListPatrimonio_Baixa();
		return listPatrimonioInativo;
	}

	public void setListPatrimonioInativo(List<Patrimonio> listPatrimonioInativo) {
		this.listPatrimonioInativo = listPatrimonioInativo;
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

	public List<Patrimonio> getSelectedPatrimonios() {
		return selectedPatrimonios;
	}

	public void setSelectedPatrimonios(List<Patrimonio> selectedPatrimonios) {
		this.selectedPatrimonios = selectedPatrimonios;
	}

	public Patrimonio getSelectedPatrimonioEdit() {
		return selectedPatrimonioEdit;
	}

	public void setSelectedPatrimonioEdit(Patrimonio selectedPatrimonioEdit) {
		this.selectedPatrimonioEdit = selectedPatrimonioEdit;
	}

	public List<Patrimonio> getListPatrimonioTodos() {
		listPatrimonioTodos = patDao.buscarTodos();
		return listPatrimonioTodos;
	}

	public void setListPatrimonioTodos(List<Patrimonio> listPatrimonioTodos) {
		this.listPatrimonioTodos = listPatrimonioTodos;
	}

	public boolean isTodos() {
		return todos;
	}

	public void setTodos(boolean todos) {
		this.todos = todos;
	}

	public String getCdPatrimonio() {
		return cdPatrimonio;
	}

	public void setCdPatrimonio(String cdPatrimonio) {
		this.cdPatrimonio = cdPatrimonio;
	}

	public boolean isPesquisar() {
		return pesquisar;
	}

	public void setPesquisar(boolean pesquisar) {
		this.pesquisar = pesquisar;
	}

	public List<Patrimonio> getListPatrimonio() {

		listPatrimonio = patDao.buscarListPatrimonio_cdPatrimonio(cdPatrimonio);

		return listPatrimonio;
	}

	public void setListPatrimonio(List<Patrimonio> listPatrimonio) {
		this.listPatrimonio = listPatrimonio;
	}

	@PostConstruct
	public void init() {
		pesquisar = false;
		todos = false;
		inativo = false;
		ativo = false;
	}

	public void pesquisar() {

		pesquisar = true;

	}

	public void clickListener() {
		todos = false;
		inativo = false;
		ativo = false;
	}
	public void inativosListener() {
		todos = false;
		cdPatrimonio = null;
		ativo = false;
	}
	public void ativosListener() {
		inativo = false;
		todos = false;
		cdPatrimonio = null;
	}

	public void clickCheckListener() {
		cdPatrimonio = null;
		inativo = false;
		ativo = false;
	}
	
	public String prepararAlteracao(){
		
		Util.passarObjeto("patrimonioAlt", selectedPatrimonioEdit);
		
		return "alterar_patrimonio.xhtml";
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
