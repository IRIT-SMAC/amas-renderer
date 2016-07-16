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

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.tool.ToolsModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This model is about the infrastructure
 */
public class InfrastructureModel extends ModelWithAttributesMap implements IModel {

    private String className;
    
    @JsonProperty
    private ToolsModel services;

    private static final String[] PROTECTED_VALUE      = {};
    private static final String[] NOT_EXPANDED         = { "Service" };

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
        return name;
    }

    @JsonIgnore
    public String getClassName() {
        return this.className;
    }
    
    @JsonProperty
    public void setClassName(String className) {
        this.className = className;
        attributesMap.put(Const.CLASSNAME, className);
    }

    public ToolsModel getServices() {
        return this.services;
    }

}
