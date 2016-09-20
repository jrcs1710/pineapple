package br.senai.sp.cfp132.PineappleSystems.Util;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;


public class MovimentacaoGenerico {

	private MovimentacaoUtil movimentacao;
	private SimpleStringProperty cdPatrimonio;
	private SimpleStringProperty solicitante;
	private SimpleLongProperty dtSolicitacao;
	private SimpleStringProperty avaliador;
	private SimpleLongProperty dataAprovacao;
	private SimpleStringProperty atual;
	private SimpleStringProperty destino;
	private SimpleStringProperty status;
	
	
	
	
	public MovimentacaoUtil getMovimentacao() {
		return movimentacao;
	}




	public void setMovimentacao(MovimentacaoUtil movimentacao) {
		this.movimentacao = movimentacao;
	}




	public SimpleStringProperty getCdPatrimonio() {
		return cdPatrimonio;
	}




	public void setCdPatrimonio(SimpleStringProperty cdPatrimonio) {
		this.cdPatrimonio = cdPatrimonio;
	}




	public SimpleStringProperty getSolicitante() {
		return solicitante;
	}




	public void setSolicitante(SimpleStringProperty solicitante) {
		this.solicitante = solicitante;
	}




	public SimpleLongProperty getDtSolicitacao() {
		return dtSolicitacao;
	}




	public void setDtSolicitacao(SimpleLongProperty dtSolicitacao) {
		this.dtSolicitacao = dtSolicitacao;
	}




	public SimpleStringProperty getAvaliador() {
		return avaliador;
	}




	public void setAvaliador(SimpleStringProperty avaliador) {
		this.avaliador = avaliador;
	}




	public SimpleLongProperty getDataAprovacao() {
		return dataAprovacao;
	}




	public void setDataAprovacao(SimpleLongProperty dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
	}




	public SimpleStringProperty getAtual() {
		return atual;
	}




	public void setAtual(SimpleStringProperty atual) {
		this.atual = atual;
	}




	public SimpleStringProperty getDestino() {
		return destino;
	}




	public void setDestino(SimpleStringProperty destino) {
		this.destino = destino;
	}




	public SimpleStringProperty getStatus() {
		return status;
	}




	public void setStatus(SimpleStringProperty status) {
		this.status = status;
	}




	public MovimentacaoGenerico(MovimentacaoUtil mov){
		this.movimentacao = mov;
		solicitante = new SimpleStringProperty(mov.getSolicitante());
		dtSolicitacao = new SimpleLongProperty(mov.getDtSolicitacao().getTime());
		avaliador = new SimpleStringProperty(mov.getAvaliador());
		dataAprovacao = new SimpleLongProperty(mov.getDataAprovacao().getTime());
		atual = new SimpleStringProperty(mov.getAtual());
		destino = new SimpleStringProperty(mov.getDestino());
		status = new SimpleStringProperty(mov.getStatus().toString());
		cdPatrimonio = new SimpleStringProperty(mov.getCdPatrimonio());
	}
}
