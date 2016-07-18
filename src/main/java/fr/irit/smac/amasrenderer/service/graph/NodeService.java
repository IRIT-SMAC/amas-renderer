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

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.agent.Agent;
import fr.irit.smac.amasrenderer.model.agent.feature.social.Target;
import javafx.beans.value.ObservableValue;

/**
 * This service is related to the business logic about the nodes of the graph of
 * agents
 */
public class NodeService {

    private Map<String, Agent> agentMap;
    private MultiGraph              graph;
    private AtomicInteger           idCount;
    private IGraphNodeService       graphNodeService;
    private static NodeService      instance = new NodeService();

    public static NodeService getInstance() {

        return instance;
    }

    public void init(MultiGraph graph, AtomicInteger idCount, IGraphNodeService graphNodeService) {

        this.graph = graph;
        this.idCount = idCount;
        this.graphNodeService = graphNodeService;
    }

    public void setAgentMap(Map<String, Agent> agentMap) {
        this.agentMap = agentMap;
    }

    /**
     * Adds a node. The coordinates are given. This method is called when the
     * user clicks on the graph of agents
     *
     * @param x
     *            the x location of the node
     * @param y
     *            the y location of the node
     */
    public void addNode(double x, double y) {

        File file = new File(getClass().getResource("../../json/initial_agent.json").getFile());
        final ObjectMapper mapper = new ObjectMapper();
        Agent agent = null;
        try {
            String id = idCount.toString();
            Node node = addNodeGraph(id);
            node.setAttribute(Const.NODE_XY, x, y);
            agent = mapper.readValue(file, Agent.class);
            agent.setIdGraph(id);
            agent.setName(id);
            agent.getCommonFeaturesModel().getFeatureBasic().getKnowledge().setId(id);
            agentMap.put(id, agent);
            handleNodeNameChange(agent);
            idCount.incrementAndGet();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Adds a node. The existing model of the agent is given. This method is
     * called when the graph of agents is generated with a json file
     * 
     * @param id
     * @param agent
     */
    public void addNode(String id, Agent agent) {

        addNodeGraph(id);
        agent.setIdGraph(idCount.toString());
        agent.setName(id);
        idCount.incrementAndGet();

        handleNodeNameChange(agent);

    }

    /**
     * Adds a node on the graph of agents
     * 
     * @param id
     * @return the created node
     */
    public Node addNodeGraph(String id) {

        String idGraph = idCount.toString();
        Node node = graph.addNode(idGraph);
        node.setAttribute(Const.NODE_WEIGHT, Const.LAYOUT_WEIGHT_NODE);
        node.setAttribute(Const.GS_UI_LABEL, id);

        return node;
    }

    /**
     * Updates the models when the name of the agent is updated
     * 
     * @param agentModel
     */
    public void handleNodeNameChange(Agent agentModel) {

        agentModel.nameProperty()
            .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {

                agentMap.remove(oldValue);
                agentMap.put(newValue, agentModel);
                graph.getNode(agentModel.getIdGraph()).setAttribute(Const.GS_UI_LABEL, newValue);
                agentModel.setId(newValue);
                agentMap.forEach((k, v) -> {

                    if (!v.getName().equals(newValue)) {
                        Map<String, Target> targets = v.getCommonFeaturesModel().getFeatureSocial().getKnowledge()
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
     * Removes a node
     * 
     * @param id
     */
    public void removeNode(String id) {

        MultiNode node = graph.getNode(id);
        node.getEachEdge().forEach(edge -> graphNodeService.removeEdge(edge.getId()));
        graph.removeNode(node);
        agentMap.remove(id);
    }

    public interface IGraphNodeService {

        public void removeEdge(String id);
    }
}
