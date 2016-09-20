package br.senai.sp.cfp132.pineapplesystems.model;

public enum Cargo {

	RESP("Responsável"), GER("Gerente"), AUDIT("Auditor");
	
	private String descricao;
	
	 Cargo(String desc){
		this.descricao = desc;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.descricao;
	}
}
