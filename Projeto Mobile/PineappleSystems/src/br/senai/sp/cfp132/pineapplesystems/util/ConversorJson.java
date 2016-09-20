package br.senai.sp.cfp132.pineapplesystems.util;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.senai.sp.cfp132.pineapplesystems.model.Ambiente;
import br.senai.sp.cfp132.pineapplesystems.model.Auditoria;
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
import br.senai.sp.cfp132.pineapplesystems.model.Tipo;
import br.senai.sp.cfp132.pineapplesystems.model.Usuario;

public class ConversorJson {

	/**
	 * Recebe um Ambiente e converte para JSONObject
	 * 
	 * @param Ambiente
	 * @return JSONObject
	 * @throws JSONException
	 * **/
	public static JSONObject converterAmbiente(Ambiente a) throws JSONException {

		JSONObject job = new JSONObject();
		job.put("id", a.getId());
		job.put("andar", a.getAndar());
		job.put("nome", a.getNome());
		job.put("status", a.isStatus());
		/* Convertendo Responsavel */
		JSONObject jobResponsavel = converterFuncionario(a.getResponsavel());
		job.put("responsavel", jobResponsavel);
		/* Convertendo Lista de Patrimonios */
		if (a.getPatrimonios() != null) {
			JSONArray array = converterListaPatrimonio(a.getPatrimonios());
			job.put("patrimonios", array);
		}

		return job;
	}

	/**
	 * Recebe uma Auditoria e converte para JSONObject
	 * 
	 * @param Auditoria
	 * @return JSONObject
	 * @throws JSONException
	 * **/
	public static JSONObject converterAuditoria(Auditoria a)
			throws JSONException {

		JSONObject job = new JSONObject();
		job.put("id", a.getId());
		job.put("nrAuditoria", a.getNrAuditoria());
		job.put("dtInicio", a.getDtInicio().getTimeInMillis());
		job.put("dtFim", a.getDtFim().getTimeInMillis());
		/* Convertendo Lista de Patrimonios */
		JSONArray array = converterListaPatrimonio(a.getPatrimonio());
		job.put("patrimonio", array);

		return job;
	}

	/**
	 * Recebe uma Conferencia e converte para JSONObject
	 * 
	 * @param Conferencia
	 * @return JSONObject
	 * @throws JSONException
	 * **/
	public static JSONObject converterConferencia(Conferencia c)
			throws JSONException {

		JSONObject job = new JSONObject();
		if (c.getId() != null) {
			job.put("id", c.getId());
		}
		if (c.getDtInicio() != null) {
			job.put("dtInicio", c.getDtInicio().getTimeInMillis());
		}
		if (c.getDtFim() != null) {
			job.put("dtFim", c.getDtFim().getTimeInMillis());
		}
		/* Convertendo Ambiente */
		JSONObject jobAmbiente = converterAmbiente(c.getAmbiente());
		job.put("ambiente", jobAmbiente);
		/* Convertendo Lista de Patrimonios */
		if (c.getPatrimonio() != null) {
			JSONArray array = converterListaPatrimonio(c.getPatrimonio());
			job.put("patrimonios", array);			
		}

		return job;
	}

	/**
	 * Recebe uma Lista de Conferencias e converte para JSONArray
	 * 
	 * @param List
	 *            <Conferencia>
	 * @return JSONArray
	 * @throws JSONException
	 * **/
	public static JSONArray converterListaConferencia(List<Conferencia> lista)
			throws JSONException {
		JSONObject job = new JSONObject();
		JSONArray array = new JSONArray();
		for (int i = 0; i < lista.size(); i++) {
			Conferencia c = lista.get(i);
			job = converterConferencia(c);

			array.put(job);
		}

		return array;
	}

	/**
	 * Recebe uma ConferenciaGeral e converte para JSONObject
	 * 
	 * @param ConferenciaGeral
	 * @return JSONObject
	 * @throws JSONException
	 * **/
	public static JSONObject converterConferenciaGeral(ConferenciaGeral cg)
			throws JSONException {

		JSONObject job = new JSONObject();
		job.put("id", cg.getId());
		job.put("nrConferencia", cg.getNrConferencia());
		job.put("dtInicio", cg.getDtInicio().getTimeInMillis());
		job.put("dtFim", cg.getDtFim().getTimeInMillis());
		/* Convertendo Lista de Conferencias */
		JSONArray array = converterListaConferencia(cg.getConferencia());
		job.put("conferencia", array);

		return job;
	}

