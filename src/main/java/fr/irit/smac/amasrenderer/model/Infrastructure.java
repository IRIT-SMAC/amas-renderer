package fr.irit.smac.amasrenderer.model;

import javafx.collections.ObservableList;

public class Infrastructure {

	private ObservableList<String> infrastructure;

	public void setInfrastructure(ObservableList<String> label) {
		this.infrastructure = label;
	}
	
	public ObservableList<String> getInfrastructure() {
		return infrastructure;
	}
	
	public void editInfrastructure(String nom){
        infrastructure.clear();
        infrastructure.add(nom);
	}
	
}
