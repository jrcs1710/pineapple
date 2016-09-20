package br.senai.sp.cfp132.PineappleSystems.Controller;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.senai.sp.cfp132.PineappleSystems.dao.FuncionarioDao;
import br.senai.sp.cfp132.PineappleSystems.dao.UsuarioDao;
import br.senai.sp.cfp132.PineappleSystems.model.Cargo;
import br.senai.sp.cfp132.PineappleSystems.model.Funcionario;
import br.senai.sp.cfp132.PineappleSystems.model.Usuario;
import br.senai.sp.cfp132.PineappleSystems.util.Mensagens;
import br.senai.sp.cfp132.PineappleSystems.util.Util;

@Transactional
@Scope("session")
@Controller
public class InformacaoPessoalController {

	@Autowired
	UsuarioDao usuDao;
	@Autowired
	FuncionarioDao funcDao;
	
	
	
	private Cargo cargo;
	private Funcionario func;
	private Usuario usuario;
	private String senha;
	
	

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Funcionario getFunc() {
		return func;
	}

	public void setFunc(Funcionario func) {
		this.func = func;
	}
	
	public void salvar(){
		if (!senha.equals("")) {
			usuario.setSenha(senha);
			usuDao.alterar(usuario);
			func.setUsuario(usuario);
			funcDao.alterar(func);
			Mensagens.informacao("Dados Alterados!", null);
		}
		
		
		
	}
	
	
	@PostConstruct
	public void init(){
		if (Util.carregarObjeto("usuario") != null) {
			func = (Funcionario) Util.carregarObjeto("usuario");
			usuario = func.getUsuario();
			cargo = func.getCargo();
		}
		
	}
	
}
