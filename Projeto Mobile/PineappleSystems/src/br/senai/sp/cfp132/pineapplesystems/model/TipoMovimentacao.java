package br.senai.sp.cfp132.pineapplesystems.model;

public enum TipoMovimentacao {

	ENTRADA ("Entrada"), SAIDA ("Saida"), TRANSF ("Transfer�ncia"), EMPRES("Empr�stimo");

	String descricao;
	
	TipoMovimentacao(String desc){
		this.descricao = desc;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.descricao;
	}
	
}
