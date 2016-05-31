package fr.irit.smac.amasrenderer.controller.tool;

import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ConfirmationDialogController {

    @FXML
    private Button buttonConfirmRemove;

    @FXML
    private Button buttonCancelRemove;

    private Stage dialogStage;

    
    private int toolIndex;

    @FXML
    public void clickConfirmRemove() {

        ToolService.getInstance().getTools().remove(toolIndex);
        dialogStage.getScene().lookup("#attributesServiceDialog").getStyleClass().remove("secondaryWindow");
        Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().remove("secondaryWindow");
        dialogStage.close();
    }

    @FXML
    public void clickCancelRemove() {

        dialogStage.getScene().lookup("#attributesServiceDialog").getStyleClass().remove("secondaryWindow");
        ((Stage) buttonCancelRemove.getScene().getWindow()).close();
    }

    public void init(Stage dialogStage, int toolIndex) {
        
        this.toolIndex = toolIndex;
        this.dialogStage = dialogStage;
    }
}
