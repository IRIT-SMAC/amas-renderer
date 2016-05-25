package fr.irit.smac.amasrenderer.controller.graph;

import java.net.URL;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.EStateGraph;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

/**
 * The Class GraphAddDelController. This controller handles the buttons of the
 * graph
 */
public class GraphToolboxController implements Initializable {

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

    @FXML
    private Button buttonResetView;

    @FXML
    private ToggleButton buttonAutoLayout;

    private EStateGraph state;

    private EStateGraph autoLayoutState;

    private IGraphButtonsState graphToolboxState;

    /**
     * Sets the implementation of graphButtonsState
     * 
     * @param graphButtonsState
     *            the implementation
     */
    public void setGraphButtonsState(IGraphButtonsState graphButtonsState) {
        this.graphToolboxState = graphButtonsState;
    }

    /**
     * Sets the new state of the buttons when the user selects the button
     * AddNode
     */
    @FXML
    public void buttonAddNode() {

        if (this.state == EStateGraph.BUTTON_ADD_NODE) {
            this.state = EStateGraph.AT_EASE;
            graphToolboxState.changedStateButtonsAddDel(EStateGraph.AT_EASE);
        }
        else {
            this.state = EStateGraph.BUTTON_ADD_NODE;
            graphToolboxState.changedStateButtonsAddDel(EStateGraph.BUTTON_ADD_NODE);
        }
    }

    /**
     * Sets the new state of the buttons when the user selects the button
     * DeleteNode
     */
    @FXML
    public void buttonDeleteNode() {

        if (this.state == EStateGraph.BUTTON_DELETE_NODE) {
            this.state = EStateGraph.AT_EASE;
            graphToolboxState.changedStateButtonsAddDel(EStateGraph.AT_EASE);
        }
        else {
            this.state = EStateGraph.BUTTON_DELETE_NODE;
            graphToolboxState.changedStateButtonsAddDel(EStateGraph.BUTTON_DELETE_NODE);
        }

    }

    /**
     * Sets the new state of the buttons when the user selects the button
     * AddEdge
     */
    @FXML
    public void buttonAddEdge() {

        if (this.state == EStateGraph.BUTTON_ADD_EDGE) {
            this.state = EStateGraph.AT_EASE;
            graphToolboxState.changedStateButtonsAddDel(EStateGraph.AT_EASE);
        }
        else {
            this.state = EStateGraph.BUTTON_ADD_EDGE;
            graphToolboxState.changedStateButtonsAddDel(EStateGraph.BUTTON_ADD_EDGE);
        }
    }

    /**
     * Sets the new state of the buttons when the user selects the button
     * DeleteEdge
     */
    @FXML
    public void buttonDeleteEdge() {

        if (this.state == EStateGraph.BUTTON_DELETE_EDGE) {
            this.state = EStateGraph.AT_EASE;
            graphToolboxState.changedStateButtonsAddDel(EStateGraph.AT_EASE);
        }
        else {
            this.state = EStateGraph.BUTTON_DELETE_EDGE;
            graphToolboxState.changedStateButtonsAddDel(EStateGraph.BUTTON_DELETE_EDGE);
        }
    }

    @FXML
    public void buttonViewCenter() {

        graphToolboxState.changedStateOtherButtons(EStateGraph.RESET_VIEW);
    }

    @FXML
    public void autoLayout() {

        if (this.autoLayoutState == EStateGraph.AUTO_LAYOUT) {
            this.autoLayoutState = EStateGraph.AT_EASE;
            graphToolboxState.changedStateAutoLayout(EStateGraph.AT_EASE);
        }
        else {
            this.autoLayoutState = EStateGraph.AUTO_LAYOUT;
            graphToolboxState.changedStateAutoLayout(EStateGraph.AUTO_LAYOUT);

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.state = EStateGraph.AT_EASE;
        this.autoLayoutState = EStateGraph.AUTO_LAYOUT;
    }

    /**
     * This interface allows to the GraphMainController to get the new state of
     * the graphButtons
     */
    public interface IGraphButtonsState {

        /**
         * This method is called when the state of the graphButtons is updated
         * 
         * @param state
         *            the new state of the graphButtons
         */
        public void changedStateButtonsAddDel(EStateGraph state);

        public void changedStateOtherButtons(EStateGraph state);
        
        public void changedStateAutoLayout(EStateGraph state);
    }
}
