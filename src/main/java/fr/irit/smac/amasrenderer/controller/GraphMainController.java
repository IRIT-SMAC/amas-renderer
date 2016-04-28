package fr.irit.smac.amasrenderer.controller;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.ResourceBundle;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.AgentGraph;
import fr.irit.smac.amasrenderer.model.Stock;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * The Class GraphMainController.
 */
public class GraphMainController implements Initializable {

    private AgentGraph model;
    
    private ViewPanel  graphView;
    
    @FXML
    private StackPane stackPaneGraphNode;
    
    @FXML
    private AnchorPane nodeEdit;
    
    @FXML
    private GraphNodeEditController nodeEditController;
    
    private GraphMouseWheelController graphMouseWheelController;
    
    private GraphDefaultMouseController defaultMouseController;

    private GraphAddDelEdgeMouseController graphAddDelEdgeMouseController;
    
    private GraphAddDelNodeMouseController graphAddDelNodeMouseController;
    
    /**
     * Instantiates a new graph main controller.
     */
    public GraphMainController() {
        this.model = new AgentGraph("AMAS Rendering");
        model.addAttribute("ui.stylesheet", "url(" + getClass().getResource("../view/styleSheet2.css") + ")");
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        Viewer viewer = new Viewer(this.model, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        this.graphView = viewer.addDefaultView(false);

        this.initGraph();
        this.initSubControllers();

        

        // attributes_synthesis.setText("No nodes are actually selected, click
        // on a node");

    }

    /**
     * Draws a graph of agents
     */
    public void drawGraph() {
        ((SwingNode) this.stackPaneGraphNode.lookup("#graphNode")).setContent(this.graphView);
    }



    /**
     * DEPRECATED
     * Adds an agent to the graph
     * 
     * Prefer using GraphAddDelNodeMouseController to add agents (alt + left click)
     */
    @FXML
    public void addAgent() {
        model.addNode("" + model.getNodeCount() + 1);
    }

    /**
     * DEPRECATED
     * Graph mouse clicked.
     * Calls 
     */
    @FXML
    public void graphMouseClicked() {
        // model.ajouterAgent();
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
    public AgentGraph getModel() {
        return model;
    }
    
    /**
     * Sets the model. (the AgentGraph)
     *
     * @param model the new model
     */
    public void setModel(AgentGraph model) {
        this.model = model;
    }

    /**
     * Initialize the graph.
     * Creates Const.NODE_INIT nodes with each Const.EDGE_INIT edge going from them to other nodes
     * For testing purposes 
     */
    private void initGraph() {
        model.addNode("0");
        for (Integer i = 1; i < Const.NODE_INIT; i++) {
            int firstNode = i;
            model.addNode("" + firstNode);
            if (i >= Const.EDGE_INIT) {
                for (int j = 0; j < Const.EDGE_INIT; j++) {
                    int secondNode = (int) Math.floor((Math.random() * i));
                    if (model.getEdge(firstNode + "" + secondNode) == null)
                        model.addEdge(firstNode + "" + secondNode, "" + firstNode, "" + secondNode, true);
                    else
                        j--;
                }
            }

        }
        // modify the layout 
        //sets edge lenght
        for (Edge edge : model.getEachEdge()) {
            edge.setAttribute("layout.weight", 20);
        }
        //sets the node repulsion
        for (Node node : model) {
            node.setAttribute("layout.weight", 300);
            // sets the Stock class to store agent info
            node.setAttribute("ui.stocked-info", new Stock());
        }
    }

    /**
     * Inits the sub controllers. (all Listeners)
     */
    private void initSubControllers() {
        MouseMotionListener[] mml = graphView.getMouseMotionListeners();
        for (MouseMotionListener mouseMotionListener : mml) {
            System.out.println(mouseMotionListener.toString());
            graphView.removeMouseMotionListener(mouseMotionListener);
        }

        MouseListener[] ml = graphView.getMouseListeners();
        for (MouseListener mouseListener : ml) {
            graphView.removeMouseListener(mouseListener);
        }

        graphMouseWheelController = new GraphMouseWheelController();
        graphMouseWheelController.init(graphView);

        //TODO update that controller to make it match the other functions
        //defaultMouseController = new GraphDefaultMouseController();
        //defaultMouseController.init(graphView, model);
        
        graphAddDelEdgeMouseController = new GraphAddDelEdgeMouseController();
        graphAddDelEdgeMouseController.init(graphView, model);
        
        graphAddDelNodeMouseController = new GraphAddDelNodeMouseController();
        graphAddDelNodeMouseController.init(graphView, model);

    }

    /* (non-Javadoc)
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //nodeEditController.init(graphView);
        //graphView.addMouseListener(nodeEditController);
    }
}
