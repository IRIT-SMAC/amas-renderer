package fr.irit.smac.amasrenderer.model.agent.feature;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.model.IModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AbstractFeatureModel implements IModel {

    protected StringProperty name;

    protected Skill skill;
    
    protected String className;
    
    @JsonIgnore
    protected Map<String, Object> attributesMap = new HashMap<>();

    public AbstractFeatureModel() {
        this.name = new SimpleStringProperty();
        this.skill = new Skill();
        this.attributesMap.put("className", className);
        this.attributesMap.put("skill", skill.getAttributesMap());
    }
    
    public AbstractFeatureModel(String id) {
        this.name = new SimpleStringProperty(id);
        this.attributesMap.put("skill", skill.getAttributesMap());
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

    @JsonIgnore
    public String getClassName() {
        return this.className;
    }
    
    @JsonAnySetter
    public void setAttributesMap(String name, Object value) {
        this.attributesMap.put(name, value);
    }
    
    @JsonAnyGetter
    public Map<String,Object> getMap() {
      return this.attributesMap;
    }

    public Map<String, Object> getAttributesMap() {
        return attributesMap;
    }
    
    @Override
    public String getNewName(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] getProtectedValue() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] getNotExpanded() {
        // TODO Auto-generated method stub
        return null;
    }

    @JsonIgnore
    public Skill getSkill() {
        return skill;
    }

    @JsonProperty
    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}
