package fr.irit.smac.amasrenderer.controller.graph;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.controller.EButtonsAddDelState;
import fr.irit.smac.amasrenderer.controller.EOthersButtonsState;
import fr.irit.smac.amasrenderer.controller.EShortcutState;
import fr.irit.smac.amasrenderer.controller.EStateGraph;
import fr.irit.smac.amasrenderer.service.GraphService;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * The Class
 */
public class GraphMainController implements Initializable, GraphToolboxController.IGraphButtonsState {

    @FXML
    private StackPane stackPaneGraphNode;

    @FXML
    private AnchorPane nodeEdit;

    @FXML
    private SwingNode graphNode;

    @FXML
    private GraphToolboxController graphToolboxController;

    private GraphService graphNodeService = GraphService.getInstance();

    private ViewPanel graphView;

    private Viewer viewer;

    private int currentNodeId;

    private Node source = null;

    private Node target = null;

    private GraphicElement selectedElement;

    private EStateGraph graphState;

    private EShortcutState shortcutState;

    private static final Logger LOGGER = Logger.getLogger(GraphMainController.class.getName());

    @FXML
    private Label classNameLabel;

    private EButtonsAddDelState buttonsAddDelState;

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

        if (this.graphState == EStateGraph.SELECTED_NODE) {
            this.graphState = EStateGraph.AT_EASE;
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

        if (this.graphState == EStateGraph.SELECTED_NODE) {
            this.graphState = EStateGraph.SELECTED_NODE;
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

        if (this.graphState != EStateGraph.READY_TO_ADD_EDGE_TARGET
            && this.graphState != EStateGraph.READY_TO_DELETE_EDGE_TARGET) {

            switch (this.buttonsAddDelState) {

                case BUTTON_ADD_NODE:
                    this.graphState = EStateGraph.READY_TO_ADD_NODE;
                    break;

                case BUTTON_DELETE_NODE:
                    this.graphState = EStateGraph.READY_TO_DELETE_NODE;
                    break;

                case BUTTON_ADD_EDGE:
                    this.graphState = EStateGraph.READY_TO_ADD_EDGE_SOURCE;
                    break;

                case BUTTON_DELETE_EDGE:
                    this.graphState = EStateGraph.READY_TO_DELETE_EDGE_SOURCE;
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
     */
    public void handleShortcutState(MouseEvent e) {

        switch (this.shortcutState) {

            case CTRL_DOWN:
                readyToAddOrDeleteNode(e);
                break;

            case SHIFT_DOWN:
                readyToAddOrDeleteEdgeShift(e);
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

        switch (this.graphState) {

            case AT_EASE:
                this.graphState = EStateGraph.AT_EASE;
                handleAttributesOrSelectNode(e);
                break;

            case READY_TO_ADD_NODE:
                this.graphState = EStateGraph.AT_EASE;
                createNode(e);
                break;

            case READY_TO_DELETE_NODE:
                this.graphState = EStateGraph.AT_EASE;
                removeNode(e);
                break;

            case READY_TO_ADD_EDGE_SOURCE:
                readyToAddOrDeleteEdgeSource(e, EStateGraph.READY_TO_ADD_EDGE_TARGET);
                break;

            case READY_TO_DELETE_EDGE_SOURCE:
                readyToAddOrDeleteEdgeSource(e, EStateGraph.READY_TO_DELETE_EDGE_TARGET);
                break;

            case READY_TO_ADD_EDGE_TARGET:
                this.graphState = EStateGraph.AT_EASE;
                addEdge(e);
                break;

            case READY_TO_DELETE_EDGE_TARGET:
                this.graphState = EStateGraph.AT_EASE;
                removeEdge(e);
                break;

            default:
                break;
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
                graphView.getCamera().resetView();
                break;
            case AUTO_LAYOUT:
                viewer.enableAutoLayout();
                break;
            case NO_AUTO_LAYOUT:
                viewer.disableAutoLayout();
                break;
            default:
                break;
        }
    }

    /**
     * Set the state to ready to add or delete an edge depending on the mouse
     * button edge
     * 
     * @param e
     *            the event
     */
    private void readyToAddOrDeleteEdgeShift(MouseEvent e) {

        if (e.isPrimaryButtonDown() && !(this.graphState == EStateGraph.READY_TO_ADD_EDGE_TARGET
            || this.graphState == EStateGraph.READY_TO_DELETE_EDGE_TARGET)) {
            this.graphState = EStateGraph.READY_TO_ADD_EDGE_SOURCE;
        }
        else if (e.isSecondaryButtonDown() && !(this.graphState == EStateGraph.READY_TO_ADD_EDGE_TARGET
            || this.graphState == EStateGraph.READY_TO_DELETE_EDGE_TARGET)) {
            this.graphState = EStateGraph.READY_TO_DELETE_EDGE_SOURCE;
        }
    }

    /**
     * Set the state to ready to add or delete the target of an edge depending
     * on the arguments and select the source
     * 
     * @param e
     *            the event
     * @param nextState
     *            the next state
     */
    private void readyToAddOrDeleteEdgeSource(MouseEvent e, EStateGraph nextState) {

        source = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
        if (source != null) {
            this.graphState = nextState;
            selectSource();
        }
    }

    /**
     * Set the state add or remove a node depending on the mouse button down
     * 
     * @param e
     */
    private void readyToAddOrDeleteNode(MouseEvent e) {

        if (e.isPrimaryButtonDown()) {
            this.graphState = EStateGraph.READY_TO_ADD_NODE;
        }
        else if (e.isSecondaryButtonDown()) {
            this.graphState = EStateGraph.READY_TO_DELETE_NODE;
        }
    }

    /**
     * Zoom or unzoom depending on the scroll event
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
     * Handles the attribute or select a node depending on the mouse button down
     * 
     * @param e
     *            the event
     */
    private void handleAttributesOrSelectNode(MouseEvent e) {

        if (e.isSecondaryButtonDown()) {
            this.graphState = EStateGraph.AT_EASE;
            handleAttributes(e);
        }
        else if (e.isPrimaryButtonDown()) {
            selectNode(e);
        }
    }

    /**
     * Unselect all nodes when the mouse button is pressed.
     *
     * @param event
     *            the event
     */
    private void selectNode(MouseEvent e) {

        selectedElement = graphView.findNodeOrSpriteAt(e.getX(), e.getY());
        if (selectedElement != null) {
            this.graphState = EStateGraph.SELECTED_NODE;
            graphView.requestFocus();
            for (Node node : GraphService.getInstance().getModel()) {
                node.addAttribute(Const.NODE_CLICKED);
                node.removeAttribute(Const.NODE_CLICKED);
            }
            graphView.freezeElement(selectedElement, true);
            selectedElement.addAttribute(Const.NODE_CLICKED);
        }
    }

    private void handleAttributes(MouseEvent e) {

        GraphicElement elt = graphView.findNodeOrSpriteAt(e.getX(), e.getY());
        if (elt != null && elt instanceof Node) {
            Platform.runLater(() -> loadFxml((Node) elt));
        }
    }

    /**
     * Mouse button release off element. When mouse released after dragging one,
     * freeze the element and remove the clicked attribute
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
     * To be called by mouseDragged moves the element to the location of the
     * mouse at each call.
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

        String curId = Integer.toString(currentNodeId++);
        Point3 clicLoc = graphView.getCamera().transformPxToGu(e.getX(), e.getY());

        this.graphNodeService.addNode(curId, clicLoc.x, clicLoc.y);
    }

    /**
     * Removes a node
     * 
     * @param e
     *            the event
     */
    private void removeNode(MouseEvent e) {

        Node node = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
        if (node != null) {
            this.graphNodeService.removeNode(node);
        }
    }

    /**
     * Selects the source node
     */
    private void selectSource() {

        if (source != null && !source.hasAttribute(Const.NODE_SELECTED)) {
            source.addAttribute(Const.NODE_SELECTED);
        }
        target = null;
    }

    /**
     * Unselects a source when an edge is created, deleted, or when the target
     * is not a node
     */
    private void unselectSource() {

        if (source != null) {
            source.removeAttribute(Const.NODE_SELECTED);
            source = null;
        }
    }

    /**
     * Add an edge between the source and the target
     * 
     * @param e
     *            the mouse event
     */
    private void addEdge(MouseEvent e) {

        if (getEdge(e) == null && source != null && target != null) {
            this.graphNodeService.addEdgeGraphModel(source.getId(), target.getId());
        }
        unselectSource();
    }

    /**
     * Remove the edge between the source and the target
     * 
     * @param e
     *            the mouse event
     */
    private void removeEdge(MouseEvent e) {

        this.graphNodeService.removeEdge(getEdge(e));
        unselectSource();
    }

    /**
     * Get the edge between the source and the target
     * 
     * @param e
     *            the event
     * @return the edge
     */
    private Edge getEdge(MouseEvent e) {

        Edge edge = null;
        target = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
        if (target != null) {
            edge = this.graphNodeService.getModel().getEdge(source + "" + target);
        }
        return edge;
    }

    /**
     * Gets the graph view.
     *
     * @return the graph view
     */
    public ViewPanel getGraphView() {
        return graphView;
    }

    /**
     * Load the graph attributes fxml
     * 
     * @param node
     *            the node
     */
    private void loadFxml(Node node) {
        FXMLLoader loaderServices = new FXMLLoader();

        loaderServices.setLocation(Main.class.getResource("view/graph/GraphAttributes.fxml"));
        try {
            BorderPane root = loaderServices.load();

            Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().add("secondaryWindow");

            NodeAttributesController treeModifyController = loaderServices.getController();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modification d'attribut");

            Window window = graphNode.getScene().getWindow();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(window);
            Scene miniScene = new Scene(root);
            dialogStage.setScene(miniScene);
            dialogStage.initStyle(StageStyle.UNDECORATED);

            double x = window.getX() + (window.getWidth() - root.getPrefWidth()) / 2;
            double y = window.getY() + (window.getHeight() - root.getPrefHeight()) / 2;
            dialogStage.setX(x);
            dialogStage.setY(y);

            treeModifyController.init(node.getId());
            treeModifyController.setNode(node);
            treeModifyController.setStage(dialogStage);

            dialogStage.showAndWait();
        }
        catch (IOException e2) {
            LOGGER.log(Level.SEVERE, "The loading of the graph attributes fxml failed", e2);
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

        this.graphNodeService = GraphService.getInstance();
        this.graphNodeService.createAgentGraph();

        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        viewer = new Viewer(this.graphNodeService.getModel(), Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        this.graphView = viewer.addDefaultView(false);

        MouseMotionListener[] mml = graphView.getMouseMotionListeners();
        for (MouseMotionListener mouseMotionListener : mml) {
            graphView.removeMouseMotionListener(mouseMotionListener);
        }

        MouseListener[] ml = graphView.getMouseListeners();
        for (MouseListener mouseListener : ml) {
            graphView.removeMouseListener(mouseListener);
        }

        this.graphState = EStateGraph.AT_EASE;
        this.shortcutState = EShortcutState.AT_EASE;
        this.buttonsAddDelState = EButtonsAddDelState.AT_EASE;

        graphToolboxController.setGraphButtonsState(this);
        graphNodeService.setQualityGraph();
        graphNode.setContent(this.graphView);
    }
}
