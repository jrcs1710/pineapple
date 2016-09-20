package br.senai.sp.cfp132.PineappleSystems.Controller;

import java.math.BigInteger;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.senai.sp.cfp132.PineappleSystems.Dao.FuncionarioDao;
import br.senai.sp.cfp132.PineappleSystems.Dao.UsuarioDao;
import br.senai.sp.cfp132.PineappleSystems.Util.GMailSender;
import br.senai.sp.cfp132.PineappleSystems.View.LoginView;
import br.senai.sp.cfp132.PineappleSystems.View.TrocaSenhaView;
import br.senai.sp.cfp132.PineappleSystems.model.Funcionario;
import br.senai.sp.cfp132.PineappleSystems.model.Usuario;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TrocaSenhaController implements Initializable {
	@FXML
	private Button btEnviar;
	@FXML
	private Button btVoltar;
	@FXML
	private TextField tfEmail;
	@FXML
	private TextField tfUsuario;

	// DAO
	UsuarioDao userDao = new UsuarioDao();
	FuncionarioDao funcdao = new FuncionarioDao();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		btVoltar.setOnAction(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				TrocaSenhaView.getStage().close();
				LoginView.getStage().show();

			}
		});

		btEnviar.setOnAction(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				if (!tfEmail.getText().isEmpty() && !tfUsuario.getText().isEmpty()) {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							
							alterarSenha(tfUsuario.getText(), tfEmail.getText());
						}
					});
					
				}else {
			JOptionPane.showMessageDialog(null, "Campos vazios", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}

	public void alterarSenha(String nome, String email) {
		Usuario usuario;
		Funcionario funcionario;
		usuario = userDao.buscarNome(nome);
		funcionario = funcdao.buscarFuncEmail(email);

		if (usuario != null && funcionario != null) {
			if (funcionario.getUsuario() == usuario) {
				String senhaNova = gerarSenha();
				usuario.setSenha(senhaNova);
				userDao.alterar(usuario);

				GMailSender sender = new GMailSender("pineapplesys@gmail.com",
						"ucantcme");
				try {
					sender.sendMail(
							"Alteração de senha",
							"Prezado "
									+ funcionario.getNome()
									+ ", \n\nRecebemos um pedido de mudança de senha para a sua conta."
									+ " Segue abaixo os dados do seu login: \n\n"
									+ "Usuário: "
									+ funcionario.getUsuario().getNomeUsuario()
									+ " \n" + "Senha: "
									+ funcionario.getUsuario().getSenha()
									+ " \n\n" + "Atenciosamente, \n"
									+ "Equipe Pineapple Systems",
							"pineapplesys@gmail.com", email);
					JOptionPane.showMessageDialog(null,
							"Senha alterada, verifique-a no seu e-mail",
							"Sucesso!", JOptionPane.INFORMATION_MESSAGE);

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Usuário ou e-mail inválidos!",
					"Erro", JOptionPane.ERROR_MESSAGE);
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
}
