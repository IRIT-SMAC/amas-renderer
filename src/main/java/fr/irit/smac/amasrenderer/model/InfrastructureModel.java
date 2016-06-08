package fr.irit.smac.amasrenderer.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Class InfrastructureModel.
 */
public class InfrastructureModel implements IConstraintFields {

    private Map<String, Object> attributesMap = new HashMap<String, Object>();

    private final String[] requiredKeySingle  = {};
    private final String[] protectedValue     = {};
    private final String[] notExpanded        = { "Service" };
    private final String[] requiredKeyComplex = {};

    private StringProperty name;

    public InfrastructureModel() {

    }

    public InfrastructureModel(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public InfrastructureModel(String key, Map<String, Object> value) {
        this.name = new SimpleStringProperty(key);
        this.attributesMap = value;
    }

    public String toString() {
        return name.get().toString();
    }

    public String getName() {
        return name.get();
    }

    @Override
    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    @JsonAnyGetter
    public Map<String, Object> getAttributesMap() {
        return this.attributesMap;
    }

    @JsonAnySetter
    public void setAttributesMap(String name, Object value) {
        this.attributesMap.put(name, value);
    }

    public void setAttributes(Map<String, Object> attributesMap) {
        this.attributesMap = attributesMap;
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
