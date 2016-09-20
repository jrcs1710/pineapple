package br.senai.sp.cfp132.PineappleSystems.Controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import br.senai.sp.cfp132.PineappleSystems.Dao.MovimentacaoDao;
import br.senai.sp.cfp132.PineappleSystems.Util.CustomDate;
import br.senai.sp.cfp132.PineappleSystems.Util.MovimentacaoGenerico;
import br.senai.sp.cfp132.PineappleSystems.Util.MovimentacaoUtil;
import br.senai.sp.cfp132.PineappleSystems.Util.VerificarUsuario;
import br.senai.sp.cfp132.PineappleSystems.View.EscolherRelatorioView;
import br.senai.sp.cfp132.PineappleSystems.View.RelatorioMovimentacaoView;
import br.senai.sp.cfp132.PineappleSystems.model.Ambiente;
import br.senai.sp.cfp132.PineappleSystems.model.Cargo;
import br.senai.sp.cfp132.PineappleSystems.model.Movimentacao;

import br.senai.sp.cfp132.PineappleSystems.model.StatusRequisicao;
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
import javafx.scene.control.cell.PropertyValueFactory;

public class RelatorioMovimentacaoController implements Initializable {

	@FXML
	private ChoiceBox<Ambiente> cbAmbientes;
	@FXML
	private Button btLogout;
	@FXML
	private Button btPrintReport;
	@FXML
	private Button btPrintQrCode;
	@FXML
	private Button btSettings;
	@FXML
	private TableView<MovimentacaoUtil> tbMovimentacao;
	@FXML
	private TableColumn<MovimentacaoGenerico, String> colNrPatrimonio;
	@FXML
	private TableColumn<MovimentacaoGenerico, String> colSolicitante;
	@FXML
	private TableColumn<MovimentacaoGenerico, CustomDate> colSolicitacao;
	@FXML
	private TableColumn<MovimentacaoGenerico, String> colAvaliador;
	@FXML
	private TableColumn<MovimentacaoGenerico, CustomDate> colAvaliacao;
	@FXML
	private TableColumn<MovimentacaoGenerico, String> colLocal;
	@FXML
	private TableColumn<MovimentacaoGenerico, String> colDestino;
	@FXML
	private TableColumn<MovimentacaoGenerico, String> colStatus;
	@FXML
	private CheckBox chkTodos;

	// LISTAS
	private List<Movimentacao> listMov;
	private List<MovimentacaoUtil> listMovUtil;
	private List<MovimentacaoUtil> listMovUtilAmbiente;
	private ObservableList<MovimentacaoUtil> movObservable;

