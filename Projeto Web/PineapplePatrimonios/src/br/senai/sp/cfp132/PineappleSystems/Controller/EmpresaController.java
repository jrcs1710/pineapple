package br.senai.sp.cfp132.PineappleSystems.Controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.transaction.Transactional;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.senai.sp.cfp132.PineappleSystems.dao.EmpresaDao;
import br.senai.sp.cfp132.PineappleSystems.model.Empresa;
import br.senai.sp.cfp132.PineappleSystems.util.Mensagens;
import br.senai.sp.cfp132.PineappleSystems.util.Util;

@Controller
@Scope("request")
@Transactional
public class EmpresaController {

	@Autowired
	EmpresaDao empDao;
	private Empresa empresa = new Empresa();
	private static byte[] b;
	private UploadedFile file;

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public byte[] getB() {
		return b;
	}

	public void setB(byte[] b) {
		this.b = b;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void salvarEmpresa() {
		if (b == null) {
			Mensagens.erro("Insira uma foto!", null);
		} else {
			empresa.setLogotipo(b);
			System.out.println(empresa.getLogotipo());
			if (empresa.getId() != 0) {
				empDao.alterar(empresa);
				Mensagens.informacao(
						"Sucesso! Dados da empresa alterados com sucesso.",
						null);
			} else {

				empDao.inserir(empresa);
				Mensagens.informacao(
						"Sucesso! Dados da empresa cadastrados com sucesso",
						null);
			}
			Util.passarObjeto("empresa", empresa);
			carregarDadosEmpresa();
		}

	}

	public void carregarLogo(FileUploadEvent event) {

		b = event.getFile().getContents();

		empresa.setLogotipo(b);

		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(event.getFile().getFileName()
						+ "carregado com sucesso!"));

	}

	@PostConstruct
	public void carregarDadosEmpresa() {
		if (Util.carregarObjeto("empresa") != null) {
			empresa = (Empresa) Util.carregarObjeto("empresa");
			b = empresa.getLogotipo();
		}

	}

}
