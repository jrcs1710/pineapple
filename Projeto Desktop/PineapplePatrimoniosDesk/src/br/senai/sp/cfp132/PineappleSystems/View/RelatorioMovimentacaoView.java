package br.senai.sp.cfp132.PineappleSystems.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RelatorioMovimentacaoView extends Application {

	private static Stage stage;

	@Override
	public void start(Stage stage) throws Exception {
		Parent parent = FXMLLoader.load(getClass().getResource(
				"relatorioMovimentacao.fxml"));

		Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.setTitle("Relatório Movimentação");
		stage.setResizable(false);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/imagem/pineapple_icon.png")));
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
		this.stage = stage;

	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getStage() {
		return stage;
	}
}
