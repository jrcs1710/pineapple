package br.senai.sp.cfp132.PineappleWS.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Movimentacao {

	@Id
	@GeneratedValue
	private Long id;
	private Calendar dataAprovacao;
	@ManyToOne
	private Funcionario solicitante;
	@ManyToOne
	private Funcionario avaliador;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(joinColumns = @JoinColumn(name = "Mov_id"), inverseJoinColumns = @JoinColumn(name = "Item_id", unique=false))
	private List<ItemTransferencia> patrimonios;

	private StatusRequisicao status;
	@ManyToOne
	private Ambiente destino;
	@ManyToOne
	private Ambiente atual;
	private String obsAprovador;
	private String obsSolicitante;
	private Calendar dtSolicitacao;

	/*-------------------------------------------------------------------------------------------------------------------------------------------------*/

	
	public Long getId() {
		return id;
	}

	public String getObsAprovador() {
		return obsAprovador;
	}

	public void setObsAprovador(String obsAprovador) {
		this.obsAprovador = obsAprovador;
	}

	public String getObsSolicitante() {
		return obsSolicitante;
	}

	public void setObsSolicitante(String obsSolicitante) {
		this.obsSolicitante = obsSolicitante;
	}

	public Ambiente getAtual() {
		return atual;
	}

	public void setAtual(Ambiente atual) {
		this.atual = atual;
	}

	public Calendar getDtSolicitacao() {
		return dtSolicitacao;
	}

	public void setDtSolicitacao(Calendar dtSolicitacao) {
		this.dtSolicitacao = dtSolicitacao;
	}

	public Ambiente getDestino() {
		return destino;
	}

	public void setDestino(Ambiente destino) {
		this.destino = destino;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StatusRequisicao getStatus() {
		return status;
	}

	public void setStatus(StatusRequisicao status) {
		this.status = status;
	}

	public Calendar getDataAprovacao() {
		return dataAprovacao;
	}

	public void setDataAprovacao(Calendar dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
	}

	public Funcionario getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Funcionario solicitante) {
		this.solicitante = solicitante;
	}

	public Funcionario getAvaliador() {
		return avaliador;
	}

	public void setAvaliador(Funcionario avaliador) {
		this.avaliador = avaliador;
	}

	public List<ItemTransferencia> getPatrimonios() {
		return patrimonios;
	}

	public void setPatrimonios(List<ItemTransferencia> patrimonios) {
		this.patrimonios = patrimonios;
	}

}
