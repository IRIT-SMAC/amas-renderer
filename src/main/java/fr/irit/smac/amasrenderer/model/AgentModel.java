package fr.irit.smac.amasrenderer.model;

import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.MultiNode;

import fr.irit.smac.amasrenderer.Const;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This model is about an agent. An agent is a MultiNode
 */
public class AgentModel extends MultiNode implements Node, IModel {

    private StringProperty name;

    private Map<String, Object> attributesMap;

    private Map<String, Object> targets;

    private Map<String, Object> portMap;

    private static final String[] REQUIRED_KEY_SINGLE  = {};
    private static final String[] PROTECTED_VALUE      = {};
    private static final String[] NOT_EXPANDED         = { Const.TARGET_MAP };
    private static final String[] REQUIRED_KEY_COMPLEX = { Const.SKILL, Const.KNOWLEDGE, Const.PORT_MAP };

    public AgentModel(AbstractGraph graph, String id) {

        super(graph, id);
        this.name = new SimpleStringProperty(id);
    }

    /**
     * Adds a target to the agent
     * 
     * @param target
     *            the target to add
     */
    public void addTarget(String id, Map<String, Object> targetMap) {
        this.targets.put(id, targetMap);
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
    public void setAttributesMap(Map<String, Object> attributesMap) {
        this.attributesMap = attributesMap;
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

    public void initAttributesMap() {

        HashMap<String, Object> attributesMap = new HashMap<>();
        attributesMap.put(Const.ID, this.id);

        Map<String, Object> primaryFeature = this.createFeature(Const.PRIMARY_FEATURE, Const.EXAMPLE_CLASSNAME,
            Const.EXAMPLE_CLASSNAME, Const.EXAMPLE_CLASSNAME);

        Map<String, Object> commonFeatures = new HashMap<>();
        commonFeatures.put(Const.CLASSNAME, Const.EXAMPLE_CLASSNAME);
        commonFeatures.put(Const.FEATURE_BASIC,
            this.createFeature(Const.FEATURE_BASIC, Const.FEATURE_DEFAULT_CLASSNAME,
                Const.FEATURE_BASIC_KNOWLEDGE_DEFAULT_CLASSNAME,
                Const.FEATURE_BASIC_SKILL_DEFAULT_CLASSNAME));
        
        ((Map<String, Object>) ((Map<String, Object>) commonFeatures
            .get(Const.FEATURE_BASIC)).get(Const.KNOWLEDGE)).put(Const.ID, this.id);
        
        commonFeatures.put(Const.FEATURE_SOCIAL,
            this.createFeature(Const.FEATURE_SOCIAL, Const.FEATURE_DEFAULT_CLASSNAME,
                Const.FEATURE_SOCIAL_KNOWLEDGE_DEFAULT_CLASSNAME,
                Const.FEATURE_SOCIAL_SKILL_DEFAULT_CLASSNAME));

        Map<String, Object> portMap = new HashMap<>();
        ((Map<String, Object>) ((Map<String, Object>) commonFeatures
            .get(Const.FEATURE_SOCIAL)).get(Const.KNOWLEDGE)).put(Const.PORT_MAP, portMap);

        this.portMap = portMap;

        Map<String, Object> targetMap = new HashMap<>();

        ((Map<String, Object>) ((Map<String, Object>) commonFeatures
            .get(Const.FEATURE_SOCIAL)).get(Const.KNOWLEDGE)).put(Const.TARGET_MAP, targetMap);

        this.targets = targetMap;

        attributesMap.put(Const.PRIMARY_FEATURE, primaryFeature);
        attributesMap.put(Const.COMMON_FEATURES, commonFeatures);

        this.attributesMap = attributesMap;
    }

    public Map<String, Object> createFeature(String id, String className, String knowledgeClassName,
        String skillClassName) {

        Map<String, Object> feature = new HashMap<>();
        feature.put(Const.CLASSNAME, className);

        Map<String, Object> knowledge = new HashMap<>();
        knowledge.put(Const.CLASSNAME, knowledgeClassName);
        feature.put(Const.KNOWLEDGE, knowledge);

        Map<String, Object> skill = new HashMap<>();
        skill.put(Const.CLASSNAME, skillClassName);
        feature.put(Const.SKILL, skill);

        return feature;
    }

    public Map<String, Object> getTargets() {
        return targets;
    }

    public Map<String, Object> getPortMap() {
        return portMap;
    }

    public void setTargets(Map<String, Object> targets) {
        this.targets = targets;
        ((Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) attributesMap
            .get(Const.COMMON_FEATURES)).get(Const.FEATURE_SOCIAL)).get(Const.KNOWLEDGE)).remove(Const.TARGET_MAP);
        ((Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) attributesMap.get(Const.COMMON_FEATURES))
            .get(Const.FEATURE_SOCIAL)).get(Const.KNOWLEDGE)).put(Const.TARGET_MAP, this.targets);
    }

}
