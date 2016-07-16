package fr.irit.smac.amasrenderer.model.agent.feature.basic;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.irit.smac.amasrenderer.model.agent.feature.AbstractFeatureModel;
import javafx.beans.property.SimpleStringProperty;

public class FeatureBasicModel extends AbstractFeatureModel{

    private KnowledgeBasic knowledge;

    public FeatureBasicModel() {
        this.name = new SimpleStringProperty("FeatureBasic");
        this.knowledge = new KnowledgeBasic();
        this.attributesMap.put("knowledge", knowledge.getAttributesMap());
    }

    @JsonIgnore
    public KnowledgeBasic getKnowledge() {
        return this.knowledge;
    }
}
