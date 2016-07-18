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

import fr.irit.smac.amasrenderer.model.agent.feature.basic.FeatureBasic;
import fr.irit.smac.amasrenderer.model.agent.feature.social.FeatureSocial;

/**
 * This model is about the common features of an agent
 */
public class CommonFeatures {

    private FeatureSocial featureSocial;

    private FeatureBasic featureBasic;

    private Map<String, AbstractFeature> features = new HashMap<>();

    @JsonProperty
    private String className;

    public CommonFeatures() {
        featureSocial = new FeatureSocial();
        featureBasic = new FeatureBasic();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @JsonIgnore
    public FeatureBasic getFeatureBasic() {
        return featureBasic;
    }

    @JsonIgnore
    public FeatureSocial getFeatureSocial() {
        return featureSocial;
    }

    @JsonAnyGetter
    public Map<String, AbstractFeature> getMap() {
        return features;
    }

    @JsonAnySetter
    public void setAttributesMap(String name, AbstractFeature value) {
        features.put(name, value);
        value.setName(name);
    }

    @JsonIgnore
    public Map<String, AbstractFeature> getFeatures() {
        return features;
    }

    @JsonProperty
    public void setFeatureSocial(FeatureSocial featureSocial) {
        this.featureSocial = featureSocial;
        features.put("featureSocial", featureSocial);
    }

    @JsonProperty
    public void setFeatureBasic(FeatureBasic featureBasic) {
        this.featureBasic = featureBasic;
        features.put("featureBasic", featureBasic);
    }

}
