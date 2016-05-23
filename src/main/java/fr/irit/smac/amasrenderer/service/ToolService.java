package fr.irit.smac.amasrenderer.service;

import java.util.HashMap;
import java.util.Map;

import fr.irit.smac.amasrenderer.model.Tool;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class ToolService {

	private Tool model;

	private static ToolService instance = new ToolService();

	private ToolService() {
		model = new Tool();
	}

	public static ToolService getInstance() {
		return instance;
	}

	public ObservableList<String> getTools() {
		return this.model.getTools();
	}

	public void setTools(ObservableList<String> items) {
		this.model.setTools(items); 
	}

	public void createServicesFromMap(Map<String,Object> map) {

		for(Map.Entry<String,Object> pair : map.entrySet()){
			if(pair.getKey() != "className"){
				this.model.getTools().clear();
				if(pair.getValue() instanceof String)
					this.model.addTool(pair.getKey(), (String)pair.getValue());
				else {
					this.model.addTool(pair.getKey(), (HashMap<String,Object>)pair.getValue());
				}
			}
		}
	}

	public HashMap<String, TreeItem<String>> getAttributes() {
		return this.model.getAttributes();
	}


}
