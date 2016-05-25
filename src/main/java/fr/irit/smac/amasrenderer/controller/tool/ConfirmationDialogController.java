package fr.irit.smac.amasrenderer.controller.tool;

import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ConfirmationDialogController {

    @FXML
    private Button buttonConfirmRemove;

    @FXML
    private Button buttonCancelRemove;

    private ListView<String> list;

    private Stage dialogStage;

    private String key;

    @FXML
    public void clickConfirmRemove() {
        
        list.getItems().remove(key);
        ToolService.getInstance().getServicesMap().remove(key);
        dialogStage.getScene().lookup("#attributesServiceDialog").getStyleClass().remove("secondaryWindow");
        Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().remove("secondaryWindow");
        dialogStage.close();
    }

    @FXML
    public void clickCancelRemove() {

        dialogStage.getScene().lookup("#attributesServiceDialog").getStyleClass().remove("secondaryWindow");
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

}
