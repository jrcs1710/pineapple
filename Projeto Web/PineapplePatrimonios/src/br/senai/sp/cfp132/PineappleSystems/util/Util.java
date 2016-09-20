package br.senai.sp.cfp132.PineappleSystems.util;

import java.io.IOException;


import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



public class Util {

	
	
	
	/**
	 * Insere o objeto passado na sess�o
	 * @param String descricao, Object objeto
	 * 
	 * 
	 * **/
	public static void passarObjeto(String descricao, Object o) {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();

		HttpSession session = request.getSession();
		session.setAttribute(descricao, o);
		

	}
	
	/**
	 * Remove o objeto passado na sess�o
	 * @param String descricao
	 * 
	 * 
	 * **/
	public static void removerObjeto(String descricao) {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();

		HttpSession session = request.getSession();
		session.removeAttribute(descricao);
		

	}

	
	/**
	 * Redireciona para outra p�gina
	 * 
	 * @param String caminho
	 * **/
	public static void redirecionarPagina(String caminho) {

		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.redirect(caminho);
	

		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}

	}
	
	/**
	 * Carrega o objeto da sessao
	 * 
	 * @param String key
	 * @return Object
	 * **/
	public static Object carregarObjeto(String key) {

		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest req = (HttpServletRequest) ec.getRequest();
		HttpSession session = req.getSession();
		
		return session.getAttribute(key);
		

		
	}
	
	
}
