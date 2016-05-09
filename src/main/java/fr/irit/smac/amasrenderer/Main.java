package fr.irit.smac.amasrenderer;

import fr.irit.smac.amasrenderer.controller.GraphMainController;
import fr.irit.smac.amasrenderer.controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The Class Main. Launch the program
 */
public class Main extends Application {

    BorderPane rootLayout;
    private GraphMainController graphMainController;
    private Stage primaryStage;
    
    /*
     * (non-Javadoc)
     * 
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loaderRootLayout = new FXMLLoader();
        loaderRootLayout.setLocation(Main.class.getResource("view/RootLayout.fxml"));
        this.rootLayout = (BorderPane) loaderRootLayout.load();
        
        primaryStage.setScene(new Scene(rootLayout));
        MainController mainController = loaderRootLayout.getController();
        mainController.getGraphMainController().initSubControllers();

        primaryStage.setMaximized(true);
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
        
        
    }    

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
