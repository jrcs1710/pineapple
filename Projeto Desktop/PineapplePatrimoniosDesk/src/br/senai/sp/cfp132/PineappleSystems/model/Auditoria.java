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
public class Auditoria {

	@Id
	@GeneratedValue
	private Long id;
	private String nrAuditoria;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(joinColumns = @JoinColumn(name = "Aud_id"), inverseJoinColumns = @JoinColumn(name = "Pat_id", unique=false))
	private List<Patrimonio> patrimonio;
	private Calendar dtInicio;
	private Calendar dtFim;
	
	
	
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Patrimonio> getPatrimonio() {
		
		return patrimonio;
	}
	public String getNrAuditoria() {
		return nrAuditoria;
	}
	public void setNrAuditoria(String nrAuditoria) {
		this.nrAuditoria = nrAuditoria;
	}
	public void setPatrimonio(List<Patrimonio> patrimonio) {
		
		this.patrimonio = patrimonio;
	}
	
	
	
}
