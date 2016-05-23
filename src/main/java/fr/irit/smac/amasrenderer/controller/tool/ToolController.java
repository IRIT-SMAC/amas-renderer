package fr.irit.smac.amasrenderer.controller.tool;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * The Class ServicesController. This controller handles the service part of the
 * main UI
 */
public class ToolController implements Initializable {

    @FXML
    private Button buttonAddService;

    @FXML
    private ListView<String> listTool;

    @FXML
    private Label infrastructureLabel;

    private Stage stage;


    private static final Logger LOGGER = Logger.getLogger(ToolController.class.getName());

    /**
     * Handle mouse click.
     */
    @FXML
    public void handleMouseClick() {
        String selectedLabel = listTool.getSelectionModel().getSelectedItem();
        if (selectedLabel != null && selectedLabel != "") {
            Platform.runLater(() -> loadFxml());
        }
    }

    /**
     * Load the services attributes fxml.
     */
    public void loadFxml() {

        FXMLLoader loaderServices = new FXMLLoader();
        loaderServices.setLocation(Main.class.getResource("view/ServiceAttributes.fxml"));
        BorderPane root;
        try {
            root = loaderServices.load();

            Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().add("secondaryWindow");

            ToolModifyController serviceModifyController = loaderServices.getController();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modification d'attribut");

            dialogStage.initModality(Modality.WINDOW_MODAL);

            Window window = buttonAddService.getScene().getWindow();
            dialogStage.initOwner(window);
            Scene miniScene = new Scene(root);
            dialogStage.setScene(miniScene);
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.setMinHeight(380);
            dialogStage.setMinWidth(440);

            double x = window.getX() + (window.getWidth() - root.getPrefWidth()) / 2;
            double y = window.getY() + (window.getHeight() - root.getPrefHeight()) / 2;
            dialogStage.setX(x);
            dialogStage.setY(y);

            serviceModifyController.setStage(dialogStage);
            serviceModifyController.init(ToolService.getInstance().getAttributes(), listTool);

            dialogStage.showAndWait();
            listTool.getSelectionModel().clearSelection();
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "The loading of the services attributes fxml failed", e);
        }
    }

    /**
     * Adds the tool.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @FXML
    public void addTool() throws IOException {

        stage = new Stage();
        stage.setTitle("Ajouter un service");
        stage.setResizable(false);

        Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().add("secondaryWindow");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/ToolDialog.fxml"));
        DialogPane root = (DialogPane) loader.load();
        stage.initModality(Modality.WINDOW_MODAL);
        Window window = buttonAddService.getScene().getWindow();
        stage.initOwner(buttonAddService.getScene().getWindow());
        stage.initStyle(StageStyle.UNDECORATED);

        Scene myScene = new Scene(root);

        double x = window.getX() + (window.getWidth() - root.getPrefWidth()) / 2;
        double y = window.getY() + (window.getHeight() - root.getPrefHeight()) / 2;
        stage.setX(x);
        stage.setY(y);

        stage.setScene(myScene);

        stage.showAndWait();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javafx.fxml.Initializable#initialize(java.net.URL,
     * java.util.ResourceBundle)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ArrayList<String> list = new ArrayList<>();
        
        ToolService tools = ToolService.getInstance();
        
        tools.setTools(FXCollections.observableArrayList(list));

        tools.getTools().addListener((ListChangeListener.Change<? extends String> e) -> {

            if (ToolService.getInstance().getTools().size() > 0) {
                String newTool = ToolService.getInstance().getTools()
                    .get(ToolService.getInstance().getTools().size() - 1);
                ToolService.getInstance().getAttributes().put(newTool, new TreeItem<String>(newTool));
                listTool.getItems().add(newTool);
            } else {
            	listTool.getItems().clear();
            }
        });
        
        tools.getTools().add("agentHandlerService");
        TreeItem<String> root = new TreeItem<String>("agentHandlerService");
        root.getChildren().add(new TreeItem<String>("className : fr.irit.smac.amasfactory.service.agenthandler.impl.BasicAgentHandler"));
        tools.getAttributes().put("agentHandlerService", root);
        
        

        InfrastructureService.getInstance().setInfrastructure(FXCollections.observableArrayList(new ArrayList<>()));

        InfrastructureService.getInstance().getInfrastructure()
            .addListener((ListChangeListener.Change<? extends String> c) -> {
                String nouvelleInfrastructure = InfrastructureService.getInstance().getInfrastructure().get(0);
                infrastructureLabel.setText(nouvelleInfrastructure);
            }

        );
    }
}
