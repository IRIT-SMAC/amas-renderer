package fr.irit.smac.amasrenderer.model;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.ObservableList;

/**
 * The Class ToolModel.
 */
public class ToolModel {

	private ObservableList<String> tools;

	private Map<String, Object> servicesMap = new HashMap<String,Object>();

	public ToolModel() {

	}

	/**
	 * Gets the tools.
	 *
	 * @return the tools
	 */
	public ObservableList<String> getTools() {
		return tools;
	}

	/**
	 * Sets the tools.
	 *
	 * @param items
	 *            the new tools
	 */
	public void setTools(ObservableList<String> items) {
		this.tools = items;
	}

	public void addTool(String name) {
		this.tools.add(name);
	}

	public Map<String, Object> getServicesMap() {
		return servicesMap;
	}

	public void setServicesMap(Map<String, Object> servicesMap) {
		this.servicesMap = servicesMap;
	}
}
