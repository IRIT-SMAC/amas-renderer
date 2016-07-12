/*
 * #%L
 * amas-renderer
 * %%
 * Copyright (C) 2016 IRIT - SMAC Team
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package fr.irit.smac.amasrenderer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

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

    private boolean displayPort;

    private boolean displayMain;

    private AtomicInteger idCount;

    public SpriteManager getSpriteManager() {
        return spriteManager;
    }

    private GraphService() {
        this.idCount = new AtomicInteger(0);
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
        this.graph.addAttribute(Const.GS_UI_STYLESHEET, "url(" + getClass().getResource("../css/graph.css") + ")");
        spriteManager = new SpriteManager(this.graph);
        this.graph.setNodeFactory((id, g) -> new AgentModel((AbstractGraph) g, id));
        this.setQualityGraph();
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
     * Adds a node.
     *
     * @param x
     *            the x location of the node
     * @param y
     *            the y location of the node
     */
    public void addNode(double x, double y) {

        String id = idCount.toString();

        AgentModel node = this.graph.addNode(id);
        node.changeAttribute(Const.NODE_XY, x, y);
        node.setAttribute(Const.NODE_WEIGHT, Const.LAYOUT_WEIGHT_NODE);
        node.setAttribute(Const.GS_UI_LABEL, id);
        node.initAttributesMap();
        this.agentMap.put(id, node.getAttributesMap());
        this.handleNodeNameChange(node, id);

        idCount.incrementAndGet();
    }

    /**
     * Adds a node with an existing attributes map.
     *
     * @param idModel
     *            the id of the node
     * @param attributesMap
     *            the attributes
     */
    public void addNode(String idModel, Map<String, Object> attributesMap, Map<String, String> idModelToGraphMap) {

        String idGraph = idCount.toString();

        AgentModel node = this.graph.addNode(idGraph);
        node.setName(idModel);
        node.setAttribute(Const.NODE_WEIGHT, Const.LAYOUT_WEIGHT_NODE);
        node.setAttribute(Const.GS_UI_LABEL, idModel);
        node.setAttributesMap(attributesMap);
        this.agentMap.put(idModel, node.getAttributesMap());
        idModelToGraphMap.put(idModel, idGraph);
        this.handleNodeNameChange(node, idGraph);

        idCount.incrementAndGet();
    }

    public void handleNodeNameChange(AgentModel agentModel, String idGraph) {

        agentModel.nameProperty()
            .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                
                this.agentMap.remove(oldValue);
                this.agentMap.put(newValue, agentModel.getAttributesMap());
                agentModel.setAttribute(Const.GS_UI_LABEL, newValue);
                agentModel.setId(newValue);
                this.graph.getNodeIterator().forEachRemaining(n -> {
                    if (!((AgentModel) n).getName().equals(newValue)) { // ?
                        Map<String, TargetModel> targets = ((AgentModel) n).getTargets();
                        targets.forEach((k2, v2) -> {
                            if (v2.getAgentTarget().equals(oldValue)) {
                                v2.setAgentTarget(newValue);
                            }
                        });
                    }
                });
            });
    }

    /**
     * Adds a directed edge from the source to the target
     * 
     * @param idGraphNodeSource
     *            the id of the source node
     * @param idGraphNodeTarget
     *            the id of the target node
     * @param c
     * @param b
     */
    public void addEdge(String idGraphNodeSource, String idGraphNodeTarget, String id, String idModelNodeSource) {

        Edge edge = addEdgeGraph(idGraphNodeSource, idGraphNodeTarget, id);
        Sprite mainSprite = addSpriteEdgeGraph(id, null, null);
        TargetModel targetModel = new TargetModel(idGraphNodeTarget, id);
        targetModel.setAgentId(idModelNodeSource);

        ((AgentModel) this.graph.getNode(idModelNodeSource)).addTarget(targetModel, id);

        this.handleTargetModelChange(targetModel, edge, mainSprite,
            ((AgentModel) this.graph.getNode(idModelNodeSource)));
    }

    private void handleTargetModelChange(TargetModel targetModel, Edge edge, Sprite mainSprite, AgentModel agentModel) {

        targetModel.nameProperty()
            .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {

                edge.setAttribute(Const.GS_UI_LABEL, newValue);
                mainSprite.setAttribute(Const.GS_UI_LABEL, newValue);

                agentModel.removeTarget(oldValue);
                agentModel.addTarget(targetModel, newValue);
            });
    }

    /**
     * Adds an existing directed edge from the source to the target
     * 
     * @param source
     *            the id of the source node
     * @param target
     *            the id of the target node
     */
    public Edge addEdgeGraph(String source, String target, String id) {

        String idGraph = idCount.toString();
        Edge edge = graph.addEdge(idGraph, source, target, true);
        edge.addAttribute(Const.GS_UI_LABEL, id);

        return edge;
    }

    private Sprite addSpriteEdgeGraph(String label, String portSource, String portTarget) {

        String idGraph = this.idCount.toString();
        Sprite mainSprite = this.spriteManager.addSprite(idGraph.concat(Const.MAIN_SPRITE));
        mainSprite.attachToEdge(idGraph);
        mainSprite.setPosition(0.5);
        mainSprite.addAttribute(Const.GS_UI_LABEL, label);
        mainSprite.addAttribute(Const.GS_UI_CLASS, Const.MAIN_SPRITE_CLASS);
        mainSprite.addAttribute(Const.TYPE_SPRITE, Const.MAIN_SPRITE_EDGE);
        mainSprite.addAttribute(Const.ID, label);
        this.displaySprite(mainSprite, getDisplayMain(), Const.MAIN_SPRITE_CLASS, Const.EDGE_SPRITE_CLASS_BACKGROUND);

        this.createPortSourceToEdge(idGraph, label, Const.SOURCE_PORT_SPRITE, portSource, 0.2,
            this.getDisplayPort());
        this.createPortSourceToEdge(idGraph, label, Const.TARGET_PORT_SPRITE, portTarget, 0.8,
            this.getDisplayPort());

        return mainSprite;
    }

    private void createPortSourceToEdge(String id, String label, String subType, String port, double position,
        boolean portSpriteVisible) {

        Sprite sprite = this.spriteManager.addSprite(id.concat(subType));
        sprite.addAttribute(Const.GS_UI_LABEL, port);
        sprite.addAttribute(Const.TYPE_SPRITE, Const.PORT);
        sprite.addAttribute(Const.SUBTYPE_SPRITE, subType);
        sprite.addAttribute(Const.ID, label);
        sprite.attachToEdge(id);
        sprite.setPosition(position);
        this.displaySprite(sprite, portSpriteVisible, Const.PORT_SPRITE_CLASS, Const.EDGE_SPRITE_CLASS_BACKGROUND);

        idCount.incrementAndGet();
    }

    /**
     * Removes an edge
     * 
     * @param edge
     *            the edge to remove
     */
    public void removeEdge(String id) {

        this.spriteManager.removeSprite(id.concat(Const.TARGET_PORT_SPRITE));
        this.spriteManager.removeSprite(id.concat(Const.SOURCE_PORT_SPRITE));
        this.spriteManager.removeSprite(id.concat(Const.MAIN_SPRITE));
        this.graph.removeEdge(id);
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
     * Fills the agent graph from a map
     * 
     * @param map
     *            the agent map
     */
    @SuppressWarnings("unchecked")
    public void fillAgentGraphFromMap() {

        Map<String, String> idModelToGraphMap = new HashMap<>();
        this.fillAgentFromMap(this.agentMap, idModelToGraphMap);

        this.agentMap.forEach((agentId, agent) -> {
            this.fillAgentFromTargets((Map<String, Object>) agent, idModelToGraphMap);
        });
    }

    /**
     * Creates as many nodes as agents in the map
     * 
     * @param map
     *            the agent map
     */
    @SuppressWarnings("unchecked")
    private void fillAgentFromMap(Map<String, Object> map, Map<String, String> idModelToGraphMap) {

        Map<String, Object> concurrentMap = new ConcurrentHashMap<>(map);

        concurrentMap.forEach((agentId, agent) -> {
            this.addNode(agentId, (Map<String, Object>) agent, idModelToGraphMap);
        });
    }

    /**
     * Creates all edges of the agent
     * 
     * @param agent
     *            the agent
     */
    @SuppressWarnings("unchecked")
    private void fillAgentFromTargets(Map<String, Object> agent, Map<String, String> idModelToGraphMap) {

        Map<String, Object> targets = (Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) agent
            .get(Const.COMMON_FEATURES)).get(Const.FEATURE_SOCIAL)).get(Const.KNOWLEDGE)).get(Const.TARGET_MAP);

        String agentId = (String) ((Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) agent
            .get(Const.COMMON_FEATURES)).get(Const.FEATURE_BASIC)).get(Const.KNOWLEDGE)).get(Const.ID);
        AgentModel agentModel = (AgentModel) this.graph.getNode(idModelToGraphMap.get(agentId));

        targets.forEach(
            (k, v) -> {

                TargetModel targetModel = new TargetModel(k, (HashMap<String, Object>) v);
                targetModel.setAgentId(agentId);
                agentModel.addTarget(targetModel, k);
                String targetIdGraph = idModelToGraphMap.get(targetModel.getAgentTarget());
                Edge edge = this.addEdgeGraph(idModelToGraphMap.get(agentId), targetIdGraph, k);
                Sprite mainSprite = addSpriteEdgeGraph(k, targetModel.getPortSource(), targetModel.getPortTarget());

                this.handleTargetModelChange(targetModel, edge, mainSprite, agentModel);
            });
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
        this.graph.addAttribute(Const.GS_UI_STYLESHEET, "url(" + getClass().getResource("../css/graph.css") + ")");
        this.setQualityGraph();
    }

    /**
     * Sets the quality of the rendering of the graph
     */
    public void setQualityGraph() {

        this.graph.addAttribute(Const.GS_UI_QUALITY);
        this.graph.addAttribute(Const.GS_LAYOUT_QUALITY, 4);
        this.graph.addAttribute(Const.GS_ANTIALIAS);
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

    public TargetModel getTargetModel(String agentId, String targetId) {

        return ((AgentModel) this.graph.getNode(agentId)).getTargets().get(targetId);
    }

    public void updateGraphFromFile(Map<String, Object> agentMap) {

        this.clearGraph();
        this.setAgentMap(agentMap);
        this.fillAgentGraphFromMap();
        this.setQualityGraph();
    }

    private void displaySprite(Sprite sprite, boolean visible, String classNormal, String classBackground) {
        if (visible) {
            sprite.addAttribute(Const.GS_UI_CLASS, classNormal);
        }
        else {
            sprite.addAttribute(Const.GS_UI_CLASS, classBackground);
        }
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
