package fr.irit.smac.amasrenderer.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.SingleNode;

import fr.irit.smac.amasrenderer.service.GraphService;

public class AgentModel extends SingleNode {

    private Map<String, Object> attributesMap;
    Map<String, Object>         knowledgeMap = new HashMap<String, Object>();

    public AgentModel(AbstractGraph graph, String id) {

        super(graph, id);
        attributesMap = new HashMap<String, Object>();
        attributesMap.put("id", id);
        Map<String, Object> knowledgeMap = new HashMap<String, Object>();
        attributesMap.put("knowledge", knowledgeMap);
        ArrayList<String> targets = new ArrayList<String>();
        knowledgeMap.put("targets", targets);
        GraphService.getInstance().getGraph().getAgentMap().put(id, attributesMap);
    }

}
