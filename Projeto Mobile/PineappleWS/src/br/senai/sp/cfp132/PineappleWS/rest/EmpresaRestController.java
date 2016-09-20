package br.senai.sp.cfp132.PineappleWS.rest;


import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.cfp132.PineappleWS.dao.EmpresaDao;
import br.senai.sp.cfp132.PineappleWS.model.Empresa;
import br.senai.sp.cfp132.PineappleWS.util.ConversorObject;

@Transactional
@RestController
@RequestMapping("/services/empresa")
public class EmpresaRestController {

	@Autowired
	EmpresaDao empDao;
	Empresa empresa;

	/**
	 * M�todo para salvar a empresa no sistema
	 * 
	 * @param JSONObject
	 * @return String
	 * **/
	@SuppressWarnings("finally")
	@RequestMapping(value = "/salvar", method = RequestMethod.POST, headers = "Accept=application/json;charset=UTF-8")
	public String salvar(String json) {
		JSONObject job = new JSONObject(json);
		empresa = ConversorObject.converterEmpresa(job);
		String retorno = null;
		try {
			if (empresa.getId() == null) {				
				empDao.inserir(empresa);
				retorno = "Empresa: " + empresa.getNome()
						+ "cadastrada com sucesso!";
			} else {
				empDao.alterar(empresa);
				retorno = "Empresa: " + empresa.getNome()
						+ "alterada com sucesso!";
			}
		} finally {
			return retorno;
		}
	}
	
	/**
	 * M�todo para buscar a empresa no sistema
	 * 
	 * @param JSONObject
	 * @return Empresa
	 * **/
	@RequestMapping(value = "/buscar", method = RequestMethod.GET, headers = "Accept=application/json;charset=UTF-8")
	public Empresa buscar(String json) {
		JSONObject job = new JSONObject(json);
		Long id = job.getLong("id");
		empresa = (Empresa) empDao.buscarId(id);
		return empresa;
	}
		
}
