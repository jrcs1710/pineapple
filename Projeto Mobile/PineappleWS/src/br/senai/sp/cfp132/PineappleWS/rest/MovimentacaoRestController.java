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

import br.senai.sp.cfp132.PineappleWS.dao.ItemTransferenciaDao;
import br.senai.sp.cfp132.PineappleWS.dao.MovimentacaoDao;
import br.senai.sp.cfp132.PineappleWS.dao.PatrimonioDao;
import br.senai.sp.cfp132.PineappleWS.model.Ambiente;
import br.senai.sp.cfp132.PineappleWS.model.Cargo;
import br.senai.sp.cfp132.PineappleWS.model.Funcionario;
import br.senai.sp.cfp132.PineappleWS.model.ItemTransferencia;
import br.senai.sp.cfp132.PineappleWS.model.Movimentacao;
import br.senai.sp.cfp132.PineappleWS.model.Patrimonio;
import br.senai.sp.cfp132.PineappleWS.model.StatusRequisicao;
import br.senai.sp.cfp132.PineappleWS.model.TipoMovimentacao;
import br.senai.sp.cfp132.PineappleWS.util.ConversorJson;
import br.senai.sp.cfp132.PineappleWS.util.ConversorObject;

@Transactional
@RestController
@RequestMapping("/services/movimentacao")
public class MovimentacaoRestController {

	@Autowired
	MovimentacaoDao movDao;
	Movimentacao movimentacao;
	@Autowired
	PatrimonioDao patDao;
	Patrimonio patrimonio;
	@Autowired
	ItemTransferenciaDao itemDao;
	ItemTransferencia item;
	
	Funcionario avaliador;

	/**
	 * Mï¿½todo para salvar uma movimentaÃ§Ã£o no sistema
	 * 
	 * @param JSONObject
	 * @return String
	 * **/
	@SuppressWarnings("finally")
	@RequestMapping(value = "/salvar", method = RequestMethod.POST, headers = "Accept=application/json;charset=UTF-8")
	public String salvar(@RequestBody String json) {
		JSONObject job = new JSONObject(json);
		movimentacao = ConversorObject.converterMovimentacao(job);
		Funcionario solicitante = ConversorObject.converterFuncionario(job.getJSONObject("solicitante"));
		if (job.getJSONObject("avaliador") != JSONObject.NULL) {
			avaliador = ConversorObject.converterFuncionario(job.getJSONObject("avaliador"));
		}
		
		String retorno = null;
		try {
			if (movimentacao.getStatus() == StatusRequisicao.RECUS) {
				movimentacao.setDataAprovacao(Calendar.getInstance());
				movDao.alterar(movimentacao);			
				retorno = "Movimentação rejeitada com sucesso";	

				return retorno;
			} else if (solicitante.getCargo().toString().equals("Gerente") || avaliador != null) {
				movimentacao.setDataAprovacao(Calendar.getInstance());
				if (movimentacao.getId() == null) {
					movDao.inserir(movimentacao);
				} else {
					movDao.alterar(movimentacao);					
				}

				ItemTransferencia item;

				for (int i = 0; i < movimentacao.getPatrimonios().size(); i++) {
					item = movimentacao.getPatrimonios().get(i);
					patrimonio = item.getPatrimonio();
					patrimonio.setAmbiente(movimentacao.getDestino());
					patDao.alterar(patrimonio);
					patrimonio = null;
				}
				
				retorno = "Movimentação feita com sucesso";
			}
		} finally {
			return retorno;
		}
	}
	

