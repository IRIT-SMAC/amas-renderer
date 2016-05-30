package fr.irit.smac.amasrenderer.controller.graph;

import java.net.URL;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.controller.EButtonsAddDelState;
import fr.irit.smac.amasrenderer.controller.EOthersButtonsState;
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

    private EButtonsAddDelState buttonsAddDelState;

    private EOthersButtonsState autoLayoutState;

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

        if (this.buttonsAddDelState == EButtonsAddDelState.BUTTON_ADD_NODE) {
            this.buttonsAddDelState = EButtonsAddDelState.AT_EASE;
            graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.AT_EASE);
        }
        else {
            this.buttonsAddDelState = EButtonsAddDelState.BUTTON_ADD_NODE;
            graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.BUTTON_ADD_NODE);
        }
    }

    /**
     * Sets the new state of the buttons when the user selects the button
     * DeleteNode
     */
    @FXML
    public void buttonDeleteNode() {

        if (this.buttonsAddDelState == EButtonsAddDelState.BUTTON_DELETE_NODE) {
            this.buttonsAddDelState = EButtonsAddDelState.AT_EASE;
            graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.AT_EASE);
        }
        else {
            this.buttonsAddDelState = EButtonsAddDelState.BUTTON_DELETE_NODE;
            graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.BUTTON_DELETE_NODE);
        }

    }

    /**
     * Sets the new state of the buttons when the user selects the button
     * AddEdge
     */
    @FXML
    public void buttonAddEdge() {

        if (this.buttonsAddDelState == EButtonsAddDelState.BUTTON_ADD_EDGE) {
            this.buttonsAddDelState = EButtonsAddDelState.AT_EASE;
            graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.AT_EASE);
        }
        else {
            this.buttonsAddDelState = EButtonsAddDelState.BUTTON_ADD_EDGE;
            graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.BUTTON_ADD_EDGE);
        }
    }

    /**
     * Sets the new state of the buttons when the user selects the button
     * DeleteEdge
     */
    @FXML
    public void buttonDeleteEdge() {

        if (this.buttonsAddDelState == EButtonsAddDelState.BUTTON_DELETE_EDGE) {
            this.buttonsAddDelState = EButtonsAddDelState.AT_EASE;
            graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.AT_EASE);
        }
        else {
            this.buttonsAddDelState = EButtonsAddDelState.BUTTON_DELETE_EDGE;
            graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.BUTTON_DELETE_EDGE);
        }
    }

    /**
     * Sets the state of the other buttons of the toolbox when the button
     * ResetView is clicked
     */
    @FXML
    public void buttonViewCenter() {

        graphToolboxState.changedStateOtherButtons(EOthersButtonsState.RESET_VIEW);
    }

    /**
     * Sets the state of the other buttons of the toolbox when the button
     * AutoLayout is selected or unselected
     */
    @FXML
    public void autoLayout() {

        if (this.autoLayoutState == EOthersButtonsState.AUTO_LAYOUT) {
            this.autoLayoutState = EOthersButtonsState.NO_AUTO_LAYOUT;
            graphToolboxState.changedStateOtherButtons(EOthersButtonsState.NO_AUTO_LAYOUT);
        }
        else {
            this.autoLayoutState = EOthersButtonsState.AUTO_LAYOUT;
            graphToolboxState.changedStateOtherButtons(EOthersButtonsState.AUTO_LAYOUT);

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.buttonsAddDelState = EButtonsAddDelState.AT_EASE;
        this.autoLayoutState = EOthersButtonsState.AUTO_LAYOUT;
    }

    /**
     * This interface allows to the GraphMainController to get the new state of
     * the graphButtons
     */
    public interface IGraphButtonsState {

        /**
         * This method is called when the state of the buttons AddDel is updated
         * 
         * @param state
         *            the new state of the graphButtons
         */
        public void changedStateButtonsAddDel(EButtonsAddDelState state);

        /**
         * This method is called when the state of the other buttons is updated
         * 
         * @param state
         *            the new state of the graphButtons
         */
        public void changedStateOtherButtons(EOthersButtonsState state);

    }
}
