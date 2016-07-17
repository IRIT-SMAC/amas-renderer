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

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.model.agent.feature.basic.FeatureBasicModel;
import fr.irit.smac.amasrenderer.model.agent.feature.social.FeatureSocialModel;

public class CommonFeaturesModel {

    private FeatureSocialModel featureSocial;

    private FeatureBasicModel featureBasic;
    
    private Map<String,AbstractFeatureModel> features = new HashMap<>();
    
    @JsonProperty
    private String className;
    
    public CommonFeaturesModel() {
        featureSocial = new FeatureSocialModel();
        featureBasic = new FeatureBasicModel();
    }
    
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @JsonIgnore
    public FeatureBasicModel getFeatureBasic() {
        return featureBasic;
    }

    @JsonIgnore
    public FeatureSocialModel getFeatureSocial() {
        return featureSocial;
    }

    @JsonAnyGetter
    public Map<String,AbstractFeatureModel> getMap() {
      return features;
    }
    
    @JsonAnySetter
    public void setAttributesMap(String name, AbstractFeatureModel value) {
        features.put(name, value);
        value.setName(name);
    }
    
    @JsonIgnore
    public Map<String, AbstractFeatureModel> getFeatures() {
        return features;
    }

    @JsonProperty
    public void setFeatureSocial(FeatureSocialModel featureSocial) {
        this.featureSocial = featureSocial;
        features.put("featureSocial",featureSocial);
    }

    @JsonProperty
    public void setFeatureBasic(FeatureBasicModel featureBasic) {
        this.featureBasic = featureBasic;
        features.put("featureBasic",featureBasic);
    }
    
    
}