	/**
	 * Método para salvar uma lista movimentações no sistema
	 * 
	 * @param JSONObject
	 * @return String
	 * **/
	@SuppressWarnings("finally")
	@RequestMapping(value = "/salvarTodas", method = RequestMethod.POST, headers = "Accept=application/json;charset=UTF-8")
	public String salvarTodas(@RequestBody String json) {
		String retorno = null;
		JSONObject job = new JSONObject(json);
		JSONArray jsonArray = job.getJSONArray("listaNumeros");	
		

		String obs = null;
		
		List<String> listStr = new ArrayList<String>();

		if (jsonArray != null) {
			for (int i = 0; i < jsonArray.length(); i++) {
				listStr.add(jsonArray.get(i).toString());
			}
		}
		
		Ambiente destino = ConversorObject.converterAmbiente(job.getJSONObject("destino"));
		Funcionario solicitante = ConversorObject.converterFuncionario(job.getJSONObject("solicitante"));
		
		//List<Movimentacao> listMovi = new ArrayList<Movimentacao>();
		List<Patrimonio> listPatTemp = new ArrayList<Patrimonio>();

		
		for (int i = 0; i < listStr.size(); i++) {
			patrimonio = patDao.buscarPatrimonio_cdPatrimonio(listStr.get(i));
			listPatTemp.add(patrimonio);
		}
		
		//pegar qnt de ambientes
		List<Ambiente> listAmb = new ArrayList<Ambiente>();
		for (int i = 0; i < listPatTemp.size(); i++) {
			if (!listAmb.contains(listPatTemp.get(i).getAmbiente())) {
				listAmb.add(listPatTemp.get(i).getAmbiente());				
			}			
		}		

		List<ItemTransferencia> listItens = new ArrayList<ItemTransferencia>();
		
		
		for (int i = 0; i < listAmb.size(); i++) {
			for (int j = 0; j < listPatTemp.size(); j++) {
				if (listAmb.get(i) == listPatTemp.get(j).getAmbiente()) {	
					ItemTransferencia item = new ItemTransferencia();
					item.setStatus(true);
					item.setTipoMovimentacao(TipoMovimentacao.TRANSF);
					item.setPatrimonio(listPatTemp.get(j));
					itemDao.inserir(item);
					listItens.add(item);
					System.out.println("inseriu item");
				}
			}
			movimentacao = new Movimentacao();
			if (job.has("observacao")) {
				obs = job.optString("observacao");
				movimentacao.setObsSolicitante(obs);
			}
			movimentacao.setDestino(destino);
			movimentacao.setSolicitante(solicitante);
			movimentacao.setDtSolicitacao(Calendar.getInstance());
			if (solicitante.getCargo() == Cargo.GER) {
				movimentacao.setObsAprovador(obs);
				movimentacao.setStatus(StatusRequisicao.APROV);	
				movimentacao.setAvaliador(solicitante);
				movimentacao.setDataAprovacao(Calendar.getInstance());
				
				for (int j = 0; j < listItens.size(); j++) {
					Patrimonio p = listItens.get(j).getPatrimonio();
					p.setAmbiente(destino);
					patDao.alterar(p);
				}
				
			} else {
				movimentacao.setStatus(StatusRequisicao.ABERTO);
			}
			movimentacao.setAtual(listAmb.get(i));			
			movimentacao.setPatrimonios(listItens);
			System.out.println(movimentacao.getStatus().toString());
			movDao.inserir(movimentacao);
			System.out.println("inseriu movi");
			listItens = new ArrayList<ItemTransferencia>();	
			
		}
		
		if (solicitante.getCargo() == Cargo.GER) {
			retorno = "Transferências feitas com sucesso";
		} else {
			retorno = "Requisições feitas com sucesso";			
		}
		
		return retorno;
	}

	/**
	 * Mï¿½todo para buscar todos as requisições abertas do sistema
	 * 
	 * 
	 * @return String (JSONArray)
	 * **/
	@RequestMapping(value = "/buscarAbertas", method = RequestMethod.GET, headers = "Accept=application/json;charset=UTF-8")
	public String buscarAbertas() {
		List<Movimentacao> listMov = movDao.buscarMovimentacao_Aberta(StatusRequisicao.ABERTO);
		

		JSONObject job = new JSONObject();
		JSONArray array = new JSONArray();
		if (listMov != null) {
			for (int i = 0; i < listMov.size(); i++) {
				Movimentacao m = listMov.get(i);
				job = ConversorJson.converterMovimentacao(m);

				array.put(job);
			}

			return array.toString();
		}else{
			return null;
		}

	}

	/**
	 * Mï¿½todo para buscar todos as movimentaÃ§Ãµes de uma determinada data
	 * 
	 * @param JSONObject
	 * @return List com as movimentaÃ§Ãµes
	 * **/
	@RequestMapping(value = "/buscarMovimentacao_Data", method = RequestMethod.GET, headers = "Accept=application/json;charset=UTF-8")
	public List<Movimentacao> buscarMovimentacao_Data(@RequestBody String json) {
		JSONObject job = new JSONObject(json);
		Calendar data = Calendar.getInstance();
		data.setTimeInMillis(job.getLong("data"));
		List<Movimentacao> listMov = movDao.buscarMovimentacao_Data(data);

		return listMov;
	}

}
