package br.senai.sp.cfp132.PineappleWS.rest;


import java.util.List;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.cfp132.PineappleWS.dao.TipoDao;
import br.senai.sp.cfp132.PineappleWS.model.Tipo;
import br.senai.sp.cfp132.PineappleWS.util.ConversorObject;

@Transactional
@RestController
@RequestMapping("/services/tipo")
public class TipoRestController {

	@Autowired
	TipoDao tipoDao;
	Tipo tipo;

	/**
	 * M�todo para salvar um tipo no sistema
	 * 
	 * @param JSONObject
	 * @return String
	 * **/
	@SuppressWarnings("finally")
	@RequestMapping(value = "/salvar", method = RequestMethod.POST, headers = "Accept=application/json;charset=UTF-8")
	public String salvar(String json) {
		JSONObject job = new JSONObject(json);
		tipo = ConversorObject.converterTipo(job);
		String retorno = null;
		try {
			if (tipo.getId() == null) {				
				tipoDao.inserir(tipo);
				retorno = "Tipo: " + tipo.getDescricao()
						+ "cadastrado com sucesso!";
			} else {
				tipoDao.alterar(tipo);
				retorno = "Tipo: " + tipo.getDescricao()
						+ "alterado com sucesso!";
			}
		} catch (PersistenceException e) {
			if (e.getMessage().contains("Duplicate")) {
				retorno = "ERRO! Nome de tipo já corresponde a um cadastro!";
			}

		} finally {
			return retorno;
		}
	}
	
	/**
	 * M�todo para buscar todos os tipos no sistema
	 * 
	 * @param JSONObject
	 * @return List com os tipos
	 * **/
	@RequestMapping(value = "/buscarTodos", method = RequestMethod.GET, headers = "Accept=application/json;charset=UTF-8")
	public List<Tipo> buscarTodos(String json) {
		JSONObject job = new JSONObject(json);
		List<Tipo> listTipo = tipoDao.buscarTodos();
		
		return listTipo;
	}
	
}
