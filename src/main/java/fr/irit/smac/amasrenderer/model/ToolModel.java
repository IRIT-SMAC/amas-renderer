package fr.irit.smac.amasrenderer.model;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class ToolModel.
 */
public class ToolModel implements IConstraintFields {

    private String name;

    private Map<String, Object> attributesMap = new HashMap<String, Object>();

    private final String[] requiredKey        = { "agentMap", "className" };
    private final String[] protectedValue     = {};
    private final String[] notExpanded        = { "agentMap" };
    private final String[] requiredKeyComplex = {};

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
        return this.requiredKey;
    }

    @Override
    public String[] getProtectedValue() {
        return this.protectedValue;
    }

    @Override
    public String[] getNotExpanded() {
        return notExpanded;
    }

    @Override
    public String[] getRequiredKeyComplex() {
        return this.requiredKeyComplex;
    }
}
