package fr.irit.smac.amasrenderer.controller.tool;

import fr.irit.smac.amasrenderer.controller.ISecondaryWindowController;
import fr.irit.smac.amasrenderer.controller.IParentWindowModal;
import fr.irit.smac.amasrenderer.model.IModel;
import fr.irit.smac.amasrenderer.model.ToolModel;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ToolDeletionController implements ISecondaryWindowController {

    @FXML
    private Button buttonConfirmRemove;

    @FXML
    private Button buttonCancelRemove;

    private String toolName;

    private ToolModel tool;

    private Stage stage;

    private IParentWindowModal parentWindowModal;

    private ToolService toolService = ToolService.getInstance();

    @FXML
    public void clickConfirmRemove() {

        toolService.getTools().remove(tool);
        InfrastructureService.getInstance().getInfrastructure().getAttributesMap().remove(this.tool.getName());
        this.parentWindowModal.closeWindow();
    }

    @FXML
    public void clickCancelRemove() {
        this.stage.close();
    }

    public void setParentWindowModal(IParentWindowModal parentWindowModal) {
        this.parentWindowModal = parentWindowModal;
    }

    @Override
    public void init(Stage stageSecondaryWindow, Object... args) {
        
        this.stage = stageSecondaryWindow;
        this.parentWindowModal = (IParentWindowModal) args[0];
        this.tool = (ToolModel) args[1];
    }
    
   
}
