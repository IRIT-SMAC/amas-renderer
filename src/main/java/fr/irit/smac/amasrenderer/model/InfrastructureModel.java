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

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.model.tool.ToolsModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This model is about the infrastructure
 */
public class InfrastructureModel implements IModel {

    private Map<String, Object> attributesMap = new HashMap<>();

    
    @JsonProperty
    private String className;
    
    @JsonProperty
    private ToolsModel services;

    private static final String[] REQUIRED_KEY_SINGLE  = {};
    private static final String[] PROTECTED_VALUE      = {};
    private static final String[] NOT_EXPANDED         = { "Service" };
    private static final String[] REQUIRED_KEY_COMPLEX = {};

    private StringProperty name;

    public InfrastructureModel() {
        this.name = new SimpleStringProperty();
    }

    public InfrastructureModel(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public InfrastructureModel(String key, Map<String, Object> value) {
        this.name = new SimpleStringProperty(key);
        this.attributesMap = value;
    }

    @Override
    public String toString() {
        return this.name.get();
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

    /**
     * Used by Jackson to serialize the infrastructure
     * 
     * @return the attributes
     */
    @JsonAnyGetter
    public Map<String, Object> getAttributesMap() {
        return this.attributesMap;
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

    public void setAttributes(Map<String, Object> attributesMap) {
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
    public String getNewName(String name) {
        return name;
    }

    public String getClassName() {
        return this.className;
    }
    
    public ToolsModel getServices() {
        return this.services;
    }

}
