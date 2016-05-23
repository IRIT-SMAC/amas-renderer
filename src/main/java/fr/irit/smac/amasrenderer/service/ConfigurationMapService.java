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
		
		GraphService.getInstance().getModel().setAgentMap((HashMap<String, Object>) ((HashMap<String, Object>) model
				.getConfigurationMap().get("agentHandlerService")).get("agentMap"));
		System.out.println((HashMap<String, Object>) ((HashMap<String, Object>) model.getConfigurationMap()
				.get("agentHandlerService")).get("agentMap"));
//		GraphService.getInstance().attributeMap = new HashMap<String, TreeItem<String>>();

	}
}
