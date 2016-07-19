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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.agent.feature.Knowledge;

/**
 * This model is about the social knowledge of an agent
 */
public class KnowledgeSocial extends Knowledge {

    private Map<String, Target> targetMap;

    private Map<String, Port> portMap;

    public KnowledgeSocial() {
        targetMap = new HashMap<>();
        attributesMap.put(Const.TARGET_MAP, targetMap);
        portMap = new HashMap<>();
        attributesMap.put(Const.PORT_MAP, portMap);
    }

    @JsonIgnore
    public Map<String, Target> getTargetMap() {
        return targetMap;
    }

    @JsonIgnore
    public Map<String, Port> getPortMap() {
        return portMap;
    }

    @JsonProperty
    public void setPortMap(Map<String, Port> portMap) {
        this.portMap = portMap;
        attributesMap.put(Const.PORT_MAP, portMap);
    }

    @JsonProperty
    public void setTargetMap(Map<String, Target> targetMap) {
        this.targetMap = targetMap;
        attributesMap.put(Const.TARGET_MAP, targetMap);
    }
}
