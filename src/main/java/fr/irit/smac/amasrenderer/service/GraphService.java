package fr.irit.smac.amasrenderer.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.AgentModel;
import fr.irit.smac.amasrenderer.model.TargetModel;
import javafx.beans.value.ObservableValue;

/**
 * This service is related to the business logic about the graph of agents
 */
public class GraphService {

    private MultiGraph graph;

    private static GraphService instance = new GraphService();

    private Map<String, Object> agentMap;

    private SpriteManager spriteManager;

    public SpriteManager getSpriteManager() {
        return spriteManager;
    }

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

        this.graph = new MultiGraph("AMAS Rendering");
        this.graph.addAttribute("ui.stylesheet", "url(" + getClass().getResource("../css/graph.css") + ")");
        spriteManager = new SpriteManager(this.graph);
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
        node.initAttributesMap();
        this.agentMap.put(id, node.getAttributesMap());

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
    public void addEdge(String source, String target, String id) {

        addEdgeGraph(source, target, source.concat(id), null, null);
        TargetModel targetModel = new TargetModel(target, id);

        if (this.graph.getNode(source).getEdgeToward(target) != null) {
            ((AgentModel) this.graph.getNode(source)).addTarget(id, targetModel.getAttributesMap());
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
    public void addEdgeGraph(String source, String target, String id, String portSource, String portTarget) {

        graph.addEdge(id, source, target, true);

        Sprite mainSprite = this.spriteManager.addSprite(id);
        mainSprite.attachToEdge(id);
        mainSprite.setPosition(0.5);
        mainSprite.addAttribute("ui.style", "size: 15px; fill-color:#4a7aaa; shape:diamond;");
        mainSprite.addAttribute("type", "main");
        mainSprite.addAttribute("id", id);

        Sprite spritePortSource = this.spriteManager.addSprite(id + "source");
        spritePortSource.addAttribute(Const.NODE_LABEL, portSource);
        spritePortSource.addAttribute("type", "source");
        spritePortSource.addAttribute("id", id);
        spritePortSource.attachToEdge(id);
        spritePortSource.setPosition(0.2);

        Sprite spritePortTarget = this.spriteManager.addSprite(id + "target");
        spritePortTarget.attachToEdge(id);
        spritePortTarget.setPosition(0.8);
        spritePortTarget.addAttribute("id", id);
        spritePortTarget.addAttribute(Const.NODE_LABEL, portTarget);
        spritePortTarget.addAttribute("type", "target");

    }

    /**
     * Removes an edge
     * 
     * @param edge
     *            the edge to remove
     */
    public void removeEdge(String id) {

        spriteManager.removeSprite(id + "target");
        spriteManager.removeSprite(id + "source");
        spriteManager.removeSprite(id);
        graph.removeEdge(id);
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
                this.removeEdge(edge.getId());
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
    public MultiGraph getGraph() {
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

        Map<String, Object> targets = (Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) agent
            .get("commonFeatures")).get("featureSocial")).get("knowledge")).get("targets");

        targets.forEach(
            (k, v) -> {
                String targetId = (String) ((Map<String, Object>) v).get("agentTarget");
                String agentId = ((String) agent.get("id"));
                String portSource = (String) ((Map<String, Object>) v).get("portSource");
                String portTarget = (String) ((Map<String, Object>) v).get("portTarget");
                this.addEdgeGraph(agentId, targetId, agentId.concat(k), portSource, portTarget);
            });
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
