package fr.irit.smac.amasrenderer.controller.tool;

import fr.irit.smac.amasrenderer.controller.LoadWindowModalController.IParentStyle;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ToolDeletionController {

    @FXML
    private Button buttonConfirmRemove;

    @FXML
    private Button buttonCancelRemove;

    private Stage dialogStage;

    private String toolName;

    private int toolIndex;

    private Stage stage;
    
    private IParentWindowModal parentWindowModal;

    @FXML
    public void clickConfirmRemove() {

        ToolService.getInstance().getTools().remove(toolIndex);
        InfrastructureService.getInstance().getInfrastructure().getAttributesMap().remove(toolName);
        this.parentWindowModal.closeWindow();
    }

    @FXML
    public void clickCancelRemove() {
        this.stage.close();
    }

    public void setParentWindowModal(IParentWindowModal parentWindowModal) {
        this.parentWindowModal = parentWindowModal;
    }
    
    public void init(Stage stageWindowModal, int toolIndex, String toolName) {

        this.toolName = toolName;
        this.toolIndex = toolIndex;
        this.stage = stageWindowModal;
    }
    
    public interface IParentWindowModal {
        
        public void closeWindow();
    }
}
