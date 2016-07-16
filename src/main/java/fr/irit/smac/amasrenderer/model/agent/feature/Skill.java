package fr.irit.smac.amasrenderer.model.agent.feature;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Skill {

    private String className;

    @JsonIgnore
    private Map<String, Object> attributesMap = new HashMap<>();

    public Skill() {
    }

    @JsonAnyGetter
    public Map<String, Object> getMap() {
        return this.attributesMap;
    }

    @JsonIgnore
    public Map<String, Object> getAttributesMap() {
        return attributesMap;
    }

    @JsonIgnore
    public String getClassName() {
        return className;
    }

    @JsonProperty
    public void setClassName(String className) {
        this.className = className;
        this.attributesMap.put("className", className);
    }
}
