package fr.irit.smac.amasrenderer.model.agent.feature.basic;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.model.agent.feature.Skill;

public class FeatureBasicModel {

    @JsonProperty
    public String className;
    
    @JsonProperty
    private KnowledgeBasic knowledge;

    @JsonProperty
    private Skill skill;
    
    public FeatureBasicModel() {
        this.knowledge = new KnowledgeBasic();
    }

    public KnowledgeBasic getKnowledge() {
        return this.knowledge;
    }
}
