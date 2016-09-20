package br.senai.sp.cfp132.PineappleWS.rest;


import java.util.List;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.cfp132.PineappleWS.dao.AmbienteDao;
import br.senai.sp.cfp132.PineappleWS.dao.ModeloDao;
import br.senai.sp.cfp132.PineappleWS.dao.PatrimonioDao;
import br.senai.sp.cfp132.PineappleWS.model.Ambiente;
import br.senai.sp.cfp132.PineappleWS.model.Modelo;
import br.senai.sp.cfp132.PineappleWS.model.Patrimonio;
import br.senai.sp.cfp132.PineappleWS.util.ConversorObject;

@Transactional
@RestController
@RequestMapping("/services/patrimonio")
public class PatrimonioRestController {

	@Autowired
	PatrimonioDao patDao;
	Patrimonio patrimonio;
	@Autowired
	AmbienteDao ambDao;
	Ambiente ambiente;
	@Autowired
	ModeloDao modDao;
	Modelo modelo;

	/**
	 * M锟絫odo para salvar um patrimonio no sistema
	 * 
	 * @param JSONObject
	 * @return String
	 * **/
	@SuppressWarnings("finally")
	@RequestMapping(value = "/salvar", method = RequestMethod.POST, headers = "Accept=application/json;charset=UTF-8")
	public String salvar(@RequestBody String json) {
		JSONObject job = new JSONObject(json);
		patrimonio = ConversorObject.converterPatrimonio(job);
		String retorno = null;
		try {
			if (patrimonio.getId() == null) {				
				patDao.inserir(patrimonio);
				retorno = "Patrim么nio: " + patrimonio.getDescricao()
						+ "cadastrado com sucesso!";
			} else {
				patDao.alterar(patrimonio);
				retorno = "Patrim么nio: " + patrimonio.getDescricao()
						+ "alterado com sucesso!";
			}
		} catch (PersistenceException e) {
			if (e.getMessage().contains("Duplicate")) {
				retorno = "ERRO! Nome de patrim么nio j谩 corresponde a um cadastro!";
			}

		} finally {
			return retorno;
		}
	}
	
	/**
	 * M锟絫odo para buscar todos os patrim么nios no sistema
	 * 
	 * @param JSONObject
	 * @return List com os patrim么nios
	 * **/
	@RequestMapping(value = "/buscarTodos", method = RequestMethod.GET, headers = "Accept=application/json;charset=UTF-8")
	public List<Patrimonio> buscarTodos(String json) {
		JSONObject job = new JSONObject(json);
		List<Patrimonio> listPat = patDao.buscarTodos();
		
		return listPat;
	}
	
	/**
	 * M锟絫odo para buscar todos os patrim么nios de um determinado modelo
	 * 
	 * @param JSONObject
	 * @return List com os patrim么nios
	 * **/
	@RequestMapping(value = "/buscarPatrimonio_Modelo", method = RequestMethod.GET, headers = "Accept=application/json;charset=UTF-8")
	public List<Patrimonio> buscarPatrimonio_Modelo(String json){
		JSONObject job = new JSONObject(json);
		Modelo m = ConversorObject.converterModelo(job);
		List<Patrimonio> listPat = patDao.buscarPatrimonio_Modelo(m);
		
		return listPat;
	}
	
	/**
	 * M锟絫odo para buscar todos os patrim么nios de um determinado ambiente
	 * 
	 * @param JSONObject
	 * @return List com os patrim么nios
	 * **/
	@RequestMapping(value = "/buscarPatrimonio_Ambiente", method = RequestMethod.GET, headers = "Accept=application/json;charset=UTF-8")
	public List<Patrimonio> buscarPatrimonio_Ambiente(String json){
		JSONObject job = new JSONObject(json);
		Ambiente a = ConversorObject.converterAmbiente(job);
		List<Patrimonio> listPat = patDao.buscarPatrimonio_Ambiente(a);
		
		return listPat;
	}
	
//	/**
//	 * M閠odo para buscar todos os patrim鬾ios de um determinado ambiente
//	 * 
//	 * @param JSONObject
//	 * @return List com os patrim鬾ios
//	 * **/
//	@RequestMapping(value = "/buscarPatrimonio_cdPatrimonio", method = RequestMethod.GET, headers = "Accept=application/json;charset=UTF-8")
//	public List<Patrimonio> buscarPatrimonio_cdPatrimonio(@RequestBody String json){
//		JSONObject job = new JSONObject(json);
//		//listCdPatrimonio
//		//conferencia
//		JSONArray array = new JSONArray(job.getJSONArray("listCdPatrimonio"));
//		
//		
//		return listPat;
//	}
	
}
