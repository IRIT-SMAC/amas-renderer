/*
 * #%L
 * amas-renderer
 * %%
 * Copyright (C) 2016 IRIT - SMAC Team
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
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

    private EOthersButtonsState hideMainSpriteState;

    /**
     * Sets the implementation of graphButtonsState
     * 
     * @param graphButtonsState
     *            the implementation
     */
    public void setGraphButtonsState(IGraphButtonsState graphButtonsState) {
        graphToolboxState = graphButtonsState;
    }

    /**
     * Sets the new state of the buttons when the user selects the button
     * AddNode
     */
    @FXML
    public void buttonAddNode() {

        if (buttonsAddDelState == EButtonsAddDelState.BUTTON_ADD_NODE) {
            buttonsAddDelState = EButtonsAddDelState.AT_EASE;
            graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.AT_EASE);
        }
        else {
            buttonsAddDelState = EButtonsAddDelState.BUTTON_ADD_NODE;
            graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.BUTTON_ADD_NODE);
        }
    }

    /**
     * Sets the new state of the buttons when the user selects the button
     * DeleteNode
     */
    @FXML
    public void buttonDeleteNode() {

        if (buttonsAddDelState == EButtonsAddDelState.BUTTON_DELETE_NODE) {
            buttonsAddDelState = EButtonsAddDelState.AT_EASE;
            graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.AT_EASE);
        }
        else {
            buttonsAddDelState = EButtonsAddDelState.BUTTON_DELETE_NODE;
            graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.BUTTON_DELETE_NODE);
        }

    }

    /**
     * Sets the new state of the buttons when the user selects the button
     * AddEdge
     */
    @FXML
    public void buttonAddEdge() {

        if (buttonsAddDelState == EButtonsAddDelState.BUTTON_ADD_EDGE) {
            buttonsAddDelState = EButtonsAddDelState.AT_EASE;
            graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.AT_EASE);
        }
        else {
            buttonsAddDelState = EButtonsAddDelState.BUTTON_ADD_EDGE;
            graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.BUTTON_ADD_EDGE);
        }
    }

    /**
     * Sets the new state of the buttons when the user selects the button
     * DeleteEdge
     */
    @FXML
    public void buttonDeleteEdge() {

        if (buttonsAddDelState == EButtonsAddDelState.BUTTON_DELETE_EDGE) {
            buttonsAddDelState = EButtonsAddDelState.AT_EASE;
            graphToolboxState.changedStateButtonsAddDel(EButtonsAddDelState.AT_EASE);
        }
        else {
            buttonsAddDelState = EButtonsAddDelState.BUTTON_DELETE_EDGE;
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
     * ResetGraph is clicked
     */
    @FXML
    public void buttonResetGraph() {

        graphToolboxState.changedStateOtherButtons(EOthersButtonsState.RESET_GRAPH);
    }

    /**
     * Sets the state of the other buttons of the toolbox when the button
     * HidePort is clicked
     */
    @FXML
    public void buttonHidePort() {

        if (hidePortState == EOthersButtonsState.HIDE_PORT) {
            hidePortState = EOthersButtonsState.DISPLAY_PORT;
            graphToolboxState.changedStateOtherButtons(EOthersButtonsState.DISPLAY_PORT);
        }
        else {
            hidePortState = EOthersButtonsState.HIDE_PORT;
            graphToolboxState.changedStateOtherButtons(EOthersButtonsState.HIDE_PORT);
        }
    }
    
    /**
     * Sets the state of the other buttons of the toolbox when the button
     * HideMainSprite is clicked
     */
    @FXML
    public void buttonHideMainSprite() {

        if (hideMainSpriteState == EOthersButtonsState.HIDE_MAIN_SPRITE) {
            hideMainSpriteState = EOthersButtonsState.DISPLAY_MAIN_SPRITE;
            graphToolboxState.changedStateOtherButtons(EOthersButtonsState.DISPLAY_MAIN_SPRITE);
        }
        else {
            hideMainSpriteState = EOthersButtonsState.HIDE_MAIN_SPRITE;
            graphToolboxState.changedStateOtherButtons(EOthersButtonsState.HIDE_MAIN_SPRITE);
        }
    }

    /**
     * Sets the state of the other buttons of the toolbox when the button
     * AutoLayout is selected or unselected
     */
    @FXML
    public void autoLayout() {

        if (autoLayoutState == EOthersButtonsState.AUTO_LAYOUT) {
            autoLayoutState = EOthersButtonsState.NO_AUTO_LAYOUT;
            graphToolboxState.changedStateOtherButtons(EOthersButtonsState.NO_AUTO_LAYOUT);
        }
        else {
            autoLayoutState = EOthersButtonsState.AUTO_LAYOUT;
            graphToolboxState.changedStateOtherButtons(EOthersButtonsState.AUTO_LAYOUT);

        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        buttonsAddDelState = EButtonsAddDelState.AT_EASE;
        autoLayoutState = EOthersButtonsState.AUTO_LAYOUT;
        hidePortState = EOthersButtonsState.DISPLAY_PORT;
        hideMainSpriteState = EOthersButtonsState.DISPLAY_MAIN_SPRITE;
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
