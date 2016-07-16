package fr.irit.smac.amasrenderer.model.agent.feature.social;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.model.agent.feature.Knowledge;

public class KnowledgeSocial extends Knowledge {

    private Map<String, TargetModel> targetMap = new HashMap<>();

    private Map<String, PortModel> portMap = new HashMap<>();

    public KnowledgeSocial() {
    }

    @JsonIgnore
    public Map<String, TargetModel> getTargetMap() {
        return this.targetMap;
    }

    @JsonAnySetter
    public void setAttributesMap(String name, Object value) {
        this.attributesMap.put(name, value);
    }

    @JsonIgnore
    public Map<String, PortModel> getPortMap() {
        return portMap;
    }

    @JsonProperty
    public void setPortMap(Map<String, PortModel> portMap) {
        this.portMap = portMap;
        this.attributesMap.put("portMap", portMap);
    }

    @JsonProperty
    public void setTargetMap(Map<String, TargetModel> targetMap) {
        this.targetMap = targetMap;
        this.attributesMap.put("targetMap", targetMap);
    }
}
