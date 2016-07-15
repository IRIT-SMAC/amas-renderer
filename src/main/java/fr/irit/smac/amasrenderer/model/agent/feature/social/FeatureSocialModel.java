package fr.irit.smac.amasrenderer.model.agent.feature.social;

import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.beans.property.SimpleStringProperty;

public class FeatureSocialModel extends AbstractFeatureModel {

    @JsonProperty
    public String className;
    
    @JsonProperty
    private KnowledgeSocial knowledge;

    public FeatureSocialModel() {
        this.name = new SimpleStringProperty("FeatureSocial");
        this.knowledge = new KnowledgeSocial();
        this.attributesMap.put("knowledge", knowledge.getAttributesMap());
    }

    public KnowledgeSocial getKnowledge() {
        return knowledge;
    }
    
}
