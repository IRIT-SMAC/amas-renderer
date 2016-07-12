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

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.irit.smac.amasrenderer.Const;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TargetModel implements IModel {

    private StringProperty name;

    private Map<String, Object> attributesMap = new HashMap<>();

    private String agentId;

    @JsonIgnore
    private String agentTarget;

    @JsonIgnore
    private String portSource;

    @JsonIgnore
    private String portTarget;

    private static final String[] REQUIRED_KEY_SINGLE  = { Const.AGENT_TARGET, Const.PORT_SOURCE, Const.PORT_TARGET,
        Const.CLASSNAME };
    private static final String[] PROTECTED_VALUE      = { Const.AGENT_TARGET, Const.PORT_SOURCE, Const.PORT_TARGET };
    private static final String[] NOT_EXPANDED         = {};
    private static final String[] REQUIRED_KEY_COMPLEX = {};


    public TargetModel (@JacksonInject String id) {
        this.attributesMap = new HashMap<String, Object>();
        this.name = new SimpleStringProperty(id);
    }

    public TargetModel(String id, Map<String, Object> attributesMap) {
        this.attributesMap = attributesMap;
        this.agentTarget = (String) attributesMap.get(Const.AGENT_TARGET);
        this.portSource = (String) attributesMap.get(Const.PORT_SOURCE);
        this.portTarget = (String) attributesMap.get(Const.PORT_TARGET);
        this.name = new SimpleStringProperty(id);
    }

    public Map<String, Object> getAttributesMap() {
        return this.attributesMap;
    }

    public void setAttributesMp(Map<String, Object> attributesMap) {
        this.attributesMap = attributesMap;
    }

    @Override
    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public String getName() {

        return this.name.get();
    }

    @Override
    public String getNewName(String name) {

        return name;
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

    public String getAgentId() {
        return this.agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentTarget() {
        return this.agentTarget;
    }

    public void setAgentTarget(String newValue) {
        this.agentTarget = newValue;
        this.attributesMap.put(Const.AGENT_TARGET, newValue);
    }

    public String getPortSource() {
        return portSource;
    }

    public String getPortTarget() {
        return portTarget;
    }

    /**
     * Used by Jackson to deserialize the infrastructure
     * 
     * @param name
     *            name of the attribute
     * @param value
     *            value of the attribute
     */
    @JsonAnySetter
    public void setAttributesMap(String name, Object value) {
        this.attributesMap.put(name, value);
    }
}
