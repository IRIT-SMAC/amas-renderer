package fr.irit.smac.amasrenderer.service;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.AgentGraph;
import fr.irit.smac.amasrenderer.model.Stock;

/**
 * The Class GraphService.
 * controls and detains the model ( kinda )
 */
public class GraphService {

    /** The model. */
    private AgentGraph          model;
    
    /** The instance. */
    private static GraphService instance = new GraphService();

    /**
     * Instantiates a new graph service.
     * to hide the constructor, use getInstance to initiate it
     */
    private GraphService() {
        
    }

    /**
     * Gets the single instance of GraphService.
     * use that to initiate your object
     * @return single instance of GraphService
     */
    public static GraphService getInstance() {
        return instance;
    }

    /**
     * Creates and initialise the agent graph.
     */
    public void createAgentGraph() {

        this.model = new AgentGraph("AMAS Rendering");
        this.model.addAttribute("ui.stylesheet", "url(" + getClass().getResource("../css/theTrueStyleSheet.css") + ")");
    }

    /**
     * Adds a node.
     *
     * @param id the id of the node
     * @param x the x location of the node
     * @param y the y location of the node
     */
    public void addNode(String id, double x, double y) {

        model.addNode(id);
        model.getNode(id).changeAttribute("xyz", x, y);
        model.getNode(id).setAttribute("ui.stocked-info", new Stock());
        model.getNode(id).setAttribute("layout.weight", Const.LAYOUT_WEIGHT_NODE);
    }

    /**
     * Removes a node.
     *
     * @param n the node
     */
    public void removeNode(Node n) {

        Iterable<Edge> edges = n.getEachEdge();
        if (edges != null) {
            for (Edge edge : edges) {
                model.removeEdge(edge.getId());
            }
        }
        model.removeNode(n.getId());
    }

    /**
     * Gets the model.
     *
     * @return the model
     */
    public AgentGraph getModel() {

        return this.model;
    }
    
    /**
     * Sets the model.
     *
     * @param model the new model
     */
    public void setModel(AgentGraph model) {
        this.model = model;
    }
}
