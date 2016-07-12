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

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.ResourceBundle;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.controller.LoadSecondaryWindowController;
import fr.irit.smac.amasrenderer.model.AgentModel;
import fr.irit.smac.amasrenderer.service.GraphService;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Window;

/**
 * This controller is related to the graph of agents
 */
public class GraphController extends LoadSecondaryWindowController
    implements Initializable, GraphToolboxController.IGraphButtonsState {

    @FXML
    private StackPane stackPaneGraphNode;

    @FXML
    private AnchorPane nodeEdit;

    @FXML
    private SwingNode graphNode;

    @FXML
    private GraphToolboxController graphToolboxController;

    private GraphService graphService = GraphService.getInstance();

    private ViewPanel graphView;

    private Viewer viewer;

    private AgentModel selectedAgent;

    private Node source = null;

    private Node target = null;

    private GraphicElement selectedElement;

    private EGraphState graphState;

    private EShortcutState shortcutState;

    private EButtonsAddDelState buttonsAddDelState;

    private Integer edgeCreatedCount = 0;

    private EDisplayNodeState displayNodeState;

    private Node foregroundNode;

    /**
     * Handles the behavior of the graph when the user pressed a key
     * 
     * @param e
     *            the key event
     */
    @FXML
    public void handleOnKeyPressed(KeyEvent e) {

        if (e.isControlDown()) {
            this.shortcutState = EShortcutState.CTRL_DOWN;
        }
        else if (e.isShiftDown()) {
            this.shortcutState = EShortcutState.SHIFT_DOWN;
        }
    }

    /**
     * Handles the behavior of the graph when the user released a key
     */
    @FXML
    public void handleOnKeyReleased() {

        switch (this.shortcutState) {

            case CTRL_DOWN:
                this.shortcutState = EShortcutState.AT_EASE;
                break;

            case SHIFT_DOWN:
                this.shortcutState = EShortcutState.AT_EASE;
                this.unselectSource();
                break;

            default:
                break;
        }
    }

    /**
     * Handles the behavior of the graph when the user released the mouse
     */
    @FXML
    public void handleOnMouseReleased() {

        if (this.graphState == EGraphState.SELECTED_NODE) {
            this.graphState = EGraphState.AT_EASE;
            this.unselectNode(this.selectedElement);
        }
    }

    /**
     * Handles the behavior of the graph when the user drag the mouse
     * 
     * @param e
     *            the mouse event
     */
    @FXML
    public void handleOnMouseDragged(MouseEvent e) {

        if (this.graphState == EGraphState.SELECTED_NODE) {
            this.graphState = EGraphState.SELECTED_NODE;
            this.moveSelectedNode(this.selectedElement, e);
        }
    }

    /**
     * Handles the behavior of the graph when the user zoom or unzoom
     * 
     * @param e
     *            the scroll event
     */
    @FXML
    public void handleOnScroll(ScrollEvent e) {

        this.zoomOrUnzoom(e);
    }

    /**
     * Handles the behavior of the graph when the user click on it
     * 
     * @param e
     *            the mouse event
     */
    @FXML
    public void handleOnMousePressed(MouseEvent e) {

        this.handleButtonsAddDelState();
        this.handleShortcutState(e);
        this.handleGraphState(e);
    }

    /**
     * Updates the state of the graph depending on the state of the buttons
     * AddDel
     */
    public void handleButtonsAddDelState() {

        if (this.graphState != EGraphState.READY_TO_ADD_EDGE_TARGET) {

            switch (this.buttonsAddDelState) {

                case BUTTON_ADD_NODE:
                    this.graphState = EGraphState.READY_TO_ADD_NODE;
                    break;

                case BUTTON_DELETE_NODE:
                    this.graphState = EGraphState.READY_TO_DELETE_NODE;
                    break;

                case BUTTON_ADD_EDGE:
                    this.graphState = EGraphState.READY_TO_ADD_EDGE_SOURCE;
                    break;

                case BUTTON_DELETE_EDGE:
                    this.graphState = EGraphState.READY_TO_DELETE_EDGE;
                    break;

                default:
                    break;
            }
        }

    }

    /**
     * Updates the state of the graph depending on the state of the shorcut
     * 
     * @param e
     *            the mouse event
     */
    public void handleShortcutState(MouseEvent e) {

        switch (this.shortcutState) {

            case CTRL_DOWN:
                this.readyToAddOrDeleteNode(e);
                break;

            case SHIFT_DOWN:
                this.readyToAddOrDeleteEdge(e);
                break;

            default:
                break;
        }
    }

    /**
     * Handles the behaviour of the graph
     * 
     * @param e
     *            the mouse event
     */
    public void handleGraphState(MouseEvent e) {

        if (this.graphState == EGraphState.AT_EASE) {

            this.graphState = EGraphState.AT_EASE;
            this.handleAttributesOrSelectNode(e);
        }
        else {

            this.handleUnselectedNodeDisplay();
            switch (this.graphState) {

                case AT_EASE:
                    this.graphState = EGraphState.AT_EASE;
                    this.handleAttributesOrSelectNode(e);
                    break;
                case READY_TO_ADD_NODE:
                    this.graphState = EGraphState.AT_EASE;
                    this.createNode(e);
                    break;

                case READY_TO_DELETE_NODE:
                    this.graphState = EGraphState.AT_EASE;
                    this.removeNode(e);
                    break;

                case READY_TO_ADD_EDGE_SOURCE:
                    this.readyToAddEdgeSource(e, EGraphState.READY_TO_ADD_EDGE_TARGET);
                    break;

                case READY_TO_ADD_EDGE_TARGET:
                    this.graphState = EGraphState.AT_EASE;
                    this.addEdge(e);
                    break;

                case READY_TO_DELETE_EDGE:
                    this.graphState = EGraphState.AT_EASE;
                    this.removeEdge(e);
                    break;

                default:
                    break;
            }
        }

    }

    @Override
    public void changedStateButtonsAddDel(EButtonsAddDelState state) {

        this.graphNode.requestFocus();
        this.buttonsAddDelState = state;
    }

    @Override
    public void changedStateOtherButtons(EOthersButtonsState state) {

        this.graphNode.requestFocus();

        switch (state) {

            case RESET_VIEW:
                this.graphView.getCamera().resetView();
                break;
            case AUTO_LAYOUT:
                this.viewer.enableAutoLayout();
                break;
            case NO_AUTO_LAYOUT:
                this.viewer.disableAutoLayout();
                break;
            case RESET_GRAPH:
                this.loadFxml(window, "view/graph/ClearGraph.fxml", true, (Class<?>) null);
                break;
            case HIDE_PORT:
                this.graphService.hideSpriteEdge(Const.PORT);
                this.graphService.setDisplayPort(false);
                break;
            case DISPLAY_PORT:
                this.graphService.displaySpriteEdge(this.displayNodeState == EDisplayNodeState.ACTIVE,
                    this.foregroundNode, Const.PORT, Const.PORT_SPRITE_CLASS);
                this.graphService.setDisplayPort(true);
                break;
            case HIDE_MAIN_SPRITE:
                this.graphService.hideSpriteEdge(Const.MAIN_SPRITE_EDGE);
                this.graphService.setDisplayMain(false);
                break;
            case DISPLAY_MAIN_SPRITE:
                this.graphService.displaySpriteEdge(this.displayNodeState == EDisplayNodeState.ACTIVE,
                    this.foregroundNode, Const.MAIN_SPRITE_EDGE, Const.MAIN_SPRITE_CLASS);
                this.graphService.setDisplayMain(true);
                break;
            default:
                break;
        }
    }

    /**
     * Sets the state to ready to add or delete an edge depending on the mouse
     * button edge
     * 
     * @param e
     *            the event
     */
    private void readyToAddOrDeleteEdge(MouseEvent e) {

        if (e.isPrimaryButtonDown() && !(this.graphState == EGraphState.READY_TO_ADD_EDGE_TARGET)) {
            this.graphState = EGraphState.READY_TO_ADD_EDGE_SOURCE;
        }
        else if (e.isSecondaryButtonDown()) {
            this.graphState = EGraphState.READY_TO_DELETE_EDGE;
        }
    }

    /**
     * Sets the state to ready to add or delete the target of an edge depending
     * on the arguments and select the source
     * 
     * @param e
     *            the event
     * @param nextState
     *            the next state
     */
    private void readyToAddEdgeSource(MouseEvent e, EGraphState nextState) {

        GraphicElement selected = graphView.findNodeOrSpriteAt(e.getX(), e.getY());
        if (selected != null && selected instanceof Node) {
            this.source = (Node) selected;
            this.graphState = nextState;
            this.selectSource();
        }
    }

    /**
     * Sets the state add or remove a node depending on the mouse button down
     * 
     * @param e
     */
    private void readyToAddOrDeleteNode(MouseEvent e) {

        if (e.isPrimaryButtonDown()) {
            this.graphState = EGraphState.READY_TO_ADD_NODE;
        }
        else if (e.isSecondaryButtonDown()) {
            this.graphState = EGraphState.READY_TO_DELETE_NODE;
        }
    }

    /**
     * Zooms or unzooms depending on the scroll event
     * 
     * @param e
     */
    private void zoomOrUnzoom(ScrollEvent e) {

        Double scale = this.graphView.getCamera().getViewPercent();
        if (e.getDeltaY() >= 0) {
            this.graphView.getCamera().setViewPercent(scale * Const.SCALE_UNZOOM_RATIO);
        }
        else {
            this.graphView.getCamera().setViewPercent(scale * Const.SCALE_ZOOM_RATIO);
        }
        Point3 newCenter = this.graphView.getCamera().getViewCenter()
            .interpolate(this.graphView.getCamera().transformPxToGu(e.getX(), e.getY()), Const.TRANSLATE_ZOOM_RATIO);
        this.graphView.getCamera().setViewCenter(newCenter.x, newCenter.y, newCenter.z);
    }

    /**
     * Handles the attribute or selects a node depending on the mouse button
     * down
     * 
     * @param e
     *            the event
     */
    private void handleAttributesOrSelectNode(MouseEvent e) {

        if (e.isSecondaryButtonDown()) {
            this.graphState = EGraphState.AT_EASE;
            this.handleAttributesOrSelectSprite(e);

        }
        else if (e.isPrimaryButtonDown()) {
            this.selectNode(e);
        }
    }

    private void handleAttributesOrSelectSprite(MouseEvent e) {

        GraphicElement elt = this.graphView.findNodeOrSpriteAt(e.getX(), e.getY());

        if (elt != null && elt instanceof Node) {
            this.handleAttributes(elt);
        }
        else if (this.selectedElement != null) {
            this.clickOnSpriteEdge(elt);
        }
    }

    /**
     * Selected the selected node
     *
     * @param event
     *            the event
     */
    private void selectNode(MouseEvent e) {

        this.selectedElement = this.graphView.findNodeOrSpriteAt(e.getX(), e.getY());
        if (this.selectedElement instanceof Node) {

            this.handleSelectedNodeDisplay();

            this.graphState = EGraphState.SELECTED_NODE;
            this.graphView.requestFocus();
            for (Node node : this.graphService.getGraph()) {
                node.addAttribute(Const.GS_UI_CLICKED);
                node.removeAttribute(Const.GS_UI_CLICKED);
            }
            this.graphView.freezeElement(selectedElement, true);
            this.selectedElement.addAttribute(Const.GS_UI_CLICKED);

        }
        else if (this.selectedElement != null) {
            this.clickOnSpriteEdge(this.selectedElement);
        }
        else {
            this.handleUnselectedNodeDisplay();
        }
    }

    private void clickOnSpriteEdge(GraphicElement elt) {

        Sprite s = this.graphService.getSpriteManager().getSprite(elt.getId());
        if (s.getAttribute(Const.GS_UI_CLASS) != Const.EDGE_SPRITE_CLASS_BACKGROUND) {
            if (s.getAttribute(Const.TYPE_SPRITE) != Const.MAIN_SPRITE_EDGE) {
                this.loadFxml(window, "view/graph/Port.fxml", true, s);
            }
            else {
                this.loadFxml(window, "view/graph/TargetAttributes.fxml", true, s);
            }
        }
    }

    private void handleUnselectedNodeDisplay() {

        if (this.displayNodeState == EDisplayNodeState.ACTIVE) {
            this.displayNodeState = EDisplayNodeState.AT_EASE;
            this.graphService.displayAllNodes();
        }
    }

    private void handleSelectedNodeDisplay() {

        this.foregroundNode = (Node) this.selectedElement;
        if (this.displayNodeState == EDisplayNodeState.ACTIVE) {
            this.graphService.displayBackgroundNode(this.foregroundNode);
        }

        this.displayNodeState = EDisplayNodeState.ACTIVE;
        this.graphService.displayForegroundNode(this.foregroundNode);
    }

    /**
     * When a node is clicked (right click), a window allowing to see and
     * updated its attributes is opened
     * 
     * @param e
     */
    private void handleAttributes(GraphicElement elt) {

        this.selectedAgent = this.graphService.getGraph().getNode(((Node) elt).getId());
        this.loadFxml(window, "view/graph/GraphAttributes.fxml", true, this.selectedAgent);
    }

    /**
     * When the mouse is released after dragging one, the selected element is
     * freezed and is not selected anymore
     * 
     * @param element
     *            the element
     * @param event
     *            the event
     */
    private void unselectNode(GraphicElement element) {

        this.graphView.freezeElement(element, false);
        this.selectedElement = null;
    }

    /**
     * The selected element is moved to the location of the mouse
     *
     * @param element
     *            the element
     * @param event
     *            the event
     */
    private void moveSelectedNode(GraphicElement element, MouseEvent event) {

        this.graphView.moveElementAtPx(element, event.getX(), event.getY());
    }

    /**
     * Creates the node.
     *
     * @param e
     *            the event
     */
    private void createNode(MouseEvent e) {

        Point3 clicLoc = this.graphView.getCamera().transformPxToGu(e.getX(), e.getY());
        this.graphService.addNode(clicLoc.x, clicLoc.y);
    }

    /**
     * Removes a node
     * 
     * @param e
     *            the event
     */
    private void removeNode(MouseEvent e) {

        GraphicElement selected = this.graphView.findNodeOrSpriteAt(e.getX(), e.getY());
        if (selected != null && selected instanceof Node) {
            this.graphService.removeNode((Node) selected);
        }
    }

    /**
     * Selects the source node
     */
    private void selectSource() {

        if (this.source != null && !this.source.hasAttribute(Const.GS_UI_SELECTED)) {
            this.source.addAttribute(Const.GS_UI_SELECTED);
        }
        this.target = null;
    }

    /**
     * Unselects a source when an edge is created, deleted, or when the target
     * is not a node
     */
    private void unselectSource() {

        if (this.source != null) {
            this.source.removeAttribute(Const.GS_UI_SELECTED);
            this.source = null;
        }
    }

    /**
     * Adds an edge between the source and the target
     * 
     * @param e
     *            the mouse event
     */
    private void addEdge(MouseEvent e) {

        this.getEdge(e);
        if (this.source != null && this.target != null) {

            this.graphService.addEdge(this.source.getId(), this.target.getId(),
                this.target.getId().concat(this.edgeCreatedCount.toString()), source.getId());
        }
        this.edgeCreatedCount++;
        this.unselectSource();
    }

    /**
     * Removes the edge between the source and the target
     * 
     * @param e
     *            the mouse event
     */
    private void removeEdge(MouseEvent e) {

        GraphicElement sprite = this.graphView.findNodeOrSpriteAt(e.getX(), e.getY());

        if (sprite != null && !(sprite instanceof Node)) {
            this.graphService
                .removeEdge(this.graphService.getSpriteManager().getSprite(sprite.getId()).getAttachment().getId());
            this.unselectSource();
        }
    }

    /**
     * Gets the edge between the source and the target
     * 
     * @param e
     *            the event
     * @return the edge
     */
    private Edge getEdge(MouseEvent e) {

        Edge edge = null;

        GraphicElement selected = this.graphView.findNodeOrSpriteAt(e.getX(), e.getY());
        if (selected != null && selected instanceof Node) {
            target = (Node) selected;
            edge = this.graphService.getGraph().getEdge(this.source + "" + this.target);
        }
        return edge;
    }

    /**
     * Gets the graph view
     *
     * @return the graph view
     */
    public ViewPanel getGraphView() {
        return this.graphView;
    }

    /**
     * Delete all the default listeners at the initialization of the graph
     */
    private void removeDefaultListeners() {

        MouseMotionListener[] mml = graphView.getMouseMotionListeners();
        for (MouseMotionListener mouseMotionListener : mml) {
            graphView.removeMouseMotionListener(mouseMotionListener);
        }

        MouseListener[] ml = graphView.getMouseListeners();
        for (MouseListener mouseListener : ml) {
            graphView.removeMouseListener(mouseListener);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javafx.fxml.Initializable#initialize(java.net.URL,
     * java.util.ResourceBundle)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.graphService.createAgentGraph();
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        this.viewer = new Viewer(this.graphService.getGraph(), Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        this.viewer.enableAutoLayout();
        this.graphView = this.viewer.addDefaultView(false);
        this.removeDefaultListeners();
        this.graphState = EGraphState.AT_EASE;
        this.shortcutState = EShortcutState.AT_EASE;
        this.buttonsAddDelState = EButtonsAddDelState.AT_EASE;
        this.displayNodeState = EDisplayNodeState.AT_EASE;
        this.graphToolboxController.setGraphButtonsState(this);
        this.graphNode.setContent(this.graphView);
        this.graphService.setDisplayMain(true);
        this.graphService.setDisplayPort(true);
    }

    @Override
    public void setWindow(Window window) {
        this.window = window;
    }

}
