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
package fr.irit.smac.amasrenderer.model.tool;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.IModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This model is about a tool
 */
public class ToolModel implements IModel {

    private StringProperty name;

    private String className;

    @JsonIgnore
    private Map<String, Object> attributesMap = new HashMap<>();

    private static final String[] PROTECTED_VALUE = {};
    private static final String[] NOT_EXPANDED = { Const.AGENT_MAP };

    public ToolModel() {
        attributesMap = new HashMap<>();
        this.name = new SimpleStringProperty();
    }

    @SuppressWarnings("unchecked")
    public ToolModel(String name, Object map) {
        super();

        this.name = new SimpleStringProperty(this.getNewName(name));
        this.className = Const.EXAMPLE_CLASSNAME;

        this.attributesMap = (Map<String, Object>) map;

        this.attributesMap.put(Const.CLASSNAME, this.className);
    }

    /**
     * Gets the attributes of the tool
     * 
     * @return the attributes
     */
    public Map<String, Object> getAttributesMap() {
        return attributesMap;
    }

    /**
     * Sets the attributes of the tool
     * 
     * @param attributesMap
     *            the attributes
     */
    @JsonAnySetter
    public void setAttrMap(String name, Object value) {
        this.attributesMap.put(name, value);
    }

    public void setAttributesMap(Map<String, Object> attributesMap) {
        this.attributesMap = attributesMap;
    }

    @Override
    public String toString() {
        return this.name.get();
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
    public String[] getProtectedValue() {
        return PROTECTED_VALUE;
    }

    @Override
    public String[] getNotExpanded() {
        return NOT_EXPANDED;
    }

    @Override
    public String getNewName(String name) {
        return name.endsWith(Const.TOOL) ? name : name.concat(Const.TOOL);
    }

    @JsonIgnore
    public String getClassName() {
        return this.className;
    }
    
    @JsonAnyGetter
    public Map<String,Object> getMap() {
      return this.attributesMap;
    }
    
    @JsonSetter
    public void setClassName(String className) {
        this.className = className;
    }
    
}
