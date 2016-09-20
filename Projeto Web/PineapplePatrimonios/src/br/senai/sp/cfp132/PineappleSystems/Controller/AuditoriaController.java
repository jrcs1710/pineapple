package br.senai.sp.cfp132.PineappleSystems.Controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.senai.sp.cfp132.PineappleSystems.dao.AuditoriaDao;
import br.senai.sp.cfp132.PineappleSystems.model.Auditoria;

@Transactional
@Controller
@Scope("view")
public class AuditoriaController {

	@Autowired
	AuditoriaDao audDao;
	
	private List<Auditoria> listAuditoria;
	private Auditoria auditoria;
	
	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public List<Auditoria> getListAuditoria() {
		listAuditoria = audDao.buscarTodos();
		return listAuditoria;
	}

	public void setListAuditoria(List<Auditoria> listAuditoria) {
		this.listAuditoria = listAuditoria;
	}
	
	
	
	
}
