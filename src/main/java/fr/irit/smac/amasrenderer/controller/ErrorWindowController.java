package fr.irit.smac.amasrenderer.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ErrorWindowController implements ISecondaryWindowController{

    @FXML
    Label titleError;
    
    @FXML
    Text infoError;
    
    @FXML
    Button confButton;

    private Stage stage;
    
    @FXML
    public void clickClose() {
        
        this.stage.close();
    }
    
    @Override
    public void init(Stage stage, Object... args) {
    
        this.stage = stage;
        this.titleError.setText((String) args[0]);
        this.infoError.setText((String) args[1]);
    }

}
