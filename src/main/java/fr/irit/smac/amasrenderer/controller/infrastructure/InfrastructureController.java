package fr.irit.smac.amasrenderer.controller.infrastructure;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.model.InfrastructureModel;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class InfrastructureController implements Initializable {

    @FXML
    private ListView<InfrastructureModel> infrastructureLabel;
    
    @FXML
    private Button infrastructureButton;

    @FXML
    private Label infrastructureWarningLabel;

    @FXML
    private ImageView infrastructureWarningIcon;

    private static final Logger LOGGER = Logger.getLogger(InfrastructureController.class.getName());
    
    /**
     * Handle mouse click.
     */
    @FXML
    public void handleInfraClick() {
        
        Platform.runLater(() -> loadFxmlInfra());
    }

    /**
     * Load the services attributes fxml.
     */
    public void loadFxmlInfra() {

        FXMLLoader loaderServices = new FXMLLoader();
        loaderServices.setLocation(Main.class.getResource("view/infrastructure/InfrastructureAttributes.fxml"));
        BorderPane root;
        try {
            root = loaderServices.load();

            Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().add("secondaryWindow");

            InfrastructureAttributesController treeInfrastructureController = loaderServices.getController();

            Stage dialogStage = new Stage();

            dialogStage.initModality(Modality.WINDOW_MODAL);

            Window window = infrastructureButton.getScene().getWindow();
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

            treeInfrastructureController.setStage(dialogStage);
            treeInfrastructureController.init(infrastructureLabel.getItems().get(0));
            miniScene.setFill(Color.BLACK);

            dialogStage.showAndWait();
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "The loading of the services attributes fxml failed", e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        ArrayList<InfrastructureModel> list = new ArrayList<>();
        InfrastructureService.getInstance().setInfrastructure(FXCollections.observableArrayList(list));
        infrastructureLabel.setItems(InfrastructureService.getInstance().getInfrastructure());
    }
    
}
