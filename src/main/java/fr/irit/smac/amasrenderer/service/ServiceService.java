package fr.irit.smac.amasrenderer.service;

import java.util.Map;

import fr.irit.smac.amasrenderer.model.Services;
import javafx.collections.ObservableList;

public class ServiceService {

	private Services model;

	private static ServiceService instance = new ServiceService();

	private ServiceService() {
		model = new Services();
	}

	public static ServiceService getInstance() {
		return instance;
	}

	public ObservableList<String> getListServices() {
		return this.model.getServices();
	}

	public void setListServices(ObservableList<String> items) {
		this.model.setServices(items); 
	}

	public void createServicesFromMap(Map<String,Object> map) {
		for(Map.Entry<String,Object> pair : map.entrySet()){
			if(pair.getKey() != "className"){
				this.model.ajouterService(pair.getKey());
			}
		}
	}


}
