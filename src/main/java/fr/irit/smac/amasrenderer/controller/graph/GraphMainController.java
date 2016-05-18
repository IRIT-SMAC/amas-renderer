package fr.irit.smac.amasrenderer.controller.graph;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.ResourceBundle;

import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import fr.irit.smac.amasrenderer.model.AgentGraphModel;
import fr.irit.smac.amasrenderer.service.GraphService;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * The Class GraphMainController.
 */
public class GraphMainController implements Initializable {

    private GraphService graphNodeService = GraphService.getInstance();

    private ViewPanel graphView;

    private Viewer viewer;

    @FXML
    private StackPane stackPaneGraphNode;

    @FXML
    private AnchorPane nodeEdit;

    @FXML
    private GraphNodeEditController nodeEditController;

    @FXML
    private GraphAddDelController graphAddDelController;

    private GraphMouseWheelController graphMouseWheelController;

    private GraphDefaultMouseController defaultMouseController;

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
        ((SwingNode) this.stackPaneGraphNode.lookup("#graphNode")).setContent(this.graphView);
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
     * Initialize the graph. Creates Const.NODE_INIT nodes with each
     * Const.EDGE_INIT edge going from them to other nodes For testing purposes
     */
    private void initGraph() {
        getModel().addAttribute("ui.quality");
        getModel().addAttribute("layout.quality", 4);
        getModel().addAttribute("ui.antialias");
    }

    /**
     * Inits the sub controllers. (all Listeners)
     */
    public void initSubControllers() {
        MouseMotionListener[] mml = graphView.getMouseMotionListeners();
        for (MouseMotionListener mouseMotionListener : mml) {
            graphView.removeMouseMotionListener(mouseMotionListener);
        }

        MouseListener[] ml = graphView.getMouseListeners();
        for (MouseListener mouseListener : ml) {
            graphView.removeMouseListener(mouseListener);
        }

        graphMouseWheelController = new GraphMouseWheelController();
        graphMouseWheelController.init(graphView);

        graphAddDelController.init(graphView, graphNodeService);

        defaultMouseController = new GraphDefaultMouseController();
        defaultMouseController.init(graphView, getModel(), graphAddDelController.getTogGroup());

        nodeEditController = new GraphNodeEditController();
        nodeEditController.init(graphView, stackPaneGraphNode.getScene().getWindow());

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

        this.initGraph();

        graphAddDelController.init(graphView, graphNodeService);

        SwingNode swingNode = new SwingNode();
        swingNode.setId("graphNode");
        stackPaneGraphNode.getChildren().add(swingNode);
        this.drawGraph();
    }

}
