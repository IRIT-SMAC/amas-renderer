package fr.irit.smac.amasrenderer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.AgentGraphModel;
import fr.irit.smac.amasrenderer.model.StockModel;
import javafx.scene.control.TreeItem;

/**
 * The Class GraphService. controls and detains the model ( kinda )
 */
public class GraphService {

    /** The model. */
    private AgentGraphModel model;

    /** The instance. */
    private static GraphService instance = new GraphService();

    /**
     * Instantiates a new graph service. to hide the constructor, use
     * getInstance to initiate it
     */
    private GraphService() {

    }

    /**
     * Gets the single instance of GraphService. use that to initiate your
     * object
     * 
     * @return single instance of GraphService
     */
    public static GraphService getInstance() {
        return instance;
    }

    /**
     * Creates and initialise the agent graph.
     */
    public void createAgentGraph() {
        this.model = new AgentGraphModel("AMAS Rendering");
        this.model.addAttribute("ui.stylesheet", "url(" + getClass().getResource("../css/graph.css") + ")");
    }

    /**
     * Adds a node.
     *
     * @param id
     *            the id of the node
     * @param x
     *            the x location of the node
     * @param y
     *            the y location of the node
     */
    public void addNode(String id, double x, double y) {

        model.addNode(id);
        Node node = model.getNode(id);
        node.changeAttribute(Const.NODE_XY, x, y);
        node.setAttribute(Const.NODE_CONTENT, new StockModel(id));
        node.setAttribute(Const.NODE_WEIGHT, Const.LAYOUT_WEIGHT_NODE);
        node.setAttribute(Const.NODE_LABEL, id);

    }

    /**
     * Adds a node.
     *
     * @param id
     *            the id of the node
     */
    public void addNode(String id) {

        model.addNode(id);
        Node node = model.getNode(id);
        node.setAttribute(Const.NODE_CONTENT, new StockModel(id));
        node.setAttribute(Const.NODE_WEIGHT, Const.LAYOUT_WEIGHT_NODE);
        node.setAttribute(Const.NODE_LABEL, id);
    }

    /**
     * Add a directed edge from the source to the target
     * 
     * @param source
     *            the id of the source node
     * @param target
     *            the id of the target node
     */
    public void addEdge(String source, String target) {

        model.addEdge(source + target, source, target, true);
        model.getEdge(source + target).setAttribute(Const.NODE_WEIGHT, Const.LAYOUT_WEIGHT_EDGE);
    }

    /**
     * Remove an edge
     * 
     * @param edge
     *            the edge to remove
     */
    public void removeEdge(Edge edge) {

        model.removeEdge(edge);
    }

    /**
     * Add or update an attribute on a node
     * 
     * @param id
     *            the node id
     * @param attribute
     *            the attribute
     * @param value
     *            the value
     * @param parent
     *            the parent node
     */
    public void setNodeAttribute(String attribute, Object value, TreeItem<String> parent) {

        TreeItem<String> item = new TreeItem<>();
        item.setValue(attribute + " : " + value);
        parent.getChildren().add(item);
    }

    /**
     * Removes a node.
     *
     * @param n
     *            the node
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
    public AgentGraphModel getModel() {
        return this.model;
    }

    /**
     * Sets the model.
     *
     * @param model
     *            the new model
     */
    public void setModel(AgentGraphModel model) {
        this.model = model;
    }

    /**
     * Create the agent graph from a map
     * 
     * @param map
     *            the map
     */
    @SuppressWarnings("unchecked")
    public void createAgentGraphFromMap(Map<?, ?> map) {
        HashMap<String, Object> agentHandlerService = (HashMap<String, Object>) map.get("agentHandlerService");
        HashMap<String, Object> agentMap = (HashMap<String, Object>) agentHandlerService.get("agentMap");
        this.clearGraph();
        fillAgentMap(agentMap);
        Iterator<Map.Entry<String, Object>> agents = agentMap.entrySet().iterator();
        while (agents.hasNext()) {
            Map.Entry<String, Object> currentAgentMap = agents.next();
            HashMap<String, Object> currentAgent = (HashMap<String, Object>) currentAgentMap.getValue();
            fillAgentTargets(currentAgent);
            String id = (String) currentAgent.get("id");
            StockModel stock = model.getNode(id).getAttribute(Const.NODE_CONTENT);
            stock.getRoot().setValue(id);
            fillAgentAttributes(currentAgent, stock.getRoot());
        }

    }

    /**
     * Create as many nodes as agents in the map
     * 
     * @param map
     *            the agent map
     */
    private void fillAgentMap(HashMap<String, Object> map) {
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> agent = it.next();
            this.addNode(agent.getKey());
        }
    }

    /**
     * Create all attributes for the agent in parameter
     * 
     * @param agent
     *            the agent to explore
     */
    @SuppressWarnings("unchecked")
    private void fillAgentAttributes(HashMap<String, Object> agent, TreeItem<String> parent) {
        Iterator<Map.Entry<String, Object>> attributeIterator = agent.entrySet().iterator();
        while (attributeIterator.hasNext()) {
            Map.Entry<String, Object> attribute = attributeIterator.next();
            String name = attribute.getKey();
            Object value = attribute.getValue();

            String id = (String) agent.get("id");
            if (value instanceof HashMap<?, ?>) {
                TreeItem<String> item = new TreeItem<>();
                item.setValue(name);
                StockModel stock = model.getNode(id).getAttribute(Const.NODE_CONTENT);
                stock.getRoot().getChildren().add(item);
                fillAgentAttributes((HashMap<String, Object>) value, item);
            }
            else {
                this.setNodeAttribute(name, value, parent);

            }
        }
    }

    /**
     * Create all outgoing edges of the agent in parameter
     * 
     * @param agent
     *            the agent to explore
     */
    @SuppressWarnings("unchecked")
    private void fillAgentTargets(HashMap<String, Object> agent) {
        HashMap<String, Object> knowledgeMap = (HashMap<String, Object>) agent.get("knowledge");
        ArrayList<String> targets = (ArrayList<String>) knowledgeMap.get("targets");
        for (String target : targets) {
            this.addEdge((String) agent.get("id"), target);
        }
    }

    /**
     * Empty the graph and reset the stylesheet
     */
    private void clearGraph() {
        this.getModel().clear();
        this.getModel().addAttribute("ui.stylesheet",
            "url(" + getClass().getResource("../css/graph.css") + ")");
    }

    /**
     * Sets the quality of rendering of the graph
     */
    public void setQualityGraph() {

        this.getModel().addAttribute("ui.quality");
        this.getModel().addAttribute("layout.quality", 4);
        this.getModel().addAttribute("ui.antialias");
    }

}
