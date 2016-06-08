package fr.irit.smac.amasrenderer.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.SingleNode;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.service.GraphService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AgentModel extends SingleNode implements Node, IModel {

    private StringProperty name;

    private Map<String, Object> attributesMap;

    private Map<String, Object> knowledgeMap = new HashMap<>();

    private List<String> targets;

    private static final String[] REQUIRED_KEY_SINGLE  = { Const.TARGETS };
    private static final String[] PROTECTED_VALUE     = { Const.TARGETS };
    private static final String[] NOT_EXPANDED        = {};
    private static final String[] REQUIRED_KEY_COMPLEX = { "knowledge" };

    public AgentModel(AbstractGraph graph, String id) {

        super(graph, id);
        this.name = new SimpleStringProperty(id);
        attributesMap = new HashMap<>();
        attributesMap.put("id", id);
        Map<String, Object> knowledge = new HashMap<>();
        attributesMap.put("knowledge", knowledge);
        targets = new ArrayList<>();
        knowledge.put(Const.TARGETS, targets);
        GraphService.getInstance().getAgentMap().put(id, attributesMap);
    }

    public List<String> getTargets() {
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
        return REQUIRED_KEY_SINGLE;
    }

    @Override
    public String[] getProtectedValue() {
        return PROTECTED_VALUE;
    }

    @Override
    public String[] getNotExpanded() {
        return NOT_EXPANDED;
    }

    @Override
    public String[] getRequiredKeyComplex() {
        return REQUIRED_KEY_COMPLEX;
    }

    public String getName() {
        return name.get();
    }

    @Override
    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }
}
