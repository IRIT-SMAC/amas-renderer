package fr.irit.smac.amasrenderer;

import java.io.IOException;

import org.graphstream.ui.swingViewer.ViewPanel;

import fr.irit.smac.amasrenderer.controller.GraphMainController;
import fr.irit.smac.amasrenderer.controller.GraphMouseWheelController;
import fr.irit.smac.amasrenderer.controller.GraphNodeEditController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    BorderPane rootLayout;
    
    @Override
    public void start(Stage primaryStage) throws Exception {

        this.initRootLayout();
        this.initGraphAgents();
        this.initServices();

        primaryStage.setScene(new Scene(rootLayout));
        primaryStage.show();
    }

    private void initRootLayout() throws IOException {
        
        FXMLLoader loaderRootLayout = new FXMLLoader();
        loaderRootLayout.setLocation(Main.class.getResource("view/RootLayout.fxml"));
        this.rootLayout = (BorderPane) loaderRootLayout.load();
    }
    
    private void initGraphAgents() throws IOException {
        
        FXMLLoader loaderGraphAgents = new FXMLLoader();
        loaderGraphAgents.setLocation(Main.class.getResource("view/GraphAgents.fxml"));
        AnchorPane root = (AnchorPane) loaderGraphAgents.load();
        GraphMainController controller = loaderGraphAgents.getController();
        controller.drawGraph();
        rootLayout.setCenter(root);
    }

    private void initServices() throws IOException {
        
        FXMLLoader loaderServices = new FXMLLoader();
        loaderServices.setLocation(Main.class.getResource("view/Services.fxml"));
        AnchorPane root3 = (AnchorPane) loaderServices.load();
        rootLayout.setLeft(root3);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
