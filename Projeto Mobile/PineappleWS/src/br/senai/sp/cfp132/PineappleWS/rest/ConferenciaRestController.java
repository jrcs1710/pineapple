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
import br.senai.sp.cfp132.PineappleWS.dao.ConferenciaDao;
import br.senai.sp.cfp132.PineappleWS.dao.ConferenciaGeralDao;
import br.senai.sp.cfp132.PineappleWS.dao.InconsistenciaDao;
import br.senai.sp.cfp132.PineappleWS.dao.ItemInconsistenciaDao;
import br.senai.sp.cfp132.PineappleWS.dao.PatrimonioDao;
import br.senai.sp.cfp132.PineappleWS.model.Ambiente;
import br.senai.sp.cfp132.PineappleWS.model.Cargo;
import br.senai.sp.cfp132.PineappleWS.model.Conferencia;
import br.senai.sp.cfp132.PineappleWS.model.ConferenciaGeral;
import br.senai.sp.cfp132.PineappleWS.model.Funcionario;
import br.senai.sp.cfp132.PineappleWS.model.Inconsistencia;
import br.senai.sp.cfp132.PineappleWS.model.ItemInconsistencia;
import br.senai.sp.cfp132.PineappleWS.model.Patrimonio;
import br.senai.sp.cfp132.PineappleWS.model.TipoInconsistencia;
import br.senai.sp.cfp132.PineappleWS.util.ConversorJson;
import br.senai.sp.cfp132.PineappleWS.util.ConversorObject;

@Transactional
@RestController
@RequestMapping("/services/conferencia")
public class ConferenciaRestController {

	@Autowired
	ConferenciaDao conDao;
	Conferencia conferencia;
	@Autowired
	ConferenciaGeralDao conGeralDao;
	ConferenciaGeral conGeral;
	@Autowired
	PatrimonioDao patDao;
	Patrimonio patrimonio;
	@Autowired
	AmbienteDao ambDao;
	Ambiente ambiente;
	@Autowired
	InconsistenciaDao incDao;
	Inconsistencia inconsistencia;
	@Autowired
	ItemInconsistenciaDao itemDao;
	ItemInconsistencia itemInconsistencia;

	Funcionario conferente;

