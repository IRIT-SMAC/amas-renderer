package fr.irit.smac.amasrenderer.model.agent.feature.social;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.model.agent.feature.Knowledge;

public class KnowledgeSocial extends Knowledge {

    @JsonProperty
    private Map<String, TargetModel> targetMap = new HashMap<>();

    @JsonProperty
    private Map<String, PortModel> portMap = new HashMap<>();

    public KnowledgeSocial() {
        this.attributesMap.put("portMap", portMap);
        this.attributesMap.put("targetMap", targetMap);
    }

    public Map<String, TargetModel> getTargetMap() {
        return this.targetMap;
    }

    @JsonAnySetter
    public void setAttributesMap(String name, Object value) {
        this.attributesMap.put(name, value);
    }

    public Map<String, PortModel> getPortMap() {
        return portMap;
    }

    public void setPortMap(Map<String, PortModel> portMap) {
        this.portMap = portMap;
    }

}
