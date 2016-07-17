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
import fr.irit.smac.amasrenderer.service.graph.GraphService;
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

    private Node selectedAgent;

    private Node source = null;

    private Node target = null;

    private GraphicElement selectedElement;

    private EGraphState graphState;

    private EShortcutState shortcutState;

    private EButtonsAddDelState buttonsAddDelState;

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
            shortcutState = EShortcutState.CTRL_DOWN;
        }
        else if (e.isShiftDown()) {
            shortcutState = EShortcutState.SHIFT_DOWN;
        }
    }

    /**
     * Handles the behavior of the graph when the user released a key
     */
    @FXML
    public void handleOnKeyReleased() {

        switch (shortcutState) {

            case CTRL_DOWN:
                shortcutState = EShortcutState.AT_EASE;
                break;

            case SHIFT_DOWN:
                shortcutState = EShortcutState.AT_EASE;
                unselectSource();
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

        if (graphState == EGraphState.SELECTED_NODE) {
            graphState = EGraphState.AT_EASE;
            unselectNode(selectedElement);
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

        if (graphState == EGraphState.SELECTED_NODE) {
            graphState = EGraphState.SELECTED_NODE;
            moveSelectedNode(selectedElement, e);
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

        zoomOrUnzoom(e);
    }

    /**
     * Handles the behavior of the graph when the user click on it
     * 
     * @param e
     *            the mouse event
     */
    @FXML
    public void handleOnMousePressed(MouseEvent e) {

        handleButtonsAddDelState();
        handleShortcutState(e);
        handleGraphState(e);
    }

    /**
     * Updates the state of the graph depending on the state of the buttons
     * AddDel
     */
    public void handleButtonsAddDelState() {

        if (graphState != EGraphState.READY_TO_ADD_EDGE_TARGET) {

            switch (buttonsAddDelState) {

                case BUTTON_ADD_NODE:
                    graphState = EGraphState.READY_TO_ADD_NODE;
                    break;

                case BUTTON_DELETE_NODE:
                    graphState = EGraphState.READY_TO_DELETE_NODE;
                    break;

                case BUTTON_ADD_EDGE:
                    graphState = EGraphState.READY_TO_ADD_EDGE_SOURCE;
                    break;

                case BUTTON_DELETE_EDGE:
                    graphState = EGraphState.READY_TO_DELETE_EDGE;
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

        switch (shortcutState) {

            case CTRL_DOWN:
                readyToAddOrDeleteNode(e);
                break;

            case SHIFT_DOWN:
                readyToAddOrDeleteEdge(e);
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

        if (graphState == EGraphState.AT_EASE) {

            graphState = EGraphState.AT_EASE;
            handleAttributesOrSelectNode(e);
        }
        else {

            handleUnselectedNodeDisplay();
            switch (graphState) {

                case AT_EASE:
                    graphState = EGraphState.AT_EASE;
                    handleAttributesOrSelectNode(e);
                    break;
                case READY_TO_ADD_NODE:
                    graphState = EGraphState.AT_EASE;
                    createNode(e);
                    break;

                case READY_TO_DELETE_NODE:
                    graphState = EGraphState.AT_EASE;
                    removeNode(e);
                    break;

                case READY_TO_ADD_EDGE_SOURCE:
                    readyToAddEdgeSource(e, EGraphState.READY_TO_ADD_EDGE_TARGET);
                    break;

                case READY_TO_ADD_EDGE_TARGET:
                    graphState = EGraphState.AT_EASE;
                    addEdge(e);
                    break;

                case READY_TO_DELETE_EDGE:
                    graphState = EGraphState.AT_EASE;
                    removeEdge(e);
                    break;

                default:
                    break;
            }
        }

    }

    @Override
    public void changedStateButtonsAddDel(EButtonsAddDelState state) {

        graphNode.requestFocus();
        buttonsAddDelState = state;
    }

    @Override
    public void changedStateOtherButtons(EOthersButtonsState state) {

        graphNode.requestFocus();

        switch (state) {

            case RESET_VIEW:
                graphView.getCamera().resetView();
                break;
            case AUTO_LAYOUT:
                viewer.enableAutoLayout();
                break;
            case NO_AUTO_LAYOUT:
                viewer.disableAutoLayout();
                break;
            case RESET_GRAPH:
                loadFxml(window, "view/graph/ClearGraph.fxml", true, (Class<?>) null);
                break;
            case HIDE_PORT:
                graphService.hideSpriteEdge(Const.PORT);
                graphService.setDisplayPort(false);
                break;
            case DISPLAY_PORT:
                graphService.displaySpriteEdge(displayNodeState == EDisplayNodeState.ACTIVE,
                    foregroundNode, Const.PORT, Const.PORT_SPRITE_CLASS);
                graphService.setDisplayPort(true);
                break;
            case HIDE_MAIN_SPRITE:
                graphService.hideSpriteEdge(Const.MAIN_SPRITE_EDGE);
                graphService.setDisplayMain(false);
                break;
            case DISPLAY_MAIN_SPRITE:
                graphService.displaySpriteEdge(displayNodeState == EDisplayNodeState.ACTIVE,
                    foregroundNode, Const.MAIN_SPRITE_EDGE, Const.MAIN_SPRITE_CLASS);
                graphService.setDisplayMain(true);
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

        if (e.isPrimaryButtonDown() && !(graphState == EGraphState.READY_TO_ADD_EDGE_TARGET)) {
            graphState = EGraphState.READY_TO_ADD_EDGE_SOURCE;
        }
        else if (e.isSecondaryButtonDown()) {
            graphState = EGraphState.READY_TO_DELETE_EDGE;
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
            source = (Node) selected;
            graphState = nextState;
            selectSource();
        }
    }

    /**
     * Sets the state add or remove a node depending on the mouse button down
     * 
     * @param e
     */
    private void readyToAddOrDeleteNode(MouseEvent e) {

        if (e.isPrimaryButtonDown()) {
            graphState = EGraphState.READY_TO_ADD_NODE;
        }
        else if (e.isSecondaryButtonDown()) {
            graphState = EGraphState.READY_TO_DELETE_NODE;
        }
    }

    /**
     * Zooms or unzooms depending on the scroll event
     * 
     * @param e
     */
    private void zoomOrUnzoom(ScrollEvent e) {

        Double scale = graphView.getCamera().getViewPercent();
        if (e.getDeltaY() >= 0) {
            graphView.getCamera().setViewPercent(scale * Const.SCALE_UNZOOM_RATIO);
        }
        else {
            graphView.getCamera().setViewPercent(scale * Const.SCALE_ZOOM_RATIO);
        }
        Point3 newCenter = graphView.getCamera().getViewCenter()
            .interpolate(graphView.getCamera().transformPxToGu(e.getX(), e.getY()), Const.TRANSLATE_ZOOM_RATIO);
        graphView.getCamera().setViewCenter(newCenter.x, newCenter.y, newCenter.z);
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
            graphState = EGraphState.AT_EASE;
            handleAttributesOrSelectSprite(e);

        }
        else if (e.isPrimaryButtonDown()) {
            selectNode(e);
        }
    }

    private void handleAttributesOrSelectSprite(MouseEvent e) {

        GraphicElement elt = graphView.findNodeOrSpriteAt(e.getX(), e.getY());

        if (elt != null && elt instanceof Node) {
            handleAttributes(elt);
        }
        else if (selectedElement != null) {
            clickOnSpriteEdge(elt);
        }
    }

    /**
     * Selected the selected node
     *
     * @param event
     *            the event
     */
    private void selectNode(MouseEvent e) {

        selectedElement = graphView.findNodeOrSpriteAt(e.getX(), e.getY());
        if (selectedElement instanceof Node) {

            handleSelectedNodeDisplay();

            graphState = EGraphState.SELECTED_NODE;
            graphView.requestFocus();
            for (Node node : graphService.getGraph()) {
                node.addAttribute(Const.GS_UI_CLICKED);
                node.removeAttribute(Const.GS_UI_CLICKED);
            }
            graphView.freezeElement(selectedElement, true);
            selectedElement.addAttribute(Const.GS_UI_CLICKED);

        }
        else if (selectedElement != null) {
            clickOnSpriteEdge(selectedElement);
        }
        else {
            handleUnselectedNodeDisplay();
        }
    }

    private void clickOnSpriteEdge(GraphicElement elt) {

        Sprite s = graphService.getSpriteManager().getSprite(elt.getId());
        if (s.getAttribute(Const.GS_UI_CLASS) != Const.EDGE_SPRITE_CLASS_BACKGROUND) {
            if (s.getAttribute(Const.TYPE_SPRITE) != Const.MAIN_SPRITE_EDGE) {
                loadFxml(window, "view/graph/Port.fxml", true, s);
            }
            else {
                loadFxml(window, "view/graph/TargetAttributes.fxml", true, s);
            }
        }
    }

    private void handleUnselectedNodeDisplay() {

        if (displayNodeState == EDisplayNodeState.ACTIVE) {
            displayNodeState = EDisplayNodeState.AT_EASE;
            graphService.displayAllNodes();
        }
    }

    private void handleSelectedNodeDisplay() {

        foregroundNode = (Node) selectedElement;
        if (displayNodeState == EDisplayNodeState.ACTIVE) {
            graphService.displayBackgroundNode(foregroundNode);
        }

        displayNodeState = EDisplayNodeState.ACTIVE;
        graphService.displayForegroundNode(foregroundNode);
    }

    /**
     * When a node is clicked (right click), a window allowing to see and
     * updated its attributes is opened
     * 
     * @param e
     */
    private void handleAttributes(GraphicElement elt) {

        selectedAgent = graphService.getGraph().getNode(((Node) elt).getId());
        loadFxml(window, "view/graph/GraphAttributes.fxml", true, selectedAgent);
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

        graphView.freezeElement(element, false);
        selectedElement = null;
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

        graphView.moveElementAtPx(element, event.getX(), event.getY());
    }

    /**
     * Creates the node.
     *
     * @param e
     *            the event
     */
    private void createNode(MouseEvent e) {

        Point3 clicLoc = graphView.getCamera().transformPxToGu(e.getX(), e.getY());
        graphService.addNode(clicLoc.x, clicLoc.y);
    }

    /**
     * Removes a node
     * 
     * @param e
     *            the event
     */
    private void removeNode(MouseEvent e) {

        GraphicElement selected = graphView.findNodeOrSpriteAt(e.getX(), e.getY());
        if (selected != null && selected instanceof Node) {
            graphService.removeNode(selected.getAttribute(Const.GS_UI_LABEL));
        }
    }

    /**
     * Selects the source node
     */
    private void selectSource() {

        if (source != null && !source.hasAttribute(Const.GS_UI_SELECTED)) {
            source.addAttribute(Const.GS_UI_SELECTED);
        }
        target = null;
    }

    /**
     * Unselects a source when an edge is created, deleted, or when the target
     * is not a node
     */
    private void unselectSource() {

        if (source != null) {
            source.removeAttribute(Const.GS_UI_SELECTED);
            source = null;
        }
    }

    /**
     * Adds an edge between the source and the target
     * 
     * @param e
     *            the mouse event
     */
    private void addEdge(MouseEvent e) {

        getEdge(e);
        if (source != null && target != null) {

            graphService.addEdge(source.getAttribute(Const.GS_UI_LABEL), target.getAttribute(Const.GS_UI_LABEL));
        }
        unselectSource();
    }

    /**
     * Removes the edge between the source and the target
     * 
     * @param e
     *            the mouse event
     */
    private void removeEdge(MouseEvent e) {

        GraphicElement sprite = graphView.findNodeOrSpriteAt(e.getX(), e.getY());

        if (sprite != null && !(sprite instanceof Node)) {
            graphService
                .removeEdge(graphService.getSpriteManager().getSprite(sprite.getId()).getAttachment().getId());
            unselectSource();
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

        GraphicElement selected = graphView.findNodeOrSpriteAt(e.getX(), e.getY());
        if (selected != null && selected instanceof Node) {
            target = (Node) selected;
            edge = graphService.getGraph().getEdge(source + "" + target);
        }
        return edge;
    }

    /**
     * Gets the graph view
     *
     * @return the graph view
     */
    public ViewPanel getGraphView() {
        return graphView;
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

        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        viewer = new Viewer(graphService.getGraph(), Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        graphView = viewer.addDefaultView(false);
        removeDefaultListeners();
        graphState = EGraphState.AT_EASE;
        shortcutState = EShortcutState.AT_EASE;
        buttonsAddDelState = EButtonsAddDelState.AT_EASE;
        displayNodeState = EDisplayNodeState.AT_EASE;
        graphToolboxController.setGraphButtonsState(this);
        graphNode.setContent(graphView);
        graphService.setDisplayMain(true);
        graphService.setDisplayPort(true);
    }

    @Override
    public void setWindow(Window window) {
        this.window = window;
    }

}
