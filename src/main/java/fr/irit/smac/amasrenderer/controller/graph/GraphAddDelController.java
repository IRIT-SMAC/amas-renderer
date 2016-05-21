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

    /**
     * Sets the implementation of graphButtonsState
     * 
     * @param graphButtonsState
     *            the implementation
     */
    public void setGraphButtonsState(IGraphButtonsState graphButtonsState) {
        this.graphButtonsState = graphButtonsState;
    }

    /**
     * Sets the new state of the buttons when the user selects the button
     * AddNode
     */
    @FXML
    public void buttonAddNode() {
        if (state == EStateGraph.BUTTON_ADD_NODE) {
            graphButtonsState.changedStateButtons(EStateGraph.AT_EASE);
        }
        else {
            graphButtonsState.changedStateButtons(EStateGraph.BUTTON_ADD_NODE);
        }
    }

    /**
     * Sets the new state of the buttons when the user selects the button
     * DeleteNode
     */
    @FXML
    public void buttonDeleteNode() {

        if (state == EStateGraph.BUTTON_DELETE_NODE) {
            graphButtonsState.changedStateButtons(EStateGraph.AT_EASE);
        }
        else {
            graphButtonsState.changedStateButtons(EStateGraph.BUTTON_DELETE_NODE);
        }

    }

    /**
     * Sets the new state of the buttons when the user selects the button
     * AddEdge
     */
    @FXML
    public void buttonAddEdge() {

        if (state == EStateGraph.BUTTON_ADD_EDGE) {
            graphButtonsState.changedStateButtons(EStateGraph.AT_EASE);
        }
        else {
            graphButtonsState.changedStateButtons(EStateGraph.BUTTON_ADD_EDGE);
        }
    }

    /**
     * Sets the new state of the buttons when the user selects the button
     * DeleteEdge
     */
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

    /**
     * This interface allows to the GraphMainController to get the new state of
     * the graphButtons
     */
    @FunctionalInterface
    public interface IGraphButtonsState {

        /**
         * This method is called when the state of the graphButtons is updated
         * 
         * @param state
         *            the new state of the graphButtons
         */
        public void changedStateButtons(EStateGraph state);
    }
}
