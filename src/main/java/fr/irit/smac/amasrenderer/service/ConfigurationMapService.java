package fr.irit.smac.amasrenderer.service;

import java.util.HashMap;

import fr.irit.smac.amasrenderer.model.ConfigurationMapModel;
import javafx.scene.control.TreeItem;

public class ConfigurationMapService {

	private ConfigurationMapModel model;

	private static ConfigurationMapService instance = new ConfigurationMapService();

	private ConfigurationMapService() {

	}

	public void init() {

		this.model = new ConfigurationMapModel();
		HashMap<String, Object> configurationMap = new HashMap<String, Object>();

		this.model.setConfigurationMap(configurationMap);

		String infrastructureClassname = "BasicInfrastructure";
		InfrastructureService.getInstance().editInfrastructure(infrastructureClassname);
		HashMap<String,Object> map = new HashMap<String,Object>();
		InfrastructureService.getInstance().getInfrastructureMap().put(infrastructureClassname, map );

		configurationMap.put("className", infrastructureClassname);
		HashMap<String, Object> agentHandlerService = new HashMap<String, Object>();
		HashMap<String, Object> agentMap = new HashMap<String, Object>();

		agentHandlerService.put("className", "fr.irit.smac.amasfactory.service.agenthandler.impl.BasicAgentHandler");
		agentHandlerService.put("agentMap", agentMap);
		configurationMap.put("agentHandlerService", agentHandlerService);

		GraphService.getInstance().getModel().setAgentMap(agentMap);
		ToolService.getInstance().setServicesMap(this.model.getConfigurationMap());
		ToolService.getInstance().getTools().add("agentHandlerService");

		GraphService.getInstance().getModel().setAgentMap(agentMap);
	}

	public static ConfigurationMapService getInstance() {
		return instance;
	}

	public ConfigurationMapModel getModel() {
		return model;
	}

	@SuppressWarnings("unchecked")
	public void setModel(ConfigurationMapModel model) {
		this.model = model;

		HashMap<String, Object> agentHandlerService = (HashMap<String, Object>) model.getConfigurationMap()
				.get("agentHandlerService");

		HashMap<String, Object> agentMap = (HashMap<String, Object>) agentHandlerService.get("agentMap");

		GraphService.getInstance().getModel().setAgentMap(agentMap);
		ToolService.getInstance().setServicesMap(this.model.getConfigurationMap());
		InfrastructureService.getInstance()
				.setInfrastructureMap(this.model.getConfigurationMap());

	}
}
