package fr.irit.smac.amasrenderer.model;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class InfrastructureModel.
 */
public class InfrastructureModel {

    private Map<String, Object> attributesMap = new HashMap<String, Object>();
    private String name;
    
    public InfrastructureModel(String name) {
        this.name = name;
    }

    public InfrastructureModel(String key, Map<String, Object> value) {
        this.name = key;
        this.attributesMap = value;
    }

    public Map<String, Object> getAttributesMap() {

        return this.attributesMap;
    }

    public void setAttributesMap(Map<String, Object> attributesMap) {

        this.attributesMap = attributesMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String toString() {
        return this.name;
    }
}
