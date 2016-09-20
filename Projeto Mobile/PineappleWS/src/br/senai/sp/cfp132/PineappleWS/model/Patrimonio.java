package br.senai.sp.cfp132.PineappleWS.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class Patrimonio {
	
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	@JoinColumn(name="modelo")
	private Modelo modelo = new Modelo();
	private String descricao;
	@Column(unique= true)
	private String cdPatrimonio;
	@ManyToOne
	@JoinColumn(name="ambiente")
	private Ambiente ambiente = new Ambiente();
	private Calendar dtEntrada;
	private Calendar dtSaida;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Modelo getModelo() {
		return modelo;
	}
	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getCdPatrimonio() {
		return cdPatrimonio;
	}
	public void setCdPatrimonio(String cdPatrimonio) {
		this.cdPatrimonio = cdPatrimonio;
	}
	public Ambiente getAmbiente() {
		return ambiente;
	}
	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}
	public Calendar getDtEntrada() {
		return dtEntrada;
	}
	public void setDtEntrada(Calendar dtEntrada) {
		this.dtEntrada = dtEntrada;
	}
	public Calendar getDtSaida() {
		return dtSaida;
	}
	public void setDtSaida(Calendar dtSaida) {
		this.dtSaida = dtSaida;
	}
	
	@Override
	public boolean equals(Object pat) {
		if (((Patrimonio)pat).getId() == id) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return cdPatrimonio;
	}
}
