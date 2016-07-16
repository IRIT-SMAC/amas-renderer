package fr.irit.smac.amasrenderer.model.agent.feature;

import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.beans.property.SimpleStringProperty;

public class FeatureModel extends AbstractFeatureModel {

    private String className;
    
    private Knowledge knowledge;
        
    public FeatureModel() {

        this.knowledge = new Knowledge();
        this.attributesMap.put("knowledge", knowledge.getAttributesMap());
        this.attributesMap.put("className", className);
    }
    
    public FeatureModel(String id) {

        this.name = new SimpleStringProperty(id);
        this.knowledge = new Knowledge();
        this.attributesMap.put("knowledge", knowledge.getAttributesMap());
        this.attributesMap.put("className", className);
    }
    
    @JsonProperty
    public void setClassName(String className) {
        this.className = className;
    }

    @JsonProperty
    public void setKnowledge(Knowledge knowledge) {
        this.knowledge = knowledge;
    }

    @JsonProperty
    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    
}
