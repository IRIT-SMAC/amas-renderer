package fr.irit.smac.amasrenderer.model;

import java.util.List;
import java.util.Map;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.SingleNode;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.service.GraphService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This model is about an agent. An agent is a singleNode
 */
public class AgentModel extends SingleNode implements Node, IModel {

    private StringProperty name;

    private Map<String, Object> attributesMap;

    private Map<String, Map<String,String>> targets;

    private static final String[] REQUIRED_KEY_SINGLE  = { Const.TARGETS };
    private static final String[] PROTECTED_VALUE      = { Const.TARGETS };
    private static final String[] NOT_EXPANDED         = {};
    private static final String[] REQUIRED_KEY_COMPLEX = { "knowledge" };

    public AgentModel(AbstractGraph graph, String id) {

        super(graph, id);
        this.name = new SimpleStringProperty(id);
    }

    /**
     * Gets the targets of the agent
     * 
     * @return the targets
     */
    public Map<String, Map<String,String>> getTargets() {
        return targets;
    }

    /**
     * Adds a target to the agent
     * 
     * @param targetMap
     *            the target to add
     */
    public void addTarget(Map<String, String> targetMap) {
        this.targets.put(targetMap.get("agentTarget"), targetMap);
    }

    /**
     * Gets the attributes of the agent
     * 
     * @return the attributes of the agent
     */
    public Map<String, Object> getAttributesMap() {
        return attributesMap;
    }

    /**
     * Sets the attributes of the agent
     * 
     * @param attributesMap
     *            the attributes
     */
    @SuppressWarnings("unchecked")
    public void setAttributesMap(Map<String, Object> attributesMap) {
        this.attributesMap = attributesMap;
        this.targets = (Map<String, Map<String,String>>) ((Map<String, Object>) attributesMap.get(Const.KNOWLEDGE)).get(Const.TARGETS);
        GraphService.getInstance().getAgentMap().put(id, attributesMap);
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

    @Override
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

    @Override
    public String getNewName(String name) {
        return name;
    }
}
