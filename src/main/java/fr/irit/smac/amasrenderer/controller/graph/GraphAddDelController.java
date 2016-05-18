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

    @FXML
    public void buttonAddNode() {

        switch (GraphMainController.state) {
            case BUTTON_ADD_NODE:
                GraphMainController.state = EStateGraph.AT_EASE;
                break;
            default:
                if (GraphMainController.state != null) {
                    GraphMainController.state = EStateGraph.BUTTON_ADD_NODE;
                }
                break;
        }
    }

    @FXML
    public void buttonDeleteNode() {

        switch (GraphMainController.state) {
            case BUTTON_DELETE_NODE:
                GraphMainController.state = EStateGraph.AT_EASE;
                break;
            default:
                if (GraphMainController.state != null) {
                    GraphMainController.state = EStateGraph.BUTTON_DELETE_NODE;
                }
                break;
        }
    }

    @FXML
    public void buttonAddEdge() {

        switch (GraphMainController.state) {
            case BUTTON_ADD_EDGE:
                GraphMainController.state = EStateGraph.AT_EASE;
                break;
            default:
                if (GraphMainController.state != null) {
                    GraphMainController.state = EStateGraph.BUTTON_ADD_EDGE;
                }
                break;
        }
    }

    @FXML
    public void buttonDeleteEdge() {

        switch (GraphMainController.state) {
            case BUTTON_DELETE_EDGE:
                GraphMainController.state = EStateGraph.AT_EASE;
                break;
            default:
                if (GraphMainController.state != null) {
                    GraphMainController.state = EStateGraph.BUTTON_DELETE_EDGE;
                }
                break;
        }
    }

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
        graphAddDelNodeMouseController = new GraphAddDelNodeMouseController();
        graphAddDelNodeMouseController.init(graphView);

    }

    public ToggleGroup getTogGroup() {
        return toggroup;
    }
}