	// DAO
	private MovimentacaoDao movDao = new MovimentacaoDao();
	private AmbienteDao ambDao = new AmbienteDao();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL url, ResourceBundle bundle) {

		//CHECKBOX
		chkTodos.setSelected(true);
		if (VerificarUsuario.getFuncionarioLogado().getCargo().equals(Cargo.RESP)) {
			chkTodos.setVisible(false);
		}else {
			chkTodos.setSelected(false);
		}
		chkTodos.selectedProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue obs, Object obj1, Object obj2) {
				if (chkTodos.isSelected()) {
					movObservable = FXCollections.observableArrayList(converterMovimentacao(movDao.buscarTodos()));
					tbMovimentacao.setItems(movObservable);
				}
				
			}
		});
		
		
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

		// EVENTO CHANGE CHOICEBOX
		cbAmbientes.getSelectionModel().selectedIndexProperty()
				.addListener(new ChangeListener() {

					@Override
					public void changed(ObservableValue observable,
							Object obj1, Object obj2) {
						chkTodos.setSelected(false);
						tbMovimentacao.getItems().clear();
						Ambiente a = cbAmbientes.getItems().get(
								(int) observable.getValue());
						if (!movDao.buscarMovimentacao_StatusAmbiente(
								StatusRequisicao.ABERTO, a, a).isEmpty()) {
							listMovUtilAmbiente = converterMovimentacao(movDao
									.buscarMovimentacao_StatusAmbiente(
											StatusRequisicao.ABERTO, a, a));
						}

						movObservable = FXCollections
								.observableArrayList(listMovUtilAmbiente);
						tbMovimentacao.setItems(movObservable);

					}
				});

		// LOGOUT
		btLogout.setOnAction(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				RelatorioMovimentacaoView.getStage().close();
				EscolherRelatorioView.getStage().show();

			}
		});

		// INICIANDO TABELA
		colNrPatrimonio
				.setCellValueFactory(new PropertyValueFactory<MovimentacaoGenerico, String>(
						"cdPatrimonio"));
		colSolicitante
				.setCellValueFactory(new PropertyValueFactory<MovimentacaoGenerico, String>(
						"solicitante"));
		colSolicitacao
				.setCellValueFactory(new PropertyValueFactory<MovimentacaoGenerico, CustomDate>(
						"dtSolicitacao"));
		colAvaliador
				.setCellValueFactory(new PropertyValueFactory<MovimentacaoGenerico, String>(
						"avaliador"));
		colAvaliacao
				.setCellValueFactory(new PropertyValueFactory<MovimentacaoGenerico, CustomDate>(
						"dataAprovacao"));
		colLocal.setCellValueFactory(new PropertyValueFactory<MovimentacaoGenerico, String>(
				"atual"));
		colDestino
				.setCellValueFactory(new PropertyValueFactory<MovimentacaoGenerico, String>(
						"destino"));
		colStatus
				.setCellValueFactory(new PropertyValueFactory<MovimentacaoGenerico, String>(
						"status"));

		// CRIANDO CLASSE TEMPORARIA
		if (VerificarUsuario.getFuncionarioLogado().getCargo()
				.equals(Cargo.GER)) {
			listMov = movDao
					.buscarMovimentacao_StatusNegativo(StatusRequisicao.ABERTO);
			listMovUtil = converterMovimentacao(listMov);
			// PREENCHENDO A TABELA
			movObservable = FXCollections.observableArrayList(listMovUtil);
			tbMovimentacao.setItems(movObservable);
		}

		// BOTÃO GERAR RELATORIO
		btPrintReport.setOnAction(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				if (movObservable != null) {
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

							try {
								gerarRelatório(listMovUtil,
										diretorio.getAbsolutePath(), nome);
							} catch (JRException e) {

								JOptionPane.showMessageDialog(null,
										e.getMessage(), "Erro",
										JOptionPane.ERROR_MESSAGE);
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

	private List<MovimentacaoUtil> converterMovimentacao(
			List<Movimentacao> listMov) {
		List<MovimentacaoUtil> listMovUtil = new ArrayList<MovimentacaoUtil>();

		for (int i = 0; i < listMov.size(); i++) {
			if (listMov.get(i).getAvaliador() != null && listMov.get(i).getDataAprovacao() != null) {

				for (int j = 0; j < listMov.get(i).getPatrimonios().size(); j++) {
					MovimentacaoUtil util = new MovimentacaoUtil();
					util.setCdPatrimonio(listMov.get(i).getPatrimonios().get(j)
							.getPatrimonio().getCdPatrimonio());
					if (listMov.get(i).getAtual() != null) {
						util.setAtual(listMov.get(i).getAtual().getNome());
					} else {
						util.setAtual("Entrada");
					}
					if (listMov.get(i).getAvaliador() != null) {
						util.setAvaliador(listMov.get(i).getAvaliador()
								.getNome());
					}
					if (listMov.get(i).getDataAprovacao() != null) {
						util.setDataAprovacao(listMov.get(i).getDataAprovacao()
								.getTime());
					}

					if (listMov.get(i).getDestino() != null) {
						util.setDestino(listMov.get(i).getDestino().getNome());
					} else {
						util.setDestino("Saída");
					}

					util.setDtSolicitacao(listMov.get(i).getDtSolicitacao()
							.getTime());
					util.setSolicitante(listMov.get(i).getSolicitante()
							.getNome());
					util.setStatus(listMov.get(i).getStatus().toString());
					listMovUtil.add(util);
				}
			}
		}
		return listMovUtil;
	}

	private void gerarRelatório(List<MovimentacaoUtil> lista, String caminho,
			String nomeDoArquivo) throws JRException {

		// compilacao do JRXML
		JasperReport report = JasperCompileManager.compileReport(getClass()
				.getResourceAsStream("/relatorio/Movimentacao.jrxml")); //
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

}
