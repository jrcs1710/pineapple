package br.senai.sp.cfp132.pineapplesystems.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.senai.sp.cfp132.pineapplesystems.model.Ambiente;
import br.senai.sp.cfp132.pineapplesystems.model.Auditoria;
import br.senai.sp.cfp132.pineapplesystems.model.Cargo;
import br.senai.sp.cfp132.pineapplesystems.model.Conferencia;
import br.senai.sp.cfp132.pineapplesystems.model.ConferenciaGeral;
import br.senai.sp.cfp132.pineapplesystems.model.Empresa;
import br.senai.sp.cfp132.pineapplesystems.model.Funcionario;
import br.senai.sp.cfp132.pineapplesystems.model.Inconsistencia;
import br.senai.sp.cfp132.pineapplesystems.model.ItemInconsistencia;
import br.senai.sp.cfp132.pineapplesystems.model.ItemTransferencia;
import br.senai.sp.cfp132.pineapplesystems.model.Modelo;
import br.senai.sp.cfp132.pineapplesystems.model.Movimentacao;
import br.senai.sp.cfp132.pineapplesystems.model.Patrimonio;
import br.senai.sp.cfp132.pineapplesystems.model.StatusRequisicao;
import br.senai.sp.cfp132.pineapplesystems.model.Tipo;
import br.senai.sp.cfp132.pineapplesystems.model.TipoInconsistencia;
import br.senai.sp.cfp132.pineapplesystems.model.TipoMovimentacao;
import br.senai.sp.cfp132.pineapplesystems.model.Usuario;

public class ConversorObject {

	/**
	 * Recebe um JSONObject e converte para ambiente
	 * 
	 * @param JSONObject
	 * @return ambiente
	 * @throws JSONException
	 * **/
	public static Ambiente converterAmbiente(JSONObject job)
			throws JSONException {

		Ambiente a = new Ambiente();
		a.setId(job.optLong("id"));
		a.setNome(job.optString("nome"));
		a.setAndar(job.optString("andar"));
		a.setStatus(job.optBoolean("status"));

		/* Converter funcionário e inserir no ambiente */
		JSONObject jobResponsavel = job.optJSONObject("responsavel");
		Funcionario f = converterFuncionario(jobResponsavel);
		a.setResponsavel(f);
		/* Convertendo Lista de Patrimonio */
		JSONArray array = job.optJSONArray("patrimonios");
		if (array != null) {
			List<Patrimonio> lista = converterListaPatrimonio(array);
			a.setPatrimonios(lista);
		}

		return a;
	}

	/**
	 * Recebe um jsonObject e converte para usuário
	 * 
	 * @param JsonObject
	 * @return Usuario
	 * **/
	public static Usuario converterUsuario(JSONObject job) {

		Usuario u = new Usuario();
		u.setId(job.optLong("id"));
		u.setNomeUsuario(job.optString("nomeUsuario"));
		u.setSenha(job.optString("senha"));

		return u;
	}

	/**
	 * Recebe um JSONObject e converte para funcionário
	 * 
	 * @param JSONObject
	 * @return Funcionario
	 * @throws JSONException
	 * **/
	public static Funcionario converterFuncionario(JSONObject job)
			throws JSONException {

		Funcionario f = new Funcionario();
		f.setId(job.optLong("id"));
		f.setEmail(job.optString("email"));
		f.setNome(job.optString("nome"));
		f.setStatus(job.optBoolean("status"));
		if (job.get("cargo").equals(0) || job.get("cargo").equals(1)
				|| job.get("cargo").equals(2)) {
			f.setCargo(Cargo.values()[job.optInt("cargo")]);
		} else {
			f.setCargo(Cargo.valueOf(job.optString("cargo")));
		}

		/* Converter o usuário para inserir no funcionário */
		JSONObject jobUsuario = job.optJSONObject("usuario");
		Usuario u = converterUsuario(jobUsuario);
		f.setUsuario(u);

		return f;
	}

	/**
	 * Recebe um JSONObject e converte para auditoria
	 * 
	 * @param JSONObject
	 * @return Auditoria
	 * @throws JSONException
	 * **/
	public static Auditoria converterAuditoria(JSONObject job)
			throws JSONException {

		Auditoria a = new Auditoria();
		a.setId(job.optLong("id"));
		a.setNrAuditoria(job.optString("nrAuditoria"));
		/* Convertendo Data de inicio da auditoria */
		Calendar dtInicio = Calendar.getInstance();
		dtInicio.setTimeInMillis(job.optLong("dtInicio"));
		a.setDtInicio(dtInicio);
		/* Convertendo Data final da auditoria */
		Calendar dtFim = Calendar.getInstance();
		dtFim.setTimeInMillis(job.optLong("dtFim"));
		a.setDtFim(dtFim);
		/* Converter Lista de Patrimonios */
		if (job.get("patrimonio") != JSONObject.NULL) {
			JSONArray array = job.optJSONArray("patrimonio");
			List<Patrimonio> lista = converterListaPatrimonio(array);
			a.setPatrimonio(lista);
		}

		return a;
	}