	/**
	 * Recebe uma Empresa e converte para JSONObject
	 * 
	 * @param Ambiente
	 * @return JSONObject
	 * @throws JSONException
	 * **/
	public static JSONObject converterEmpresa(Empresa e) throws JSONException {

		JSONObject job = new JSONObject();
		job.put("id", e.getId());
		job.put("bairro", e.getBairro());
		job.put("cidade", e.getCidade());
		job.put("cnpj", e.getCnpj());
		job.put("estado", e.getEstado());
		job.put("nome", e.getNome());
		job.put("numero", e.getNumero());
		job.put("rua", e.getRua());
		job.put("logotipo", e.getLogotipo().toString());

		return job;
	}

	/**
	 * Recebe um Funcionario e converte para JSONObject
	 * 
	 * @param Funcionario
	 * @return JSONObject
	 * @throws JSONException
	 * **/
	public static JSONObject converterFuncionario(Funcionario f)
			throws JSONException {

		JSONObject job = new JSONObject();
		job.put("id", f.getId());
		job.put("cargo", f.getCargo().ordinal());
		job.put("email", f.getEmail());
		job.put("nome", f.getNome());
		job.put("status", f.isStatus());
		/* Convertendo Usuario */
		JSONObject jobUsuario = converterUsuario(f.getUsuario());
		job.put("usuario", jobUsuario);

		return job;
	}

	/**
	 * Recebe um Usuario e converte para JSONObject
	 * 
	 * @param UsuarioDao
	 * @return JSONObject
	 * @throws JSONException
	 * **/
	public static JSONObject converterUsuario(Usuario u) throws JSONException {

		JSONObject job = new JSONObject();
		job.put("id", u.getId());
		job.put("senha", u.getSenha());
		job.put("nomeUsuario", u.getNomeUsuario());

		return job;
	}

	/**
	 * Recebe uma Inconsistencia e converte para JSONObject
	 * 
	 * @param Inconsistencia
	 * @return JSONObject
	 * @throws JSONException
	 * **/
	public static JSONObject converterInconsistencia(Inconsistencia i)
			throws JSONException {

		JSONObject job = new JSONObject();
		job.put("id", i.getId());
		/* Convertendo Conferencia */
		JSONObject jobConferencia = converterConferencia(i.getConferencia());
		job.put("conferencia", jobConferencia);
		/* Convertendo Item da Inconsistencia */
		JSONArray array = converterListaItemIncosistencia(i
				.getItemInconsistencia());
		job.put("itemInconsistencia", array);

		return job;
	}

	/**
	 * Recebe um ItemInconsistencia e converte para JSONObject
	 * 
	 * @param ItemInconsistencia
	 * @return JSONObject
	 * @throws JSONException
	 * **/
	public static JSONObject converterItemInconsistencia(ItemInconsistencia inc)
			throws JSONException {

		JSONObject job = new JSONObject();
		job.put("id", inc.getId());
		job.put("tipoInconsistencia", inc.getTipoInconsistencia().ordinal());
		/* Convertendo Patrimonio */
		JSONObject jobPatrimonio = converterPatrimonio(inc.getPatrimonio());
		job.put("patrimonio", jobPatrimonio);

		return job;
	}

	/**
	 * Recebe uma Lista de ItemInconsistencia e converte para JSONArray
	 * 
	 * @param ItemInconsistencia
	 * @return JSONArray
	 * @throws JSONException
	 * **/
	public static JSONArray converterListaItemIncosistencia(
			List<ItemInconsistencia> lista) throws JSONException {

		JSONObject job = new JSONObject();
		JSONArray array = new JSONArray();
		for (int i = 0; i < lista.size(); i++) {
			ItemInconsistencia inc = lista.get(i);
			job = converterItemInconsistencia(inc);

			array.put(job);
		}

		return array;
	}

	/**
	 * Recebe um ItemTransferencia e converte para JSONObject
	 * 
	 * @param ItemTransferencia
	 * @return JSONObject
	 * @throws JSONException
	 * **/
	public static JSONObject converterItemTransferencia(ItemTransferencia trans)
			throws JSONException {

		JSONObject job = new JSONObject();
		job.put("id", trans.getId());
		job.put("status", trans.isStatus());
		job.put("tipoMovimentacao", trans.getTipoMovimentacao().ordinal());
		/* Convertendo Patrimonio */
		JSONObject jobPatrimonio = converterPatrimonio(trans.getPatrimonio());
		job.put("patrimonio", jobPatrimonio);
		return job;
	}

