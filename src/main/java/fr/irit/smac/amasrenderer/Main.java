package fr.irit.smac.amasrenderer;
	
import fr.irit.smac.amasrenderer.controller.AmasRenderingController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    
    private AmasRenderingController controller;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setController(new AmasRenderingController());
        Parent root = loader.load(getClass().getResource("view/amasRenderer.fxml").openStream());
        root.getStylesheets().add(getClass().getResource("view/layouts.css").getPath());
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
        this.controller = loader.getController();
        controller.drawGraph();
        
    }
    
    public static void main(String[] args){
        launch(args);
    }
}
