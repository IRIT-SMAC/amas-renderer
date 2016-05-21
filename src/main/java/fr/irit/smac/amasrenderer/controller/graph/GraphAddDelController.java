package fr.irit.smac.amasrenderer.controller.graph;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

/**
 * The Class GraphAddDelController. This controller handles the buttons of the
 * graph
 */
public class GraphAddDelController implements Initializable {

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

    private EStateGraph state;

    private IGraphButtonsState graphButtonsState;

    @FXML
    public void buttonAddNode() {
        if (state == EStateGraph.BUTTON_ADD_NODE) {
            graphButtonsState.changedStateButtons(EStateGraph.AT_EASE);
        }
        else {
            graphButtonsState.changedStateButtons(EStateGraph.BUTTON_ADD_NODE);
        }
    }

    public void init(IGraphButtonsState interaction) {
        this.graphButtonsState = interaction;
    }

    @FXML
    public void buttonDeleteNode() {

        if (state == EStateGraph.BUTTON_DELETE_NODE) {
            graphButtonsState.changedStateButtons(EStateGraph.AT_EASE);
        }
        else {
            graphButtonsState.changedStateButtons(EStateGraph.BUTTON_DELETE_NODE);
        }

    }

    @FXML
    public void buttonAddEdge() {

        if (state == EStateGraph.BUTTON_ADD_EDGE) {
            graphButtonsState.changedStateButtons(EStateGraph.AT_EASE);
        }
        else {
            graphButtonsState.changedStateButtons(EStateGraph.BUTTON_ADD_EDGE);
        }
    }

    @FXML
    public void buttonDeleteEdge() {

        if (state == EStateGraph.BUTTON_DELETE_EDGE) {
            graphButtonsState.changedStateButtons(EStateGraph.AT_EASE);
        }
        else {
            graphButtonsState.changedStateButtons(EStateGraph.BUTTON_DELETE_EDGE);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        state = EStateGraph.AT_EASE;
    }

    public interface IGraphButtonsState {

        public void changedStateButtons(EStateGraph state);
    }
}
