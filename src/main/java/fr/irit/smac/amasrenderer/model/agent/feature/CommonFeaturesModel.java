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
        this.featureSocial = new FeatureSocialModel();
        this.featureBasic = new FeatureBasicModel();
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
      return this.features;
    }
    
    @JsonAnySetter
    public void setAttributesMap(String name, AbstractFeatureModel value) {
        this.features.put(name, value);
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