	/**
	 * Método para salvar uma conferência no sistema
	 * 
	 * @param JSONObject
	 * @return String
	 * **/
	@RequestMapping(value = "/salvar", method = RequestMethod.POST, headers = "Accept=application/json;charset=UTF-8")
	public String salvar(@RequestBody String json) {
		String retorno = null;
		JSONObject job = new JSONObject(json);

		List<String> listStr = new ArrayList<String>();
		JSONArray array = job.getJSONArray("listCdPatrimonio");
		if (array != null) {
			for (int i = 0; i < array.length(); i++) {
				listStr.add(array.get(i).toString());
			}
		}

		String nrConferencia = job.getString("nrConferencia");
		
		conGeral = conGeralDao.buscarNrConferencia(nrConferencia);

		conferencia = ConversorObject.converterConferencia(job
				.getJSONObject("conferencia"));

		List<Patrimonio> patrimonios = new ArrayList<Patrimonio>();
		Patrimonio patTemp;

		List<String> listInvalidos = new ArrayList<String>();
		List<String> listErrados = new ArrayList<String>();

		itemInconsistencia = new ItemInconsistencia();
		List<ItemInconsistencia> listItens = new ArrayList<ItemInconsistencia>();
		
		ambiente = (Ambiente) ambDao.buscarId(conferencia.getAmbiente().getId());

		if (conferencia.getDtInicio() != conGeral.getDtInicio()) {

			for (int i = 0; i < listStr.size(); i++) {
				patTemp = patDao.buscarPatrimonio_cdPatrimonio(listStr.get(i));
				if (patTemp != null) {
					if (patTemp.getAmbiente() != null && patTemp.getDtSaida() == null) {
						if (patTemp.getAmbiente() == ambiente) {
							patrimonios.add(patTemp);
						} else {
							itemInconsistencia.setPatrimonio(patTemp);
							itemInconsistencia.setStatus(true);
							itemInconsistencia.setTipoInconsistencia(TipoInconsistencia.PATSOB);
							itemDao.inserir(itemInconsistencia);
							listItens.add(itemInconsistencia);	
							System.out.println("passou insert sob");
							itemInconsistencia = new ItemInconsistencia();

							patrimonios.add(patTemp);
							listErrados.add(listStr.get(i));
						}
					} else {
						listInvalidos.add(listStr.get(i));						
					}
				} else {
					listInvalidos.add(listStr.get(i));
				}
			}

			JSONObject resposta = new JSONObject();
			
			List<String> listNaoEncontrados = new ArrayList<String>();
			
			boolean achou = false;
			
			for (int i = 0; i < ambiente.getPatrimonios().size(); i++) {
				for (int j = 0; j < patrimonios.size(); j++) {
					if (ambiente.getPatrimonios().get(i) == patrimonios.get(j)) {
						achou = true;
					}					
				}
				
				
				if (achou == false && !patrimonios.isEmpty()) {
					System.out.println(i + " for inconsistencia");
					listNaoEncontrados.add(ambiente.getPatrimonios().get(i).getCdPatrimonio());
					itemInconsistencia.setPatrimonio(ambiente.getPatrimonios().get(i));
					itemInconsistencia.setStatus(true);
					itemInconsistencia.setTipoInconsistencia(TipoInconsistencia.PATFALT);
					itemDao.inserir(itemInconsistencia);
					listItens.add(itemInconsistencia);	
					itemInconsistencia = new ItemInconsistencia();
					System.out.println("passou if");
					
				}
				achou = false;
			}
			
			if (!listNaoEncontrados.isEmpty()) {
				resposta.put("naoEncontrados", listNaoEncontrados);
			}

			if (!listInvalidos.isEmpty()) {
				resposta.put("invalidos", listInvalidos);
			}

			if (!listErrados.isEmpty()) {
				resposta.put("errados", listErrados);
			}

			if (!patrimonios.isEmpty() && listInvalidos.isEmpty()
					&& listErrados.isEmpty() && listNaoEncontrados.isEmpty()) {
				conferencia.setPatrimonio(patrimonios);
				conferencia.setDtFim(Calendar.getInstance());
				conferencia.setDtInicio(conGeralDao.buscarNrConferencia(
						job.getString("nrConferencia")).getDtInicio());
				conferencia.setStatus(true);
				conDao.inserir(conferencia);
				conGeral.getConferencia().add(conferencia);
				conGeralDao.alterar(conGeral);
				retorno = "sucesso";
			} else if (!patrimonios.isEmpty() && (!listInvalidos.isEmpty()
					|| !listErrados.isEmpty() || !listNaoEncontrados.isEmpty())) {
				conferencia.setPatrimonio(patrimonios);
				conferencia.setDtFim(Calendar.getInstance());
				conferencia.setDtInicio(conGeralDao.buscarNrConferencia(
						job.getString("nrConferencia")).getDtInicio());
				conferencia.setStatus(true);
				conDao.inserir(conferencia);
				conGeral.getConferencia().add(conferencia);
				conGeralDao.alterar(conGeral);
				retorno = resposta.toString();
			} else {
				retorno = null;
			}

			if (!listItens.isEmpty() && conferencia.getPatrimonio() != null) {
				inconsistencia = new Inconsistencia();
				inconsistencia.setConferencia(conferencia);
				inconsistencia.setItemInconsistencia(listItens);
				System.out.println(listItens.size() + " tam. list itens");
				incDao.inserir(inconsistencia);
			}
		} else {
			retorno = "Conferência repetida";
		}

		return retorno;
	}

