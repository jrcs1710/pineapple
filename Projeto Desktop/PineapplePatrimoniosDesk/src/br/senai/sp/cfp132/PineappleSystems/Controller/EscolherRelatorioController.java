package br.senai.sp.cfp132.PineappleSystems.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.senai.sp.cfp132.PineappleSystems.Dao.Factory;
import br.senai.sp.cfp132.PineappleSystems.View.EscolherRelatorioView;
import br.senai.sp.cfp132.PineappleSystems.View.RelatorioMovimentacaoView;
import br.senai.sp.cfp132.PineappleSystems.View.TelaInicialView;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class EscolherRelatorioController implements Initializable{

	@FXML
	private Button btLogout;
	@FXML
	private Button btPatrimonio;
	@FXML
	private Button btMovimentacao;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		// LOGOUT
				btLogout.setOnAction(new EventHandler() {

					@Override
					public void handle(Event arg0) {
						EscolherRelatorioView.getStage().close();
						Factory.close();


					}
				});
				
				//RELATORIO DE PATRIMONIO
				btPatrimonio.setOnAction(new EventHandler() {

					@Override
					public void handle(Event arg0) {
					try {
						new TelaInicialView().start(new Stage());
						EscolherRelatorioView.getStage().hide();
					} catch (Exception e) {
						e.getMessage();
						JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					}
						
					}
				});
		
	
				//RELATORIO DE MOVIMENTAÇÕES
				btMovimentacao.setOnAction(new EventHandler() {

					@Override
					public void handle(Event arg0) {
						try {
							new RelatorioMovimentacaoView().start(new Stage());
							EscolherRelatorioView.getStage().hide();
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
						}
						
					}
				});
	}
	
	
	
}
