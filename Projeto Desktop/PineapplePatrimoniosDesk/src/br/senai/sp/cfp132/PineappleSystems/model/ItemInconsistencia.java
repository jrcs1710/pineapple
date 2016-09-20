package br.senai.sp.cfp132.PineappleSystems.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ItemInconsistencia {

	@Id
	@GeneratedValue
	private Long id;
	@OneToOne
	private Patrimonio patrimonio;
	private TipoInconsistencia tipoInconsistencia;
	private boolean status;
	
	

	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Patrimonio getPatrimonio() {
		return patrimonio;
	}
	public void setPatrimonio(Patrimonio patrimonio) {
		this.patrimonio = patrimonio;
	}
	public TipoInconsistencia getTipoInconsistencia() {
		return tipoInconsistencia;
	}
	public void setTipoInconsistencia(TipoInconsistencia tipoInconsistencia) {
		this.tipoInconsistencia = tipoInconsistencia;
	}
	
	 
	
	
}
