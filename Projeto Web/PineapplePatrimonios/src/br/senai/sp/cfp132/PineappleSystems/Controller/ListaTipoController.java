package br.senai.sp.cfp132.PineappleSystems.Controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.senai.sp.cfp132.PineappleSystems.dao.TipoDao;
import br.senai.sp.cfp132.PineappleSystems.model.Tipo;
import br.senai.sp.cfp132.PineappleSystems.util.Mensagens;
import br.senai.sp.cfp132.PineappleSystems.util.Util;

@Scope("session")
@Transactional
@Controller
public class ListaTipoController {

	@Autowired
	TipoDao tipoDao;

	private List<Tipo> listTipo;
	private List<Tipo> listTipoNome;

	private boolean todos;

	private boolean pesquisar;

	private String nomeTipo;

	private Tipo selectedTipo;

	public List<Tipo> getListTipoNome() {
		listTipoNome = tipoDao.buscarTipo_Nome(nomeTipo);
		return listTipoNome;
	}

	public void setListTipoNome(List<Tipo> listTipoNome) {
		this.listTipoNome = listTipoNome;
	}

	public boolean isPesquisar() {
		return pesquisar;
	}

	public void setPesquisar(boolean pesquisar) {
		this.pesquisar = pesquisar;
	}

	public Tipo getSelectedTipo() {
		return selectedTipo;
	}

	public void setSelectedTipo(Tipo selectedTipo) {
		this.selectedTipo = selectedTipo;
	}

	public String getNomeTipo() {
		return nomeTipo;
	}

	public void setNomeTipo(String nomeTipo) {
		this.nomeTipo = nomeTipo;
	}

	public List<Tipo> getListTipo() {
		listTipo = tipoDao.buscarTodos();
		return listTipo;
	}

	public void setListTipo(List<Tipo> listTipo) {
		this.listTipo = listTipo;
	}

	public boolean isTodos() {
		return todos;
	}

	public void setTodos(boolean todos) {
		this.todos = todos;
	}

	public void todosListener() {

		nomeTipo = null;
	}

	public void nomeListener() {

		todos = false;
	}

	public void pesquisar() {
		pesquisar = true;
	}

	public String prepararAlteracao() {
		Util.passarObjeto("selectedTipo", selectedTipo);
		return "cadastro_tipo.xhtml";
	}

	@PostConstruct
	public void init() {

		todos = false;
		nomeTipo = null;
	}

}
