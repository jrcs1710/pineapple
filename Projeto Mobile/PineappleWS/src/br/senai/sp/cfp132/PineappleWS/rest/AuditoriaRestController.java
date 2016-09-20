package br.senai.sp.cfp132.PineappleWS.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.cfp132.PineappleWS.dao.AmbienteDao;
import br.senai.sp.cfp132.PineappleWS.dao.AuditoriaDao;
import br.senai.sp.cfp132.PineappleWS.dao.PatrimonioDao;
import br.senai.sp.cfp132.PineappleWS.model.Ambiente;
import br.senai.sp.cfp132.PineappleWS.model.Auditoria;
import br.senai.sp.cfp132.PineappleWS.model.Funcionario;
import br.senai.sp.cfp132.PineappleWS.model.Patrimonio;
import br.senai.sp.cfp132.PineappleWS.util.ConversorObject;

@Transactional
@RestController
@RequestMapping("/services/auditoria")
public class AuditoriaRestController {

	@Autowired
	AuditoriaDao auditDao;
	Auditoria auditoria;
	@Autowired
	PatrimonioDao patDao;
	Patrimonio patrimonio;
	@Autowired
	AmbienteDao ambDao;
	Ambiente ambiente;

	Funcionario auditor;

	/**
	 * Método para salvar uma auditoria no sistema
	 * 
	 * @param JSONObject
	 * @return String
	 * **/
	@RequestMapping(value = "/salvar", method = RequestMethod.POST, headers = "Accept=application/json;charset=UTF-8")
	public String salvar(@RequestBody String json) {
		String retorno = null;
		JSONObject job = new JSONObject(json);
		
		String nrAuditoria = job.getString("nrAuditoria");		

		List<Patrimonio> patrimonios = new ArrayList<Patrimonio>();
		Patrimonio patTemp;

		List<String> listStr = new ArrayList<String>();
		JSONArray array = job.getJSONArray("listCdPatrimonio");
		if (array != null) {
			for (int i = 0; i < array.length(); i++) {
				listStr.add(array.get(i).toString());
			}
		}

		for (int i = 0; i < listStr.size(); i++) {
			patTemp = patDao.buscarPatrimonio_cdPatrimonio(listStr.get(i));
			if (patTemp != null) {
				if (patTemp.getDtSaida() == null) {
					patrimonios.add(patTemp);					
				}
			}
		}
		
		if (!patrimonios.isEmpty()) {
			auditoria = auditDao.buscarNrAuditoria(nrAuditoria);
			if (auditoria.getPatrimonio().isEmpty()) {
				auditoria.setPatrimonio(patrimonios);
				auditDao.alterar(auditoria);
			} else {
				Auditoria auditBanco = (Auditoria) auditDao.buscarId(auditoria.getId());
				List<Patrimonio> listBanco = new ArrayList<Patrimonio>();
				listBanco.addAll(auditBanco.getPatrimonio());

				for (int i = 0; i < listBanco.size(); i++) {
					if (!patrimonios.contains(listBanco.get(i))) {
						patrimonios.add(listBanco.get(i));
					}

				}
				auditoria.setPatrimonio(patrimonios);
				auditDao.alterar(auditoria);

			}
			retorno = "sucesso";
			
		} else {
			retorno = "Código inválido";
		}

		return retorno;
	}

	/**
	 * Método para comparar um número de auditoria com o do sistema
	 * 
	 * @param JSONObject
	 * @return String
	 * **/
	@RequestMapping(value = "/buscarNrAudit", method = RequestMethod.POST, headers = "Accept=application/json;charset=UTF-8")
	public String buscarNrAudit(@RequestBody String json) {
		JSONObject job = new JSONObject(json);
		String retorno = null;
		String nrAuditoria = job.getString("nrAuditoria");
		auditor = ConversorObject.converterFuncionario(job
				.getJSONObject("auditor"));

		auditoria = auditDao.buscarNrAuditoria(nrAuditoria);
		List<Ambiente> listAmbFull = ambDao.buscarTodosNew();
		List<Ambiente> listAmbValidos = new ArrayList<Ambiente>();
		List<Patrimonio> listEscaneados = new ArrayList<Patrimonio>();
		List<String> listStrEscaneados = new ArrayList<String>();

		if (auditoria != null
				&& auditoria.getDtFim().after(Calendar.getInstance())
				&& auditoria.getDtInicio().before(Calendar.getInstance())) {
			retorno = "OK";

			if (!auditoria.getPatrimonio().isEmpty()) {
				listEscaneados.addAll(auditoria.getPatrimonio());
				for (int i = 0; i < listEscaneados.size(); i++) {
					listStrEscaneados.add(listEscaneados.get(i).getCdPatrimonio());
				}
				JSONArray jsonEscaneados = new JSONArray(listStrEscaneados.toString());
				retorno = jsonEscaneados.toString();
			}
		}

		return retorno;
	}
}
