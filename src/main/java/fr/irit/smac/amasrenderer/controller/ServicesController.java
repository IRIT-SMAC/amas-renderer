package fr.irit.smac.amasrenderer.controller;

import java.io.IOException;

import fr.irit.smac.amasrenderer.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The Class ServicesController.
 * This controller handles the service part of the main UI 
 */
public class ServicesController {

    /** The button in the main UI */
    @FXML
    private Button buttonAddService;
    
    /** The service list of the infrastructure */
    @FXML
    private ListView<Label> listServices;

    /** The popup form */
    private Stage form;
    

    /** Click on buttonAddService handler */
    @FXML
    public void addService() throws IOException {
        
        form = new Stage();
        form.setTitle("Ajouter un service");
        form.setResizable(false);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/ServiceDialog.fxml"));
        loader.setController(new ServiceDialogController(listServices));
        VBox root = (VBox) loader.load();

        form.initModality(Modality.WINDOW_MODAL);
        form.initOwner(buttonAddService.getScene().getWindow());
        form.setScene(new Scene(root));
        form.showAndWait();

    }

}
