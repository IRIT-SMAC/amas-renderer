package fr.irit.smac.amasrenderer.model.agent.feature.basic;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.model.agent.feature.Knowledge;

public class KnowledgeBasic extends Knowledge {

    @JsonProperty
    String id;

    public KnowledgeBasic() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
