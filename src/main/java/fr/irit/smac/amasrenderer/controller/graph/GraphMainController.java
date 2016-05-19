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
import fr.irit.smac.amasrenderer.model.AgentGraphModel;
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
 * The Class GraphMainController.
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

    private static final Logger LOGGER = Logger.getLogger(GraphMainController.class.getName());

    private GraphService graphNodeService = GraphService.getInstance();

    private ViewPanel graphView;

    private Viewer viewer;

    private int currentNodeId;

    public static EStateGraph state = EStateGraph.AT_EASE;

    private Node source = null;

    private Node target = null;

    private EStateGraph previousState;

    protected GraphicElement curElement;

    protected float x1;

    protected float y1;

    @FXML
    public void handleOnMouseReleased(MouseEvent e) {

        switch (GraphMainController.state) {

            case SELECTED_NODE:
                state = EStateGraph.AT_EASE;
                mouseButtonReleaseOffElement(curElement, e);
                break;
            default:
                break;
        }
    }

    @FXML
    public void handleOnMouseDragged(MouseEvent e) {
        switch (GraphMainController.state) {

            case SELECTED_NODE:
                state = EStateGraph.SELECTED_NODE;
                elementMoving(curElement, e);
                break;
            default:
                break;
        }
    }

    @FXML
    public void handleOnScroll(ScrollEvent e) {

        switch (GraphMainController.state) {

            default:
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
                break;
        }

    }

    @FXML
    public void handleOnMousePressed(MouseEvent e) {
        switch (GraphMainController.state) {

            case AT_EASE:
                if (e.isSecondaryButtonDown()) {
                    loadAttributes(e);
                }
                else if (e.isPrimaryButtonDown()) {
                    state = EStateGraph.SELECTED_NODE;
                    mouseButtonPress(e);
                }
                break;
            case CTRL_DOWN:
                if (e.isPrimaryButtonDown()) {
                    createNode(e);
                }
                else if (e.isSecondaryButtonDown()) {
                    Node node = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
                    this.graphNodeService.removeNode(node);
                }
                GraphMainController.state = EStateGraph.CTRL_DOWN;
                break;

            case BUTTON_ADD_NODE:
                createNode(e);
                GraphMainController.state = EStateGraph.BUTTON_ADD_NODE;
                break;

            case BUTTON_DELETE_NODE:
                Node n = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
                this.graphNodeService.removeNode(n);
                GraphMainController.state = EStateGraph.BUTTON_DELETE_NODE;
                break;

            case SHIFT_DOWN:
                source = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
                if (source != null) {
                    previousState = EStateGraph.SHIFT_DOWN;
                    if (e.isPrimaryButtonDown()) {
                        GraphMainController.state = EStateGraph.READY_TO_ADD;

                    }
                    else if (e.isSecondaryButtonDown()) {
                        GraphMainController.state = EStateGraph.READY_TO_DELETE;

                    }
                    selectSource();
                }
                break;

            case BUTTON_ADD_EDGE:
                source = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
                if (source != null) {
                    previousState = EStateGraph.BUTTON_ADD_EDGE;
                    GraphMainController.state = EStateGraph.READY_TO_ADD;
                    selectSource();
                }
                break;

            case BUTTON_DELETE_EDGE:
                source = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
                if (source != null) {
                    previousState = EStateGraph.BUTTON_DELETE_EDGE;
                    GraphMainController.state = EStateGraph.READY_TO_DELETE;
                    selectSource();
                }
                break;

            case READY_TO_ADD:
                GraphMainController.state = previousState;
                addEdge(e);
                unselectSource();
                break;

            case READY_TO_DELETE:
                GraphMainController.state = previousState;
                removeEdge(e);
                unselectSource();
                break;

            default:
                break;

        }
    }

    /**
     * Method to unselect all nodes when the Mouse button is pressed.
     *
     * @param event
     *            the event
     */
    protected void mouseButtonPress(MouseEvent e) {

        curElement = graphView.findNodeOrSpriteAt(e.getX(), e.getY());
        if (curElement != null) {

            graphView.requestFocus();

            for (Node node : GraphService.getInstance().getModel()) {
                node.addAttribute("ui.selected");
                // nonsense line, but don't always work without it
                node.removeAttribute("ui.selected");
                node.addAttribute("ui.clicked");
                // nonsense line, but don't always work without it
                node.removeAttribute("ui.clicked");
            }

            graphView.freezeElement(curElement, true);
            if (e.isSecondaryButtonDown()) {
                curElement.addAttribute("ui.selected");
            }
            else {
                curElement.addAttribute("ui.clicked");
            }
        }
    }

    private void loadAttributes(MouseEvent e) {
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
    protected void mouseButtonReleaseOffElement(GraphicElement element, MouseEvent event) {
        graphView.freezeElement(element, false);
        if (event.isSecondaryButtonDown()) {
            element.removeAttribute("ui.clicked");
        }
        curElement = null;
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
    protected void elementMoving(GraphicElement element, MouseEvent event) {
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
     * Select the source node
     */
    public void selectSource() {

        if (source != null && !source.hasAttribute("ui.selected")) {
            source.addAttribute("ui.selected");
        }
        target = null;
    }

    /**
     * Add an edge between the source and the target
     * 
     * @param e
     *            the mouse event
     */
    public void addEdge(MouseEvent e) {

        if (getEdge(e) == null && target != null) {
            this.graphNodeService.addEdge(source.getId(), target.getId());
        }
    }

    /**
     * Remove the edge between the source and the target
     * 
     * @param e
     *            the mouse event
     */
    public void removeEdge(MouseEvent e) {

        this.graphNodeService.removeEdge(getEdge(e));
    }

    /**
     * Get the edge between the source and the target
     * 
     * @param e
     *            the event
     * @return
     */
    private Edge getEdge(MouseEvent e) {

        target = (Node) graphView.findNodeOrSpriteAt(e.getX(), e.getY());
        if (target != null) {
            Edge edge = this.graphNodeService.getModel().getEdge(source + "" + target);
            return edge;
        }
        return null;
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
     * Gets the viewer
     * 
     * @return the viewer
     */
    public Viewer getViewer() {
        return this.viewer;
    }

    /**
     * Draw the graph inside a swing node
     */
    public void drawGraph() {
        graphNode.setContent(this.graphView);
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
     * Gets the model. (the AgentGraph)
     *
     * @return the model
     */
    public AgentGraphModel getModel() {
        return this.graphNodeService.getModel();
    }

    /**
     * Load the graph attributes fxml
     * 
     * @param node
     *            the node
     */
    public void loadFxml(Node node) {
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
     * Initialize the graph. Creates Const.NODE_INIT nodes with each
     * Const.EDGE_INIT edge going from them to other nodes For testing purposes
     */
    private void initGraph() {
        getModel().addAttribute("ui.quality");
        getModel().addAttribute("layout.quality", 4);
        getModel().addAttribute("ui.antialias");
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

        this.initGraph();

        this.drawGraph();
    }

}
