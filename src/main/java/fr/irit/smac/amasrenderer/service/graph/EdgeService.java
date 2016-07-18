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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.agent.Agent;
import fr.irit.smac.amasrenderer.model.agent.feature.social.Target;
import javafx.beans.value.ObservableValue;

/**
 * This service is related to the business logic about the edges of the graph of
 * agents
 */
public class EdgeService implements IEdgeService {

    private Map<String, Agent> agentMap;
    private MultiGraph         graph;
    private AtomicInteger      idCount;
    private SpriteManager      spriteManager;
    private IGraphEdgeService  graphEdgeService;
    private static EdgeService instance = new EdgeService();

    private static final Logger LOGGER = Logger.getLogger(EdgeService.class.getName());

    public static EdgeService getInstance() {

        return instance;
    }

    public void init(MultiGraph graph, AtomicInteger idCount,
        SpriteManager spriteManager, IGraphEdgeService graphEdgeService) {

        this.graph = graph;
        this.idCount = idCount;
        this.spriteManager = spriteManager;
        this.graphEdgeService = graphEdgeService;
    }

    public void setAgentMap(Map<String, Agent> agentMap) {
        this.agentMap = agentMap;
    }

    @Override
    public void addEdge(String idNodeSource, String idNodeTarget) {

        File file = new File(getClass().getResource("../../json/initial_target.json").getFile());
        final ObjectMapper mapper = new ObjectMapper();
        Target targetModel = null;
        try {
            String id = idNodeSource.concat(idNodeTarget);
            Edge edge = addEdgeGraph(agentMap.get(idNodeSource).getIdGraph(), agentMap.get(idNodeTarget).getIdGraph(),
                id);
            Sprite mainSprite = addSpriteEdgeGraph(id, null, null);
            targetModel = mapper.readValue(file, Target.class);
            targetModel.setAgentTarget(graph.getNode(idNodeTarget).getAttribute(Const.GS_UI_LABEL));
            targetModel.setAgentId(idNodeSource);
            targetModel.setName(id);
            agentMap.get(idNodeSource).getCommonFeaturesModel().getFeatureSocial().getKnowledge().getTargetMap().put(id,
                targetModel);
            handleTargetModelChange(targetModel, edge, mainSprite, agentMap.get(targetModel.getAgentId()));
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "An error occured during the loading of the json file target", e);
        }
    }

    /**
     * Adds an edge. The existing model of the edge is given. This method is
     * called when the graph of agents is generated with a json file
     * 
     * @param id
     * @param target
     * @param agent
     */
    public void addEdge(String id, Target target, Agent agent) {

        String targetIdGraph = agentMap.get(target.getAgentTarget()).getIdGraph();
        Edge edge = addEdgeGraph(agent.getIdGraph(), targetIdGraph, id);
        Sprite mainSprite = addSpriteEdgeGraph(id, target.getPortSource(), target.getPortTarget());
        handleTargetModelChange(target, edge, mainSprite, agent);
    }

    /**
     * Updates the models when the name of the edge is updated
     * 
     * @param target
     * @param edge
     * @param mainSprite
     * @param agentModel
     */
    private void handleTargetModelChange(Target target, Edge edge, Sprite mainSprite, Agent agentModel) {

        target.nameProperty()
            .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {

                edge.setAttribute(Const.GS_UI_LABEL, newValue);
                mainSprite.setAttribute(Const.GS_UI_LABEL, newValue);
                Map<String, Target> targetMap = agentModel.getCommonFeaturesModel().getFeatureSocial().getKnowledge()
                    .getTargetMap();
                targetMap.remove(oldValue);
                targetMap.put(newValue, target);
            });
    }

    /**
     * Adds a directed edge from the source to the target on the graph of agents
     * 
     * @param idNodeSource
     * @param idNodeTarget
     * @param id
     * @return the edge
     */
    public Edge addEdgeGraph(String idNodeSource, String idNodeTarget, String id) {

        String idGraph = idCount.toString();
        Edge edge = graph.addEdge(idGraph, idNodeSource, idNodeTarget, true);
        edge.addAttribute(Const.GS_UI_LABEL, id);

        return edge;
    }

    /**
     * Adds three sprites on the edge. The main sprite allows to access to the
     * attributes of the target. The two others represents the ports of the
     * target.
     * 
     * @param label
     * @param portSource
     * @param portTarget
     * @return the main sprite
     */
    private Sprite addSpriteEdgeGraph(String label, String portSource, String portTarget) {

        String idGraph = idCount.toString();
        Sprite mainSprite = spriteManager.addSprite(idGraph.concat(Const.MAIN_SPRITE));
        mainSprite.attachToEdge(idGraph);
        mainSprite.setPosition(0.5);
        mainSprite.addAttribute(Const.GS_UI_LABEL, label);
        mainSprite.addAttribute(Const.GS_UI_CLASS, Const.MAIN_SPRITE_CLASS);
        mainSprite.addAttribute(Const.TYPE_SPRITE, Const.MAIN_SPRITE_EDGE);
        mainSprite.addAttribute(Const.ID, label);
        graphEdgeService.displaySprite(mainSprite, graphEdgeService.getDisplayMain(),
            Const.MAIN_SPRITE_CLASS, Const.EDGE_SPRITE_CLASS_BACKGROUND);

        createPortSourceToEdge(idGraph, label, Const.SOURCE_PORT_SPRITE,
            portSource, 0.2,
            graphEdgeService.getDisplayPort());
        createPortSourceToEdge(idGraph, label, Const.TARGET_PORT_SPRITE,
            portTarget, 0.8,
            graphEdgeService.getDisplayPort());

        idCount.incrementAndGet();

        return mainSprite;
    }

    /**
     * Creates a sprite which represents a port of an edge
     * 
     * @param id
     * @param label
     * @param subType
     * @param port
     * @param position
     * @param portSpriteVisible
     */
    private void createPortSourceToEdge(String id, String label, String subType, String port, double position,
        boolean portSpriteVisible) {

        Sprite sprite = spriteManager.addSprite(id.concat(subType));
        sprite.addAttribute(Const.GS_UI_LABEL, port);
        sprite.addAttribute(Const.TYPE_SPRITE, Const.PORT);
        sprite.addAttribute(Const.SUBTYPE_SPRITE, subType);
        sprite.addAttribute(Const.ID, label);
        sprite.attachToEdge(id);
        sprite.setPosition(position);
        graphEdgeService.displaySprite(sprite, portSpriteVisible,
            Const.PORT_SPRITE_CLASS, Const.EDGE_SPRITE_CLASS_BACKGROUND);

    }

    public void removeEdge(String id) {

        if (graph.getEdge(id) != null) {
            String sourceNodeIdModel = graph.getEdge(id).getSourceNode().getAttribute(Const.GS_UI_LABEL);
            agentMap.get(sourceNodeIdModel).getCommonFeaturesModel().getFeatureSocial().getKnowledge().getTargetMap()
                .remove(graph.getEdge(id).getAttribute(Const.GS_UI_LABEL));
            spriteManager.removeSprite(id.concat(Const.TARGET_PORT_SPRITE));
            spriteManager.removeSprite(id.concat(Const.SOURCE_PORT_SPRITE));
            spriteManager.removeSprite(id.concat(Const.MAIN_SPRITE));
            graph.removeEdge(id);
        }
    }

    /**
     * This interface allows to the service to access to some methods of
     * GraphService
     */
    public interface IGraphEdgeService {

        public boolean getDisplayPort();

        public boolean getDisplayMain();

        public void displaySprite(Sprite sprite, boolean portSpriteVisible, String portSpriteClass,
            String edgeSpriteClassBackground);
    }
}
