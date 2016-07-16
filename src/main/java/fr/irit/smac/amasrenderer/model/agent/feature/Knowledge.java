package fr.irit.smac.amasrenderer.model.agent.feature;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Knowledge {

    private String className;
    
    @JsonIgnore
    protected Map<String,Object> attributesMap = new HashMap<>();
    
    public Knowledge() {
    }
    
    @JsonAnyGetter
    public Map<String,Object> getMap() {
      return this.attributesMap;
    }

    
    @JsonAnySetter
    public void setAttributesMap(String key, Object value) {
        this.attributesMap.put(key, value);
    }

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
