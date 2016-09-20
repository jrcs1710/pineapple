package br.senai.sp.cfp132.PineappleSystems.model;

public enum TipoInconsistencia {

	PATSOB("Patrimonio sobre-excedente"), PATFALT("Patrimonio faltando");
	
	String descricao;
	
	TipoInconsistencia(String desc){
		this.descricao = desc;
	}
	
	@Override
	public String toString() {
		return this.descricao;
	}
}
