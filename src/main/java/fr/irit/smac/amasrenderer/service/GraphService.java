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
 * This service is related to the business logic about the graph of agents
 */
public class GraphService {

    private SingleGraph graph;

    private static GraphService instance = new GraphService();

    private Map<String, Object> agentMap;

    private GraphService() {
    }

    /**
     * Gets the single instance of GraphService.
     * 
     * @return single instance of GraphService
     */
    public static GraphService getInstance() {
        return instance;
    }

    /**
     * Creates and initializes the agent graph.
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
        this.agentMap.put(id, attributesMap);

        node.nameProperty()
            .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                GraphService.getInstance().getAgentMap().remove(oldValue);
                GraphService.getInstance().getAgentMap().put(newValue, node.getAttributesMap());
            });
    }

    /**
     * Adds a node with an existing attributes map.
     *
     * @param id
     *            the id of the node
     * @param attributesMap
     *            the attributes
     */
    public void addNode(String id, Map<String, Object> attributesMap) {

        AgentModel node = this.graph.addNode(id);
        node.setAttribute(Const.NODE_WEIGHT, Const.LAYOUT_WEIGHT_NODE);
        node.setAttribute(Const.NODE_LABEL, id);
        node.setAttributesMap(attributesMap);
        this.agentMap.put(id, attributesMap);

        node.nameProperty()
            .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                GraphService.getInstance().getAgentMap().remove(oldValue);
                GraphService.getInstance().getAgentMap().put(newValue, node.getAttributesMap());
            });
    }

    /**
     * Adds a directed edge from the source to the target
     * 
     * @param source
     *            the id of the source node
     * @param target
     *            the id of the target node
     */
    public void addEdge(String source, String target) {

        addEdgeGraph(source, target);
        
        if (this.graph.getNode(source).getEdgeToward(target) != null) {
            ((AgentModel) this.graph.getNode(source)).addTarget(target);
        }
    }

    /**
     * Adds an existing directed edge from the source to the target
     * 
     * @param source
     *            the id of the source node
     * @param target
     *            the id of the target node
     */
    public void addEdgeGraph(String source, String target) {
        graph.addEdge(source + target, source, target, true);
        graph.getEdge(source + target).setAttribute(Const.NODE_WEIGHT, Const.LAYOUT_WEIGHT_EDGE);
    }

    /**
     * Removes an edge
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
     * Gets the graph
     *
     * @return the graph
     */
    public SingleGraph getGraph() {
        return this.graph;
    }

    /**
     * Fills the agent graph from a map
     * 
     * @param map
     *            the agent map
     */
    @SuppressWarnings("unchecked")
    public void fillAgentGraphFromMap(Map<String, Object> map) {

        this.clearGraph();
        this.fillAgentFromMap(map);
        Iterator<Map.Entry<String, Object>> agents = map.entrySet().iterator();

        while (agents.hasNext()) {
            Map.Entry<String, Object> currentAgentMap = agents.next();
            HashMap<String, Object> currentAgent = (HashMap<String, Object>) currentAgentMap.getValue();
            this.fillAgentFromTargets(currentAgent);
        }
    }

    /**
     * Creates as many nodes as agents in the map
     * 
     * @param map
     *            the agent map
     */
    @SuppressWarnings("unchecked")
    private void fillAgentFromMap(Map<String, Object> map) {

        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> agent = it.next();
            this.addNode(agent.getKey(), (Map<String, Object>) agent.getValue());
        }
    }

    /**
     * Creates all edges of the agent
     * 
     * @param agent
     *            the agent
     */
    @SuppressWarnings("unchecked")
    private void fillAgentFromTargets(HashMap<String, Object> agent) {

        HashMap<String, Object> knowledgeMap = (HashMap<String, Object>) agent.get("knowledge");
        ArrayList<String> targets = (ArrayList<String>) knowledgeMap.get("targets");
        Iterator<String> it = targets.iterator();
        while (it.hasNext()) {
            String target = it.next();
            this.addEdgeGraph((String) agent.get("id"), target);
        }
    }

    /**
     * Empties the graph and resets the stylesheet
     */
    public void clearGraph() {

        this.agentMap.clear();
        this.graph.clear();
        this.graph.addAttribute("ui.stylesheet", "url(" + getClass().getResource("../css/graph.css") + ")");
        this.setQualityGraph();
    }

    /**
     * Sets the quality of the rendering of the graph
     */
    public void setQualityGraph() {

        this.graph.addAttribute("ui.quality");
        this.graph.addAttribute("layout.quality", 4);
        this.graph.addAttribute("ui.antialias");
    }

    /**
     * Gets the agent map
     * 
     * @return the agent map
     */
    public Map<String, Object> getAgentMap() {
        return agentMap;
    }

    /**
     * Sets the agent map
     * 
     * @param agentMap
     *            the agent map
     */
    public void setAgentMap(Map<String, Object> agentMap) {
        this.agentMap = agentMap;
    }
}
