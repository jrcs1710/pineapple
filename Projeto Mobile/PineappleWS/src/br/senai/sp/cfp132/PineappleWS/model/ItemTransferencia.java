package br.senai.sp.cfp132.PineappleWS.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemTransferencia {

	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private Patrimonio patrimonio;
	private boolean status;
	
	private TipoMovimentacao tipoMovimentacao;

	/*-------------------------------------------------------------------------------------------------------------------------------------------------*/

	public Long getId() {
		return id;
	}

	public TipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	
}
