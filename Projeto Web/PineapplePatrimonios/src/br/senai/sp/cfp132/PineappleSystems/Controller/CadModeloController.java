package br.senai.sp.cfp132.PineappleSystems.Controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.senai.sp.cfp132.PineappleSystems.dao.ModeloDao;
import br.senai.sp.cfp132.PineappleSystems.dao.TipoDao;
import br.senai.sp.cfp132.PineappleSystems.model.Modelo;
import br.senai.sp.cfp132.PineappleSystems.model.Tipo;
import br.senai.sp.cfp132.PineappleSystems.util.Mensagens;
import br.senai.sp.cfp132.PineappleSystems.util.Util;

@Controller
@Scope("request")
@Transactional
public class CadModeloController {

	@Autowired
	ModeloDao modDao;
	@Autowired
	TipoDao tipDao;

	private Modelo modelo = new Modelo();
	private Tipo tipo = new Tipo();
	private List<Tipo> listTipo;
	private static byte[] b;
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public static byte[] getB() {
		return b;
	}

	public static void setB(byte[] b) {
		CadModeloController.b = b;
	}

	public List<Tipo> getListTipo() {
		listTipo = tipDao.buscarTodos();

		return listTipo;
	}

	public void setListTipo(List<Tipo> listTipo) {
		this.listTipo = listTipo;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public void salvar() {
		
		if (b == null) {
			Mensagens.erro("Insira uma foto!", null);
		} else {
			modelo.setFoto(b);
			tipo = (Tipo) tipDao.buscarId(id);
			modelo.setTipo(tipo);
			if (modelo.getId() != 0) {
				if (modDao.buscarModelo_Nome(modelo.getNome().trim()) != null && (modDao.buscarModelo_Nome(modelo.getNome().trim())).getId() == modelo.getId()) {
					modelo.setStatus(true);
					modDao.alterar(modelo);
					Mensagens.informacao("Sucesso! " + modelo.getNome().trim()
							+ " alterado com sucesso!", null);
					modelo = new Modelo();
					tipo = new Tipo();
					id = Long.parseLong("0");
				}else {
					Mensagens.erro("Erro! Nome ja está sendo utilizado.", null);
				}
				
				
				 
			} else {
				
				if (modDao.buscarModelo_Nome(modelo.getNome().trim()) != null){
					
					System.out.println("Passou aqui");
					Mensagens.erro("Erro! Nome ja está sendo ultilizado.", null);
				}else {
					modelo.setStatus(true);
					modDao.inserir(modelo);
					Mensagens.informacao("Sucesso! " + modelo.getNome().trim()
							+ " inserido com sucesso!", null);
					modelo = new Modelo();
					tipo = new Tipo();
					id = Long.parseLong("0");
				}
				
			}
			
		}
		

	}

	public void carregarImagem(FileUploadEvent event) {

		b = event.getFile().getContents();

		modelo.setFoto(b);
		Mensagens.informacao(event.getFile().getFileName()
				+ " carregado com sucesso", null);

	}
	
	@PostConstruct
	public void init() {
		if (Util.carregarObjeto("selectedModelo") != null) {
			modelo = (Modelo) Util.carregarObjeto("selectedModelo");
			b = modelo.getFoto();
			id = modelo.getTipo().getId();
			Util.removerObjeto("selectedModelo");
		}

	}
}
