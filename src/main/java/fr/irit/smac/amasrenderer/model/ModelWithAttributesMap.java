package fr.irit.smac.amasrenderer.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class ModelWithAttributesMap {

    @JsonIgnore
    protected Map<String,Object> attributesMap = new HashMap<>();
    
    @JsonAnyGetter
    public Map<String, Object> getAttributesMap() {
        return attributesMap;
    }
    
    @JsonAnySetter
    public void setAttributesMap(String name, Object value) {
        this.attributesMap.put(name, value);
    }
}
