package br.senai.sp.cfp132.PineappleSystems.Controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import br.senai.sp.cfp132.PineappleSystems.Dao.AmbienteDao;
import br.senai.sp.cfp132.PineappleSystems.Dao.PatrimonioDao;
import br.senai.sp.cfp132.PineappleSystems.Util.PatrimonioGenerico;
import br.senai.sp.cfp132.PineappleSystems.Util.PatrimonioUtil;
import br.senai.sp.cfp132.PineappleSystems.Util.VerificarUsuario;
import br.senai.sp.cfp132.PineappleSystems.View.EscolherRelatorioView;
import br.senai.sp.cfp132.PineappleSystems.View.TelaInicialView;
import br.senai.sp.cfp132.PineappleSystems.model.Ambiente;
import br.senai.sp.cfp132.PineappleSystems.model.Cargo;
import br.senai.sp.cfp132.PineappleSystems.model.Patrimonio;

public class TelaInicialController implements Initializable {

	@FXML
	private Button btLogout;
	@FXML
	private Button btPrintReport;
	@FXML
	private Button btPrintQrCode;
	@FXML
	private Button btSettings;
	@FXML
	private TextField txfCdPatrimonio;
	@FXML
	private TableView<PatrimonioUtil> tbPatrimonio;
	@FXML
	private TableColumn<PatrimonioGenerico, String> colAmbiente;
	@FXML
	private TableColumn<PatrimonioGenerico, String> colNrPat;
	@FXML
	private TableColumn<PatrimonioGenerico, String> colModelo;
	@FXML
	private TableColumn<PatrimonioGenerico, String> colTipo;
	@FXML
	private CheckBox chkTodos;

	@FXML
	private ChoiceBox<Ambiente> cbAmbientes;

	// Genericos
	private ObservableList<PatrimonioUtil> genericosC;

	// DAOs
	private PatrimonioDao patDao = new PatrimonioDao();
	private AmbienteDao ambDao = new AmbienteDao();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		// TABELA
		colAmbiente
				.setCellValueFactory(new PropertyValueFactory<PatrimonioGenerico, String>(
						"ambiente"));
		colNrPat.setCellValueFactory(new PropertyValueFactory<PatrimonioGenerico, String>(
				"cdPatrimonio"));
		colModelo
				.setCellValueFactory(new PropertyValueFactory<PatrimonioGenerico, String>(
						"modelo"));
		colTipo.setCellValueFactory(new PropertyValueFactory<PatrimonioGenerico, String>(
				"tipo"));


		// CHOICEBOX
		if (VerificarUsuario.getFuncionarioLogado().getCargo()
				.equals(Cargo.GER)) {
			ObservableList<Ambiente> ambientes = FXCollections
					.observableArrayList(ambDao.buscarTodosAtivos());
			cbAmbientes.setItems(ambientes);
		} else if (VerificarUsuario.getFuncionarioLogado().getCargo()
				.equals(Cargo.RESP)) {
			ObservableList<Ambiente> ambientes = FXCollections
					.observableArrayList(ambDao
							.buscarAmbienteStatus_Funcionario(
									VerificarUsuario.getFuncionarioLogado(),
									true));
			cbAmbientes.setItems(ambientes);
		}

		// CHECKBOX

