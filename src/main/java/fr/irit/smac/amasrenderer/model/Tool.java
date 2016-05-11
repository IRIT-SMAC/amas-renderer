package fr.irit.smac.amasrenderer.model;

import fr.irit.smac.amasrenderer.Const;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class Tool {

	private ObservableList<Label> tools;

	public ObservableList<Label> getServices() {
		return tools;
	}
	
	public void setTools(ObservableList<Label> items) {
		this.tools = items;
	}
	
	public void addTool(String nom){
        Label nouveauService = new Label(nom);
        nouveauService.setFont(new Font("OpenSymbol", Const.FONT_SIZE));
		this.tools.add(nouveauService);
	}
}
