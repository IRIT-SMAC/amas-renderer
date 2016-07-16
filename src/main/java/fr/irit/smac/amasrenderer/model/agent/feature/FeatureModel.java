package fr.irit.smac.amasrenderer.model.agent.feature;

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
}
