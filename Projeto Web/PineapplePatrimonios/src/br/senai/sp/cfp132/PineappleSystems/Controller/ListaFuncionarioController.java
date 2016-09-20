package br.senai.sp.cfp132.PineappleSystems.Controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.senai.sp.cfp132.PineappleSystems.dao.AmbienteDao;
import br.senai.sp.cfp132.PineappleSystems.dao.FuncionarioDao;
import br.senai.sp.cfp132.PineappleSystems.model.Ambiente;
import br.senai.sp.cfp132.PineappleSystems.model.Cargo;
import br.senai.sp.cfp132.PineappleSystems.model.Funcionario;
import br.senai.sp.cfp132.PineappleSystems.util.Mensagens;
import br.senai.sp.cfp132.PineappleSystems.util.Util;

@Controller
@Transactional
@Scope("session")
public class ListaFuncionarioController {

	@Autowired
	FuncionarioDao funcDao;
	@Autowired
	AmbienteDao ambDao;
	private List<Ambiente> listAmbiente;

	private List<Funcionario> listFuncionario;
	private List<Funcionario> listFuncionarioAtivo;
	private List<Funcionario> listFuncionarioInativo;
	private List<Funcionario> listFuncionarioNome;
	private List<Funcionario> listFuncionarioCargo;

	private Funcionario selectedFuncionario = new Funcionario();
	private Cargo c = Cargo.RESP;

	private boolean todos;
	private boolean inativo;
	private boolean ativo;
	private boolean pesquisar;
	private boolean cargoB;

	private String nomeFuncionario;
	private Cargo cargoId;

	public boolean isCargoB() {
		return cargoB;
	}

	public void setCargoB(boolean cargoB) {
		this.cargoB = cargoB;
	}

	public List<Funcionario> getListFuncionarioCargo() {

		listFuncionarioCargo = funcDao.buscarFuncCargo(cargoId, true);
		return listFuncionarioCargo;
	}

	public void setListFuncionarioCargo(List<Funcionario> listFuncionarioCargo) {
		this.listFuncionarioCargo = listFuncionarioCargo;
	}

	public Cargo getCargoId() {
		return cargoId;
	}

	public void setCargoId(Cargo cargoId) {
		this.cargoId = cargoId;
	}

	public Cargo[] getCargo() {
		return Cargo.values();
	}

	public List<Funcionario> getListFuncionarioAtivo() {
		listFuncionarioAtivo = funcDao.buscarFuncStatus(true);
		return listFuncionarioAtivo;
	}

	public void setListFuncionarioAtivo(List<Funcionario> listFuncionarioAtivo) {
		this.listFuncionarioAtivo = listFuncionarioAtivo;
	}

	public List<Funcionario> getListFuncionarioInativo() {
		listFuncionarioInativo = funcDao.buscarFuncStatus(false);
		return listFuncionarioInativo;
	}

	public void setListFuncionarioInativo(
			List<Funcionario> listFuncionarioInativo) {
		this.listFuncionarioInativo = listFuncionarioInativo;
	}

	public List<Funcionario> getListFuncionarioNome() {
		listFuncionarioNome = funcDao.buscarFuncNome(nomeFuncionario);
		return listFuncionarioNome;
	}

	public void setListFuncionarioNome(List<Funcionario> listFuncionarioNome) {
		this.listFuncionarioNome = listFuncionarioNome;
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

	public boolean isPesquisar() {
		return pesquisar;
	}

	public void setPesquisar(boolean pesquisar) {
		this.pesquisar = pesquisar;
	}

	public String getNomeFuncionario() {
		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}

	public Cargo getC() {
		return c;
	}

	public void setC(Cargo c) {
		this.c = c;
	}

	public List<Ambiente> getListAmbiente() {
		if (selectedFuncionario.getId() != 0) {
			listAmbiente = ambDao
					.buscarAmbiente_Funcionario(selectedFuncionario);
		}

		return listAmbiente;
	}

	public void setListAmbiente(List<Ambiente> listAmbiente) {
		this.listAmbiente = listAmbiente;
	}

	public List<Funcionario> getListFuncionario() {
		listFuncionario = funcDao.buscarTodos();
		return listFuncionario;
	}

	public void setListFuncionario(List<Funcionario> listFunc) {
		this.listFuncionario = listFunc;
	}

	public Funcionario getSelectedFuncionario() {

		return selectedFuncionario;
	}

	public void setSelectedFuncionario(Funcionario selectedFuncionario) {

		this.selectedFuncionario = selectedFuncionario;
	}

	public String prepararAlteracao() {
		Util.passarObjeto("selectedFuncionario", selectedFuncionario);

		return "alterar_funcionario.xhtml";
	}

	public void ativar() {
		selectedFuncionario.setStatus(true);
		funcDao.alterar(selectedFuncionario);
		Mensagens.informacao(selectedFuncionario.getNome() + " foi ativado",
				null);
	}

	public void desativar() {
		List<Ambiente> listA = ambDao.buscarTodosNew();
		if (selectedFuncionario.getId() != ((Funcionario) Util
				.carregarObjeto("usuario")).getId()) {

			if (listA != null) {

				if (ambDao.buscarAmbiente_Funcionario(selectedFuncionario).size() != 0) {
					
					Mensagens.erro("Não foi possível inativar o usuário "
							+ selectedFuncionario.getNome()
							+ ". Existem ambientes sobre sua monitoria.", null);
				} else {
					selectedFuncionario.setStatus(false);
					funcDao.alterar(selectedFuncionario);
					Mensagens.informacao(selectedFuncionario.getNome()
							+ " foi desativado", null);
				}
			} else {
				selectedFuncionario.setStatus(false);
				funcDao.alterar(selectedFuncionario);
				Mensagens.informacao(selectedFuncionario.getNome()
						+ " foi desativado", null);
			}
		} else {
			Mensagens.erro(selectedFuncionario.getNome()
					+ " é o usuário logado no sistema", null);
		}
	}

	public void todosListener() {
		ativo = false;
		inativo = false;
		nomeFuncionario = null;
		pesquisar = false;
		cargoB = false;
	}

	public void ativosListener() {
		todos = false;
		inativo = false;
		nomeFuncionario = null;
		pesquisar = false;
		cargoB = false;
	}

	public void inativosListener() {
		ativo = false;
		todos = false;
		nomeFuncionario = null;
		pesquisar = false;
		cargoB = false;

	}

	public void nomeListener() {
		ativo = false;
		inativo = false;
		todos = false;
		pesquisar = false;
		cargoB = false;
	}

	public void pesquisar() {
		pesquisar = true;
		ativo = false;
		todos = false;
		nomeFuncionario = null;
		cargoB = false;
	}

	public void cargoListener() {
		ativo = false;
		inativo = false;
		todos = false;
		pesquisar = false;
		cargoB = true;
	}

	@PostConstruct
	public void init() {
		ativo = false;
		inativo = false;
		todos = false;
		nomeFuncionario = null;
		cargoB = false;
	}

}
