package fr.irit.smac.amasrenderer.controller.tool;

import fr.irit.smac.amasrenderer.controller.IParentWindowModal;
import fr.irit.smac.amasrenderer.controller.ISecondaryWindowController;
import fr.irit.smac.amasrenderer.model.ToolModel;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * This controller is related to the deletion of a tool
 */
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

    /**
     * When the confirm button is clicked, the selected tool is deleted
     */
    @FXML
    public void clickConfirmRemove() {

        this.toolService.getTools().remove(this.tool);
        InfrastructureService.getInstance().getInfrastructure().getAttributesMap().remove(this.toolName);
        this.parentWindowModal.closeWindow();
    }

    /**
     * When the cancel button is clicked, no tool is removed and the window is
     * closed
     */
    @FXML
    public void clickCancelRemove() {
        this.stage.close();
    }

    /**
     * Sets the owner of the window. This attribute is useful when this window
     * is closed because the owner window has to be closed at the same time.
     * 
     * @param parentWindowModal
     */
    public void setParentWindowModal(IParentWindowModal parentWindowModal) {
        this.parentWindowModal = parentWindowModal;
    }

    @Override
    public void init(Stage stageSecondaryWindow, Object... args) {

        this.stage = stageSecondaryWindow;
        this.parentWindowModal = (IParentWindowModal) args[0];
        this.tool = (ToolModel) args[1];
        this.toolName = this.tool.getName();
    }

}
