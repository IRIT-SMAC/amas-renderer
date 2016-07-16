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
package fr.irit.smac.amasrenderer.model.agent;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.IModel;
import fr.irit.smac.amasrenderer.model.agent.feature.CommonFeaturesModel;
import fr.irit.smac.amasrenderer.model.agent.feature.FeatureModel;
import fr.irit.smac.amasrenderer.model.agent.feature.social.TargetModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This model is about an agent. An agent is a MultiNode
 */
public class AgentModel implements IModel {

    private StringProperty name;

    @JsonIgnore
    private Map<String, Object> attributesMap = new HashMap<>();

    @JsonProperty
    private FeatureModel primaryFeature;

    @JsonProperty
    private CommonFeaturesModel commonFeatures;

    @JsonIgnore
    private String idGraph;

    private static final String[] PROTECTED_VALUE = {};
    private static final String[] NOT_EXPANDED    = { Const.TARGET_MAP };

    public AgentModel() {

        this.name = new SimpleStringProperty();
    }

    /**
     * Adds a target to the agent
     * 
     * @param target
     *            the target to add
     */
    public void addTarget(TargetModel targetModel, String id) {
        getCommonFeaturesModel().getFeatureSocial().getKnowledge().getTargetMap().put(id,
            targetModel);
    }

    public void removeTarget(String id) {
        getCommonFeaturesModel().getFeatureSocial().getKnowledge().getTargetMap().remove(id);
    }

    @JsonAnySetter
    public void setAttributesMap(String name, Object value) {
        this.attributesMap.put(name, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getAttributeMap() {
        return attributesMap;
    }

    public void setAttributes(Map<String, Object> attributesMap) {
        this.attributesMap = attributesMap;
    }

    /**
     * Gets the attributes of the agent
     * 
     * @return the attributes of the agent
     */
    public Map<String, Object> getAttributesMap() {
        return attributesMap;
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

    public String getNewName(String name) {
        return name;
    }
    
    public void setId(String id) {
        this.commonFeatures.getFeatureBasic().getKnowledge().setId(id);
    }

    @JsonIgnore
    public void setIdGraph(String idGraph) {
        this.idGraph = idGraph;
    }

    @JsonIgnore
    public String getIdGraph() {
        return this.idGraph;
    }

    @JsonIgnore
    public CommonFeaturesModel getCommonFeaturesModel() {
        return this.commonFeatures;
    }

    public FeatureModel getPrimaryFeature() {
        return this.primaryFeature;
    }

}
