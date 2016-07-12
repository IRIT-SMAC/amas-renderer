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

import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.MultiNode;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.irit.smac.amasrenderer.Const;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This model is about an agent. An agent is a MultiNode
 */
public class AgentModel extends MultiNode implements Node, IModel {

    private StringProperty name;

    private Map<String, Object> attributesMap = new HashMap<>();

    @JsonIgnore
    private Map<String, Object> commonFeatures;

    private Map<String, TargetModel> targets;

    private Map<String, Object> portMap;

    private Map<String, Object> targetMap;

    private static final String[] REQUIRED_KEY_SINGLE  = {};
    private static final String[] PROTECTED_VALUE      = {};
    private static final String[] NOT_EXPANDED         = { Const.TARGET_MAP };
    private static final String[] REQUIRED_KEY_COMPLEX = { Const.SKILL, Const.KNOWLEDGE, Const.PORT_MAP };

    public AgentModel(@JacksonInject AbstractGraph graph, @JacksonInject String id) {

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

    @JsonAnySetter
    public void setAttributesMap(String name, Object value) {
        this.attributesMap.put(name, value);
    }

    public void setAttributes(Map<String, Object> attributesMap) {
        this.attributesMap = attributesMap;
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
    // public void setAttributesMap(Map<String, Object> attributesMap) {
    // this.attributesMap = attributesMap;
    // }

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

    public String getNewName(String name) {
        return name;
    }

    public Map<String, TargetModel> getTargets() {
        return this.targets;
    }

    public Map<String, Object> getPortMap() {
        return this.portMap;
    }

    public void setId(String id) {
        ((Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) this.attributesMap
            .get(Const.COMMON_FEATURES)).get(Const.FEATURE_BASIC)).get(Const.KNOWLEDGE)).put(Const.ID, id);
    }

}
