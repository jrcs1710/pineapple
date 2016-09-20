package br.senai.sp.cfp132.PineappleSystems.Controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.senai.sp.cfp132.PineappleSystems.dao.PatrimonioDao;
import br.senai.sp.cfp132.PineappleSystems.model.Patrimonio;

@Transactional
@Scope("view")
@Controller
public class TesteDeQr {

	@Autowired
	PatrimonioDao patDao;
	
	private List<Patrimonio> lista;

	public List<Patrimonio> getLista() {
		lista = patDao.buscarTodos();
		return lista;
	}

	public void setLista(List<Patrimonio> lista) {
		this.lista = lista;
	}
	
	
	
	
}
