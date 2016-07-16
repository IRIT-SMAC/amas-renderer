package fr.irit.smac.amasrenderer.model.agent.feature.basic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.agent.feature.AbstractFeatureModel;
import javafx.beans.property.SimpleStringProperty;

public class FeatureBasicModel extends AbstractFeatureModel {

    private static final String[] PROTECTED_VALUE = { Const.ID, Const.SKILL, Const.KNOWLEDGE };

    private KnowledgeBasic knowledge;

    public FeatureBasicModel() {
        this.name = new SimpleStringProperty("featureBasic");
    }

    @JsonIgnore
    public KnowledgeBasic getKnowledge() {
        return this.knowledge;
    }

    @JsonProperty
    public void setKnowledge(KnowledgeBasic knowledge) {
        this.knowledge = knowledge;
        this.attributesMap.put("knowledge", knowledge.getAttributesMap());
    }

    @Override
    public String[] getProtectedValue() {
        return PROTECTED_VALUE;
    }
}
