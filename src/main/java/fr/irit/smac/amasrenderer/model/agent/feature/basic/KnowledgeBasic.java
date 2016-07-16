package fr.irit.smac.amasrenderer.model.agent.feature.basic;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.irit.smac.amasrenderer.model.agent.feature.Knowledge;

public class KnowledgeBasic extends Knowledge {

    String id;

    public KnowledgeBasic() {
        attributesMap.put("id", id);
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public String getId() {
        return id;
    }
}
