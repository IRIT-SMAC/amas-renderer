package fr.irit.smac.amasrenderer.service;

import java.util.HashMap;
import java.util.Map;

import fr.irit.smac.amasrenderer.model.ToolModel;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 * The Class ToolService.
 */
public class ToolService {

	private ToolModel model;

	private static ToolService instance = new ToolService();

	/**
	 * Instantiates a new tool service.
	 */
	private ToolService() {
		model = new ToolModel();
	}

	/**
	 * Gets the single instance of ToolService.
	 *
	 * @return single instance of ToolService
	 */
	public static ToolService getInstance() {
		return instance;
	}

	/**
	 * Gets the tools from to the model.
	 *
	 * @return the tools
	 */
	public ObservableList<String> getTools() {
		return this.model.getTools();
	}

	/**
	 * Sets the tools in the model.
	 *
	 * @param items
	 *            the new tools
	 */
	public void setTools(ObservableList<String> items) {
		this.model.setTools(items);
	}

	/**
	 * Creates the services from a map.
	 *
	 * @param map
	 *            the map
	 */
	public void createServicesFromMap(Map<String, Object> map) {

		this.model.getTools().clear();
		for (Map.Entry<String, Object> pair : map.entrySet()) {
			if (pair.getKey() != "className") {
				this.model.addTool(pair.getKey());
			}
		}

	}

	public Map<String, Object> getServicesMap() {
		return this.model.getServicesMap();
	}

	public void setServicesMap(Map<String, Object> servicesMap) {
		this.model.setServicesMap(servicesMap);
	}

	public void updateServiceMap(String id, TreeItem<String> item) {
		this.model.getServicesMap().remove(id);
		Map<String, Object> singleServiceMap = new HashMap<String, Object>();
		this.updateSingleServiceMap(item, singleServiceMap, id);
		this.model.getServicesMap().put(id, singleServiceMap.get(id));
	}

	public void updateSingleServiceMap(TreeItem<String> item, Map<String, Object> map, String key) {

		ObservableList<TreeItem<String>> node = item.getChildren();

		if (node.size() > 0) {
			Map<String, Object> newServiceMap = new HashMap<String, Object>();
			for (TreeItem<String> subItem : node) {

				String[] splitItem = ((String) subItem.getValue()).split(" : ");
				String keyItem = splitItem[0];
				updateSingleServiceMap(subItem, newServiceMap, keyItem);

			}
			map.put(key, newServiceMap);

		} else {

			String[] splitItem = ((String) item.getValue()).split(" : ");
			String value = splitItem[1];
			map.put(key, value);
		}
	}
}
