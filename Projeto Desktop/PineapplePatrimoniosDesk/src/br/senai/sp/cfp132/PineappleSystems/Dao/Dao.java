package br.senai.sp.cfp132.PineappleSystems.Dao;

import java.util.List;



public interface Dao {

	public void inserir(Object o);
	public void alterar(Object o);
	
	public Object buscarId(Long id);
	
}
