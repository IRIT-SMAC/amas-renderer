package fr.irit.smac.amasrenderer;

import java.io.IOException;

import fr.irit.smac.amasrenderer.controller.GraphMainController;
import fr.irit.smac.amasrenderer.controller.TreeModifyController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//TODO verifier que ce que j'ai marqu� est bon, ne connaissans pas cette classe ( michael )
/**
 * The Class Main.
 * Launch the program
 */
public class Main extends Application {

    /** The root layout. */
    BorderPane rootLayout;
    private GraphMainController graphMainController;
    private Stage primaryStage;
    
    /* (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.initRootLayout();
        this.initGraphAgents();
        this.initServices();
        
        primaryStage.setScene(new Scene(rootLayout));
        graphMainController.initSubControllers();        

        primaryStage.setOnCloseRequest(event -> {
				Platform.exit();	
				System.exit(0);
		});
        primaryStage.show();
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
        
        graphMainController = loaderGraphAgents.getController();
        rootLayout.setCenter(root);
        graphMainController.drawGraph();

        
        System.out.println("heyyyyyyy" + stackPaneGraphNode.getScene());

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
    


    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
