package br.senai.sp.cfp132.PineappleWS.model;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Inconsistencia {

	@Id
	@GeneratedValue
	private Long id;	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(joinColumns = @JoinColumn(name = "Incon_id"), inverseJoinColumns = @JoinColumn(name = "ItemIncon_id", unique=false))	
	private List<ItemInconsistencia> itemInconsistencia;
	@OneToOne
	private Conferencia conferencia;
	
	
	
	
	public Conferencia getConferencia() {
		return conferencia;
	}
	public void setConferencia(Conferencia conferencia) {
		this.conferencia = conferencia;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<ItemInconsistencia> getItemInconsistencia() {
		return itemInconsistencia;
	}
	public void setItemInconsistencia(List<ItemInconsistencia> itemInconsistencia) {
		this.itemInconsistencia = itemInconsistencia;
	}
	
	
	
	
}
