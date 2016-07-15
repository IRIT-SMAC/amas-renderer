package fr.irit.smac.amasrenderer.model.agent.feature;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.model.IModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FeatureModel implements IModel {

    private StringProperty name;

    @JsonProperty
    public String className;
    
    @JsonProperty
    public Knowledge knowledge;
    
    @JsonProperty
    public Skill skill;
    
    @JsonIgnore
    private Map<String,Object> attributesMap = new HashMap<>();
    
    public FeatureModel() {

        this.knowledge = new Knowledge();
        this.skill = new Skill();
        this.attributesMap.put("knowledge", knowledge.getAttributesMap());
        this.attributesMap.put("className", className);
        this.attributesMap.put("skill", skill.getAttributesMap());
    }
    
    public FeatureModel(String id) {

        this.name = new SimpleStringProperty(id);
        this.knowledge = new Knowledge();
        this.skill = new Skill();
        this.attributesMap.put("knowledge", knowledge.getAttributesMap());
        this.attributesMap.put("className", className);
        this.attributesMap.put("skill", skill.getAttributesMap());
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
    public String[] getRequiredKeySingle() {
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

    @Override
    public String[] getRequiredKeyComplex() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public StringProperty nameProperty() {
        return this.name;
    }
    
    @Override
    public String getName() {
        return this.name.get();
    }

    @Override
    public void setName(String name) {
        this.name.set(name);
    }
}
