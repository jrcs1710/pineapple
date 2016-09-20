package br.senai.sp.cfp132.PineappleSystems.Controller;




import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import br.senai.sp.cfp132.PineappleSystems.Dao.Factory;
import br.senai.sp.cfp132.PineappleSystems.Dao.FuncionarioDao;
import br.senai.sp.cfp132.PineappleSystems.Dao.UsuarioDao;
import br.senai.sp.cfp132.PineappleSystems.Util.VerificarUsuario;
import br.senai.sp.cfp132.PineappleSystems.View.EscolherRelatorioView;
import br.senai.sp.cfp132.PineappleSystems.View.LoginView;
import br.senai.sp.cfp132.PineappleSystems.View.TrocaSenhaView;
import br.senai.sp.cfp132.PineappleSystems.model.Cargo;
import br.senai.sp.cfp132.PineappleSystems.model.Funcionario;
import br.senai.sp.cfp132.PineappleSystems.model.Usuario;


public class LoginController implements Initializable {

	@FXML
	private TextField tfUsuario;
	@FXML
	private PasswordField pfSenha;
	@FXML
	private Button btLogin;
	@FXML
	private Button btSair;
	@FXML
	private Hyperlink linkSenha;
	
	//Modelos
	Usuario usuario;
	Funcionario funcionario;
	
	//DAOs
	UsuarioDao usuDao = new UsuarioDao();
	FuncionarioDao funcDao = new FuncionarioDao();
	

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		//HYPERLINK
		linkSenha.setOnAction(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				try {
					new TrocaSenhaView().start(new Stage());
					LoginView.getStage().hide();
				} catch (Exception e) {
					e.printStackTrace();
					e.getMessage();
					JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
				


			}
		});
		//BOTAO LOGIN
		btLogin.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				if (tfUsuario.getText().isEmpty() || pfSenha.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Campos em branco!", "Erro", JOptionPane.ERROR_MESSAGE);
				}else {
					if (efetuarLogin(tfUsuario.getText(), pfSenha.getText())) {
						funcionario = funcDao.buscarUsuario(usuario);
						if (funcionario.getCargo().equals(Cargo.AUDIT)) {
							JOptionPane.showMessageDialog(null, "", "Erro", JOptionPane.ERROR_MESSAGE);
						}else {
							VerificarUsuario.setFuncionarioLogado(funcionario);
							try {
								new EscolherRelatorioView().start(new Stage());
								LoginView.getStage().close();
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
							}
							
						}
						
						
					}else {
						JOptionPane.showMessageDialog(null, "Login e/ou senha inválidos", "Erro", JOptionPane.ERROR_MESSAGE);
					}
				}
				
				
				
				
				
			}
		});
		//EVENTO LOGIN TXFSENHA
		pfSenha.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					
					if (tfUsuario.getText().isEmpty() || pfSenha.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Campos em branco!", "Erro", JOptionPane.ERROR_MESSAGE);
					}else {
						if (efetuarLogin(tfUsuario.getText(), pfSenha.getText())) {
							funcionario = funcDao.buscarUsuario(usuario);
							if (funcionario.getCargo().equals(Cargo.AUDIT)) {
								JOptionPane.showMessageDialog(null, "", "Erro", JOptionPane.ERROR_MESSAGE);
							}else {
								VerificarUsuario.setFuncionarioLogado(funcionario);
								try {
									new EscolherRelatorioView().start(new Stage());
									LoginView.getStage().close();
								} catch (Exception e) {
									JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
								}
								
							}
							
							
						}else {
							JOptionPane.showMessageDialog(null, "Login e/ou senha inválidos", "Erro", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				
			}
		});
		//EVENTO LOGIN TFUSUARIO
		tfUsuario.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					
					if (tfUsuario.getText().isEmpty() || pfSenha.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Campos em branco!", "Erro", JOptionPane.ERROR_MESSAGE);
					}else {
						if (efetuarLogin(tfUsuario.getText(), pfSenha.getText())) {
							funcionario = funcDao.buscarUsuario(usuario);
							if (funcionario.getCargo().equals(Cargo.AUDIT)) {
								JOptionPane.showMessageDialog(null, "", "Erro", JOptionPane.ERROR_MESSAGE);
							}else {
								VerificarUsuario.setFuncionarioLogado(funcionario);
								try {
									new EscolherRelatorioView().start(new Stage());
									LoginView.getStage().close();
								} catch (Exception e) {
									JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
								}
								
							}
							
							
						}else {
							JOptionPane.showMessageDialog(null, "Login e/ou senha inválidos", "Erro", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				
			}
		});
		
		//EVENTO LOGIN
		btLogin.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					
					if (tfUsuario.getText().isEmpty() || pfSenha.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Campos em branco!", "Erro", JOptionPane.ERROR_MESSAGE);
					}else {
						if (efetuarLogin(tfUsuario.getText(), pfSenha.getText())) {
							funcionario = funcDao.buscarUsuario(usuario);
							if (funcionario.getCargo().equals(Cargo.AUDIT)) {
								JOptionPane.showMessageDialog(null, "", "Erro", JOptionPane.ERROR_MESSAGE);
							}else {
								VerificarUsuario.setFuncionarioLogado(funcionario);
								try {
									new EscolherRelatorioView().start(new Stage());
									LoginView.getStage().close();
								} catch (Exception e) {
									JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
								}
								
							}
							
							
						}else {
							JOptionPane.showMessageDialog(null, "Login e/ou senha inválidos", "Erro", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				
			}
		});
		//BOTAO SAIR
		btSair.setOnAction(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				LoginView.getStage().close();
				Factory.close();


			}
		});
		
		
	}

	//EFETUAR LOGIN
	private boolean efetuarLogin(String nomeUsuario, String senha){
		boolean temUsuario = false;
		if (usuDao.buscarNomeSenha(nomeUsuario, senha) != null) {
			usuario = (Usuario) usuDao.buscarNomeSenha(nomeUsuario, senha);
			temUsuario = true;
		}
		
		
		
		return temUsuario;
	}
	
	
}
