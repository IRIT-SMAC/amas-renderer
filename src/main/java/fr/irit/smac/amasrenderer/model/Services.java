package fr.irit.smac.amasrenderer.model;

import javafx.collections.ObservableList;

public class Services {

	private ObservableList<String> services;

	public ObservableList<String> getServices() {
		return services;
	}
	
	public void setServices(ObservableList<String> items) {
		this.services = items;
	}
	
	public void ajouterService(String nom){
		this.services.add(nom);
	}
}
