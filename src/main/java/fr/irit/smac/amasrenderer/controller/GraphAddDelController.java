package fr.irit.smac.amasrenderer.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.graphstream.ui.swingViewer.ViewPanel;

import fr.irit.smac.amasrenderer.service.GraphService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

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

    public void init(ViewPanel graphView, GraphService graphNodeService) {

        graphAddDelEdgeMouseController = new GraphAddDelEdgeMouseController();
        graphAddDelEdgeMouseController.init(graphView, graphNodeService);
        graphAddDelEdgeMouseController.setButtonAddEdge(this.buttonAddEdge);
        graphAddDelEdgeMouseController.setButtonDelEdge(this.buttonDelEdge);
        graphAddDelNodeMouseController = new GraphAddDelNodeMouseController();
        graphAddDelNodeMouseController.setButtonAddAgent(this.buttonAddAgent);
        graphAddDelNodeMouseController.setButtonDelAgent(this.buttonDelAgent);
        graphAddDelNodeMouseController.init(graphView, graphNodeService);
    }
}
