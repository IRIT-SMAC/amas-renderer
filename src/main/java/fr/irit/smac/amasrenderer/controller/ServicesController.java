package fr.irit.smac.amasrenderer.controller;

import java.io.IOException;

import fr.irit.smac.amasrenderer.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * The Class ServicesController.
 * This controller handles the service part of the main UI 
 */
public class ServicesController {

    @FXML
    private Button buttonAddService;

    @FXML
    private ListView<Label> listServices;

    private Stage stage;
    
    @FXML
    public void addService() throws IOException {

        stage = new Stage();
        stage.setTitle("Ajouter un service");
        stage.setResizable(false);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/ServiceDialog.fxml"));
        DialogPane root = (DialogPane) loader.load();
        ServiceDialogController serviceDialogController = loader.getController();
        serviceDialogController.setList(listServices);
        
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

}
