package fr.irit.smac.amasrenderer.controller.graph;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

/**
 * This controller is related to the toolbox which allows to interact with the
 * graph of agents
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
    private ToggleButton buttonHidePort;

    @FXML
    private Button buttonResetView;

    @FXML
    private ToggleButton buttonAutoLayout;

    @FXML
    private Button buttonResetGraph;
    
    @FXML
    private Button buttonViewCenter;
    
    @FXML
    private ToggleButton buttonHideMainSprite;

    private EButtonsAddDelState buttonsAddDelState;

    private EOthersButtonsState autoLayoutState;

    private IGraphButtonsState graphToolboxState;

    private EOthersButtonsState hidePortState;

    private EOthersButtonsState hideMainSprite;

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
            this.graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.AT_EASE);
        }
        else {
            this.buttonsAddDelState = EButtonsAddDelState.BUTTON_ADD_NODE;
            this.graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.BUTTON_ADD_NODE);
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
            this.graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.AT_EASE);
        }
        else {
            this.buttonsAddDelState = EButtonsAddDelState.BUTTON_DELETE_NODE;
            this.graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.BUTTON_DELETE_NODE);
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
            this.graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.AT_EASE);
        }
        else {
            this.buttonsAddDelState = EButtonsAddDelState.BUTTON_ADD_EDGE;
            this.graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.BUTTON_ADD_EDGE);
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
            this.graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.AT_EASE);
        }
        else {
            this.buttonsAddDelState = EButtonsAddDelState.BUTTON_DELETE_EDGE;
            this.graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.BUTTON_DELETE_EDGE);
        }
    }

    /**
     * Sets the state of the other buttons of the toolbox when the button
     * ResetView is clicked
     */
    @FXML
    public void buttonViewCenter() {

        this.graphToolboxState.changedStateOtherButtons(EOthersButtonsState.RESET_VIEW);
    }

    /**
     * Sets the state of the other buttons of the toolbox when the button
     * ResetGraph is clicked
     */
    @FXML
    public void buttonResetGraph() {

        this.graphToolboxState.changedStateOtherButtons(EOthersButtonsState.RESET_GRAPH);
    }

    /**
     * Sets the state of the other buttons of the toolbox when the button
     * HidePort is clicked
     */
    @FXML
    public void buttonHidePort() {

        if (this.hidePortState == EOthersButtonsState.HIDE_PORT) {
            this.hidePortState = EOthersButtonsState.DISPLAY_PORT;
            this.graphToolboxState.changedStateOtherButtons(EOthersButtonsState.DISPLAY_PORT);
        }
        else {
            this.hidePortState = EOthersButtonsState.HIDE_PORT;
            this.graphToolboxState.changedStateOtherButtons(EOthersButtonsState.HIDE_PORT);
        }
    }
    
    /**
     * Sets the state of the other buttons of the toolbox when the button
     * HideMainSprite is clicked
     */
    @FXML
    public void buttonHideMainSprite() {

        if (this.hideMainSprite == EOthersButtonsState.HIDE_MAIN_SPRITE) {
            this.hideMainSprite = EOthersButtonsState.DISPLAY_MAIN_SPRITE;
            this.graphToolboxState.changedStateOtherButtons(EOthersButtonsState.DISPLAY_MAIN_SPRITE);
        }
        else {
            this.hideMainSprite = EOthersButtonsState.HIDE_MAIN_SPRITE;
            this.graphToolboxState.changedStateOtherButtons(EOthersButtonsState.HIDE_MAIN_SPRITE);
        }
    }

    /**
     * Sets the state of the other buttons of the toolbox when the button
     * AutoLayout is selected or unselected
     */
    @FXML
    public void autoLayout() {

        if (this.autoLayoutState == EOthersButtonsState.AUTO_LAYOUT) {
            this.autoLayoutState = EOthersButtonsState.NO_AUTO_LAYOUT;
            this.graphToolboxState.changedStateOtherButtons(EOthersButtonsState.NO_AUTO_LAYOUT);
        }
        else {
            this.autoLayoutState = EOthersButtonsState.AUTO_LAYOUT;
            this.graphToolboxState.changedStateOtherButtons(EOthersButtonsState.AUTO_LAYOUT);

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.buttonsAddDelState = EButtonsAddDelState.AT_EASE;
        this.autoLayoutState = EOthersButtonsState.AUTO_LAYOUT;
        this.hidePortState = EOthersButtonsState.DISPLAY_PORT;
        this.hideMainSprite = EOthersButtonsState.HIDE_MAIN_SPRITE;
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
