package br.senai.sp.cfp132.PineappleSystems.Controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.senai.sp.cfp132.PineappleSystems.dao.ConferenciaGeralDao;
import br.senai.sp.cfp132.PineappleSystems.dao.InconsistenciaDao;
import br.senai.sp.cfp132.PineappleSystems.model.Conferencia;
import br.senai.sp.cfp132.PineappleSystems.model.ConferenciaGeral;
import br.senai.sp.cfp132.PineappleSystems.model.Inconsistencia;

@Transactional
@Controller
@Scope("view")
public class ConferenciaController {

	@Autowired
	ConferenciaGeralDao confGerDao;
	@Autowired
	InconsistenciaDao inconDao;
	
	
	private List<ConferenciaGeral> listConfGer;
	private ConferenciaGeral confGer;
	
	private Conferencia conferencia;
	private Inconsistencia inconsistencia;
	
	
	
	
	public Conferencia getConferencia() {
		return conferencia;
	}
	public void setConferencia(Conferencia conferencia) {
		this.conferencia = conferencia;
	}
	public Inconsistencia getInconsistencia() {
		
			inconsistencia = inconDao.buscarInconsistencia_Conferencia(conferencia).get(0);
		
		
		return inconsistencia;
	}
	public void setInconsistencia(Inconsistencia inconsistencia) {
		this.inconsistencia = inconsistencia;
	}
	
	public List<ConferenciaGeral> getListConfGer() {
		listConfGer = confGerDao.buscarTodos();
		return listConfGer;
	}
	public void setListConfGer(List<ConferenciaGeral> listConfGer) {
		this.listConfGer = listConfGer;
	}
	public ConferenciaGeral getConfGer() {
		
		return confGer;
	}
	public void setConfGer(ConferenciaGeral confGer) {
		this.confGer = confGer;
	}
	
	


	
	
	
}
