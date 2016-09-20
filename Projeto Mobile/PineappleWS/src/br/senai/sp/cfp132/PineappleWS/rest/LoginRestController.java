package br.senai.sp.cfp132.PineappleWS.rest;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.cfp132.PineappleWS.dao.FuncionarioDao;
import br.senai.sp.cfp132.PineappleWS.dao.UsuarioDao;
import br.senai.sp.cfp132.PineappleWS.model.Funcionario;
import br.senai.sp.cfp132.PineappleWS.model.Usuario;

@Transactional
@RestController
@RequestMapping("/services/funcionario")
public class LoginRestController {

	@Autowired
	FuncionarioDao funcDao;
	@Autowired
	UsuarioDao userDao;
	Funcionario funcionario;
	Usuario usuario;

	/**
	 * M�todo para efetuar login no sistema
	 * 
	 * @param JSONObject
	 * @return Funcionario
	 * **/
	@RequestMapping(value = "/logar", method = RequestMethod.POST, headers = "Accept=application/json;charset=UTF-8")
	public Funcionario logar(@RequestBody String json) {
		JSONObject job = new JSONObject(json);
		// Usuario u = ConversorObject.converterUsuario(job);

		usuario = userDao.buscarNomeSenha(job.getString("nomeUsuario"),
				job.getString("senha"));
		funcionario = funcDao.buscarUsuario(usuario);
		if (funcionario != null) {
			if (funcionario.isStatus()) {
				return funcionario;				
			}
		}

		return null;
	}

	/**
	 * M�todo para Salvar um funcionario
	 * 
	 * @param JSONObject
	 * @return String mensagem
	 * **/
	@SuppressWarnings("finally")
	@RequestMapping(value = "/salvar", method = RequestMethod.POST, headers = "Accept=application/json;charset=UTF-8")
	public String salvar(@RequestBody String json) {
		JSONObject job = new JSONObject(json);
		usuario = userDao.buscarNome(job.getString("nomeUsuario"));
		funcionario = funcDao.buscarFuncEmail(job.getString("email"));
		String retorno = null;
		try {
			if (funcionario.getId() == null) {
				funcDao.inserir(funcionario);
				retorno = "Funcion�rio: " + funcionario.getNome()
						+ "cadastrado com sucesso!";
			} else {
				funcDao.alterar(funcionario);
				retorno = "Funcion�rio: " + funcionario.getNome()
						+ "alterado com sucesso!";
			}
		} catch (PersistenceException e) {
			if (e.getMessage().contains("Duplicate")) {
				retorno = "ERRO! Nome de usu�rio ou e-mail j� correspondem a um cadastro!";
			}

		} finally {
			return retorno;
		}

	}

	/**
	 * M�todo para alterar a senha de um funcionario
	 * 
	 * @param JSONObject
	 * @return Funcionario
	 * **/
	@RequestMapping(value = "/alterarSenha", method = RequestMethod.POST, headers = "Accept=application/json;charset=UTF-8")
	public Funcionario alterar(@RequestBody String json) {
		JSONObject job = new JSONObject(json);
		usuario = userDao.buscarNome(job.getString("nomeUsuario"));
		funcionario = funcDao.buscarFuncEmail(job.getString("email"));

		if (usuario != null && funcionario != null) {
			if (funcionario.getUsuario() == usuario) {
				String senhaNova = userDao.gerarSenha();
				usuario.setSenha(senhaNova);
				userDao.alterar(usuario);				
			}
		}

		return funcionario;
	}

	/**
	 * M�todo para desativar um usu�rio
	 * 
	 * @param JSONObject
	 * @return String
	 * **/
	@RequestMapping(value = "/desativar", method = RequestMethod.POST, headers = "Accept=application/json;charset=UTF-8")
	public String desativar(@RequestBody String json) {
		JSONObject job = new JSONObject(json);
		Long id = job.getLong("id");
		funcionario = (Funcionario) funcDao.buscarId(id);
		funcionario.setStatus(false);
		funcDao.alterar(funcionario);

		return "Funcion�rio: " + funcionario.getNome()
				+ "desativado com sucesso!";
	}

}
