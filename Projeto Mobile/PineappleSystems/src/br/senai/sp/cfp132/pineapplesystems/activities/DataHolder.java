package br.senai.sp.cfp132.pineapplesystems.activities;

import br.senai.sp.cfp132.pineapplesystems.model.Funcionario;

public class DataHolder {
	private Funcionario f;
	
	public Funcionario getFunc() {
		return f;
	}
	
	public void setFunc(Funcionario f){
		this.f = f;
	}

	private static final DataHolder holder = new DataHolder();
	public static DataHolder getInstance(){
		return holder;
	}
}
