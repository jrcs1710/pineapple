package br.senai.sp.cfp132.PineappleWS.rest;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.cfp132.PineappleWS.dao.AmbienteDao;
import br.senai.sp.cfp132.PineappleWS.dao.FuncionarioDao;
import br.senai.sp.cfp132.PineappleWS.model.Ambiente;
import br.senai.sp.cfp132.PineappleWS.model.Funcionario;
import br.senai.sp.cfp132.PineappleWS.util.ConversorObject;
import br.senai.sp.cfp132.PineappleWS.util.Mensagens;

@Transactional
@RestController
@RequestMapping("/services/ambiente")
public class AmbienteRestController {

	@Autowired
	AmbienteDao ambDao;
	Ambiente ambiente;
	@Autowired
	FuncionarioDao funcDao;
	Funcionario funcionario;

	/**
	 * M�todo para salvar um ambiente no sistema
	 * 
	 * @param JSONObject
	 * @return String
	 * **/
	@SuppressWarnings("finally")
	@RequestMapping(value = "/salvar", method = RequestMethod.POST, headers = "Accept=application/json;charset=UTF-8")
	public String salvar(String json) {
		JSONObject job = new JSONObject(json);
		ambiente = ConversorObject.converterAmbiente(job);
		String retorno = null;
		try {
			if (ambiente.getId() == null) {
				ambDao.inserir(ambiente);
				retorno = "Ambiente: " + ambiente.getNome()
						+ "cadastrado com sucesso!";
			} else {
				ambDao.alterar(ambiente);
				retorno = "Ambiente: " + ambiente.getNome()
						+ "alterado com sucesso!";
			}
		} catch (PersistenceException e) {
			if (e.getMessage().contains("Duplicate")) {
				retorno = "ERRO! Nome de ambiente já corresponde a um cadastro!";
			}

		} finally {
			return retorno;
		}
	}

	/**
	 * M�todo para buscar um ambiente no sistema
	 * 
	 * @param JSONObject
	 * @return Ambiente
	 * **/
	@RequestMapping(value = "/buscar", method = RequestMethod.GET, headers = "Accept=application/json;charset=UTF-8")
	public Ambiente buscar(String json) {
		JSONObject job = new JSONObject(json);
		Long id = job.getLong("id");
		ambiente = (Ambiente) ambDao.buscarId(id);

		return ambiente;
	}

	/**
	 * M�todo para buscar todos os ambientes no sistema
	 * 
	 * @param JSONObject
	 * @return List com os ambientes
	 * **/
	@RequestMapping(value = "/buscarTodos", method = RequestMethod.GET, headers = "Accept=application/json;charset=UTF-8")
	public List<Ambiente> buscarTodos() {
		// JSONObject job = new JSONObject(json);
		List<Ambiente> listAmb = ambDao.buscarTodosNew();

		// Gson gson = new GsonBuilder().create();
		// JsonArray jsonArray = gson.toJsonTree(listAmb).getAsJsonArray();

		return listAmb;
	}
	
	/**
	 * M�todo para buscar todos os ambientes de um determinado funcion�rio no sistema
	 * 
	 * @param JSONObject
	 * @return List com os ambientes
	 * **/
	@RequestMapping(value = "/buscarAmbFunc", method = RequestMethod.POST, headers = "Accept=application/json;charset=UTF-8")
	public List<Ambiente> buscarAmbFunc(@RequestBody String json) {
		JSONObject job = new JSONObject(json);
		funcionario = ConversorObject.converterFuncionario(job.getJSONObject("responsavel"));
		
		List<Ambiente> listAmb = ambDao.buscarAmbiente_Funcionario(funcionario);


		return listAmb;
	}

	/**
	 * M�todo para excluir um ambiente do sistema
	 * 
	 * @param JSONObject
	 * **/
	@RequestMapping(value = "/excluir", method = RequestMethod.POST, headers = "Accept=application/json;charset=UTF-8")
	public void excluir(String json) {
		JSONObject job = new JSONObject(json);
		ambiente = ConversorObject.converterAmbiente(job);
		if (ambiente.getPatrimonios().size() != 0) {
			Mensagens
					.erro("Não foi possível excluir o ambiente selecionado! Existem patrimonios no ambiente!",
							null);
		} else {
			ambDao.excluir(ambiente.getId());
			Mensagens.informacao("Sucesso! Ambiente excluido com sucesso!",
					null);
		}

	}

}
