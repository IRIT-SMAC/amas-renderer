package fr.irit.smac.amasrenderer.model.agent.feature.social;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.model.agent.feature.Knowledge;
import fr.irit.smac.amasrenderer.model.agent.feature.Skill;

public class FeatureSocialModel extends Knowledge {

    @JsonProperty
    public String className;
    
    @JsonProperty
    private KnowledgeSocial knowledge;
    
    @JsonProperty
    private Skill skill;
    
    private Map<String,Object> features = new HashMap<>();
    
    public FeatureSocialModel() {
        this.knowledge = new KnowledgeSocial();
    }

    public KnowledgeSocial getKnowledge() {
        return knowledge;
    }
    
    @JsonAnySetter
    public void setAttributesMap(String name, Object value) {
        this.features.put(name, value);
    }
    
    
}
