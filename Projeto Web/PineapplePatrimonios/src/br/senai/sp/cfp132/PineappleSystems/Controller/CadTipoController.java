package br.senai.sp.cfp132.PineappleSystems.Controller;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.senai.sp.cfp132.PineappleSystems.dao.TipoDao;
import br.senai.sp.cfp132.PineappleSystems.model.Tipo;
import br.senai.sp.cfp132.PineappleSystems.util.Mensagens;
import br.senai.sp.cfp132.PineappleSystems.util.Util;

@Controller
@Scope("request")
@Transactional
public class CadTipoController {

	
	@Autowired
	TipoDao tipoDao;
	private Tipo tipo = new Tipo();
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	
	public void salvar(){
		if (tipo.getId() != 0) {
			if (tipoDao.buscarTipo_Nome(tipo.getDescricao().trim()) != null && (tipoDao.buscarTipo_Nome(tipo.getDescricao().trim())).get(0).getId() == tipo.getId()) {
				tipoDao.alterar(tipo);
				Mensagens.informacao("Sucesso! Tipo alterado com sucesso!", null);
				tipo = new Tipo();
			}else {
				Mensagens.erro("Erro! Nome ja está sendo ultilizado.", null);
			}
			
			
			
		}else {
			if (tipoDao.buscarTipo_NomeU(tipo.getDescricao().trim()) != null){
				Mensagens.erro("Erro! Nome ja está sendo ultilizado.", null);
			}else {
				tipoDao.inserir(tipo);
				Mensagens.informacao("Sucesso! Tipo inserido com sucesso!", null);
				tipo = new Tipo();
			}
		
		}		
		
		
	}
	
	@PostConstruct
	public void init(){
		if (Util.carregarObjeto("selectedTipo") != null) {
			tipo = (Tipo) Util.carregarObjeto("selectedTipo");
		}
	}
	
}
