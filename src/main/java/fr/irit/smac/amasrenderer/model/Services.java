package fr.irit.smac.amasrenderer.model;

import fr.irit.smac.amasrenderer.Const;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class Services {

	private ObservableList<Label> services;

	public ObservableList<Label> getServices() {
		return services;
	}
	
	public void setServices(ObservableList<Label> items) {
		this.services = items;
	}
	
	public void ajouterService(String nom){
        Label nouveauService = new Label(nom);
        nouveauService.setFont(new Font("OpenSymbol", Const.FONT_SIZE));
		this.services.add(nouveauService);
	}
}
