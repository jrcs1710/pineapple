package br.senai.sp.cfp132.PineappleWS.rest;


import java.util.List;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.cfp132.PineappleWS.dao.ModeloDao;
import br.senai.sp.cfp132.PineappleWS.model.Modelo;
import br.senai.sp.cfp132.PineappleWS.util.ConversorObject;

@Transactional
@RestController
@RequestMapping("/services/modelo")
public class ModeloRestController {

	@Autowired
	ModeloDao modDao;
	Modelo modelo;

	/**
	 * M�todo para salvar um modelo no sistema
	 * 
	 * @param JSONObject
	 * @return String
	 * **/
	@SuppressWarnings("finally")
	@RequestMapping(value = "/salvar", method = RequestMethod.POST, headers = "Accept=application/json;charset=UTF-8")
	public String salvar(String json) {
		JSONObject job = new JSONObject(json);
		modelo = ConversorObject.converterModelo(job);
		String retorno = null;
		try {
			if (modelo.getId() == null) {				
				modDao.inserir(modelo);
				retorno = "Modelo: " + modelo.getNome()
						+ "cadastrado com sucesso!";
			} else {
				modDao.alterar(modelo);
				retorno = "Modelo: " + modelo.getNome()
						+ "alterado com sucesso!";
			}
		} catch (PersistenceException e) {
			if (e.getMessage().contains("Duplicate")) {
				retorno = "ERRO! Nome de modelo já corresponde a um cadastro!";
			}

		} finally {
			return retorno;
		}
	}
	
	/**
	 * M�todo para buscar todos os modelos no sistema
	 * 
	 * @param JSONObject
	 * @return List com os modelos
	 * **/
	@RequestMapping(value = "/buscarTodos", method = RequestMethod.GET, headers = "Accept=application/json;charset=UTF-8")
	public List<Modelo> buscarTodos(String json) {
		JSONObject job = new JSONObject(json);
		List<Modelo> listMod = modDao.buscarTodos();
		
		return listMod;
	}
	
}
