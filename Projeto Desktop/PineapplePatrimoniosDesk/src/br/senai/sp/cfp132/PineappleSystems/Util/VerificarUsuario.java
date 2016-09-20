package br.senai.sp.cfp132.PineappleSystems.Util;

import br.senai.sp.cfp132.PineappleSystems.model.Funcionario;

public class VerificarUsuario {

	private static Funcionario funcionarioLogado;

	public static Funcionario getFuncionarioLogado() {
		return funcionarioLogado;
	}

	public static void setFuncionarioLogado(Funcionario funcionarioLogado) {
		VerificarUsuario.funcionarioLogado = funcionarioLogado;
	}
	
	public boolean isUsuarioLogado(Funcionario f){
		if (funcionarioLogado.equals(f)) {
			return true;
		}
		return false;
		
	}
	
	
}
