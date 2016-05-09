package fr.irit.smac.amasrenderer.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ServiceDialogController implements Initializable {

	private ListView<Label> serviceList;

	@FXML
	private Button buttonConfirm;

	@FXML
	private Button buttonCancel;

	@FXML
	private TextField textfieldService;

	@FXML
	private Text invalidField;

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

		    invalidField.setVisible(true);
	        
		}
	}

	@FXML 
	public void clickCancel(){
		((Stage) buttonCancel.getScene().getWindow()).close();
	}

    public void setList(ListView<Label> list) {
        this.serviceList = list;
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        invalidField.setVisible(false);
    }
}
