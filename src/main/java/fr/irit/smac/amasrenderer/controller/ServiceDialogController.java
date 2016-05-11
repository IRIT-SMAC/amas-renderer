package fr.irit.smac.amasrenderer.controller;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.service.ServiceService;

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

/**
 * The Class ServiceDialogController This controller manages the popup form
 */
public class ServiceDialogController implements Initializable {

    /** The confirm button */
    @FXML
    private Button buttonConfirm;

    /** The cancel button */
    @FXML
    private Button buttonCancel;

    /** The new service textfield */
    @FXML
    private TextField textfieldService;

    /**
     * @param list
     *            The instance of the list in which we add the new service
     */
    public ServiceDialogController() {
    	
    }

	@FXML
	private Text invalidField;
	
    /**
     * Click on the confirm button handler
     */
	@FXML
	public void clickConfirm(){
	    
		if(textfieldService.getText() != null 
				&& !textfieldService.getText().trim().isEmpty()
				&& !textfieldService.getText().trim().contains(" ")){

            Label nouveauService = new Label(textfieldService.getText().trim());
            nouveauService.setFont(new Font("OpenSymbol", Const.FONT_SIZE));
            
            boolean found = false;

            for (String item : ServiceService.getInstance().getListServices()) {
                if (item.equals(nouveauService.getText())){
                    found = true;
                }
            }

            if(!found){
                ServiceService.getInstance().getListServices().add(nouveauService.getText());
            }

            ((Stage) buttonConfirm.getScene().getWindow()).close();
        } else {
		    invalidField.setVisible(true);

        }
    }

    /**
     * Click on the cancel button handler
     */
    @FXML
    public void clickCancel() {
        ((Stage) buttonCancel.getScene().getWindow()).close();
	}


    @Override
    public void initialize(URL location, ResourceBundle resources) {  
        invalidField.setVisible(false);
    }
}
