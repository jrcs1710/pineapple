package br.senai.sp.cfp132.PineappleWS.model;

public enum Cargo {

	RESP("Responsável"), GER("Gerente"), AUDIT("Auditor");
	
	private String descricao;
	
	 Cargo(String desc){
		this.descricao = desc;
	}
	
	@Override
	public String toString() {
		
		return this.descricao;
	}
}
