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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.IModel;
import fr.irit.smac.amasrenderer.model.ModelWithAttributesMap;
import fr.irit.smac.amasrenderer.model.agent.feature.CommonFeaturesModel;
import fr.irit.smac.amasrenderer.model.agent.feature.FeatureModel;
import fr.irit.smac.amasrenderer.model.agent.feature.social.TargetModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This model is about an agent. An agent is a MultiNode
 */
public class AgentModel extends ModelWithAttributesMap implements IModel {

    private StringProperty name;

    @JsonProperty
    private FeatureModel primaryFeature;

    @JsonProperty
    private CommonFeaturesModel commonFeatures;

    @JsonIgnore
    private String idGraph;

    private static final String[] PROTECTED_VALUE = {};
    private static final String[] NOT_EXPANDED    = { Const.TARGET_MAP };

    public AgentModel() {

        name = new SimpleStringProperty();
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
        return name.get();
    }

    @Override
    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getNewName(String name) {
        return name;
    }
    
    public void setId(String id) {
        commonFeatures.getFeatureBasic().getKnowledge().setId(id);
    }

    @JsonIgnore
    public void setIdGraph(String idGraph) {
        this.idGraph = idGraph;
    }

    @JsonIgnore
    public String getIdGraph() {
        return idGraph;
    }

    @JsonIgnore
    public CommonFeaturesModel getCommonFeaturesModel() {
        return commonFeatures;
    }

    public FeatureModel getPrimaryFeature() {
        return primaryFeature;
    }

}
