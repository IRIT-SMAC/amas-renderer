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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.agent.AgentModel;
import fr.irit.smac.amasrenderer.model.agent.feature.FeatureModel;
import fr.irit.smac.amasrenderer.model.agent.feature.social.PortModel;
import fr.irit.smac.amasrenderer.model.agent.feature.social.TargetModel;
import javafx.beans.value.ObservableValue;

/**
 * This service is related to the business logic about the graph of agents
 */
public class GraphService {

    private MultiGraph graph;

    private static GraphService instance = new GraphService();

    private Map<String, AgentModel> agentMap;

    private SpriteManager spriteManager;

    private boolean displayPort;

    private boolean displayMain;

    private AtomicInteger idCount;

    public SpriteManager getSpriteManager() {
        return spriteManager;
    }

    public String getIdCount() {
        return idCount.toString();
    }

    private GraphService() {

        this.idCount = new AtomicInteger(0);
        this.graph = new MultiGraph("AMAS Rendering");
        this.graph.addAttribute(Const.GS_UI_STYLESHEET, "url(" + getClass().getResource("../css/graph.css") + ")");
        spriteManager = new SpriteManager(this.graph);
        this.setQualityGraph();
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

        File file = new File(getClass().getResource("../json/initial_agent.json").getFile());
        final ObjectMapper mapper = new ObjectMapper();
        AgentModel agent = null;
        try {
            String id = idCount.toString();
            Node node = this.graph.addNode(id);
            agent = mapper.readValue(file, AgentModel.class);
            agent.setName(id);
            agent.getCommonFeaturesModel().getFeatureBasic().getKnowledge().setId(id);
            node.setAttribute(Const.NODE_WEIGHT, Const.LAYOUT_WEIGHT_NODE);
            node.setAttribute(Const.GS_UI_LABEL, id);
            agent.setIdGraph(id);
            this.agentMap.put(id, agent);
            this.handleNodeNameChange(agent, id);
            idCount.incrementAndGet();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Adds a node with an existing attributes map.
     *
     * @param idModel
     *            the id of the node
     * @param attributesMap
     *            the attributes
     */
    public void addNode(String idModel, AgentModel agent) {

        String idGraph = idCount.toString();
        Node node = this.graph.addNode(idGraph);
        agent.setIdGraph(idGraph);
        agent.setName(idModel);
        node.setAttribute(Const.NODE_WEIGHT, Const.LAYOUT_WEIGHT_NODE);
        node.setAttribute(Const.GS_UI_LABEL, idModel);
        this.handleNodeNameChange(agent, idGraph);

        idCount.incrementAndGet();
    }

    public void handleNodeNameChange(AgentModel agentModel, String idGraph) {

        agentModel.nameProperty()
            .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {

                this.agentMap.remove(oldValue);
                this.agentMap.put(newValue, agentModel);
                graph.getNode(agentModel.getIdGraph()).setAttribute(Const.GS_UI_LABEL, newValue);
                agentModel.setId(newValue);
                this.agentMap.forEach((k, v) -> {

                    if (!v.getName().equals(newValue)) {
                        Map<String, TargetModel> targets = v.getCommonFeaturesModel().getFeatureSocial().getKnowledge()
                            .getTargetMap();
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

        File file = new File(getClass().getResource("../json/initial_target.json").getFile());
        final ObjectMapper mapper = new ObjectMapper();
        TargetModel targetModel = null;
        try {
            Edge edge = addEdgeGraph(idGraphNodeSource, idGraphNodeTarget, id);
            Sprite mainSprite = addSpriteEdgeGraph(id, null, null);
            targetModel = mapper.readValue(file, TargetModel.class);
            targetModel.setAgentTarget(this.graph.getNode(idGraphNodeTarget).getAttribute(Const.GS_UI_LABEL));
            targetModel.setAgentId(idModelNodeSource);
            targetModel.setName(id);
            String idd = this.graph.getNode(idGraphNodeSource).getAttribute(Const.GS_UI_LABEL);
            agentMap.get(idd).getCommonFeaturesModel().getFeatureSocial().getKnowledge().getTargetMap().put(id,
                targetModel);
            this.handleTargetModelChange(targetModel, edge, mainSprite, agentMap.get(targetModel.getAgentId()));
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

        if (this.graph.getEdge(id) != null) {
            String sourceNodeIdModel = graph.getEdge(id).getSourceNode().getAttribute(Const.GS_UI_LABEL);
            agentMap.get(sourceNodeIdModel).getCommonFeaturesModel().getFeatureSocial().getKnowledge().getTargetMap()
                .remove(graph.getEdge(id).getAttribute(Const.GS_UI_LABEL));
            this.spriteManager.removeSprite(id.concat(Const.TARGET_PORT_SPRITE));
            this.spriteManager.removeSprite(id.concat(Const.SOURCE_PORT_SPRITE));
            this.spriteManager.removeSprite(id.concat(Const.MAIN_SPRITE));
            this.graph.removeEdge(id);
        }
    }

    /**
     * Removes a node.
     *
     * @param n
     *            the node
     */
    public void removeNode(Node n) {

        n.getEachEdge().forEach(edge -> removeEdge(edge.getId()));
        this.graph.removeNode(n.getId());
        this.agentMap.remove(n.getAttribute(Const.GS_UI_LABEL));
    }

    /**
     * Fills the agent graph from a map
     * 
     * @param map
     *            the agent map
     */
    public void fillAgentGraphFromMap() {

        this.fillAgentFromMap();

        this.agentMap.forEach((agentId, agent) -> {
            this.fillAgentFromTargets(agent);
        });
    }

    /**
     * Creates as many nodes as agents in the map
     * 
     * @param map
     *            the agent map
     */
    private void fillAgentFromMap() {

        agentMap.forEach((agentId, agent) -> {
            this.addNode(agentId, agent);
        });
    }

    /**
     * Creates all edges of the agent
     * 
     * @param agent
     *            the agent
     */
    private void fillAgentFromTargets(AgentModel agent) {

        Map<String, TargetModel> targets = agent.getCommonFeaturesModel().getFeatureSocial().getKnowledge()
            .getTargetMap();

        targets.forEach(
            (k, v) -> {

                String targetIdGraph = agentMap.get(v.getAgentTarget()).getIdGraph();
                Edge edge = this.addEdgeGraph(agent.getIdGraph(), targetIdGraph, k);
                Sprite mainSprite = addSpriteEdgeGraph(k, v.getPortSource(), v.getPortTarget());
                this.handleTargetModelChange(v, edge, mainSprite, agent);
                this.idCount.incrementAndGet();
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
    public Map<String, AgentModel> getAgentMap() {
        return agentMap;
    }

    /**
     * Sets the agent map
     * 
     * @param agentMap
     *            the agent map
     */
    public void setAgentMap(Map<String, AgentModel> agentMap) {
        this.agentMap = agentMap;
    }

    public TargetModel getTargetModel(String agentId, String targetId) {

        return agentMap.get(agentId).getCommonFeaturesModel().getFeatureSocial().getKnowledge().getTargetMap()
            .get(targetId);
    }

    public void updateGraphFromFile(Map<String, AgentModel> agentMap) {

        if (this.agentMap != null) {
            this.clearGraph();
        }
        this.setAgentMap(agentMap);
        this.fillAgentGraphFromMap();
        this.setQualityGraph();
    }

    public FeatureModel addFeature(AgentModel agent) {

        File file = new File(getClass().getResource("../json/initial_feature.json").getFile());
        final ObjectMapper mapper = new ObjectMapper();
        FeatureModel feature = null;
        try {
            feature = mapper.readValue(file, FeatureModel.class);
            feature.setName("feature");
            agent.getCommonFeaturesModel().getFeatures().put("feature", feature);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return feature;
    }

    public PortModel addPort(AgentModel agent) {

        File file = new File(getClass().getResource("../json/initial_port.json").getFile());
        final ObjectMapper mapper = new ObjectMapper();
        PortModel port = null;
        try {
            port = mapper.readValue(file, PortModel.class);
            port.setName("port");
            agent.getCommonFeaturesModel().getFeatureSocial().getKnowledge().getPortMap().put("port", port);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return port;
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

    public void incrementIdCount() {
        this.idCount.incrementAndGet();
    }
}
