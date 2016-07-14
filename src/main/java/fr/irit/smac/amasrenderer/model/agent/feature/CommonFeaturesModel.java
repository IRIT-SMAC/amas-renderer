package fr.irit.smac.amasrenderer.model.agent.feature;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.model.agent.feature.basic.FeatureBasicModel;
import fr.irit.smac.amasrenderer.model.agent.feature.social.FeatureSocialModel;

public class CommonFeaturesModel {

    @JsonProperty
    private FeatureSocialModel featureSocial;

    @JsonProperty
    private FeatureBasicModel featureBasic;
    
    private Map<String,FeatureModel> features = new HashMap<>();
    
    public CommonFeaturesModel() {
        this.featureSocial = new FeatureSocialModel();
        this.featureBasic = new FeatureBasicModel();
    }
    
    public FeatureBasicModel getFeatureBasic() {
        return featureBasic;
    }

    public FeatureSocialModel getFeatureSocial() {
        return featureSocial;
    }

    @JsonAnyGetter
    public Map<String,FeatureModel> getMap() {
      return this.features;
    }
    
    @JsonAnySetter
    public void setAttributesMap(String name, FeatureModel value) {
        this.features.put(name, value);
    }
    
    public Map<String, FeatureModel> getFeatures() {
        return features;
    }
}