		chkTodos.setSelected(false);
		if (VerificarUsuario.getFuncionarioLogado().getCargo()
				.equals(Cargo.RESP)) {
			chkTodos.setVisible(false);
		}
		chkTodos.selectedProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue obs, Object obj1, Object obj2) {
				if (chkTodos.isSelected()) {
					genericosC = FXCollections
							.observableArrayList(converterPatrimonio(patDao
									.buscarTodos()));
					tbPatrimonio.setItems(genericosC);
				}

			}
		});

		// TEXTFIELD
		txfCdPatrimonio.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					if (patDao
							.buscarListPatrimonio_cdPatrimonio(txfCdPatrimonio
									.getText()) != null) {
						genericosC = FXCollections.observableArrayList(converterPatrimonio(patDao
								.buscarListPatrimonio_cdPatrimonio(txfCdPatrimonio
										.getText())));
						tbPatrimonio.setItems(genericosC);
					}else {
						tbPatrimonio.getItems().clear();
					}
				}

			}
		});

		// EVENTO CHANGE CHOICEBOX
		cbAmbientes.getSelectionModel().selectedIndexProperty()
				.addListener(new ChangeListener() {

					@Override
					public void changed(ObservableValue observable,
							Object obj1, Object obj2) {
						chkTodos.setSelected(false);
						tbPatrimonio.getItems().clear();
						Ambiente a = cbAmbientes.getItems().get(
								(int) observable.getValue());
						if (patDao.buscarPatrimonio_Ambiente(a) != null) {
							genericosC = FXCollections
									.observableArrayList(converterPatrimonio(patDao
											.buscarPatrimonio_Ambiente(a)));
							tbPatrimonio.setItems(genericosC);
						}

					}
				});
		// PDFQRCODE
		btPrintQrCode.setOnAction(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				if (genericosC != null) {
					String nome = JOptionPane
							.showInputDialog("Digite o nome que deseja salvar o arquivo.");
					if (nome != null) {
						JFileChooser fc = new JFileChooser();

						// restringe a amostra a diretorios apenas
						fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

						int res = fc.showOpenDialog(null);
						File diretorio = null;
						if (res == JFileChooser.APPROVE_OPTION) {
							diretorio = fc.getSelectedFile();
							ArrayList<String> cdPatrimonio = new ArrayList<String>();

							for (int i = 0; i < genericosC.size(); i++) {

								cdPatrimonio.add(genericosC.get(i)
										.getCdPatrimonio());
							}

							try {
								gerarQrReport(genericosC,
										diretorio.getAbsolutePath(), nome);

							} catch (JRException e) {
								JOptionPane.showMessageDialog(null,
										e.getMessage(), "Erro",
										JOptionPane.ERROR_MESSAGE);
								;
							}
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "A lista esta vazia",
							"Erro", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		// LOGOUT
		btLogout.setOnAction(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				TelaInicialView.getStage().close();
				EscolherRelatorioView.getStage().show();

			}
		});

		btPrintReport.setOnAction(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				if (genericosC != null) {
					String nome = JOptionPane
							.showInputDialog("Digite o nome que deseja salvar o arquivo.");
					if (!nome.isEmpty()) {
						JFileChooser fc = new JFileChooser();

						// restringe a amostra a diretorios apenas
						fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

						int res = fc.showOpenDialog(null);
						File diretorio = null;
						if (res == JFileChooser.APPROVE_OPTION) {
							diretorio = fc.getSelectedFile();
							ArrayList<PatrimonioUtil> p = new ArrayList<PatrimonioUtil>();

							if (genericosC.size() != 0) {
								for (int i = 0; i < genericosC.size(); i++) {

									p.add(genericosC.get(i));
								}
								try {
									gerarRelatório(p,
											diretorio.getAbsolutePath(), nome);
								} catch (JRException e) {

									JOptionPane.showMessageDialog(null,
											e.getMessage(), "Erro",
											JOptionPane.ERROR_MESSAGE);
								}

							}

						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "A lista esta vazia",
							"Erro", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
	}

	private void gerarRelatório(List<PatrimonioUtil> lista, String caminho,
			String nomeDoArquivo) throws JRException {

		// compilacao do JRXML
		JasperReport report = JasperCompileManager.compileReport(getClass()
				.getResourceAsStream("/relatorio/patrimonios.jrxml")); //
		// preenchimento do relatorio, note que o metodo recebe 3 parametros: //
		// 1 - o relatorio // // 2 - um Map, com parametros que sao passados ao
		// relatorio // no momento do preenchimento. No nosso caso eh null, pois
		// nao estamos usando nenhum parametro 3 - o data source. Note
		// que nao devemos passar a lista diretamente, // e sim "transformar" em
		// um data source utilizando a classe // JRBeanCollectionDataSource
		JasperPrint print = JasperFillManager.fillReport(report, null,
				new JRBeanCollectionDataSource(lista));

		// exportacao do relatorio para outro formato, no caso PDF
		JasperExportManager.exportReportToPdfFile(print, caminho + "/"
				+ nomeDoArquivo + ".pdf");
		JOptionPane.showMessageDialog(null,
				"Relatório gerado e salvo na pasta especificada.",
				"Finalizado!", JOptionPane.INFORMATION_MESSAGE);

	}

	private void gerarQrReport(List<PatrimonioUtil> lista, String caminho,
			String nomeDoArquivo) throws JRException {

		// compilacao do JRXML
		JasperReport report = JasperCompileManager.compileReport(getClass()
				.getResourceAsStream("/relatorio/QRCode.jrxml")); //
		// preenchimento do relatorio, note que o metodo recebe 3 parametros: //
		// 1 - o relatorio // // 2 - um Map, com parametros que sao passados ao
		// relatorio // no momento do preenchimento. No nosso caso eh null, pois
		// nao estamos usando nenhum parametro 3 - o data source. Note
		// que nao devemos passar a lista diretamente, // e sim "transformar" em
		// um data source utilizando a classe // JRBeanCollectionDataSource
		JasperPrint print = JasperFillManager.fillReport(report, null,
				new JRBeanCollectionDataSource(lista));

		// exportacao do relatorio para outro formato, no caso PDF
		JasperExportManager.exportReportToPdfFile(print, caminho + "/"
				+ nomeDoArquivo + ".pdf");
		JOptionPane.showMessageDialog(null,
				"Relatório gerado e salvo na pasta especificada.",
				"Finalizado!", JOptionPane.INFORMATION_MESSAGE);

	}

	private List<PatrimonioUtil> converterPatrimonio(List<Patrimonio> pat) {
		List<PatrimonioUtil> patUtil = new ArrayList<PatrimonioUtil>();

		for (int i = 0; i < pat.size(); i++) {
			PatrimonioUtil util = new PatrimonioUtil();
			util.setCdPatrimonio(pat.get(i).getCdPatrimonio());
			util.setAmbiente(pat.get(i).getAmbiente().getNome());
			util.setModelo(pat.get(i).getModelo().getNome());
			util.setTipo(pat.get(i).getModelo().getTipo().getDescricao());
			patUtil.add(util);
		}

		return patUtil;
	}

}
