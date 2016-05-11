package fr.irit.smac.amasrenderer.model;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;

public class Infrastructure {

	private Label infrastructure;

	public void setInfrastructure(Label label) {
		this.infrastructure = label;
	}
	
	public Label getInfrastructure() {
		return infrastructure;
	}
	
	public void editInfrastructure(String nom){
		this.infrastructure.setText(nom);
	}
	
}
