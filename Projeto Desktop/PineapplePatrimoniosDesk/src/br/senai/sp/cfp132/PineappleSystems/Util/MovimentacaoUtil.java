package br.senai.sp.cfp132.PineappleSystems.Util;

import java.util.Date;

public class MovimentacaoUtil {

	private String cdPatrimonio;
	private String solicitante;
	private Date dtSolicitacao;
	private String avaliador;
	private Date dataAprovacao;
	private String atual;
	private String destino;
	private String status;

	public String getCdPatrimonio() {
		return cdPatrimonio;
	}

	public void setCdPatrimonio(String cdPatrimonio) {
		this.cdPatrimonio = cdPatrimonio;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public String getAvaliador() {
		return avaliador;
	}

	public void setAvaliador(String avaliador) {
		this.avaliador = avaliador;
	}



	public void setDtSolicitacao(Date dtSolicitacao) {
		this.dtSolicitacao = dtSolicitacao;
	}

	

	public void setDataAprovacao(Date dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
	}

	public String getAtual() {
		return atual;
	}

	public void setAtual(String atual) {
		this.atual = atual;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public CustomDate getDtSolicitacao() {
	    return new CustomDate(dtSolicitacao.getTime()); //myDateAttr is a java.util.Date           
	}
	public CustomDate getDataAprovacao() {
	    return new CustomDate(dataAprovacao.getTime()); //myDateAttr is a java.util.Date           
	}
	

}
