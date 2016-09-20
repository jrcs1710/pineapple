package br.senai.sp.cfp132.PineappleWS.model;

public enum TipoInconsistencia {

	PATSOB("Patrimonio sobre-excedente"), PATFALT("Patrimonio Faltando");
	
	String descricao;
	
	TipoInconsistencia(String desc){
		this.descricao = desc;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.descricao;
	}
}
