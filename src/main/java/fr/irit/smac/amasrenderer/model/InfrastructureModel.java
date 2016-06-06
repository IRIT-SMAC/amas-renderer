package fr.irit.smac.amasrenderer.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

/**
 * The Class InfrastructureModel.
 */
public class InfrastructureModel implements IConstraintFields{

    private Map<String, Object> attributesMap = new HashMap<String, Object>();
    private String[] REQUIRED_KEY = {"className"};
    private String[] PROTECTED_VALUE = {}; 
    private String[] NOT_EXPANDED = {"Service"};

    private String name;

    public InfrastructureModel() {

    }

    public InfrastructureModel(String name) {
        this.name = name;
    }

    public InfrastructureModel(String key, Map<String, Object> value) {
        this.name = key;
        this.attributesMap = value;
    }

    @JsonAnyGetter
    public Map<String, Object> getAttributesMap() {
        return this.attributesMap;
    }

    @JsonAnySetter
    public void setAttributesMap(String name, Object value) {
        this.attributesMap.put(name, value);
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

    @Override
    public String[] getRequiredKey() {
        return this.REQUIRED_KEY;
    }

    @Override
    public String[] getProtectedValue() {
        return this.PROTECTED_VALUE;
    }

    @Override
    public String[] getNotExpanded() {
        return this.NOT_EXPANDED;
    }
}
