package fr.irit.smac.amasrenderer.controller;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.ResourceBundle;

import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.AgentGraph;
import fr.irit.smac.amasrenderer.model.Stock;
import fr.irit.smac.amasrenderer.service.GraphService;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The Class GraphMainController.
 */
public class GraphMainController implements Initializable {

    private GraphService graphNodeService = GraphService.getInstance();

    private ViewPanel graphView;

    private Viewer viewer;

    private Stage dialogStage;
    
   
    
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

    private TreeModifyController treeModifyController;

    /**
     * Instantiates a new graph main controller.
     */
    public GraphMainController() {
        
    }

    public Viewer getViewer() {
    	return this.viewer;
    }
    
    /**
     * Draws he graph in the container.
     */
    public void drawGraph() {
        ((SwingNode) this.stackPaneGraphNode.lookup("#graphNode")).setContent(this.graphView);
    }

    /**
     * DEPRECATED Adds an agent to the graph
     * 
     * Prefer using GraphAddDelNodeMouseController to add agents (alt + left click).
     */
    @FXML
    public void addAgent() {
        this.graphNodeService.getModel().addNode("" + this.graphNodeService.getModel().getNodeCount() + 1);
    }
    
    public void setDialogStage(Stage dialogStage){
        System.out.println("test"+dialogStage);
        this.dialogStage = dialogStage;
    }
    
    public void setTreeModifyController(TreeModifyController treeModifyController) {
        this.treeModifyController = treeModifyController;
    }
    
    
    /**
     * DEPRECATED
     * Graph mouse clicked.
     * DEPRECATED Graph mouse clicked. Calls
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
     * Initialize the graph. Creates Const.NODE_INIT nodes with each
     * Const.EDGE_INIT edge going from them to other nodes For testing purposes
     */
    private void initGraph() {
        getModel().addAttribute("ui.quality");
        getModel().addAttribute("layout.quality",4);
        getModel().addAttribute("ui.antialias");
        AgentGraph model = this.graphNodeService.getModel();
        /*model.addNode("0");
        model.getNode("0").setAttribute("ui.label", "Ag0");*/
        for (Integer i = 0; i < Const.NODE_INIT; i++) {
            int firstNode = i;
            model.addNode("" + firstNode);
            model.getNode(""+firstNode).setAttribute("ui.label", "Ag"+firstNode );
            model.getNode(""+firstNode).setAttribute("ui.stocked-info", new Stock());
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

        defaultMouseController = new GraphDefaultMouseController();
        defaultMouseController.init(graphView, getModel());

        nodeEditController = new GraphNodeEditController();
        nodeEditController.init(graphView, dialogStage, treeModifyController);

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
        Viewer viewer = new Viewer(this.graphNodeService.getModel(), Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        this.graphView = viewer.addDefaultView(false);

        this.initGraph();

        this.initSubControllers();

        graphAddDelController.init(graphView, graphNodeService);

        // nodeEditController.init(graphView);
        // graphView.addMouseListener(nodeEditController);
    }

}
