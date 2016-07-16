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
package fr.irit.smac.amasrenderer.model.agent.feature;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FeatureModel extends AbstractFeatureModel {

    private String className;
    
    private Knowledge knowledge;
        
    public FeatureModel() {
    }
    
    @JsonProperty
    public void setClassName(String className) {
        this.className = className;
        this.attributesMap.put("className", className);
    }

    @JsonProperty
    public void setKnowledge(Knowledge knowledge) {
        this.knowledge = knowledge;
        this.attributesMap.put("knowledge", knowledge.getAttributesMap());
    }

    @JsonProperty
    public void setSkill(Skill skill) {
        this.skill = skill;
        this.attributesMap.put("skill", skill.getAttributesMap());
    }

    @JsonIgnore
    public String getClassName() {
        return className;
    }

    @JsonIgnore
    public Knowledge getKnowledge() {
        return knowledge;
    }
}
