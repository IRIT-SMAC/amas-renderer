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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.agent.Agent;
import fr.irit.smac.amasrenderer.model.agent.feature.Feature;
import fr.irit.smac.amasrenderer.model.agent.feature.social.Port;
import fr.irit.smac.amasrenderer.model.agent.feature.social.Target;
import fr.irit.smac.amasrenderer.service.graph.EdgeService.IGraphEdgeService;
import fr.irit.smac.amasrenderer.service.graph.NodeService.IGraphNodeService;

/**
 * This service is related to the business logic about the graph of agents. It
 * uses subservices EdgeService, FeatureService, NodeService, PortDisplayService
 * and PortService
 */
public class GraphService implements IGraphEdgeService, IGraphNodeService, INodeService, IEdgeService, IFeatureService,
    IPortService, IPortDisplayService {

    private MultiGraph graph;

    private static GraphService instance = new GraphService();

    private Map<String, Agent> agentMap;

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

    public static GraphService getInstance() {

        return instance;
    }

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

    public Map<String, Agent> getAgentMap() {
        return agentMap;
    }

    public void setAgentMap(Map<String, Agent> agentMap) {
        this.agentMap = agentMap;
        nodeService.setAgentMap(agentMap);
        edgeService.setAgentMap(agentMap);
    }

    public void fillAgentGraphFromAgentMap() {

        agentMap.forEach((agentId, agent) -> nodeService.addNode(agentId, agent));

        agentMap.forEach((agentId, agent) -> {

            Map<String, Target> targets = agent.getCommonFeaturesModel().getFeatureSocial().getKnowledge()
                .getTargetMap();

            targets.forEach(
                (k, v) -> edgeService.addEdge(k, v, agent));
        });
    }

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

    public void setQualityGraph() {

        graph.addAttribute(Const.GS_UI_QUALITY);
        graph.addAttribute(Const.GS_LAYOUT_QUALITY, 4);
        graph.addAttribute(Const.GS_ANTIALIAS);
    }

    public Target getTargetModel(String agentId, String targetId) {

        return agentMap.get(agentId).getCommonFeaturesModel().getFeatureSocial().getKnowledge().getTargetMap()
            .get(targetId);
    }

    public void updateGraphFromAgentMap(Map<String, Agent> agentMap) {

        if (this.agentMap != null) {
            clearGraph();
        }

        setAgentMap(agentMap);
        fillAgentGraphFromAgentMap();
        setQualityGraph();
    }

    @Override
    public void addNode(double x, double y) {

        nodeService.addNode(x, y);
    }

    @Override
    public void removeNode(String id) {

        nodeService.removeNode(id);
    }

    @Override
    public void addEdge(String idNodeSource, String idNodeTarget) {

        edgeService.addEdge(idNodeSource, idNodeTarget);
    }

    @Override
    public void removeEdge(String id) {

        edgeService.removeEdge(id);
    }
    
    @Override
    public void removeEdges(Collection<Edge> edges) {

        edgeService.removeEdges(edges);
    }

    @Override
    public Feature addFeature(Agent agent, String name) {
        return featureService.addFeature(agent, name);
    }

    @Override
    public Port addPort(Agent agent, String name) {
        return portService.addPort(agent, name);
    }

    @Override
    public void displaySpritesEdge(boolean displayNode, Node foregroundNode, String type, String styleClass) {
        portDisplayService.displaySpritesEdge(displayNode, foregroundNode, type, styleClass);
    }

    @Override
    public void setDisplayPort(boolean visible) {
        portDisplayService.setDisplayPort(visible);
    }

    @Override
    public void setDisplayMain(boolean visible) {
        portDisplayService.setDisplayMain(visible);
    }

    @Override
    public void hideSpriteEdge(String type) {
        portDisplayService.hideSpriteEdge(type);
    }

    @Override
    public void displayForegroundNode(Node foregroundNode) {
        portDisplayService.displayForegroundNode(foregroundNode);
    }

    @Override
    public void displayBackgroundNode(Node backgroundNode) {
        portDisplayService.displayBackgroundNode(backgroundNode);
    }

    @Override
    public void displayAllNodesNormally() {
        portDisplayService.displayAllNodesNormally();
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
