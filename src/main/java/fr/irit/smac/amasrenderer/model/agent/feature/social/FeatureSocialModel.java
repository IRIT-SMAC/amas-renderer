package fr.irit.smac.amasrenderer.model.agent.feature.social;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.agent.feature.AbstractFeatureModel;
import javafx.beans.property.SimpleStringProperty;

public class FeatureSocialModel extends AbstractFeatureModel {

    private static final String[] NOT_EXPANDED = { Const.PORT_MAP, Const.TARGET_MAP };

    private KnowledgeSocial knowledge;

    public FeatureSocialModel() {
        this.name = new SimpleStringProperty("featureSocial");
    }

    @JsonIgnore
    public KnowledgeSocial getKnowledge() {
        return knowledge;
    }
    
    @JsonProperty
    public void setKnowledge(KnowledgeSocial knowledge) {
        this.knowledge = knowledge;
        this.attributesMap.put("knowledge", knowledge.getAttributesMap());
    }
    
    @Override
    public String[] getNotExpanded() {
        return NOT_EXPANDED;
    }
}
