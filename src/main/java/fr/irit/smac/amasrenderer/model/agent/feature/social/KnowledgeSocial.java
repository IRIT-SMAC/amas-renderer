package fr.irit.smac.amasrenderer.model.agent.feature.social;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class KnowledgeSocial {

    @JsonProperty
    private Map<String, TargetModel> targetMap;

    @JsonProperty
    private Map<String, PortModel> portMap = new HashMap<>();

    private Map<String, Object> features = new HashMap<>();

    public KnowledgeSocial() {

    }

    public Map<String, TargetModel> getTargetMap() {
        return this.targetMap;
    }

    @JsonAnySetter
    public void setAttributesMap(String name, Object value) {
        this.features.put(name, value);
    }

    public Map<String, PortModel> getPortMap() {
        return portMap;
    }

    public void setPortMap(Map<String, PortModel> portMap) {
        this.portMap = portMap;
    }

}
