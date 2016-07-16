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
package fr.irit.smac.amasrenderer.model.agent.feature.social;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.model.IModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PortModel implements IModel{

    private StringProperty name;

    private String type;

    private String id;
    
    private String className;

    @JsonIgnore
    private Map<String,Object> attributesMap = new HashMap<>();
    
    public PortModel() {
        this.name = new SimpleStringProperty();
    }

    @JsonProperty
    public void setId(String id) {
        this.id = id;
        this.name.set(id);
        this.attributesMap.put("id", id);
    }
    
    @Override
    public String toString() {
        return this.name.get();
    }
    
    public StringProperty idProperty() {
        return this.name;
    }
    
    public Map<String, Object> getAttributesMap() {
        return attributesMap;
    }

    @JsonAnyGetter
    public Map<String,Object> getMap() {
      return this.attributesMap;
    }

    @Override
    public void setName(String name) {
        this.name.set(name);
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
    public String[] getProtectedValue() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] getNotExpanded() {
        // TODO Auto-generated method stub
        return null;
    }

    @JsonProperty
    public void setType(String type) {
        this.type = type;
        this.attributesMap.put("type", type);
    }

    @JsonProperty
    public void setClassName(String className) {
        this.className = className;
        this.attributesMap.put("className", className);
    }
}