	/**
	 * Método para comparar um número de conferência com o do sistema
	 * 
	 * @param JSONObject
	 * @return String
	 * **/
	@RequestMapping(value = "/buscarNrConf", method = RequestMethod.POST, headers = "Accept=application/json;charset=UTF-8")
	public String buscarNrConf(@RequestBody String json) {
		JSONObject job = new JSONObject(json);
		String retorno = null;
		String nrConferencia = job.getString("nrConferencia");
		conferente = ConversorObject.converterFuncionario(job
				.getJSONObject("conferente"));

		conGeral = conGeralDao.buscarNrConferencia(nrConferencia);

		List<Ambiente> listAmbFull;
		List<Ambiente> listAmbValidos;
		List<Conferencia> listConferencias = new ArrayList<Conferencia>();

		if (conGeral != null
				&& conGeral.getDtFim().after(Calendar.getInstance())
				&& conGeral.getDtInicio().before(Calendar.getInstance())) {
			listConferencias = conDao.buscarConf_ConfGeral(conGeral);

			if (!listConferencias.isEmpty()) {
				if (conferente.getCargo() == Cargo.GER) {
					listAmbFull = ambDao.buscarTodosNew();
					listAmbValidos = new ArrayList<Ambiente>();
					boolean achou = false;
					
					for (int i = 0; i < listAmbFull.size(); i++) {
						if (!listAmbFull.get(i).getPatrimonios().isEmpty() && listAmbFull.get(i).isStatus() == true) {
							for (int j = 0; j < listConferencias.size(); j++) {
									if (listAmbFull.get(i) == listConferencias.get(j).getAmbiente()) {
										achou = true;
									}
							}
							if (achou == false) {
								listAmbValidos.add(listAmbFull.get(i));					
							}
						}
						achou = false;
					}

					JSONArray listAmbJSON = ConversorJson
							.converterListaAmbiente(listAmbValidos, false);

					if (listAmbValidos.size() > 0) {
						retorno = listAmbJSON.toString();
					} else if (listAmbValidos.size() == 0) {
						retorno = "Todas as conferências já foram realizadas";
					}

				} else {
					listAmbFull = ambDao.buscarAmbiente_Funcionario(conferente);
					listAmbValidos = new ArrayList<Ambiente>();
					boolean achou = false;

					for (int i = 0; i < listAmbFull.size(); i++) {
						if (!listAmbFull.get(i).getPatrimonios().isEmpty() && listAmbFull.get(i).isStatus() == true) {
							for (int j = 0; j < listConferencias.size(); j++) {
									if (listAmbFull.get(i) == listConferencias.get(j).getAmbiente()) {
										achou = true;
									}
							}
							if (achou == false) {
								listAmbValidos.add(listAmbFull.get(i));							
							}
						}
						achou = false;
					}

					JSONArray listAmbJSON = ConversorJson
							.converterListaAmbiente(listAmbValidos, false);

					if (listAmbValidos.size() > 0) {
						retorno = listAmbJSON.toString();
					} else if (listAmbValidos.size() == 0) {
						retorno = "0 ambientes";
					}
				}
			} else {
				if (conferente.getCargo() == Cargo.GER) {
					listAmbFull = ambDao.buscarTodosNew();
					listAmbValidos = new ArrayList<Ambiente>();

					for (int i = 0; i < listAmbFull.size(); i++) {
						if (listAmbFull.get(i).isStatus() == true
								&& !listAmbFull.get(i).getPatrimonios()
										.isEmpty()) {
							listAmbValidos.add(listAmbFull.get(i));
						}
					}

					JSONArray listAmbJSON = ConversorJson
							.converterListaAmbiente(listAmbValidos, false);

					if (listAmbValidos.size() > 0) {
						retorno = listAmbJSON.toString();
					} else if (listAmbValidos.size() == 0) {
						retorno = "0 ambientes";
					}
				} else {
					listAmbFull = ambDao.buscarAmbiente_Funcionario(conferente);
					listAmbValidos = new ArrayList<Ambiente>();

					for (int i = 0; i < listAmbFull.size(); i++) {
						if (listAmbFull.get(i).isStatus() == true
								&& !listAmbFull.get(i).getPatrimonios()
										.isEmpty()) {
							listAmbValidos.add(listAmbFull.get(i));
						}
					}

					JSONArray listAmbJSON = ConversorJson
							.converterListaAmbiente(listAmbValidos, false);

					if (listAmbValidos.size() > 0) {
						retorno = listAmbJSON.toString();
					} else if (listAmbValidos.size() == 0) {
						retorno = "0 ambientes";
					}
				}
			}

		}

		return retorno;
	}
}
