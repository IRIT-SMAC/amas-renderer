package fr.irit.smac.amasrenderer.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This model is about the infrastructure
 */
public class InfrastructureModel implements IModel {

    private Map<String, Object> attributesMap = new HashMap<>();

    private static final String[] REQUIRED_KEY_SINGLE  = {};
    private static final String[] PROTECTED_VALUE      = {};
    private static final String[] NOT_EXPANDED         = { "services" };
    private static final String[] REQUIRED_KEY_COMPLEX = {};

    private StringProperty name;

    public InfrastructureModel() {
        // Required by Jackson
    }

    public InfrastructureModel(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public InfrastructureModel(String key, Map<String, Object> value) {
        this.name = new SimpleStringProperty(key);
        this.attributesMap = value;
    }

    @Override
    public String toString() {
        return this.name.get();
    }

    @Override
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

    /**
     * Used by Jackson to serialize the infrastructure
     * 
     * @return the attributes
     */
    @JsonAnyGetter
    public Map<String, Object> getAttributesMap() {
        return this.attributesMap;
    }

    /**
     * Used by Jackson to deserialize the infrastructure
     * 
     * @param name
     *            name of the attribute
     * @param value
     *            value of the attribute
     */
    @JsonAnySetter
    public void setAttributesMap(String name, Object value) {
        this.attributesMap.put(name, value);
    }

    public void setAttributes(Map<String, Object> attributesMap) {
        this.attributesMap = attributesMap;
    }

    @Override
    public String[] getRequiredKeySingle() {
        return REQUIRED_KEY_SINGLE;
    }

    @Override
    public String[] getProtectedValue() {
        return PROTECTED_VALUE;
    }

    @Override
    public String[] getNotExpanded() {
        return NOT_EXPANDED;
    }

    @Override
    public String[] getRequiredKeyComplex() {
        return REQUIRED_KEY_COMPLEX;
    }

    @Override
    public String getNewName(String name) {
        return name;
    }
}
