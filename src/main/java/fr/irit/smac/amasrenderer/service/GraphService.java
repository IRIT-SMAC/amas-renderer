package fr.irit.smac.amasrenderer.service;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import com.fasterxml.jackson.core.JsonParser;

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
        this.model.addAttribute("ui.stylesheet", "url(" + getClass().getResource("../view/TheTrueStyleSheet.css") + ")");
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
     * Adds a node.
     *
     * @param id the id of the node
     */
    public void addNode(String id) {
        model.addNode(id);
        model.getNode(id).setAttribute("ui.stocked-info", new Stock());
        model.getNode(id).setAttribute("layout.weight", Const.LAYOUT_WEIGHT_NODE);
        model.getNode(id).setAttribute("ui.label", id);
    }
    
    /**
     * Add a directed edge from the source to the target
     * @param source
     * @param target
     */
    public void addEdge(String source, String target) {
        model.addEdge(source+target, source, target, true);
        model.getEdge(source+target).setAttribute("layout.weight", Const.LAYOUT_WEIGHT_EDGE);
    }
    
    /**
     * Add or update an attribute on a node
     * @param id the node id
     * @param attribute
     * @param value
     */
    public void setNodeAttribute(String id, String attribute, Object value){
        Node node = model.getNode(id);
        node.setAttribute(attribute, value);
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

	public void createAgentGraphFromJson() {
		// TODO Auto-generated method stub
		
	}

}
