package fr.irit.smac.amasrenderer.model.agent.feature;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.model.IModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AbstractFeatureModel implements IModel {

    protected StringProperty name;

    protected Skill skill;

    @JsonProperty
    protected String className;

    @JsonIgnore
    protected Map<String, Object> attributesMap = new HashMap<>();

    private static final String[] NOT_EXPANDED = { };
    private static final String[] PROTECTED_VALUE = { Const.SKILL, Const.KNOWLEDGE };

    public AbstractFeatureModel() {
        this.name = new SimpleStringProperty();
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    @JsonProperty
    public void setClassName(String className) {
        this.className = className;
        this.attributesMap.put("className", className);
    }

    @JsonIgnore
    public String getClassName() {
        return this.className;
    }

    @JsonAnySetter
    public void setAttributesMap(String name, Object value) {
        this.attributesMap.put(name, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getMap() {
        return this.attributesMap;
    }

    public Map<String, Object> getAttributesMap() {
        return attributesMap;
    }

    @Override
    public String getNewName(String name) {
        return name;
    }

    @Override
    public String[] getProtectedValue() {
        return PROTECTED_VALUE;
    }

    @Override
    public String[] getNotExpanded() {
        return NOT_EXPANDED;
    }

    @JsonIgnore
    public Skill getSkill() {
        return skill;
    }

    @JsonProperty
    public void setSkill(Skill skill) {
        this.skill = skill;
        this.attributesMap.put("skill", skill.getAttributesMap());
    }
}
