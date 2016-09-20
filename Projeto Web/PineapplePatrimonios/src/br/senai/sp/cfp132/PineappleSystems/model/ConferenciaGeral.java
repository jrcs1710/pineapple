package br.senai.sp.cfp132.PineappleSystems.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class ConferenciaGeral {

	@Id
	@GeneratedValue
	private long id;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(joinColumns = @JoinColumn(name = "ConfGer_id"), inverseJoinColumns = @JoinColumn(name = "Conf_id", unique=false))
	private List<Conferencia> conferencia;
	private Calendar dtInicio;
	private Calendar dtFim;
	private String nrConferencia;
	
	
	
	
	
	public String getNrConferencia() {
		return nrConferencia;
	}
	public void setNrConferencia(String nrConferencia) {
		this.nrConferencia = nrConferencia;
	}
	public Calendar getDtInicio() {
		return dtInicio;
	}
	public void setDtInicio(Calendar dtInicio) {
		this.dtInicio = dtInicio;
	}
	public Calendar getDtFim() {
		return dtFim;
	}
	public void setDtFim(Calendar dtFim) {
		this.dtFim = dtFim;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<Conferencia> getConferencia() {
		return conferencia;
	}
	public void setConferencia(List<Conferencia> conferencia) {
		this.conferencia = conferencia;
	}
	
}
