package fr.irit.smac.amasrenderer.controller.tool;

import java.net.URL;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConfirmationDialogController implements Initializable{
    
    @FXML
    private Button buttonConfirmRemove;
    
    @FXML
    private Button buttonCancelRemove;
    
    private ListView<String> list;
    
    private Stage dialogStage;
    
    private String key;
    
    @FXML
    public void clickConfirmRemove(){
        list.getItems().remove(key);
        ToolService.getInstance().getAttributes().remove(key);
        Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().remove("secondaryWindow");
        dialogStage.close();
        ((Stage) buttonConfirmRemove.getScene().getWindow()).close();
    }
    
    @FXML
    public void clickCancelRemove(){
        ((Stage) buttonCancelRemove.getScene().getWindow()).close();
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public void setList(ListView<String> list) {
        this.list = list;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        
    }
}
