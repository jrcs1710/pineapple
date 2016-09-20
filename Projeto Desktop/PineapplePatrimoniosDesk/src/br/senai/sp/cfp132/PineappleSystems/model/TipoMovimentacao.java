package br.senai.sp.cfp132.PineappleSystems.model;

public enum TipoMovimentacao {

	ENTRADA ("Entrada"), SAIDA ("Saída"), TRANSF ("Transferência"), EMPRES("Empréstimo");

	String descricao;
	
	TipoMovimentacao(String desc){
		this.descricao = desc;
	}
	
	@Override
	public String toString() {
		return this.descricao;
	}
	
}
