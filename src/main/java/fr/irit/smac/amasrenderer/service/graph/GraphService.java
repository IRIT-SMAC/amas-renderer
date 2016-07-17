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
package fr.irit.smac.amasrenderer.service.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.agent.AgentModel;
import fr.irit.smac.amasrenderer.model.agent.feature.FeatureModel;
import fr.irit.smac.amasrenderer.model.agent.feature.social.PortModel;
import fr.irit.smac.amasrenderer.model.agent.feature.social.TargetModel;
import fr.irit.smac.amasrenderer.service.graph.EdgeService.IGraphEdgeService;
import fr.irit.smac.amasrenderer.service.graph.NodeService.IGraphNodeService;

/**
 * This service is related to the business logic about the graph of agents
 */
public class GraphService implements IGraphEdgeService, IGraphNodeService {

    private MultiGraph graph;

    private static GraphService instance = new GraphService();

    private Map<String, AgentModel> agentMap;

    private SpriteManager spriteManager;

    private AtomicInteger idCount;

    private NodeService nodeService = NodeService.getInstance();

    private EdgeService edgeService = EdgeService.getInstance();

    private PortDisplayService portDisplayService = PortDisplayService.getInstance();

    private FeatureService featureService = FeatureService.getInstance();

    private PortService portService = PortService.getInstance();

    private GraphService() {

        idCount = new AtomicInteger(0);
        graph = new MultiGraph("AMAS Rendering");
        graph.addAttribute(Const.GS_UI_STYLESHEET, "url(" + getClass().getResource("../../css/graph.css") + ")");
        spriteManager = new SpriteManager(graph);
        setQualityGraph();

        nodeService.init(graph, idCount, this);
        edgeService.init(graph, idCount, spriteManager, this);
        portDisplayService.init(graph, spriteManager);
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
        return graph;
    }

    public SpriteManager getSpriteManager() {
        return spriteManager;
    }

    public String getIdCountString() {
        return idCount.toString();
    }
    
    public AtomicInteger getIdCount() {
        return idCount;
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
        nodeService.setAgentMap(agentMap);
        edgeService.setAgentMap(agentMap);
    }
    
    /**
     * Fills the agent graph from a map
     * 
     * @param map
     *            the agent map
     */
    public void fillAgentGraphFromMap() {

        agentMap.forEach((agentId, agent) -> {
            nodeService.addNode(agentId, agent);
        });

        agentMap.forEach((agentId, agent) -> {

            Map<String, TargetModel> targets = agent.getCommonFeaturesModel().getFeatureSocial().getKnowledge()
                .getTargetMap();

            targets.forEach(
                (k, v) -> {
                edgeService.addEdge(k, v, agent);
            });
        });
    }

    /**
     * Empties the graph and resets the stylesheet
     */
    public void clearGraph() {

        agentMap.clear();
        graph.clear();
        List<String> idSpriteList = new ArrayList<>();
        spriteManager.forEach(s -> idSpriteList.add(s.getId()));
        idSpriteList.stream().forEach(id -> spriteManager.removeSprite(id));
        spriteManager.forEach(s -> spriteManager.removeSprite(s.getId()));
        graph.addAttribute(Const.GS_UI_STYLESHEET, "url(" + getClass().getResource("../../css/graph.css") + ")");
        setQualityGraph();
    }

    /**
     * Sets the quality of the rendering of the graph
     */
    public void setQualityGraph() {

        graph.addAttribute(Const.GS_UI_QUALITY);
        graph.addAttribute(Const.GS_LAYOUT_QUALITY, 4);
        graph.addAttribute(Const.GS_ANTIALIAS);
    }

    public TargetModel getTargetModel(String agentId, String targetId) {

        return agentMap.get(agentId).getCommonFeaturesModel().getFeatureSocial().getKnowledge().getTargetMap()
            .get(targetId);
    }

    public void updateGraphFromFile(Map<String, AgentModel> agentMap) {

        if (this.agentMap != null) {
            clearGraph();
        }
        
        setAgentMap(agentMap);
        fillAgentGraphFromMap();
        setQualityGraph();
    }

    public void addNode(double x, double y) {

        nodeService.addNode(x, y);
    }

    public void removeNode(String id) {

        nodeService.removeNode(id);
    }

    public void addEdge(String idNodeSource, String idNodeTarget) {

        edgeService.addEdge(idNodeSource, idNodeTarget);
    }

    @Override
    public void removeEdge(String id) {

        edgeService.removeEdge(id);
    }

    public FeatureModel addFeature(AgentModel agent) {
        return featureService.addFeature(agent);
    }

    public PortModel addPort(AgentModel agent) {
        return portService.addPort(agent);
    }

    public void displaySpriteEdge(boolean displayNode, Node foregroundNode, String type, String styleClass) {
        portDisplayService.displaySpriteEdge(displayNode, foregroundNode, type, styleClass);
    }

    public void setDisplayPort(boolean visible) {
        portDisplayService.setDisplayPort(visible);
    }

    public void setDisplayMain(boolean visible) {
        portDisplayService.setDisplayMain(visible);
    }

    public void hideSpriteEdge(String type) {
        portDisplayService.hideSpriteEdge(type);
    }

    public void displayForegroundNode(Node foregroundNode) {
        portDisplayService.displayForegroundNode(foregroundNode);
    }

    public void displayBackgroundNode(Node backgroundNode) {
        portDisplayService.displayBackgroundNode(backgroundNode);
    }

    public void displayAllNodes() {
        portDisplayService.displayAllNodes();
    }

    @Override
    public boolean getDisplayPort() {
        return portDisplayService.getDisplayPort();
    }

    @Override
    public boolean getDisplayMain() {
        return portDisplayService.getDisplayMain();
    }

    @Override
    public void displaySprite(Sprite sprite, boolean portSpriteVisible, String portSpriteClass,
        String edgeSpriteClassBackground) {
        portDisplayService.displaySprite(sprite, portSpriteVisible, portSpriteClass,
            edgeSpriteClassBackground);
    }
}
