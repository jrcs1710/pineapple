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
@Scope("view")
@Transactional
public class CadAmbienteController {

	@Autowired
	AmbienteDao ambDao;
	@Autowired
	FuncionarioDao funcDao;
	private Ambiente ambiente = new Ambiente();
	private List<Funcionario> func;
	private Funcionario funcionario = new Funcionario();
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Ambiente getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}

	public List<Funcionario> getFunc() {
		func = funcDao.buscarFuncCargo(Cargo.RESP, true);
		return func;
	}

	public void setFunc(List<Funcionario> func) {
		this.func = func;
	}

	public void salvar() {

		funcionario = (Funcionario) funcDao.buscarId(id);

		ambiente.setResponsavel(funcionario);
		
		

		if (ambiente.getId() != 0) {
		if (ambDao.buscarAmbiente_Nome(ambiente.getNome().trim()) != null && (ambDao.buscarAmbiente_Nome(ambiente.getNome().trim())).get(0).getId() == ambiente.getId()) {
			ambDao.alterar(ambiente);

			Mensagens
					.informacao("Sucesso! Ambiente alterado com sucesso", null);
			ambiente = new Ambiente();
			funcionario = new Funcionario();
		}else {
			Mensagens
			.erro("Erro! Nome do ambiente ja está sendo ultilizado", null);
		}
			
			
		} else {
			ambiente.setId(0);
			ambiente.setStatus(true);
		if (ambDao.buscarAmbiente_NomeU(ambiente.getNome().trim()) != null) {
			Mensagens
			.erro("Erro! Nome do ambiente ja está sendo ultilizado", null);
		}else {
			ambDao.inserir(ambiente);

			Mensagens
					.informacao("Sucesso! Ambiente inserido com sucesso", null);
			ambiente = new Ambiente();
			funcionario = new Funcionario();
		}
			
			
			
			

		}
	}

	@PostConstruct
	public void init() {
		if (Util.carregarObjeto("selectedAmbiente") != null) {
			ambiente = (Ambiente) Util.carregarObjeto("selectedAmbiente");
			id = ambiente.getResponsavel().getId();
			Util.removerObjeto("selectedAmbiente");
		}
	}

}
