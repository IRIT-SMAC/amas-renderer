package fr.irit.smac.amasrenderer.controller.graph;

import fr.irit.smac.amasrenderer.controller.ISecondaryWindowController;
import fr.irit.smac.amasrenderer.service.GraphService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * This controller is related to the deletion of a tool
 */
public class GraphResetController implements ISecondaryWindowController {

    @FXML
    private Button buttonConfirm;

    @FXML
    private Button buttonCancel;

    private Stage stage;

    private GraphService graphService = GraphService.getInstance();

    /**
     * When the confirm button is clicked, the graph is reseted
     */
    @FXML
    public void clickConfirm() {

        graphService.clearGraph();
        this.stage.close();
    }

    /**
     * When the cancel button is clicked, the graph is not reseted
     */
    @FXML
    public void clickCancel() {
        this.stage.close();
    }

    @Override
    public void init(Stage stageSecondaryWindow, Object... args) {

        this.stage = stageSecondaryWindow;
    }

}
