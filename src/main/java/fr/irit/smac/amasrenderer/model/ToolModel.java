package fr.irit.smac.amasrenderer.model;

import java.util.HashMap;
import java.util.Map;

import fr.irit.smac.amasrenderer.Const;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Class ToolModel.
 */
public class ToolModel implements IModel {

    private StringProperty name;

    private Map<String, Object> attributesMap = new HashMap<>();

    private static final String[] REQUIRED_KEY_SINGLE  = {};
    private static final String[] PROTECTED_VALUE      = {};
    private static final String[] NOT_EXPANDED         = { Const.AGENT_MAP };
    private static final String[] REQUIRED_KEY_COMPLEX = {};

    public ToolModel(String name) {
        super();
        this.name = new SimpleStringProperty(name);
    }

    @SuppressWarnings("unchecked")
    public ToolModel(String name, Object map) {
        super();
        
        this.name = new SimpleStringProperty(this.getNewName(name));
        this.attributesMap = (Map<String, Object>) map;

        if (this.attributesMap.get(Const.CLASSNAME) == null) {
            this.attributesMap.put(Const.CLASSNAME, Const.EXAMPLE_CLASSNAME);
        }
    }

    public Map<String, Object> getAttributesMap() {
        return attributesMap;
    }

    public void setAttributesMap(Map<String, Object> servicesMap) {
        this.attributesMap = servicesMap;
    }

    @Override
    public String toString() {
        return this.name.get();
    }

    public String getName() {
        return this.name.get();
    }

    @Override
    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return this.name;
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
        return name.endsWith(Const.TOOL) ? name : name.concat(Const.TOOL);
    }
}
