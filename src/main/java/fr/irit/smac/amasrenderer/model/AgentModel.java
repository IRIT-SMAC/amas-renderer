package fr.irit.smac.amasrenderer.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.SingleNode;

import fr.irit.smac.amasrenderer.service.GraphService;

public class AgentModel extends SingleNode implements Node, IConstraintFields {

    private Map<String, Object> attributesMap;

    private Map<String, Object> knowledgeMap = new HashMap<>();

    private ArrayList<String> targets;

    private final String[] requiredKeySingle  = { "id", "targets" };
    private final String[] protectedValue     = { "targets" };
    private final String[] notExpanded        = {};
    private final String[] requiredKeyComplex = { "knowledge" };

    public AgentModel(AbstractGraph graph, String id) {

        super(graph, id);
        attributesMap = new HashMap<String, Object>();
        attributesMap.put("id", id);
        Map<String, Object> knowledgeMap = new HashMap<String, Object>();
        attributesMap.put("knowledge", knowledgeMap);
        targets = new ArrayList<String>();
        knowledgeMap.put("targets", targets);
        GraphService.getInstance().getAgentMap().put(id, attributesMap);
    }

    public ArrayList<String> getTargets() {
        return targets;
    }

    public void addTarget(String target) {
        this.targets.add(target);
    }

    public Map<String, Object> getAttributesMap() {
        return attributesMap;
    }

    @Override
    public String[] getRequiredKeySingle() {
        return requiredKeySingle;
    }

    @Override
    public String[] getProtectedValue() {
        return protectedValue;
    }

    @Override
    public String[] getNotExpanded() {
        return this.notExpanded;
    }

    @Override
    public String[] getRequiredKeyComplex() {
        return this.requiredKeyComplex;
    }
}
