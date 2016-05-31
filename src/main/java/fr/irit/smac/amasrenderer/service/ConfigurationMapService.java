package fr.irit.smac.amasrenderer.service;

import java.util.HashMap;

import fr.irit.smac.amasrenderer.model.ConfigurationMapModel;

public class ConfigurationMapService {

    private ConfigurationMapModel configurationMap;

    private static ConfigurationMapService instance = new ConfigurationMapService();

    private ConfigurationMapService() {

    }

    public static ConfigurationMapService getInstance() {
        return instance;
    }
    
    public void init() {

        this.configurationMap = new ConfigurationMapModel();
        HashMap<String, Object> configurationMap = new HashMap<String, Object>();

        this.configurationMap.setConfigurationMap(configurationMap);

        String infrastructureClassname = "BasicInfrastructure";

        configurationMap.put("className", infrastructureClassname);
        HashMap<String, Object> agentHandlerService = new HashMap<String, Object>();
        HashMap<String, Object> agentMap = new HashMap<String, Object>();

        agentHandlerService.put("className", "fr.irit.smac.amasfactory.service.agenthandler.impl.BasicAgentHandler");
        agentHandlerService.put("agentMap", agentMap);
        configurationMap.put("agentHandlerService", agentHandlerService);

        ToolService.getInstance().createServicesFromMap(configurationMap);
        InfrastructureService.getInstance()
            .createInfrastructureFromMap(configurationMap);
        GraphService.getInstance().getGraph().setAgentMap(agentMap);

    }

    public ConfigurationMapModel getConfigurationMap() {
        return configurationMap;
    }

    @SuppressWarnings("unchecked")
    public void setModel(ConfigurationMapModel configurationMap) {
        
        this.configurationMap = configurationMap;

        HashMap<String, Object> agentHandlerService = (HashMap<String, Object>) configurationMap.getConfigurationMap()
            .get("agentHandlerService");

        HashMap<String, Object> agentMap = (HashMap<String, Object>) agentHandlerService.get("agentMap");

        GraphService.getInstance().getGraph().setAgentMap(agentMap);
    }
}
