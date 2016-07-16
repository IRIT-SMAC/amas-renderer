package fr.irit.smac.amasrenderer.model.agent.feature;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Knowledge {

    private String className;
    
    @JsonIgnore
    protected Map<String,Object> attributesMap = new HashMap<>();
    
    public Knowledge() {
        this.attributesMap.put("className", className);
    }
    
    @JsonAnyGetter
    public Map<String,Object> getMap() {
      return this.attributesMap;
    }

    public Map<String, Object> getAttributesMap() {
        return attributesMap;
    }

    @JsonIgnore
    public String getClassName() {
        return className;
    }
}
