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
package fr.irit.smac.amasrenderer.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private Map<String, Object> commonFeatures;

    private Map<String, TargetModel> targets;

    private Map<String, Object> portMap;

    private Map<String, Object> targetMap;

    private static final String[] REQUIRED_KEY_SINGLE  = {};
    private static final String[] PROTECTED_VALUE      = {};
    private static final String[] NOT_EXPANDED         = { Const.TARGET_MAP };
    private static final String[] REQUIRED_KEY_COMPLEX = { Const.SKILL, Const.KNOWLEDGE, Const.PORT_MAP };

    public AgentModel(AbstractGraph graph, String id) {

        super(graph, id);
        this.name = new SimpleStringProperty(id);
        this.targets = new HashMap<>();
        this.targetMap = new HashMap<>();
        this.portMap = new HashMap<>();
    }

    /**
     * Adds a target to the agent
     * 
     * @param target
     *            the target to add
     */
    public void addTarget(TargetModel targetModel, String id) {
        this.targets.put(id, targetModel);
        this.targetMap.put(id, targetModel.getAttributesMap());
    }

    public void removeTarget(String id) {
        this.targets.remove(id);
        this.targetMap.remove(id);
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
        return this.name.get();
    }

    @Override
    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return this.name;
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
        this.commonFeatures = commonFeatures;

        commonFeatures.put(Const.CLASSNAME, Const.EXAMPLE_CLASSNAME);
        Map<String, Object> featureBasic = this.createFeature(Const.FEATURE_BASIC, Const.FEATURE_DEFAULT_CLASSNAME,
            Const.FEATURE_BASIC_KNOWLEDGE_DEFAULT_CLASSNAME,
            Const.FEATURE_BASIC_SKILL_DEFAULT_CLASSNAME);
        commonFeatures.put(Const.FEATURE_BASIC, featureBasic);
        ((Map<String, Object>) featureBasic.get(Const.KNOWLEDGE)).put(Const.ID, this.id);

        Map<String, Object> featureSocial = this.createFeature(Const.FEATURE_SOCIAL, Const.FEATURE_DEFAULT_CLASSNAME,
            Const.FEATURE_SOCIAL_KNOWLEDGE_DEFAULT_CLASSNAME,
            Const.FEATURE_SOCIAL_SKILL_DEFAULT_CLASSNAME);
        commonFeatures.put(Const.FEATURE_SOCIAL,
            featureSocial);
        ((Map<String, Object>) featureSocial.get(Const.KNOWLEDGE)).put(Const.PORT_MAP, this.portMap);
        ((Map<String, Object>) featureSocial.get(Const.KNOWLEDGE)).put(Const.TARGET_MAP, this.targetMap);

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

    public Map<String, TargetModel> getTargets() {
        return this.targets;
    }

    public Map<String, Object> getPortMap() {
        return this.portMap;
    }

    public Map<String, Object> getCommonFeatures() {
        return this.commonFeatures;
    }

    public void setCommonFeatures(Map<String, Object> commonFeatures) {
        this.commonFeatures = commonFeatures;
    }

    public void setId(String id) {
        ((Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) this.attributesMap
            .get(Const.COMMON_FEATURES)).get(Const.FEATURE_BASIC)).get(Const.KNOWLEDGE)).put(Const.ID, id);
        this.attributesMap.put(Const.ID, id);
    }

}
