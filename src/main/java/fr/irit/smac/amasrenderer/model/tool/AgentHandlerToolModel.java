package fr.irit.smac.amasrenderer.model.tool;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.irit.smac.amasrenderer.model.agent.AgentModel;

public class AgentHandlerToolModel extends ToolModel{

    @JsonProperty
    private Map<String,AgentModel> agentMap;
    
    public AgentHandlerToolModel() {
    }

    public Map<String, AgentModel> getAgentMap() {
        return this.agentMap;
    }
}
