package fr.irit.smac.amasrenderer.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

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

	/**
	 * Adds a tool.
	 *
	 * @param name
	 *            the name
	 */
	public void addTool(String name, String value) {
		this.tools.add(name);
//		TreeItem<String> tree = new TreeItem<String>(name);
//		tree.getChildren().add(new TreeItem<String>(value));
//		servicesMap.put(name, value);
	}

	public void addTool(String name, HashMap<String, Object> attributes) {
		this.tools.add(name);
//		TreeItem<String> tree = new TreeItem<String>(name);
//		exploreTree(attributes, tree);
	}

	@SuppressWarnings("unchecked")
	private void exploreTree(HashMap<String, Object> attributes, TreeItem<String> tree) {
		for (Entry<String, Object> attribute : attributes.entrySet()) {

			Object valeur = attribute.getValue();
			String nom = attribute.getKey();
			TreeItem<String> newItem = new TreeItem<String>(nom);
			if (valeur instanceof HashMap<?, ?>) {
				tree.getChildren().add(newItem);
				exploreTree((HashMap<String, Object>) valeur, newItem);
			} else {
				TreeItem<String> child = new TreeItem<String>((String) valeur.toString());
				newItem.getChildren().add(child);
				tree.getChildren().add(newItem);
			}
//			attributeMap.put(tree.getValue(), tree);
		}

	}

	
	public Map<String, Object> getServicesMap() {
		return servicesMap;
	}

	public void setServicesMap(Map<String, Object> servicesMap) {
		this.servicesMap = servicesMap;
	}
}
