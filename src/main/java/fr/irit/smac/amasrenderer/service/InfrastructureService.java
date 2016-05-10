package fr.irit.smac.amasrenderer.service;

import java.util.Map;

import fr.irit.smac.amasrenderer.model.Infrastructure;
import javafx.scene.control.Label;

public class InfrastructureService {

	private Infrastructure model;

	private static InfrastructureService instance = new InfrastructureService();

	private InfrastructureService() {
		model = new Infrastructure();
	}

	public static InfrastructureService getInstance() {
		return instance;
	}
	
	public Label getLabelInfrastructure(){
		return this.model.getInfrastructure();
	}
	
	public void setLabelInfrastructures(Label label){
		this.model.setInfrastructure(label);
	}
	
	public void modifierInfrastructure(String nom){
		this.model.modifierInfrastructure(nom);
	}

	public void createInfrastructuresFromMap(Map<String, Object> map) {
		this.model.modifierInfrastructure((String)map.get("className"));
	}
	
}
