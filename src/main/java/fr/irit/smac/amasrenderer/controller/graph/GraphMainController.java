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
import fr.irit.smac.amasrenderer.controller.attribute.TreeModifyController;
import fr.irit.smac.amasrenderer.service.GraphService;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
public class GraphMainController implements Initializable {

    @FXML
    private StackPane stackPaneGraphNode;

    @FXML
    private AnchorPane nodeEdit;

    @FXML
    private SwingNode graphNode;

    @FXML
    private GraphAddDelController graphAddDelController;

    private GraphService graphNodeService = GraphService.getInstance();

    private ViewPanel graphView;

    private Viewer viewer;

    private int currentNodeId;

    private Node source = null;

    private Node target = null;

    private EStateGraph previousState;

    private GraphicElement selectedElement;

    public static EStateGraph state = EStateGraph.AT_EASE;

    private static final Logger LOGGER = Logger.getLogger(GraphMainController.class.getName());

    @FXML
    public void handleOnMouseReleased(MouseEvent e) {

        if (state == EStateGraph.SELECTED_NODE) {
            state = EStateGraph.AT_EASE;
            unselectNode(selectedElement);
        }
    }

    @FXML
    public void handleOnMouseDragged(MouseEvent e) {

        if (state == EStateGraph.SELECTED_NODE) {
            state = EStateGraph.SELECTED_NODE;
            moveSelectedNode(selectedElement, e);
        }
    }

    @FXML
    public void handleOnScroll(ScrollEvent e) {

        zoomOrUnzoom(e);
    }

    @FXML
    public void handleOnMousePressed(MouseEvent e) {
        switch (state) {

            case AT_EASE:
                handleAttributesOrSelectNode(e);
                break;
            case CTRL_DOWN:
                state = EStateGraph.CTRL_DOWN;
                createOrRemoveNode(e);
                break;

            case BUTTON_ADD_NODE:
                state = EStateGraph.BUTTON_ADD_NODE;
                createNode(e);
                break;

            case BUTTON_DELETE_NODE:
                state = EStateGraph.BUTTON_DELETE_NODE;
                removeNode(e);
                break;

            case SHIFT_DOWN:
                readyToAddOrDeleteEdgeShift(e);
                break;

            case BUTTON_ADD_EDGE:
                readyToAddOrDeleteEdge(e, EStateGraph.BUTTON_ADD_EDGE, EStateGraph.READY_TO_ADD);
                break;

            case BUTTON_DELETE_EDGE:
                readyToAddOrDeleteEdge(e, EStateGraph.BUTTON_DELETE_EDGE, EStateGraph.READY_TO_DELETE);
                break;

            case READY_TO_ADD:
                state = previousState;
                addEdge(e);
                break;

            case READY_TO_DELETE:
                state = previousState;
                removeEdge(e);
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

        previousState = EStateGraph.SHIFT_DOWN;
        if (e.isPrimaryButtonDown()) {
            readyToAddOrDeleteEdge(e, previousState, EStateGraph.READY_TO_ADD);
        }
        else if (e.isSecondaryButtonDown()) {
            readyToAddOrDeleteEdge(e, previousState, EStateGraph.READY_TO_DELETE);

        }
    }

    /**
     * Set the state to ready to add or delete an edge depending on the
     * arguments
     * 
     * @param e
     *            the event
     * @param previousState
     *            the previous state that has to be set after the next state is
     *            done
     * @param nextState
     *            the next state
     */
    private void readyToAddOrDeleteEdge(MouseEvent e, EStateGraph previousState, EStateGraph nextState) {

        source = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
        if (source != null) {
            this.previousState = previousState;
            state = nextState;
            selectSource();
        }
    }

    /**
     * Create or remove a node depending on the mouse button down
     * 
     * @param e
     */
    private void createOrRemoveNode(MouseEvent e) {

        if (e.isPrimaryButtonDown()) {
            createNode(e);
        }
        else if (e.isSecondaryButtonDown()) {
            removeNode(e);
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
            graphView.getCamera().setViewPercent(scale * Const.SCALE_ZOOM_RATIO);
        }
        else {
            graphView.getCamera().setViewPercent(scale * Const.SCALE_UNZOOM_RATIO);
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
            state = EStateGraph.AT_EASE;
            handleAttributes(e);
        }
        else if (e.isPrimaryButtonDown()) {
            state = EStateGraph.SELECTED_NODE;
            selectNode(e);
        }
    }

    /**
     * Method to unselect all nodes when the Mouse button is pressed.
     *
     * @param event
     *            the event
     */
    private void selectNode(MouseEvent e) {

        selectedElement = graphView.findNodeOrSpriteAt(e.getX(), e.getY());
        if (selectedElement != null) {
            graphView.requestFocus();
            for (Node node : GraphService.getInstance().getModel()) {
                node.addAttribute("ui.clicked");
                node.removeAttribute("ui.clicked");
            }
            graphView.freezeElement(selectedElement, true);
            selectedElement.addAttribute("ui.clicked");
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
        this.graphNodeService.removeNode(node);
    }

    /**
     * Select the source node
     */
    private void selectSource() {

        if (source != null && !source.hasAttribute("ui.selected")) {
            source.addAttribute("ui.selected");
        }
        target = null;
    }

    /**
     * Unselect a source when an edge is created, deleted, or when the target is
     * not a node
     */
    private void unselectSource() {

        source.removeAttribute("ui.selected");
        source = null;
    }

    /**
     * Add an edge between the source and the target
     * 
     * @param e
     *            the mouse event
     */
    private void addEdge(MouseEvent e) {

        if (getEdge(e) == null && target != null) {
            this.graphNodeService.addEdge(source.getId(), target.getId());
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
     * @return
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
     * Load the graph attributes fxml
     * 
     * @param node
     *            the node
     */
    private void loadFxml(Node node) {
        FXMLLoader loaderServices = new FXMLLoader();

        loaderServices.setLocation(Main.class.getResource("view/GraphAttributes.fxml"));
        try {
            BorderPane root = loaderServices.load();

            TreeModifyController treeModifyController = loaderServices.getController();

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

            treeModifyController.setStage(dialogStage);
            treeModifyController.setStock(node.getAttribute(Const.NODE_CONTENT));
            treeModifyController.setNode(node);

            dialogStage.showAndWait();
        }
        catch (IOException e2) {
            LOGGER.log(Level.SEVERE, "The loading of the graph attributes fxml failed", e2);
        }
    }

    /**
     * Gets the graph view.
     *
     * @return the graph view
     */
    public ViewPanel getGraphView() {
        return graphView;
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

        graphNodeService.setQualityGraph();
        graphNode.setContent(this.graphView);
    }

}
