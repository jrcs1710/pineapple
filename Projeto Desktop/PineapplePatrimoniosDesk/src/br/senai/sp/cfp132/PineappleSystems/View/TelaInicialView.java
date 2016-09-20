package br.senai.sp.cfp132.PineappleSystems.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TelaInicialView extends Application {

	private static Stage stage;

	public static Stage getStage() {
		return stage;
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent parent = FXMLLoader.load(getClass().getResource(
				"Main_Screen.fxml"));
		Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.setTitle("Tela Principal");
		stage.setResizable(false);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/imagem/pineapple_icon.png")));
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();

		this.stage = stage;
	}

	public static void main(String[] args) {
		launch(args);
	}

}