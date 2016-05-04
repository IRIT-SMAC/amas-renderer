package fr.irit.smac.amasrenderer.controller;

import java.io.IOException;

import fr.irit.smac.amasrenderer.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The Class ServicesController.
 * it exists but nobody knows why, kind of like patrick sebastien
 */
public class ServicesController {

	@FXML
	private Button buttonAddService;

	@FXML
	private Button buttonCancel;

	@FXML
	private Button buttonConfirm;
	
	@FXML
	private ListView<Label> listServices;

	private Stage stage;
	

	@FXML
	public void addService() throws IOException {
		
		stage = new Stage();
		stage.setTitle("Ajouter un service");
		stage.setResizable(false);

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/ServiceDialog.fxml"));
		loader.setController(new ServiceDialogController(listServices));
		Pane root = (Pane) loader.load();

		stage.initModality(Modality.WINDOW_MODAL);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.initOwner(buttonAddService.getScene().getWindow());
		Scene myScene = new Scene(root);
		stage.setScene(myScene);
		stage.showAndWait();

	}

}
