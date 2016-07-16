package fr.irit.smac.amasrenderer.model.agent.feature.social;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.irit.smac.amasrenderer.model.agent.feature.AbstractFeatureModel;
import javafx.beans.property.SimpleStringProperty;

public class FeatureSocialModel extends AbstractFeatureModel {

    private KnowledgeSocial knowledge;

    public FeatureSocialModel() {
        this.name = new SimpleStringProperty("FeatureSocial");
        this.knowledge = new KnowledgeSocial();
        this.attributesMap.put("knowledge", knowledge.getAttributesMap());
    }

    @JsonIgnore
    public KnowledgeSocial getKnowledge() {
        return knowledge;
    }
    
}
