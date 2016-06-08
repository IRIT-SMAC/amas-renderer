package fr.irit.smac.amasrenderer.model;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Class ToolModel.
 */
public class ToolModel implements IConstraintFields {

    private StringProperty name;

    private Map<String, Object> attributesMap = new HashMap<String, Object>();

    private final String[] requiredKeySingle  = {};
    private final String[] protectedValue     = {};
    private final String[] notExpanded        = { "agentMap" };
    private final String[] requiredKeyComplex = {};

    public ToolModel() {

    }

    public ToolModel(String name) {
        super();
        this.name = new SimpleStringProperty(name);
    }

    @SuppressWarnings("unchecked")
    public ToolModel(String name, Object map) {
        super();
        this.name = new SimpleStringProperty(name);
        this.attributesMap = (Map<String, Object>) map;
    }

    public Map<String, Object> getAttributesMap() {
        return attributesMap;
    }

    public void setAttributesMap(Map<String, Object> servicesMap) {
        this.attributesMap = servicesMap;
    }

    public String toString() {
        return this.name.get().toString();
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    @Override
    public String[] getRequiredKeySingle() {
        return this.requiredKeySingle;
    }

    @Override
    public String[] getProtectedValue() {
        return this.protectedValue;
    }

    @Override
    public String[] getNotExpanded() {
        return this.notExpanded;
    }

    @Override
    public String[] getRequiredKeyComplex() {
        return this.requiredKeyComplex;
    }
}
