package fr.irit.smac.amasrenderer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

    private Map<String, Map<String, TargetModel>> targets = new HashMap<>();

    private SpriteManager spriteManager;

    private boolean displayPort;

    private boolean displayMain;

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
        node.setAttribute(Const.GS_UI_LABEL, id);
        node.initAttributesMap();
        this.agentMap.put(id, node.getAttributesMap());
        this.targets.put(id, new HashMap<>());

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
        node.setAttribute(Const.GS_UI_LABEL, id);
        node.setAttributesMap(attributesMap);
        this.agentMap.put(id, attributesMap);
        this.targets.put(id, new HashMap<>());

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
     * @param c
     * @param b
     */
    public void addEdge(String source, String target, String id) {

        addEdgeGraph(source, target, source.concat(id), null, null);
        TargetModel targetModel = new TargetModel(target, id);
        ((AgentModel) this.graph.getNode(source)).addTarget(id, targetModel.getAttributesMap());
        this.targets.get(source).put(id, targetModel);
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
        mainSprite.addAttribute(Const.GS_UI_CLASS, Const.MAIN_SPRITE_CLASS);
        mainSprite.addAttribute(Const.TYPE_SPRITE, Const.MAIN_SPRITE_EDGE);
        mainSprite.addAttribute(Const.ID, id);
        this.displaySprite(mainSprite, getDisplayMain(), Const.MAIN_SPRITE_CLASS, Const.EDGE_SPRITE_CLASS_BACKGROUND);

        this.createPortSourceToEdge(id, Const.SOURCE_PORT_SPRITE, portSource, 0.2, this.getDisplayPort());
        this.createPortSourceToEdge(id, Const.TARGET_PORT_SPRITE, portTarget, 0.8, this.getDisplayPort());

    }

    private void createPortSourceToEdge(String id, String subType, String port, double position,
        boolean portSpriteVisible) {

        Sprite sprite = this.spriteManager.addSprite(id + subType);
        sprite.addAttribute(Const.GS_UI_LABEL, port);
        sprite.addAttribute(Const.TYPE_SPRITE, Const.PORT);
        sprite.addAttribute(Const.SUBTYPE_SPRITE, subType);
        sprite.addAttribute(Const.ID, id);
        sprite.attachToEdge(id);
        sprite.setPosition(position);
        this.displaySprite(sprite, portSpriteVisible, Const.PORT_SPRITE_CLASS, Const.EDGE_SPRITE_CLASS_BACKGROUND);
    }

    private void displaySprite(Sprite sprite, boolean visible, String classNormal, String classBackground) {
        if (visible) {
            sprite.addAttribute(Const.GS_UI_CLASS, classNormal);
        }
        else {
            sprite.addAttribute(Const.GS_UI_CLASS, classBackground);
        }
    }

    /**
     * Removes an edge
     * 
     * @param edge
     *            the edge to remove
     */
    public void removeEdge(String id) {

        spriteManager.removeSprite(id + Const.TARGET_PORT_SPRITE);
        spriteManager.removeSprite(id + Const.SOURCE_PORT_SPRITE);
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
    public void fillAgentGraphFromMap() {

        this.fillAgentFromMap(this.agentMap);
        Iterator<Map.Entry<String, Object>> agents = this.agentMap.entrySet().iterator();

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

        String agentId = ((String) agent.get(Const.ID));

        targets.forEach(
            (k, v) -> {
                String targetId = (String) ((Map<String, Object>) v).get("agentTarget");
                String portSource = (String) ((Map<String, Object>) v).get("portSource");
                String portTarget = (String) ((Map<String, Object>) v).get("portTarget");
                String className = (String) ((Map<String, Object>) v).get("className");
                this.addEdgeGraph(agentId, targetId, agentId.concat(k), portSource, portTarget);
                TargetModel targetModel = new TargetModel(targetId, k, portSource, portTarget, className);
                this.targets.get(agentId).put(k,
                    targetModel);
            });

        targets.clear();
        this.targets.get(agentId).forEach((k, v) -> targets.put(k, v.getAttributesMap()));

    }

    /**
     * Empties the graph and resets the stylesheet
     */
    public void clearGraph() {

        this.agentMap.clear();
        this.graph.clear();
        List<String> idSpriteList = new ArrayList<>();
        this.spriteManager.forEach(s -> idSpriteList.add(s.getId()));
        idSpriteList.stream().forEach(id -> this.spriteManager.removeSprite(id));
        this.spriteManager.forEach(s -> this.spriteManager.removeSprite(s.getId()));
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

    public Map<String, Map<String, TargetModel>> getTargets() {
        return targets;
    }

    public void updateGraphFromFile(Map<String, Object> agentMap) {

        this.clearGraph();
        this.setAgentMap(agentMap);
        this.fillAgentGraphFromMap();
        this.setQualityGraph();
    }

    public void hideSpriteEdge(String type) {

        this.getSpriteManager().forEach(s -> {
            if (s.getAttribute(Const.TYPE_SPRITE).equals(type)) {
                s.setAttribute(Const.GS_UI_CLASS, Const.EDGE_SPRITE_CLASS_BACKGROUND);
            }
        });
    }

    public void displaySpriteEdge(boolean displayNode, Node foregroundNode, String type, String styleClass) {

        if (displayNode) {
            this.getSpriteManager().forEach(s -> {
                Edge edge = (Edge) s.getAttachment();
                String id = foregroundNode.getId();
                if ((edge.getSourceNode().getId() == id
                    || edge.getTargetNode().getId() == id) && (s.getAttribute(Const.TYPE_SPRITE).equals(type))) {
                    s.setAttribute(Const.GS_UI_CLASS, styleClass);
                }
            });
        }
        else {
            this.getSpriteManager().forEach(s -> {
                if (s.getAttribute(Const.TYPE_SPRITE).equals(type)) {
                    s.setAttribute(Const.GS_UI_CLASS, styleClass);
                }
            });
        }

    }

    public void displayForegroundNode(Node foregroundNode) {

        String id = foregroundNode.getId();
        this.graph.getEachNode().forEach(node -> {
            if (node.getId() != id) {
                node.addAttribute(Const.GS_UI_CLASS, Const.NODE_CLASS_BACKGROUND);
            }
        });

        this.graph.getEachEdge().forEach(edge -> {
            if (edge.getSourceNode().getId() != id && edge.getTargetNode().getId() != id) {
                edge.addAttribute(Const.GS_UI_CLASS, Const.EDGE_SPRITE_CLASS_BACKGROUND);
                this.spriteManager.forEach(s -> {
                    if (s.getAttachment().equals(edge)) {
                        s.setAttribute(Const.GS_UI_CLASS, Const.EDGE_SPRITE_CLASS_BACKGROUND);
                    }
                });
            }
        });
    }

    public void displayBackgroundNode(Node foregroundNode) {

        String id = foregroundNode.getId();
        this.graph.getEachNode().forEach(node -> {
            if (node.getId() == id) {
                node.removeAttribute(Const.GS_UI_CLASS);
            }
        });

        this.graph.getEachEdge().forEach(edge -> {
            if (edge.getSourceNode().getId() == id || edge.getTargetNode().getId() == id) {
                edge.removeAttribute(Const.GS_UI_CLASS);
                this.displaySprite(getDisplayMain(), this.getDisplayPort(), edge);
            }
        });
    }

    public void displayAllNodes() {

        this.graph.getEachNode().forEach(node -> {
            node.removeAttribute(Const.GS_UI_CLASS);
        });

        this.graph.getEachEdge().forEach(edge -> {
            edge.removeAttribute(Const.GS_UI_CLASS);
            this.displaySprite(this.getDisplayMain(), this.getDisplayPort(), edge);
        });
    }

    private void displaySprite(boolean mainSpriteVisible, boolean portSpriteVisible, Edge edge) {

        if (mainSpriteVisible || portSpriteVisible) {
            this.spriteManager.forEach(s -> {

                if (s.getAttachment().equals(edge)) {

                    if (mainSpriteVisible && s.getAttribute(Const.TYPE_SPRITE).equals(Const.MAIN_SPRITE_EDGE)) {
                        s.setAttribute(Const.GS_UI_CLASS, Const.MAIN_SPRITE_CLASS);
                    }
                    else if (portSpriteVisible && s.getAttribute(Const.TYPE_SPRITE).equals(Const.PORT)) {
                        s.setAttribute(Const.GS_UI_CLASS, Const.PORT_SPRITE_CLASS);
                    }
                }
            });
        }
    }

    public void setDisplayPort(boolean visible) {
        this.displayPort = visible;
    }
    
    public void setDisplayMain(boolean visible) {
        this.displayMain = visible;
    }
    
    private boolean getDisplayPort() {
        return this.displayPort;
    }
    
    private boolean getDisplayMain() {
        return this.displayMain;
    }

}