	/**
	 * Recebe um JSONObject e converte para Conferencia
	 * 
	 * @param JSONObject
	 * @return Conferencia
	 * @throws JSONException
	 * **/
	public static Conferencia converterConferencia(JSONObject job)
			throws JSONException {

		Conferencia c = new Conferencia();
		c.setId(job.optLong("id"));
		c.setStatus(job.optBoolean("status"));
		/* Convertendo Data de inicio da Conferencia */
		Calendar dtInicio = Calendar.getInstance();
		dtInicio.setTimeInMillis(job.optLong("dtInicio"));
		c.setDtInicio(dtInicio);
		/* Convertendo Data final da Conferencia */
		Calendar dtFim = Calendar.getInstance();
		dtFim.setTimeInMillis(job.optLong("dtFim"));
		c.setDtFim(dtFim);
		/* Convertendo ambiente */
		JSONObject jobAmbiente = job.optJSONObject("ambiente");
		Ambiente a = converterAmbiente(jobAmbiente);
		c.setAmbiente(a);
		/* Converter Lista de Patrimonios */
		JSONArray array = job.optJSONArray("patrimonio");
		List<Patrimonio> lista = converterListaPatrimonio(array);
		c.setPatrimonio(lista);

		return c;
	}

	/**
	 * Recebe um JSONArray e converte para Lista de conferencia
	 * 
	 * @param JSONArray
	 * @return List<Conferencia>
	 * @throws JSONException
	 * **/
	public static List<Conferencia> converterListaConferencia(JSONArray array)
			throws JSONException {

		List<Conferencia> lista = new ArrayList<Conferencia>();

		/* Laço para criar todos os objetos e inserir na lista */
		for (int i = 0; i < array.length(); i++) {
			JSONObject job = array.optJSONObject(i);
			Conferencia c = converterConferencia(job);
			lista.add(c);

		}

		return lista;
	}

	/**
	 * Recebe um JSONObject e converte para ConferenciaGeral
	 * 
	 * @param JSONObject
	 * @return ConferenciaGeral
	 * @throws JSONException
	 * **/
	public static ConferenciaGeral converterConferenciaGeral(JSONObject job)
			throws JSONException {

		ConferenciaGeral cg = new ConferenciaGeral();
		cg.setId(job.optLong("id"));
		cg.setNrConferencia(job.optString("nrConferencia"));
		/* Convertendo Data de inicio da ConferenciaGeral */
		Calendar dtInicio = Calendar.getInstance();
		dtInicio.setTimeInMillis(job.optLong("dtInicio"));
		cg.setDtInicio(dtInicio);
		/* Convertendo Data final da ConferenciaGeral */
		Calendar dtFim = Calendar.getInstance();
		dtFim.setTimeInMillis(job.optLong("dtFim"));
		cg.setDtFim(dtFim);
		/* Converter lista de conferencia. */
		JSONArray array = job.optJSONArray("conferencia");
		List<Conferencia> lista = converterListaConferencia(array);
		cg.setConferencia(lista);

		return cg;
	}

	/**
	 * Recebe um JSONObject e converte para Empresa
	 * 
	 * @param JSONObject
	 * @return Empresa
	 * **/
	public static Empresa converterEmpresa(JSONObject job) {

		Empresa e = new Empresa();
		e.setId(job.optLong("id"));
		e.setNome(job.optString("nome"));
		e.setBairro(job.optString("bairro"));
		e.setCidade(job.optString("cidade"));
		e.setCnpj(job.optString("cnpj"));
		e.setEstado(job.optString("estado"));
		e.setNumero(job.optString("numero"));
		e.setRua(job.optString("rua"));
		e.setLogotipo(job.optString("logoTipo").getBytes());

		return e;
	}

