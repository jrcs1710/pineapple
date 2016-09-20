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

@Controller
@Scope("view")
@Transactional
public class CadUsuarioController {
	@Autowired
	FuncionarioDao funcDao;
	private Funcionario func = new Funcionario();
	@Autowired
	UsuarioDao usuDao;
	private Usuario usuario = new Usuario();
	private String nome;
	private String senha;
	Long id;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Funcionario getFunc() {
		return func;
	}

	public void setFunc(Funcionario func) {
		this.func = func;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Cargo[] getCargo() {
		return Cargo.values();
	}

	public void salvar() {

		if (usuario.getId() == null) {
			nome = nome.toLowerCase();
			nome = nome.trim();
			if (usuDao.buscarNome(nome) == null) {
				usuario.setNomeUsuario(nome);
				if (senha != "") {
					usuario.setSenha(senha);
				}

				// TODO REDIRECIONAMENTOS LISTA

				if (func.getId() != 0) {

					usuDao.alterar(usuario);

					func.setUsuario(usuario);
					funcDao.alterar(func);
					Mensagens.informacao(
							"Sucesso! Usuário alterado com sucesso", null);

				} else {
					

					func.setId(0);
					if (funcDao.buscarFuncEmail(func.getEmail()) == null) {
						
						func.setEmail(func.getEmail().toLowerCase().trim());
						func.setStatus(true);
						usuDao.inserir(usuario);

						func.setUsuario(usuario);
						funcDao.inserir(func);

						Mensagens
								.informacao(
										"Sucesso! Usuário cadastrado com sucesso",
										null);
						func = new Funcionario();
						usuario = new Usuario();
						nome = null;
						senha = null;
					} else {
						Mensagens
								.erro("ERRO!  Endereço de e-mail ja está sendo ultilizado",
										null);
					}

				}
			} else {
				Mensagens.erro("ERRO! Nome de usuário ja cadastrado!", null);

			}
		} else {
			func.setId(id);
			
			nome = nome.toLowerCase();
			nome = nome.trim();
			if (usuDao.buscarNome(nome) != null
					&& usuDao.buscarNome(nome).getId() == usuario.getId()) {
				usuario.setNomeUsuario(nome);
				usuDao.alterar(usuario);
				func.setUsuario(usuario);
				funcDao.alterar(func);

				Util.redirecionarPagina("lista_funcionario.xhtml");
			} else {
				Mensagens.erro("Nome de usuário existente.", null);
			}

		}

	}

	@PostConstruct
	public void init() {
		if (Util.carregarObjeto("selectedFuncionario") != null) {
			func = (Funcionario) Util.carregarObjeto("selectedFuncionario");

			usuario.setId(func.getUsuario().getId());
			usuario.setNomeUsuario(func.getUsuario().getNomeUsuario());
			usuario.setSenha(func.getUsuario().getSenha());
			nome = func.getUsuario().getNomeUsuario();
			
			id = func.getId();
			Util.removerObjeto("selectedFuncionario");
		}
		func.setId(0);
	}

}
