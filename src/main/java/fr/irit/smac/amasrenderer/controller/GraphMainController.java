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
import fr.irit.smac.amasrenderer.service.GraphService;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * The Class GraphMainController.
 */
public class GraphMainController implements Initializable {

    /** The graph node service. */
    private GraphService graphNodeService = GraphService.getInstance();
    
    /** The graph view. */
    private ViewPanel  graphView;
    
    /** The toggroup. */
    @FXML
    ToggleGroup toggroup;
    
    /** The button add agent. */
    @FXML
    private ToggleButton buttonAddAgent;
    
    /** The button del agent. */
    @FXML
    private ToggleButton buttonDelAgent;
    
    /** The button add edge. */
    @FXML
    private ToggleButton buttonAddEdge;
    
    /** The button del edge. */
    @FXML
    private ToggleButton buttonDelEdge;
    
    /** The stack pane graph node. */
    @FXML
    private StackPane stackPaneGraphNode;
    
    /** The node edit. */
    @FXML
    private AnchorPane nodeEdit;
    
    /** The node edit controller. */
    @FXML
    private GraphNodeEditController nodeEditController;
    
    /** The graph mouse wheel controller. */
    private GraphMouseWheelController graphMouseWheelController;
    
    /** The default mouse controller. */
    private GraphDefaultMouseController defaultMouseController;

    /** The graph add del edge mouse controller. */
    protected GraphAddDelEdgeMouseController graphAddDelEdgeMouseController;
    
    /** The graph add del node mouse controller. */
    protected GraphAddDelNodeMouseController graphAddDelNodeMouseController;
    
    /**
     * Instantiates a new graph main controller.
     */
    public GraphMainController() {
        graphNodeService.createAgentGraph();
        getModel().addAttribute("ui.stylesheet", "url(" + getClass().getResource("../view/styleSheet1.css") + ")");
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        Viewer viewer = new Viewer(getModel(), Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        this.graphView = viewer.addDefaultView(false);
        
        this.initGraph();
        this.initSubControllers();
    }

    /**
     * Draws he graph in the container.
     */
    public void drawGraph() {
        ((SwingNode) this.stackPaneGraphNode.lookup("#graphNode")).setContent(this.graphView);
    }



    /**
     * DEPRECATED
     * Adds an agent to the graph
     * 
     * Prefer using GraphAddDelNodeMouseController to add agents (alt + left click).
     */
    @FXML
    public void addAgent() {
        this.graphNodeService.getModel().addNode("" + this.graphNodeService.getModel().getNodeCount() + 1);
    }

    /**
     * function controlling the effect of the buttons ( on press ) depending on which button is pressed.
     */
    @FXML
    public void clickButton(){
        toggleButton(toggroup);
    }
    
    /**
     * sets the boolean in the controllers depending on the state of the button group
     *
     * @param toggroup the toggroup
     */
    private void toggleButton(ToggleGroup toggroup){
        ToggleButton curButton = (ToggleButton)toggroup.getSelectedToggle();
        graphAddDelNodeMouseController.setButtonAddAgent(false);
        graphAddDelNodeMouseController.setButtonDelAgent(false);
        graphAddDelEdgeMouseController.setButtonAddEdge(false);
        graphAddDelEdgeMouseController.setButtonDelEdge(false);
        if(curButton != null){
            switch(curButton.getId()){
                case "buttonAddAgent":
                    graphAddDelNodeMouseController.setButtonAddAgent(true);
                    break;
                case "buttonDelAgent":
                    graphAddDelNodeMouseController.setButtonDelAgent(true);
                    break;
                case "buttonAddEdge":
                    graphAddDelEdgeMouseController.setButtonAddEdge(true);
                    break;
                case "buttonDelEdge":
                    graphAddDelEdgeMouseController.setButtonDelEdge(true);
                    break;
                default:
                    //Do Nothing
                    break;
            }
        }
    }
    
    /**
     * DEPRECATED
     * Graph mouse clicked.
     *  
     */
    @FXML
    public void graphMouseClicked() {
        //TODO delete this function
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
        return this.graphNodeService.getModel();
    }

    
    
    
    /**
     * Initialize the graph.
     * Creates Const.NODE_INIT nodes with each Const.EDGE_INIT edge going from them to other nodes
     * For testing purposes 
     */
    private void initGraph() {

        getModel().addAttribute("ui.quality");
        getModel().addAttribute("layout.quality",4);
        getModel().addAttribute("ui.antialias");
        AgentGraph model = this.graphNodeService.getModel();
        model.addNode("0");
        model.getNode("0").setAttribute("ui.label", "Ag0");
        for (Integer i = 1; i < Const.NODE_INIT; i++) {
            int firstNode = i;
            model.addNode("" + firstNode);
            model.getNode(""+firstNode).setAttribute("ui.label", "Ag"+firstNode );
            int j = 0;
            while(i >= Const.EDGE_INIT && j < Const.EDGE_INIT){
                int secondNode = (int) Math.floor(Math.random() * i);
                if (model.getEdge(firstNode + "" + secondNode) == null){
                    model.addEdge(firstNode + "" + secondNode, "" + firstNode, "" + secondNode, true);
                    model.getEdge(firstNode + "" + secondNode).setAttribute("layout.weight", Const.LAYOUT_WEIGHT_EDGE);
                    j++;
                }
            }

        }
        // modify the layout 
        //sets edge lenght
        for (Edge edge : model.getEachEdge()) {
            edge.setAttribute("layout.weight", Const.LAYOUT_WEIGHT_EDGE);
        }
        //sets the node repulsion
        for (Node node : model) {
            node.setAttribute("layout.weight", Const.LAYOUT_WEIGHT_NODE);
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
            graphView.removeMouseMotionListener(mouseMotionListener);
        }

        MouseListener[] ml = graphView.getMouseListeners();
        for (MouseListener mouseListener : ml) {
            graphView.removeMouseListener(mouseListener);
        }

        graphMouseWheelController = new GraphMouseWheelController();
        graphMouseWheelController.init(graphView);

        defaultMouseController = new GraphDefaultMouseController();
        defaultMouseController.init(graphView, getModel());
        
        graphAddDelEdgeMouseController = new GraphAddDelEdgeMouseController();
        graphAddDelEdgeMouseController.init(graphView, this.graphNodeService);
        
        graphAddDelNodeMouseController = new GraphAddDelNodeMouseController();
        graphAddDelNodeMouseController.init(graphView, this.graphNodeService);

    }

    /* (non-Javadoc)
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.graphNodeService = GraphService.getInstance();
        this.graphNodeService.createAgentGraph();

        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        Viewer viewer = new Viewer(this.graphNodeService.getModel(), Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        this.graphView = viewer.addDefaultView(false);

        this.initGraph();
        this.initSubControllers();
    }
}
