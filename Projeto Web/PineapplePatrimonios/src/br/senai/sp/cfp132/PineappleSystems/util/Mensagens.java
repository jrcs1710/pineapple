package br.senai.sp.cfp132.PineappleSystems.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Mensagens {

	/**
	 * Método para enviar uma mensagem informativa
	 * **/
	public static void informacao(String titulo, String mensagem){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, mensagem));
		
	}
	
	/**
	 * Método para enviar uma mensagem de erro
	 * **/
	public static void erro(String titulo, String mensagem){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, mensagem));
		
	}
	
}

