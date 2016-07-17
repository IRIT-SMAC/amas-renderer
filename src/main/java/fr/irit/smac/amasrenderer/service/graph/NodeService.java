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
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.agent.AgentModel;
import fr.irit.smac.amasrenderer.model.agent.feature.social.TargetModel;
import javafx.beans.value.ObservableValue;

public class NodeService {

    private static NodeService instance = new NodeService();

    public static NodeService getInstance() {

        return instance;
    }

    private Map<String, AgentModel> agentMap;
    private MultiGraph              graph;
    private AtomicInteger                 idCount;
    private IGraphNodeService            graphNodeService;

    public void init(MultiGraph graph, AtomicInteger idCount, IGraphNodeService graphNodeService) {

        this.graph = graph;
        this.idCount = idCount;
        this.graphNodeService = graphNodeService;
    }

    public void setAgentMap(Map<String, AgentModel> agentMap) {
        this.agentMap = agentMap;
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

        File file = new File(getClass().getResource("../../json/initial_agent.json").getFile());
        final ObjectMapper mapper = new ObjectMapper();
        AgentModel agent = null;
        try {
            String id = idCount.toString();
            addNodeGraph(id);
            agent = mapper.readValue(file, AgentModel.class);
            agent.setIdGraph(id);
            agent.setName(id);
            agent.getCommonFeaturesModel().getFeatureBasic().getKnowledge().setId(id);
            agentMap.put(id, agent);
            this.handleNodeNameChange(agent);
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
     * @param id
     *            the id of the node
     * @param attributesMap
     *            the attributes
     */
    public void addNode(String id, AgentModel agent) {

        addNodeGraph(id);
        agent.setIdGraph(idCount.toString());
        agent.setName(id);
        idCount.incrementAndGet();

        this.handleNodeNameChange(agent);

    }

    public Node addNodeGraph(String id) {

        String idGraph = idCount.toString();
        Node node = this.graph.addNode(idGraph);
        node.setAttribute(Const.NODE_WEIGHT, Const.LAYOUT_WEIGHT_NODE);
        node.setAttribute(Const.GS_UI_LABEL, id);

        return node;
    }

    public void handleNodeNameChange(AgentModel agentModel) {

        agentModel.nameProperty()
            .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {

                agentMap.remove(oldValue);
                agentMap.put(newValue, agentModel);
                graph.getNode(agentModel.getIdGraph()).setAttribute(Const.GS_UI_LABEL, newValue);
                agentModel.setId(newValue);
                agentMap.forEach((k, v) -> {

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
     * Removes a node.
     *
     * @param n
     *            the node
     * @return
     */
    public Collection<Edge> removeNode(String id) {

        MultiNode node = graph.getNode(id);
        node.getEachEdge().forEach(edge -> graphNodeService.removeEdge(edge.getId()));
        this.graph.removeNode(node);
        this.agentMap.remove(id);

        return node.getEdgeSet();
    }

    public interface IGraphNodeService {

        public void removeEdge(String id);
    }
}
