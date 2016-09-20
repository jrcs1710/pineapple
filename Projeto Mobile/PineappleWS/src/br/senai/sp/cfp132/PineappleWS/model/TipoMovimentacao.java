package br.senai.sp.cfp132.PineappleWS.model;

public enum TipoMovimentacao {

	ENTRADA ("Entrada"), SAIDA ("Saída"), TRANSF ("Transferência"), EMPRES("Empréstimo");

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
