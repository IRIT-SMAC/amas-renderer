package fr.irit.smac.amasrenderer.service;

import java.util.HashMap;

import fr.irit.smac.amasrenderer.model.ConfigurationMapModel;
import javafx.scene.control.TreeItem;

public class ConfigurationMapService {

	private ConfigurationMapModel model;

	private static ConfigurationMapService instance = new ConfigurationMapService();

	private ConfigurationMapService() {

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
		
		HashMap<String, Object> agentHandlerService = (HashMap<String, Object>) model
				.getConfigurationMap().get("agentHandlerService");
		
		HashMap<String, Object> agentMap = (HashMap<String, Object>) agentHandlerService.get("agentMap");
				
		GraphService.getInstance().getModel().setAgentMap(agentMap);
		ToolService.getInstance().setServicesMap(this.model.getConfigurationMap());
		InfrastructureService.getInstance().setInfrastructureClassname(this.model.getConfigurationMap().get("className").toString());

	}
}