	/**
	 * Recebe um JSONObject e converte para Inconsistencia
	 * 
	 * @param JSONObject
	 * @return Inconsistencia
	 * @throws JSONException
	 * **/
	public static Inconsistencia converterInconsistencia(JSONObject job)
			throws JSONException {

		Inconsistencia i = new Inconsistencia();
		i.setId(job.optLong("id"));
		/* Convertendo conferencia */
		JSONObject jobConferencia = job.optJSONObject("conferencia");
		Conferencia c = converterConferencia(jobConferencia);
		i.setConferencia(c);
		/* Convertendo Item Inconsistencia */
		JSONArray array = job.optJSONArray("itemInconsistencia");
		List<ItemInconsistencia> lista = converterListaItemInconsistencia(array);
		i.setItemInconsistencia(lista);

		return i;
	}

	/**
	 * Recebe um JSONObject e converte para ItemInconsistencia
	 * 
	 * @param JSONObject
	 * @return ItemInconsistencia
	 * @throws JSONException
	 * **/
	public static ItemInconsistencia converterItemInconsistencia(JSONObject job)
			throws JSONException {

		ItemInconsistencia inc = new ItemInconsistencia();
		inc.setId(job.optLong("id"));
		inc.setStatus(job.optBoolean("status"));
		inc.setTipoInconsistencia(TipoInconsistencia.values()[job
				.optInt("tipoInconsistencia")]);
		/* Convertendo Patrimonio */
		JSONObject jobPatrimonio = job.optJSONObject("patrimonio");
		Patrimonio p = converterPatrimonio(jobPatrimonio);
		inc.setPatrimonio(p);

		return inc;
	}

	/**
	 * Recebe um JSONObject e converte para Lista de ItemInconsistencia
	 * 
	 * @param JSONObject
	 * @return List<ItemInconsistencia>
	 * @throws JSONException
	 * **/
	public static List<ItemInconsistencia> converterListaItemInconsistencia(
			JSONArray array) throws JSONException {

		List<ItemInconsistencia> lista = new ArrayList<ItemInconsistencia>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject job = array.optJSONObject(i);
			ItemInconsistencia inc = converterItemInconsistencia(job);
			lista.add(inc);
		}

