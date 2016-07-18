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

/**
 * This model is about the feature of an agent
 */
public class Feature extends AbstractFeature {

    private Knowledge knowledge;

    public Feature() {
        // Needed by Jackson        
    }

    @JsonProperty
    public void setKnowledge(Knowledge knowledge) {
        this.knowledge = knowledge;
        attributesMap.put("knowledge", knowledge.getAttributesMap());
    }

    @JsonProperty
    @Override
    public void setSkill(Skill skill) {
        this.skill = skill;
        attributesMap.put("skill", skill.getAttributesMap());
    }

    @JsonIgnore
    public Knowledge getKnowledge() {
        return knowledge;
    }
}
