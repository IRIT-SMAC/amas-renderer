package fr.irit.smac.amasrenderer.service;

import java.util.Map;

import fr.irit.smac.amasrenderer.model.Infrastructure;
import javafx.collections.ObservableList;

public class InfrastructureService {

	private Infrastructure model;

	private static InfrastructureService instance = new InfrastructureService();

	private InfrastructureService() {
		model = new Infrastructure();
	}

	public static InfrastructureService getInstance() {
		return instance;
	}
	
	public ObservableList<String> getInfrastructure(){
		return this.model.getInfrastructure();
	}
	
	public void setInfrastructure(ObservableList<String> list){
		this.model.setInfrastructure(list);
	}

	public void createInfrastructuresFromMap(Map<String, Object> map) {
		this.model.modifierInfrastructure((String)map.get("className"));
	}
	
}
