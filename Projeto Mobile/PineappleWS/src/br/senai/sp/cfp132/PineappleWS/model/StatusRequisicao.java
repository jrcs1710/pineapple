package br.senai.sp.cfp132.PineappleWS.model;

public enum StatusRequisicao {

	APROV ("Aprovado"), RECUS ("Recusado"), ABERTO ("Em Aberto");
	
	String descricao;
	
	StatusRequisicao(String desc){
		this.descricao = desc;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.descricao;
	}
	
}
