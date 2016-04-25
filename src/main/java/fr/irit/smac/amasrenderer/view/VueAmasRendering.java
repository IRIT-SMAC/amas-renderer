package fr.irit.smac.amasrenderer.view;
import fr.irit.smac.amasrenderer.controller.ControllerAmasRendering;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VueAmasRendering extends Application{
	
	private ControllerAmasRendering controller;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setController(new ControllerAmasRendering());
		Parent root = loader.load(getClass().getResource("userInterface.fxml").openStream());
		root.getStylesheets().add("view/style/layouts.css");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		
		this.controller = loader.getController();
		controller.drawGraph();
		
	}
	
	public static void main(String[] args){
		launch(args);
	}

}
