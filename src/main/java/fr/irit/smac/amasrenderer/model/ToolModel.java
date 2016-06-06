package fr.irit.smac.amasrenderer.model;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class ToolModel.
 */
public class ToolModel implements IConstraintFields {

    private String name;
    private Map<String, Object> attributesMap = new HashMap<String, Object>();
    private String[] REQUIRED_KEY = {"agentMap", "className"};
    private String[] PROTECTED_VALUE = {}; 
    private String[] NOT_EXPANDED = {"agentMap"};
    
    public ToolModel() {

    }
    
    public ToolModel(String name) {
        super();
        this.name = name;
    }
    
    @SuppressWarnings("unchecked")
    public ToolModel(String name, Object map) {
        super();
        this.name = name;
        this.attributesMap = (Map<String, Object>) map;
    }

    public Map<String, Object> getAttributesMap() {
        return attributesMap;
    }

    public void setAttributesMap(Map<String, Object> servicesMap) {
        this.attributesMap = servicesMap;
    }
    
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return NOT_EXPANDED;
    }
}
