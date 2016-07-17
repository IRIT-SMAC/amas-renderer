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

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.agent.AgentModel;
import fr.irit.smac.amasrenderer.model.agent.feature.social.TargetModel;
import javafx.beans.value.ObservableValue;

public class EdgeService {

    private static EdgeService instance = new EdgeService();

    public static EdgeService getInstance() {

        return instance;
    }

    private Map<String, AgentModel> agentMap;
    private MultiGraph              graph;
    private AtomicInteger           idCount;
    private SpriteManager           spriteManager;
    private IGraphEdgeService       graphEdgeService;

    public void init(MultiGraph graph, AtomicInteger idCount,
        SpriteManager spriteManager, IGraphEdgeService graphEdgeService) {

        this.graph = graph;
        this.idCount = idCount;
        this.spriteManager = spriteManager;
        this.graphEdgeService = graphEdgeService;
    }

    public void setAgentMap(Map<String, AgentModel> agentMap) {
        this.agentMap = agentMap;
    }

    /**
     * Adds a directed edge from the source to the target
     * 
     * @param idNodeSource
     *            the id of the source node
     * @param idNodeTarget
     *            the id of the target node
     * @param c
     * @param b
     */
    public void addEdge(String idNodeSource, String idNodeTarget) {

        File file = new File(getClass().getResource("../../json/initial_target.json").getFile());
        final ObjectMapper mapper = new ObjectMapper();
        TargetModel targetModel = null;
        try {
            String id = idNodeSource.concat(idNodeTarget);
            Edge edge = addEdgeGraph(agentMap.get(idNodeSource).getIdGraph(), agentMap.get(idNodeTarget).getIdGraph(),
                id);
            Sprite mainSprite = addSpriteEdgeGraph(id, null, null);
            targetModel = mapper.readValue(file, TargetModel.class);
            targetModel.setAgentTarget(this.graph.getNode(idNodeTarget).getAttribute(Const.GS_UI_LABEL));
            targetModel.setAgentId(idNodeSource);
            targetModel.setName(id);
            agentMap.get(idNodeSource).getCommonFeaturesModel().getFeatureSocial().getKnowledge().getTargetMap().put(id,
                targetModel);
            this.handleTargetModelChange(targetModel, edge, mainSprite, agentMap.get(targetModel.getAgentId()));
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void addEdge(String k, TargetModel v, AgentModel agent) {

        String targetIdGraph = agentMap.get(v.getAgentTarget()).getIdGraph();
        Edge edge = this.addEdgeGraph(agent.getIdGraph(), targetIdGraph, k);
        Sprite mainSprite = addSpriteEdgeGraph(k, v.getPortSource(), v.getPortTarget());
        this.handleTargetModelChange(v, edge, mainSprite, agent);
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
        graphEdgeService.displaySprite(mainSprite, graphEdgeService.getDisplayMain(),
            Const.MAIN_SPRITE_CLASS, Const.EDGE_SPRITE_CLASS_BACKGROUND);

        this.createPortSourceToEdge(idGraph, label, Const.SOURCE_PORT_SPRITE,
            portSource, 0.2,
            graphEdgeService.getDisplayPort());
        this.createPortSourceToEdge(idGraph, label, Const.TARGET_PORT_SPRITE,
            portTarget, 0.8,
            graphEdgeService.getDisplayPort());

        idCount.incrementAndGet();

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
        graphEdgeService.displaySprite(sprite, portSpriteVisible,
            Const.PORT_SPRITE_CLASS, Const.EDGE_SPRITE_CLASS_BACKGROUND);

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

    public interface IGraphEdgeService {

        public boolean getDisplayPort();

        public boolean getDisplayMain();

        public void displaySprite(Sprite sprite, boolean portSpriteVisible, String portSpriteClass,
            String edgeSpriteClassBackground);
    }
}
