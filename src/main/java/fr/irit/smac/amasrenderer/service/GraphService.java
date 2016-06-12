package fr.irit.smac.amasrenderer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.SingleGraph;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.AgentModel;
import javafx.beans.value.ObservableValue;

/**
 * The Class GraphService.
 */
public class GraphService {

    private SingleGraph graph;

    private static GraphService instance = new GraphService();

    private Map<String, Object> agentMap;

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

        this.graph = new SingleGraph("AMAS Rendering");
        this.graph.addAttribute("ui.stylesheet", "url(" + getClass().getResource("../css/graph.css") + ")");
        this.graph.setNodeFactory((id, g) -> new AgentModel((AbstractGraph) g, id));
        this.setQualityGraph();
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
    public void addNode(double x, double y) {

        String id = Integer.toString(agentMap.size());
        AgentModel node = this.graph.addNode(id);
        node.changeAttribute(Const.NODE_XY, x, y);
        node.setAttribute(Const.NODE_WEIGHT, Const.LAYOUT_WEIGHT_NODE);
        node.setAttribute(Const.NODE_LABEL, id);
        HashMap<String, Object> attributesMap = new HashMap<>();
        attributesMap.put(Const.ID, id);
        Map<String, Object> knowledge = new HashMap<>();
        knowledge.put(Const.CLASSNAME, Const.EXAMPLE_CLASSNAME);
        attributesMap.put(Const.KNOWLEDGE, knowledge);
        List<String> targets = new ArrayList<>();
        knowledge.put(Const.TARGETS, targets);
        node.setAttributesMap(attributesMap);

        node.nameProperty()
            .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                GraphService.getInstance().getAgentMap().remove(oldValue);
                GraphService.getInstance().getAgentMap().put(newValue, node.getAttributesMap());
            });
    }

    /**
     * Adds a node.
     *
     * @param id
     *            the id of the node
     */
    public void addNode(String id, Map<String, Object> attributesMap) {

        AgentModel node = this.graph.addNode(id);
        node.setAttribute(Const.NODE_WEIGHT, Const.LAYOUT_WEIGHT_NODE);
        node.setAttribute(Const.NODE_LABEL, id);
        node.setAttributesMap(attributesMap);

        node.nameProperty()
            .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                GraphService.getInstance().getAgentMap().remove(oldValue);
                GraphService.getInstance().getAgentMap().put(newValue, node.getAttributesMap());
            });
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

        graph.addEdge(source + target, source, target, true);
        graph.getEdge(source + target).setAttribute(Const.NODE_WEIGHT, Const.LAYOUT_WEIGHT_EDGE);
        ((AgentModel) this.graph.getNode(source)).addTarget(target);
    }

    public void addEdgeGraph(String source, String target) {
        graph.addEdge(source + target, source, target, true);
        graph.getEdge(source + target).setAttribute(Const.NODE_WEIGHT, Const.LAYOUT_WEIGHT_EDGE);
    }

    /**
     * Remove an edge
     * 
     * @param edge
     *            the edge to remove
     */
    public void removeEdge(Edge edge) {

        graph.removeEdge(edge);
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
                this.graph.removeEdge(edge.getId());
            }
        }
        this.graph.removeNode(n.getId());
        this.agentMap.remove(n.getId());
    }

    /**
     * Gets the model.
     *
     * @return the model
     */
    public SingleGraph getGraph() {
        return this.graph;
    }

    /**
     * Sets the model.
     *
     * @param graph
     *            the new model
     */
    public void setGraph(SingleGraph graph) {
        this.graph = graph;
    }

    /**
     * Creates the agent graph from a map
     * 
     * @param map
     *            the map
     */
    @SuppressWarnings("unchecked")
    public void createAgentGraphFromMap(Map<String, Object> map) {

        this.clearGraph();
        fillAgentMap(map);
        Iterator<Map.Entry<String, Object>> agents = map.entrySet().iterator();

        while (agents.hasNext()) {
            Map.Entry<String, Object> currentAgentMap = agents.next();
            HashMap<String, Object> currentAgent = (HashMap<String, Object>) currentAgentMap.getValue();
            fillAgentTargets(currentAgent);
        }
    }

    /**
     * Creates as many nodes as agents in the map
     * 
     * @param map
     *            the agent map
     */
    private void fillAgentMap(Map<String, Object> map) {

        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> agent = it.next();
            this.addNode(agent.getKey(), (Map<String, Object>) agent.getValue());
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
        Iterator<String> it = targets.iterator();
        while (it.hasNext()) {
            String target = it.next();
            this.addEdgeGraph((String) agent.get("id"), target);
        }
    }

    /**
     * Empty the graph and reset the stylesheet
     */
    public void clearGraph() {

        this.agentMap.clear();
        this.graph.clear();
        this.graph.addAttribute("ui.stylesheet", "url(" + getClass().getResource("../css/graph.css") + ")");
        this.setQualityGraph();
    }

    /**
     * Sets the quality of rendering of the graph
     */
    public void setQualityGraph() {

        this.graph.addAttribute("ui.quality");
        this.graph.addAttribute("layout.quality", 4);
        this.graph.addAttribute("ui.antialias");
    }

    public Map<String, Object> getAgentMap() {
        return agentMap;
    }

    public void setAgentMap(Map<String, Object> agentMap) {
        this.agentMap = agentMap;
    }
}
