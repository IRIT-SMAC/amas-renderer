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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.Const;

public abstract class ModelWithAttributesMap {

    private String className;

    @JsonIgnore
    protected Map<String,Object> attributesMap = new HashMap<>();
    
    @JsonIgnore
    public String getClassName() {
        return this.className;
    }
    
    @JsonProperty
    public void setClassName(String className) {
        this.className = className;
        attributesMap.put(Const.CLASSNAME, className);
    }
    
    @JsonAnyGetter
    public Map<String, Object> getAttributesMap() {
        return attributesMap;
    }
    
    @JsonAnySetter
    public void setAttributesMap(String name, Object value) {
        this.attributesMap.put(name, value);
    }
}
