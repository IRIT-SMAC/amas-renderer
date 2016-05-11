package fr.irit.smac.amasrenderer.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fr.irit.smac.amasrenderer.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * The Class ServicesController.
 */
public class ServicesController {

    @FXML
    private Button buttonAddService;

    @FXML
    private ListView<Label> listServices;

    private static BorderPane root3;
    
    private Stage stage;
    
    private HashMap<Label,TreeItem<String>> attributeMap = new HashMap<Label, TreeItem<String>>();

    @FXML
    public void handleMouseClick(MouseEvent e){
        Label selectedLabel = listServices.getSelectionModel().getSelectedItem();
        if(selectedLabel != null){
            Platform.runLater(new Runnable() {
                @Override public void run() {
                   
                    
                    FXMLLoader loaderServices = new FXMLLoader();
                    loaderServices.setLocation(Main.class.getResource("view/ServiceAttributes.fxml"));
                    root3 = null;
                    try {
                        root3 = loaderServices.load();
                    }
                    catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    
                    ServiceModifyController serviceModifyController = loaderServices.getController();
                    
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Modification d'attribut");
                    //fenetre modale, obligation de quitter pour revenir a la fenetre principale
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    dialogStage.initOwner(buttonAddService.getScene().getWindow());
                    Scene miniScene = new Scene(root3);
                    dialogStage.setScene(miniScene);
                    dialogStage.initStyle(StageStyle.UNDECORATED);
                    dialogStage.setMinHeight(380);
                    dialogStage.setMinWidth(440);
                    
                    serviceModifyController.setStage(dialogStage);
                    serviceModifyController.init(attributeMap, listServices);
                    
                    
                    dialogStage.showAndWait();
                    listServices.getSelectionModel().clearSelection();
                }
                
            });
        }   
    }
    
    @FXML
    public void addService() throws IOException {
        System.out.println("map = "+attributeMap);
        stage = new Stage();
        stage.setTitle("Ajouter un service");
        stage.setResizable(false);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/ServiceDialog.fxml"));
        DialogPane root = (DialogPane) loader.load();
        ServiceDialogController serviceDialogController = loader.getController();
        serviceDialogController.setList(listServices);
        serviceDialogController.setAttributeMap(attributeMap);
        
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
