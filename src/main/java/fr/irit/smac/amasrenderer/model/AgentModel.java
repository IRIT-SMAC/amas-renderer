package fr.irit.smac.amasrenderer.model;

import java.util.HashMap;
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

    private static final String[] REQUIRED_KEY_SINGLE  = {};
    private static final String[] PROTECTED_VALUE      = {};
    private static final String[] NOT_EXPANDED         = { Const.TARGETS };
    private static final String[] REQUIRED_KEY_COMPLEX = { "knowledge" };

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
        ((Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) this.attributesMap.get("commonFeatures")).get("featureSocial")).get("knowledge")).get("targets")).put(id,
            targetMap);
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

    public void initAttributesMap() {

        HashMap<String, Object> attributesMap = new HashMap<>();
        attributesMap.put(Const.ID, id);

        Map<String,Object> primaryFeature = this.createFeature("primaryFeature", Const.EXAMPLE_CLASSNAME, Const.EXAMPLE_CLASSNAME, Const.EXAMPLE_CLASSNAME);

        Map<String, Object> commonFeatures = new HashMap<>();
        commonFeatures.put(Const.CLASSNAME, Const.EXAMPLE_CLASSNAME);
        commonFeatures.put("featureBasic",
            this.createFeature("featureBasic", "fr.irit.smac.amasfactory.agent.features.impl.Feature",
                "fr.irit.smac.amasfactory.agent.features.basic.impl.KnowledgeBasic",
                "fr.irit.smac.amasfactory.agent.features.basic.impl.SkillBasic"));
        commonFeatures.put("featureSocial",
            this.createFeature("featureSocial", "fr.irit.smac.amasfactory.agent.features.impl.Feature",
                "fr.irit.smac.amasfactory.agent.features.social.impl.KnowledgeSocial",
                "fr.irit.smac.amasfactory.agent.features.social.impl.SkillSocial"));

        ((Map<String, Object>) ((Map<String, Object>) commonFeatures.get("featureSocial")).get("knowledge")).put(Const.TARGETS, new HashMap<>());

        attributesMap.put("primaryFeature", primaryFeature);
        attributesMap.put("commonFeatures",commonFeatures);
        
        this.attributesMap = attributesMap;
    }

    public Map<String, Object> createFeature(String id, String className, String knowledgeClassName,
        String skillClassName) {

        Map<String, Object> feature = new HashMap<>();
        feature.put(Const.CLASSNAME, className);

        Map<String, Object> knowledge = new HashMap<>();
        knowledge.put(Const.CLASSNAME, knowledgeClassName);
        feature.put("knowledge", knowledge);

        Map<String, Object> skill = new HashMap<>();
        skill.put(Const.CLASSNAME, skillClassName);
        feature.put("skill", skill);

        return feature;
    }
}
