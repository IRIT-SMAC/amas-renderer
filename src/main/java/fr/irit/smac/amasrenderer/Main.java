package fr.irit.smac.amasrenderer;

import java.io.IOException;

import fr.irit.smac.amasrenderer.controller.GraphMainController;
import fr.irit.smac.amasrenderer.controller.TreeModifyController;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//TODO verifier que ce que j'ai marqué est bon, ne connaissans pas cette classe ( michael )
/**
 * The Class Main.
 * Launch the program
 */
public class Main extends Application {

    /** The root layout. */
    BorderPane rootLayout;
    
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
        GraphMainController controller = loaderGraphAgents.getController();
        rootLayout.setCenter(root);
        controller.drawGraph();

        FXMLLoader loaderServices = new FXMLLoader();
        loaderServices.setLocation(Main.class.getResource("view/GraphAttributes.fxml"));
        BorderPane root3 = loaderServices.load();
        TreeModifyController treeModifyController = loaderServices.getController();
        controller.setTreeModifyController(treeModifyController);

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Modification d'attribut");
        //fenetre modale, obligation de quitter pour revenir a la fenetre principale
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene miniScene = new Scene(root3);
        dialogStage.setScene(miniScene);
        dialogStage.initStyle(StageStyle.UNIFIED);
        dialogStage.setMinHeight(380);
        dialogStage.setMinWidth(440);
        controller.setDialogStage(dialogStage); 
        controller.initSubControllers();


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
