package fr.irit.smac.amasrenderer;

import java.io.IOException;

import fr.irit.smac.amasrenderer.controller.GraphMainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The Class Main.
 * Launch the program
 */
public class Main extends Application {

    /** The root layout. */
    BorderPane rootLayout;
    
    /** The main stage of the application */
    private static Stage mainStage;
    
    /**
    
    /* (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        this.initRootLayout();
        this.initGraphAgents();
        this.initServices();
        
        primaryStage.setScene(new Scene(rootLayout));
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        primaryStage.show();
        
        Main.mainStage = primaryStage;
    }

    /**
     * Inits the root layout.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void initRootLayout() throws IOException {
        
        FXMLLoader loaderRootLayout = new FXMLLoader();
        loaderRootLayout.setLocation(Main.class.getResource("view/RootLayout.fxml"));
        this.rootLayout = (BorderPane) loaderRootLayout.load();
        FXMLLoader loaderMenuBar = new FXMLLoader();
        loaderMenuBar.setLocation(Main.class.getResource("view/MenuBar.fxml"));
        rootLayout.setTop(loaderMenuBar.load());
        
    }
    
    
    /**
     * Inits the graph agents.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void initGraphAgents() throws IOException {
        
        FXMLLoader loaderGraphAgents = new FXMLLoader();
        loaderGraphAgents.setLocation(Main.class.getResource("view/GraphAgents.fxml"));
        BorderPane root = (BorderPane) loaderGraphAgents.load();
        SwingNode swingNode = new SwingNode();
        swingNode.setId("graphNode");
        StackPane stackPaneGraphNode = (StackPane) root.lookup("#stackPaneGraphNode");
        stackPaneGraphNode.getChildren().add(swingNode);
        GraphMainController controller = loaderGraphAgents.getController();
        controller.drawGraph();
        rootLayout.setCenter(root);
    }

    /**
     * Inits the services.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void initServices() throws IOException {
        
        FXMLLoader loaderServices = new FXMLLoader();
        loaderServices.setLocation(Main.class.getResource("view/Services.fxml"));
        VBox root3 = (VBox) loaderServices.load();
        rootLayout.setLeft(root3);
        
    }
    
    public static Stage getMainStage() {
        return mainStage;
    }


    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
