package fr.irit.smac.amasrenderer.controller.tool;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The Class ServiceDialogController This controller manages the popup form
 */
public class ToolDialogController implements Initializable {

    /** The confirm button */
    @FXML
    private Button buttonConfirm;

    /** The cancel button */
    @FXML
    private Button buttonCancel;

    /** The new service textfield */
    @FXML
    private TextField textfieldTool;
    
    private HashMap<String,TreeItem<String>> map;

    @FXML
    private Text invalidField;

	
    /**
     * Click on the confirm button handler
     */
	@FXML
	public void clickConfirm(){
	    
		if(textfieldTool.getText() != null 
				&& !textfieldTool.getText().trim().isEmpty()
				&& !textfieldTool.getText().trim().contains(" ")){

            String newTool = textfieldTool.getText().trim();
//            newTool.setFont(new Font("OpenSymbol", Const.FONT_SIZE));
            
            boolean found = false;

            for (String item : ToolService.getInstance().getTools()) {
                if (item.equals(newTool)) {
                    found = true;
                }
            }

            if (!found) {
              HashMap<String,Object> map = new HashMap<String,Object>();
              ToolService.getInstance().getServicesMap().put(newTool, map );
              ToolService.getInstance().getTools().add(newTool);
            }

            Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().remove("secondaryWindow");

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
