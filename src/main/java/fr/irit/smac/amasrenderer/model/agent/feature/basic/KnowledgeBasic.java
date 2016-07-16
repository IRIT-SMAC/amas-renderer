package fr.irit.smac.amasrenderer.model.agent.feature.basic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.model.agent.feature.Knowledge;

public class KnowledgeBasic extends Knowledge {

    String id;

    public KnowledgeBasic() {
    }

    @JsonProperty
    public void setId(String id) {
        this.id = id;
        attributesMap.put("id", id);
    }

    @JsonIgnore
    public String getId() {
        return id;
    }
}
