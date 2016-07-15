package fr.irit.smac.amasrenderer.model.agent.feature.basic;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.model.agent.feature.social.AbstractFeatureModel;
import javafx.beans.property.SimpleStringProperty;

public class FeatureBasicModel extends AbstractFeatureModel{

    @JsonProperty
    public String className;
    
    @JsonProperty
    private KnowledgeBasic knowledge;

    public FeatureBasicModel() {
        this.name = new SimpleStringProperty("FeatureBasic");
        this.knowledge = new KnowledgeBasic();
        this.attributesMap.put("knowledge", knowledge.getAttributesMap());
    }

    public KnowledgeBasic getKnowledge() {
        return this.knowledge;
    }
}