		return lista;
	}

	/**
	 * Recebe um JSONObject e converte para ItemTransferencia
	 * 
	 * @param JSONObject
	 * @return ItemTransferencia
	 * @throws JSONException
	 * **/
	public static ItemTransferencia converterItemTransferencia(JSONObject job)
			throws JSONException {
		ItemTransferencia trans = new ItemTransferencia();
		trans.setId(job.optLong("id"));
		trans.setStatus(job.optBoolean("status"));
		trans.setTipoMovimentacao(TipoMovimentacao.values()[job
				.optInt("tipoMovimentacao")]);
		/* Convertendo Patrimonio */
		JSONObject jobPatrimonio = job.optJSONObject("patrimonio");
		Patrimonio p = converterPatrimonio(jobPatrimonio);
		trans.setPatrimonio(p);

		return trans;
	}

	/**
	 * Recebe um JSONObject e converte para Lista de ItemTransferencia
	 * 
	 * @param JSONObject
	 * @return List<ItemTransferencia>
	 * @throws JSONException
	 * **/
	public static List<ItemTransferencia> converterListaItemTransferencia(
			JSONArray array) throws JSONException {

		List<ItemTransferencia> lista = new ArrayList<ItemTransferencia>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject job = array.optJSONObject(i);
			ItemTransferencia trans = converterItemTransferencia(job);
			lista.add(trans);
		}

		return lista;
	}

	/**
	 * Recebe um JSONObject e converte para Modelo
	 * 
	 * @param JSONObject
	 * @return Modelo
	 * **/
	public static Modelo converterModelo(JSONObject job) {

		Modelo m = new Modelo();
		m.setId(job.optLong("id"));
		m.setNome(job.optString("nome"));
		m.setFoto(job.optString("foto").getBytes());
		/* Convertendo Tipo */
		JSONObject jobTipo = job.optJSONObject("tipo");
		Tipo t = converterTipo(jobTipo);
		m.setTipo(t);
		m.setStatus(job.optBoolean("status"));

		return m;
	}

	/**
	 * Recebe um JSONObject e converte para Movimentacao
	 * 
	 * @param JSONObject
	 * @return Movimentacao
	 * @throws JSONException
	 * **/
	public static Movimentacao converterMovimentacao(JSONObject job)
			throws JSONException {
		Movimentacao mm = new Movimentacao();
		mm.setId(job.optLong("id"));
		if (job.has("dataAprovacao")) {
			if (job.get("dataAprovacao") != JSONObject.NULL) {
				System.out.println(job.toString());
				Calendar dataAprovacao = Calendar.getInstance();
				dataAprovacao.setTimeInMillis(job.optLong("dataAprovacao"));
				mm.setDataAprovacao(dataAprovacao);
				System.out.println("setou data conversor");
			}
		}
		Calendar dtSolicitacao = Calendar.getInstance();
		dtSolicitacao.setTimeInMillis(job.optLong("dtSolicitacao"));
		mm.setDtSolicitacao(dtSolicitacao);
		if (job.get("status").equals(0) || job.get("status").equals(1)
				|| job.get("status").equals(2)) {
			mm.setStatus(StatusRequisicao.values()[job.optInt("status")]);
		} else {
			mm.setStatus(StatusRequisicao.valueOf(job.optString("status")));
		}
		/* Convertendo Destino(Ambiente) */
		JSONObject jobDestino = job.optJSONObject("destino");
		Ambiente ambDestino = converterAmbiente(jobDestino);
		mm.setDestino(ambDestino);
		/* Convertendo Origem(Ambiente) */
		JSONObject jobOrigem = job.optJSONObject("atual");
		Ambiente ambOrigem = converterAmbiente(jobOrigem);
		mm.setAtual(ambOrigem);
		/* Convertendo Avaliador(Funcionário) */
		if (job.has("avaliador")) {
			if (job.get("avaliador") != JSONObject.NULL) {
				JSONObject jobAvaliador = job.optJSONObject("avaliador");
				Funcionario avaliador = converterFuncionario(jobAvaliador);
				mm.setAvaliador(avaliador);
			}			
		}
		/* Convertendo solicitante */
		JSONObject jobSolicitante = job.optJSONObject("solicitante");
		Funcionario solicitante = converterFuncionario(jobSolicitante);
		mm.setSolicitante(solicitante);
		/* Converter Lista de ItemTransferencia */
		JSONArray array = job.optJSONArray("patrimonios");
		List<ItemTransferencia> lista = converterListaItemTransferencia(array);
		mm.setPatrimonios(lista);
		/* Converter observações */
		mm.setObsAprovador(job.optString("obsAprovador"));
		mm.setObsSolicitante(job.optString("obsSolicitante"));

		return mm;
	}

	/**
	 * Recebe um JSONObject e converte para Patrimonio
	 * 
	 * @param JSONObject
	 * @return Patrimonio
	 * @throws JSONException
	 * **/
	public static Patrimonio converterPatrimonio(JSONObject job)
			throws JSONException {

		Patrimonio p = new Patrimonio();
		p.setId(job.optLong("id"));
		p.setCdPatrimonio(job.optString("cdpatrimonio"));
		p.setDescricao(job.optString("descricao"));
		/* Convertendo DtEntrada */
		Calendar dtEntrada = Calendar.getInstance();
		dtEntrada.setTimeInMillis(job.optLong("dtEntrada"));
		p.setDtEntrada(dtEntrada);
		/* Convertendo DtSaida */
		if (job.has("dtSaida")) {
			if (job.get("dtSaida") != JSONObject.NULL) {
				Calendar dtSaida = Calendar.getInstance();
				dtSaida.setTimeInMillis(job.optLong("dtSaida"));
				p.setDtSaida(dtSaida);
			}			
		}
		/* Convertendo Ambiente */
		JSONObject jobAmbiente = job.optJSONObject("ambiente");
		Ambiente a = converterAmbiente(jobAmbiente);
		p.setAmbiente(a);
		/* Convertendo Modelo */
		JSONObject jobModelo = job.optJSONObject("modelo");
		Modelo m = converterModelo(jobModelo);
		p.setModelo(m);

		return p;
	}

	/**
	 * Recebe um JSONObject e converte para Lista de Patrimonios
	 * 
	 * @param JSONObject
	 * @return List<Patrimonio>
	 * @throws JSONException
	 * **/
	public static List<Patrimonio> converterListaPatrimonio(JSONArray array)
			throws JSONException {

		List<Patrimonio> lista = new ArrayList<Patrimonio>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject job = array.optJSONObject(i);
			Patrimonio p = converterPatrimonio(job);
			lista.add(p);
		}

		return lista;
	}

	/**
	 * Recebe um JSONObject e converte para Tipo
	 * 
	 * @param JSONObject
	 * @return Tipo
	 * **/
	public static Tipo converterTipo(JSONObject job) {

		Tipo t = new Tipo();
		t.setId(job.optLong("id"));
		t.setDescricao(job.optString("descricao"));

		return t;
	}

}
