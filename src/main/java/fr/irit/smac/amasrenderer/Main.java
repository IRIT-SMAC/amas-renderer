package fr.irit.smac.amasrenderer;

import java.util.logging.Logger;

import fr.irit.smac.amasrenderer.controller.GraphMainController;
import fr.irit.smac.amasrenderer.controller.MainController;
import fr.irit.smac.amasrenderer.controller.MenuBarController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The Class Main. Launch the program
 */
public class Main extends Application {

    BorderPane rootLayout;
    private GraphMainController graphMainController;
    private Stage primaryStage;
    
	public static final Logger LOGGER = Logger.getLogger(MenuBarController.class.getName());
    
    /** The main stage of the application */
    private static Stage mainStage;
    
    /**
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
        GraphMainController graphMainController = mainController.getGraphMainController();
        graphMainController.initSubControllers();
        primaryStage.setOnCloseRequest(event -> {
        	Platform.exit();
        	System.exit(0);
        });
        primaryStage.show();
        
        Main.mainStage = primaryStage;
    }
    
    public static Stage getMainStage() {
        return mainStage;
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
