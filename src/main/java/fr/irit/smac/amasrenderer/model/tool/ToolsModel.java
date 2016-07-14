package fr.irit.smac.amasrenderer.model.tool;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ToolsModel {

    @JsonProperty
    private String className;

    @JsonProperty
    private AgentHandlerToolModel agentHandlerService;
    
    @JsonIgnore
    private Map<String, ToolModel> services = new HashMap<>();

    
    public ToolsModel() {

    }

    @JsonAnySetter
    public void setServicesMap(String name, ToolModel value) {

        this.services.put(name, value);
    }

    public String getClassName() {
        return this.className;
    }

    public Map<String, ToolModel> getServices() {
        return this.services;
    }

    public AgentHandlerToolModel getAgentHandlerToolModel() {
        return agentHandlerService;
    }

    public void setAgentHandlerToolModel(AgentHandlerToolModel agentHandlerToolModel) {
        this.agentHandlerService = agentHandlerToolModel;
    }
    
    @JsonAnyGetter
    public Map<String,ToolModel> getMap() {
      return this.services;
    }
}