	/**
	 * Recebe uma Lista de ItemTransferencia e converte para JSONArray
	 * 
	 * @param ItemTransferencia
	 * @return JSONArray
	 * @throws JSONException
	 * **/
	public static JSONArray converterListaItemTransferencia(
			List<ItemTransferencia> lista) throws JSONException {

		JSONObject job = new JSONObject();
		JSONArray array = new JSONArray();
		for (int i = 0; i < lista.size(); i++) {
			ItemTransferencia trans = lista.get(i);
			job = converterItemTransferencia(trans);

			array.put(job);
		}

		return array;

	}

	/**
	 * Recebe um Modelo e converte para JSONObject
	 * 
	 * @param Modelo
	 * @return JSONObject
	 * @throws JSONException
	 * **/
	public static JSONObject converterModelo(Modelo m) throws JSONException {

		JSONObject job = new JSONObject();
		job.put("id", m.getId());
		job.put("nome", m.getNome());
		if (m.getFoto() != null) {
			job.put("foto", m.getFoto().toString());
		}
		/* Convertendo Tipo */
		JSONObject jobTipo = converterTipo(m.getTipo());
		job.put("tipo", jobTipo);
		job.put("status", m.isStatus());

		return job;
	}

	/**
	 * Recebe um Movimentacao e converte para JSONObject
	 * 
	 * @param Movimentacao
	 * @return JSONObject
	 * @throws JSONException
	 * **/
	public static JSONObject converterMovimentacao(Movimentacao mm)
			throws JSONException {

		JSONObject job = new JSONObject();
		job.put("id", mm.getId());
		if (mm.getDataAprovacao() != null) {
			job.put("dataAprovacao", mm.getDataAprovacao().getTimeInMillis());
		}
		job.put("dtSolicitacao", mm.getDtSolicitacao().getTimeInMillis());
		job.put("status", mm.getStatus().ordinal());
		/* Convertendo Destino(Ambiente) */
		JSONObject jobDestino = converterAmbiente(mm.getDestino());
		job.put("destino", jobDestino);
		/* Convertendo Origem(Ambiente) */
		JSONObject jobOrigem = converterAmbiente(mm.getAtual());
		job.put("atual", jobOrigem);
		/* Convertendo Solicitante(Funcionario) */
		JSONObject jobSolicitante = converterFuncionario(mm.getSolicitante());
		job.put("solicitante", jobSolicitante);
		/* Convertendo Avaliador(Funcionario) */
		JSONObject jobAvaliador = converterFuncionario(mm.getAvaliador());
		job.put("avaliador", jobAvaliador);
		/* Convertendo Lista de ItemTransferencia */
		JSONArray array = converterListaItemTransferencia(mm.getPatrimonios());
		job.put("patrimonios", array);
		/* Converter observações */
		job.put("obsAprovador", mm.getObsAprovador());
		job.put("obsSolicitante", mm.getObsSolicitante());

		return job;
	}

	/**
	 * Recebe um Patrimonio e converte para JSONObject
	 * 
	 * @param Patrimonio
	 * @return JSONObject
	 * @throws JSONException
	 * **/
	public static JSONObject converterPatrimonio(Patrimonio p)
			throws JSONException {

		JSONObject job = new JSONObject();
		job.put("id", p.getId());
		job.put("cdpatrimonio", p.getCdPatrimonio());
		job.put("descricao", p.getDescricao());
		job.put("dtEntrada", p.getDtEntrada().getTimeInMillis());
		if (p.getDtSaida() != null) {
			job.put("dtSaida", p.getDtSaida().getTimeInMillis());
		}
		/* Convertendo Ambiente */
		JSONObject jobAmbiente = converterAmbiente(p.getAmbiente());
		job.put("ambiente", jobAmbiente);
		/* Convertendo Modelo */
		JSONObject jobModelo = converterModelo(p.getModelo());
		job.put("modelo", jobModelo);

		return job;
	}

	/**
	 * Recebe uma Lista de Patrimonios e converte para JSONArray
	 * 
	 * @param Patrimonio
	 * @return JSONArray
	 * @throws JSONException
	 * **/
	public static JSONArray converterListaPatrimonio(List<Patrimonio> lista)
			throws JSONException {

		JSONObject job = new JSONObject();
		JSONArray array = new JSONArray();
		for (int i = 0; i < lista.size(); i++) {
			Patrimonio p = lista.get(i);
			job = converterPatrimonio(p);

			array.put(job);
		}

		return array;

	}

	/**
	 * Recebe um Tipo e converte para JSONObject
	 * 
	 * @param Tipo
	 * @return JSONObject
	 * @throws JSONException
	 * **/
	public static JSONObject converterTipo(Tipo t) throws JSONException {

		JSONObject job = new JSONObject();
		job.put("id", t.getId());
		job.put("descricao", t.getDescricao());
		return job;
	}

}
