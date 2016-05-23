package fr.irit.smac.amasrenderer.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class Tool {

	private ObservableList<String> tools;
	
    private HashMap<String,TreeItem<String>> attributeMap = new HashMap<String, TreeItem<String>>();

    public Tool() {
    	
    }
    
	public ObservableList<String> getTools() {
		return tools;
	}
	
	public void setTools(ObservableList<String> items) {
		this.tools = items;
	}
	
	public void addTool(String name, HashMap<String, Object> attributes){
		this.tools.add(name);
		TreeItem<String> tree = new TreeItem<String>(name);
		exploreTree(attributes, tree);
	}
	
	public void addTool(String name, String value){
		this.tools.add(name);
		TreeItem<String> tree = new TreeItem<String>(name);
		tree.getChildren().add(new TreeItem<String>(value));
		attributeMap.put(tree.getValue(), tree);
	}
	
	@SuppressWarnings("unchecked")
	private void exploreTree(HashMap<String, Object> attributes, TreeItem<String> tree) {
		for(Entry<String, Object> attribute : attributes.entrySet()){
			Object valeur = attribute.getValue();
			String nom = attribute.getKey();
			TreeItem<String> newItem = new TreeItem<String>(nom);
			if(valeur instanceof HashMap<?, ?>){
				tree.getChildren().add(newItem);				
				exploreTree((HashMap<String,Object>)valeur, newItem);
			}
			else{
				TreeItem<String> child = new TreeItem<String>((String) valeur.toString());
				newItem.getChildren().add(child);
			}
			tree.getChildren().add(newItem);
			attributeMap.put(tree.getValue(), tree);
		}

	}

	public HashMap<String, TreeItem<String>> getAttributes() {
		return this.attributeMap;
	}
	
//    @JsonAnyGetter
//    public HashMap<String, Object> any() {
//   
//    	
//    	
//     return attributeMap;
//    }
}
