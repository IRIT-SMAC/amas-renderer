package fr.irit.smac.amasrenderer.controller;

import java.io.IOException;

import fr.irit.smac.amasrenderer.Main;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

// TODO: Do something with this class
/**
 * The Class ServicesController.
 * it exists but nobody knows why, kind of like patrick sebastien
 */
public class ServicesController {


	@FXML
	private ListView<String> listService;

	@FXML
	private Button buttonAddService;

	@FXML
	private Button buttonCancel;

	@FXML
	private Button buttonConfirm;

	@FXML
	private TextField textfieldService;

	private Stage stage;

	@FXML
	public void addService() throws IOException {
		stage = new Stage();
		stage.setTitle("Ajouter un service");
		stage.setResizable(false);

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/ServiceDialog.fxml"));
		VBox root = (VBox) loader.load();

		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(new Scene(root));
		stage.showAndWait();

	}

	@FXML
	public void clickConfirm(){
		if(textfieldService.getText() != null || textfieldService.getText() != ""){
			ObservableList<String> items = listService.getItems();
			items.add(textfieldService.getText());
			listService.setItems(items);
		}
	}

	@FXML
	public void clickCancel(){
		Stage stage = (Stage) buttonCancel.getScene().getWindow();
		stage.close();
	}
}
