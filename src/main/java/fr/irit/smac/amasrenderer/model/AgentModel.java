package fr.irit.smac.amasrenderer.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.SingleNode;

import fr.irit.smac.amasrenderer.service.GraphService;

public class AgentModel extends SingleNode implements Node, IConstraintFields{

    private Map<String, Object> attributesMap;
    Map<String, Object>         knowledgeMap = new HashMap<String, Object>();
    ArrayList<String>           targets;
    private String[] REQUIRED_KEY = {"knowledge", "id", "targets"};
    private String[] PROTECTED_VALUE = {"targets"}; 
    private String[] NOT_EXPANDED = {};
    
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
    
    public String[] getRequiredKey() {
        return REQUIRED_KEY;
    }
    
    public String[] getProtectedValue() {
        return PROTECTED_VALUE;
    }

    @Override
    public String[] getNotExpanded() {
        return this.NOT_EXPANDED;
    }
}
