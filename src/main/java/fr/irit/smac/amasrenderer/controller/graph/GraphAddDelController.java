package fr.irit.smac.amasrenderer.controller.graph;

import org.graphstream.ui.swingViewer.ViewPanel;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

/**
 * The Class GraphAddDelController. This controller handles the buttons of the
 * graph
 */
public class GraphAddDelController {

    @FXML
    ToggleGroup toggroup;

    @FXML
    private ToggleButton buttonAddAgent;

    @FXML
    private ToggleButton buttonDelAgent;

    @FXML
    private ToggleButton buttonAddEdge;

    @FXML
    private ToggleButton buttonDelEdge;

    private GraphAddDelEdgeMouseController graphAddDelEdgeMouseController;

    private GraphAddDelNodeMouseController graphAddDelNodeMouseController;

    /**
     * Inits the subcontrollers GraphAddDelEdgeMouseController and
     * GraphAddDelMouseController.
     *
     * @param graphView
     *            the graph view
     * @param graphNodeService
     *            the graph node service
     */
    public void init(ViewPanel graphView) {

        graphAddDelEdgeMouseController = new GraphAddDelEdgeMouseController();
        graphAddDelEdgeMouseController.init(graphView);
        graphAddDelEdgeMouseController.setButtonAddEdge(this.buttonAddEdge);
        graphAddDelEdgeMouseController.setButtonDelEdge(this.buttonDelEdge);
        graphAddDelNodeMouseController = new GraphAddDelNodeMouseController();
        graphAddDelNodeMouseController.setButtonAddAgent(this.buttonAddAgent);
        graphAddDelNodeMouseController.setButtonDelAgent(this.buttonDelAgent);
        graphAddDelNodeMouseController.init(graphView);

    }

    public ToggleGroup getTogGroup() {
        return toggroup;
    }
}
