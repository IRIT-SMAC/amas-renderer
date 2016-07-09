package fr.irit.smac.amasrenderer.model;

import java.util.Map;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.MultiNode;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.service.GraphService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This model is about an agent. An agent is a MultiNode
 */
public class AgentModel extends MultiNode implements Node, IModel {

    private StringProperty name;

    private Map<String, Object> attributesMap;

    private Map<String, TargetModel> targets;

    private static final String[] REQUIRED_KEY_SINGLE  = {};
    private static final String[] PROTECTED_VALUE      = {};
    private static final String[] NOT_EXPANDED         = { Const.TARGETS };
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
    public Map<String, TargetModel> getTargets() {
        return targets;
    }

    /**
     * Adds a target to the agent
     * 
     * @param target
     *            the target to add
     */
    public void addTarget(TargetModel target) {
        this.targets.put(target.getName(), target);
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
        this.targets = (Map<String, TargetModel>) ((Map<String, Object>) attributesMap.get(Const.KNOWLEDGE))
            .get(Const.TARGETS);
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
