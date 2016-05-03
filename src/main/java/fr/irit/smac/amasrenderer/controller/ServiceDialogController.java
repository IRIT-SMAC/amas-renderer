package fr.irit.smac.amasrenderer.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ServiceDialogController {

	private ListView<Label> serviceList;

	@FXML
	private Button buttonConfirm;

	@FXML
	private Button buttonCancel;

	@FXML
	private TextField textfieldService;

	public ServiceDialogController(ListView<Label> list){
		this.serviceList = list;
	}


	@FXML
	public void clickConfirm(){
		if(textfieldService.getText() != null 
				&& !textfieldService.getText().trim().isEmpty()
				&& !textfieldService.getText().trim().contains(" ")){

			Label nouveauService = new Label(textfieldService.getText().trim());
			nouveauService.setFont(new Font("OpenSymbol", 18));

			boolean found = false;

			for(Label item : serviceList.getItems()){
				if(item.getText().equals(nouveauService.getText()))
					found = true;
			}

			if(!found)
				this.serviceList.getItems().add(nouveauService);

			((Stage) buttonConfirm.getScene().getWindow()).close();
		}
		else{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setHeaderText(null);
			alert.setContentText("Champs vide ou nom invalide");
			alert.showAndWait();
		}
	}

	@FXML 
	public void clickCancel(){
		((Stage) buttonCancel.getScene().getWindow()).close();
	}
}
