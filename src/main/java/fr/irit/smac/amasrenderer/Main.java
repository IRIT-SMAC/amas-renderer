package fr.irit.smac.amasrenderer;

import java.util.logging.Logger;

import fr.irit.smac.amasrenderer.controller.MainController;
import fr.irit.smac.amasrenderer.controller.menu.MenuBarController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * The Class Main. Launch the program
 */
public class Main extends Application {

    public static final Logger LOGGER = Logger.getLogger(MenuBarController.class.getName());

    /**
     * (non-Javadoc)
     * 
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loaderRootLayout = new FXMLLoader();
        loaderRootLayout.setLocation(Main.class.getResource("view/RootLayout.fxml"));
        BorderPane rootLayout = (BorderPane) loaderRootLayout.load();
        MainController controller = loaderRootLayout.getController();
        Scene scene = new Scene(rootLayout);
        scene.setFill(Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
        
        controller.init();
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
