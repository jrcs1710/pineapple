package br.senai.sp.cfp132.PineappleSystems.Controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.senai.sp.cfp132.PineappleSystems.dao.AmbienteDao;
import br.senai.sp.cfp132.PineappleSystems.dao.ConferenciaGeralDao;
import br.senai.sp.cfp132.PineappleSystems.dao.EmpresaDao;
import br.senai.sp.cfp132.PineappleSystems.dao.FuncionarioDao;
import br.senai.sp.cfp132.PineappleSystems.dao.UsuarioDao;
import br.senai.sp.cfp132.PineappleSystems.model.Ambiente;
import br.senai.sp.cfp132.PineappleSystems.model.Cargo;
import br.senai.sp.cfp132.PineappleSystems.model.Conferencia;
import br.senai.sp.cfp132.PineappleSystems.model.Empresa;
import br.senai.sp.cfp132.PineappleSystems.model.Funcionario;
import br.senai.sp.cfp132.PineappleSystems.model.Usuario;
import br.senai.sp.cfp132.PineappleSystems.util.GMailSender;
import br.senai.sp.cfp132.PineappleSystems.util.Mensagens;
import br.senai.sp.cfp132.PineappleSystems.util.Util;

@Controller
@Scope("session")
@Transactional
public class LoginController {
	public static Boolean enviados = false;

	private String nome;

	private String senha;
	private String email;

	private Usuario usuario = new Usuario();
	@Autowired
	AmbienteDao ambDao;
	@Autowired
	FuncionarioDao funcDao;

	@Autowired
	ConferenciaGeralDao confGeralDao;
	
	@Autowired
	FuncionarioDao funcdao;
	@Autowired
	UsuarioDao userDao;
	@Autowired
	EmpresaDao empDao;
	private Empresa empresa;

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	private Funcionario funcionario = new Funcionario();

	public Usuario getUsuario() {
		return usuario;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

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
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String esqueci(){
		
		return "/esqueci_senha.xhtml";		
	}
	
	public void alterarSenha(){
		usuario = userDao.buscarNome(nome);
		funcionario = funcdao.buscarFuncEmail(email);

		if (usuario != null && funcionario != null) {
			if (funcionario.getUsuario() == usuario) {
				String senhaNova = gerarSenha();
				usuario.setSenha(senhaNova);
				userDao.alterar(usuario);

				GMailSender sender = new GMailSender(
						"pineapplesys@gmail.com", "ucantcme");
				try {
					sender.sendMail("Alteração de senha", "Prezado " + funcionario.getNome() +
							", \n\nRecebemos um pedido de mudança de senha para a sua conta."
							+ " Segue abaixo os dados do seu login: \n\n"
							+ "Usuário: " + funcionario.getUsuario().getNomeUsuario() + " \n"
							+ "Senha: " + funcionario.getUsuario().getSenha() + " \n\n"
							+ "Atenciosamente, \n"
							+ "Equipe Pineapple Systems"								
							,"pineapplesys@gmail.com",
							email);
					
					Mensagens.informacao("Sucesso!", "Senha alterada, verifique-a no seu e-mail");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			Mensagens.erro("Erro!", "Usuario ou e-mail invalidos!");			
		}
		
	}
	
	

	public String gerarSenha() {
		String letrasM = "ABCDEFGHIJK";
		String charsEspeciais = "+-*<>?!@#$%&";
		int N = letrasM.length();
		int M = charsEspeciais.length();
		Random rd = new Random();
		StringBuilder sb = new StringBuilder();
		sb.append(letrasM.charAt(rd.nextInt(N)));
		sb.append(charsEspeciais.charAt(rd.nextInt(M)));


		return new BigInteger(30, rd).toString(32) + rd.nextInt(9)
				+ sb.toString();

	}

	public void logar() {
		new ConferenciaTask();
		
		usuario = userDao.buscarNomeSenha(nome, senha);

		if (usuario == null) {
			Mensagens.erro("Erro!", "Usuario ou senha invalidos!");

		} else {			

			funcionario = funcdao.buscarUsuario(usuario);
			Util.passarObjeto("usuario", funcionario);
			empresa = (Empresa) empDao.buscarId((long) 1);
			// TODO CONDI��O PARA REDIRECIONAR A P�GINA ADM
			if (funcionario.getCargo().equals(Cargo.GER)) {
				
				
				if (empresa != null) {
					Util.passarObjeto("empresa", empresa);
					Util.redirecionarPagina("gerencia/requisicoes.xhtml");
					
				}else {
					Util.redirecionarPagina("gerencia/info_empresa.xhtml");
				}

				
				Mensagens
						.informacao("Bem Vindo!", "Login efetuado com sucesso");
			}else if (funcionario.getCargo().equals(Cargo.RESP)) {
				Util.redirecionarPagina("funcionario/seus_ambientes.xhtml");
			}

		}

	}

	public void logout() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest req = (HttpServletRequest) ec.getRequest();
		HttpSession session = req.getSession();

		try {
			ec.redirect("../login.xhtml");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		session.invalidate();

	}
	
	public class ConferenciaTask{
		
		public ConferenciaTask() {
			if (confGeralDao.buscarUltima().getDtFim().before(Calendar.getInstance())) {
				if (enviados == false) {
					if (ambDao.buscarTodosAtivos().size() != confGeralDao
							.buscarUltima().getConferencia().size()) {
						new Thread(new Runnable() {

							@Override
							public void run() {
								try {
									GMailSender sender = new GMailSender(
											"pineapplesys@gmail.com", "ucantcme");
										sender.sendMail(
												"Conferência não efetuada",
												"Prezado, \n\nFoi identificado que uma ou mais conferências não foram realizadas dentro do prazo."
												+ "\nSegue a lista de ambientes: \n\n"
												+ buscarNaoConferidos()
												+ " \n\n" + "Número da conferência: " + confGeralDao.buscarUltima().getNrConferencia()
												+ " \n\n" + "Atenciosamente, \n"
												+ "Equipe Pineapple Systems",
												"pineapplesys@gmail.com", buscarGerentes());
								} catch (Exception e) {
								
									Mensagens
											.erro("Falha ao enviar e-mail. Verifique sua conexão de internet",
													null);
								}

							}
						}).start();

						enviados = true;
					}
				}
			} else {
				enviados = false;
			}
		}	
			
	}
	
	
	public String buscarNaoConferidos(){
		List<Ambiente> listAmb = ambDao.buscarTodosAtivos();
		List<Ambiente> listAmbConf = new ArrayList<Ambiente>();
		List<Conferencia> listConf = confGeralDao.buscarUltima().getConferencia();
		List<String> listStrAmbientes = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < listConf.size(); i++) {
			listAmbConf.add(listConf.get(i).getAmbiente());
			
		}
		
		for (int j = 0; j < listAmb.size(); j++) {
			if (!listAmbConf.contains(listAmb.get(j))) {
				if (!listStrAmbientes.contains(listAmb.get(j).getNome())) {
					sb.append(listAmb.get(j).getNome());
					sb.append("\n");					
				}
			}				
		}
		

		return sb.toString().trim();
	}

	public String buscarGerentes() {
		List<Funcionario> listFunc = funcDao.buscarFuncCargo(Cargo.GER, true);
		List<String> listEmails = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < listFunc.size(); i++) {
			if (!listEmails.contains(listFunc.get(i).getEmail()) && !listFunc.get(i).getEmail().equals("null")) {
				sb.append(listFunc.get(i).getEmail());
				sb.append(", ");
				listEmails.add(listFunc.get(i).getEmail());
			}

		}

		return sb.toString().trim();
	}

}
