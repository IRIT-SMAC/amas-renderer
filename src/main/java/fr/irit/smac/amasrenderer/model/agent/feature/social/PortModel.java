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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.IModel;
import fr.irit.smac.amasrenderer.model.ModelWithAttributesMap;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PortModel extends ModelWithAttributesMap implements IModel{

    private StringProperty name;

    private String type;

    private String id;
    
    public PortModel() {
        name = new SimpleStringProperty();
    }

    @Override
    public String toString() {
        return name.get();
    }
    
    public StringProperty idProperty() {
        return name;
    }
    
    @JsonIgnore
    public String getType() {
        return type;
    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonProperty
    public void setId(String id) {
        this.id = id;
        name.set(id);
        attributesMap.put(Const.ID, id);
    }
    
    @JsonProperty
    public void setType(String type) {
        this.type = type;
        attributesMap.put(Const.TYPE, type);
    }

    @Override
    public void setName(String name) {
        this.name.set(name);
    }

    @Override
    public String getName() {
        return name.get();
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
}
