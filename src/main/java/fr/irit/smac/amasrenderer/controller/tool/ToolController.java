package fr.irit.smac.amasrenderer.controller.tool;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.controller.infrastructure.InfrastructureAttributesController;
import fr.irit.smac.amasrenderer.model.InfrastructureModel;
import fr.irit.smac.amasrenderer.model.ToolModel;
import fr.irit.smac.amasrenderer.service.ConfigurationMapService;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
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
    private ListView<ToolModel> listTool;

    @FXML
    private TextField infrastructureTextField;

    @FXML
    private ListView<InfrastructureModel> infrastructureLabel;

    @FXML
    private Button infrastructureButton;

    @FXML
    private Label infrastructureWarningLabel;

    @FXML
    private ImageView infrastructureWarningIcon;

    @FXML
    private Button generateButton;

    private Stage stage;

    private static final Logger LOGGER = Logger.getLogger(ToolController.class.getName());

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
        loaderServices.setLocation(Main.class.getResource("view/InfrastructureAttributes.fxml"));
        BorderPane root;
        try {
            root = loaderServices.load();

            Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().add("secondaryWindow");

            InfrastructureAttributesController treeInfrastructureController = loaderServices.getController();

            Stage dialogStage = new Stage();

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

            treeInfrastructureController.setStage(dialogStage);
            treeInfrastructureController.init(infrastructureLabel, infrastructureLabel.getItems().get(0));
            miniScene.setFill(Color.BLACK);

            dialogStage.showAndWait();
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "The loading of the services attributes fxml failed", e);
        }
    }

    /**
     * Handle mouse click.
     */
    @FXML
    public void handleMouseClick() {
        ToolModel selectedLabel = listTool.getSelectionModel().getSelectedItem();
        if (selectedLabel != null && selectedLabel.getName() != "") {
            Platform.runLater(() -> loadFxml(selectedLabel.getName(), selectedLabel));
        }
    }

    /**
     * Load the services attributes fxml.
     */
    public void loadFxml(String selectedLabel, ToolModel tool) {

        FXMLLoader loaderServices = new FXMLLoader();
        loaderServices.setLocation(Main.class.getResource("view/ServiceAttributes.fxml"));
        BorderPane root;
        try {
            root = loaderServices.load();

            Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().add("secondaryWindow");

            ToolAttributesController serviceModifyController = loaderServices.getController();

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
            serviceModifyController.init(listTool, selectedLabel, tool);
            miniScene.setFill(Color.BLACK);

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
        ToolDialogController toolDialogController = loader.getController();
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

    @FXML
    public void buttonActionPerformed(){
        activateTextField();
    }
    
    @FXML
    public void textFieldActionPerformed(){
        validateEntry();
    }
    
    
    
    private void validateEntry(){
        String s = infrastructureTextField.getText();
        if (!s.trim().isEmpty()) {
            hideInfrastructureError();
//            infrastructureLabel.setText(infrastructureTextField.getText());
            infrastructureLabel.setVisible(true);
            infrastructureTextField.setVisible(false);
        }
        else {
            showInfrastructureError("Veuillez ne pas laisser ce champ vide.");
        }
    }

    private void showInfrastructureError(String message) {
        infrastructureWarningLabel.setText(message);
        infrastructureWarningLabel.setVisible(true);
        infrastructureWarningIcon.setVisible(true);
    }

    private void hideInfrastructureError() {
        infrastructureWarningLabel.setVisible(false);
        infrastructureWarningIcon.setVisible(false);
    }

    public void activateTextField() {
        infrastructureTextField.setVisible(true);
//        infrastructureTextField.setText(infrastructureLabel.getText());
        infrastructureLabel.setVisible(false);
        infrastructureTextField.requestFocus();
    }

    // ------------------------------------------------------------------------------------
    // end Infrastructure functions
    /*
     * (non-Javadoc)
     * 
     * @see javafx.fxml.Initializable#initialize(java.net.URL,
     * java.util.ResourceBundle)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ArrayList<ToolModel> list = new ArrayList<>();

        for (ToolModel tool : listTool.getItems()) {
            list.add(tool);
        }
        ToolService.getInstance().setTools(FXCollections.observableArrayList(list));

        listTool.setItems(ToolService.getInstance().getTools());
        ArrayList<InfrastructureModel> list2 = new ArrayList<>();
        
        InfrastructureService.getInstance().setInfrastructure(FXCollections.observableArrayList(list2));
        infrastructureLabel.setItems(InfrastructureService.getInstance().getInfrastructure());
    }

}
