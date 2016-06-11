package fr.irit.smac.amasrenderer.controller.menu;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.model.InfrastructureModel;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * This controller manages the menu bar events
 */
public class MenuBarController {

    private static final Logger LOGGER = Logger.getLogger(MenuBarController.class.getName());
    private Scene               scene;

    /**
     * On click on the menu item "Charger" in the menu "Fichier". Open a file
     * chooser to load a configuration file. Generate the graph from the
     * configuration file.
     */
    @FXML
    public void clickMenuCharger() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier de configuration");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(scene.getWindow());
        ObjectMapper mapper = new ObjectMapper();
        try {
            InfrastructureModel infrastructure = mapper.readValue(file, InfrastructureModel.class);
            InfrastructureService.getInstance().updateInfrastructureFromModel(infrastructure);
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Impossible de lire le fichier spécifié.", e);
        }
    }

    /**
     * On click on the menu item "Fermer" in the menu "Fichier" Close the
     * program
     */
    @FXML
    public void clickMenuFermer() {

        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void clickMenuSave() {

        File file = new FileChooser().showSaveDialog(scene.getWindow());

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            if (file != null)
                mapper.writeValue(file,
                    InfrastructureService.getInstance().getInfrastructure().getAttributesMap());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * On click on the menu item "A propos" in the menu "Aide" Open a help
     * window
     */
    @FXML
    public void clickMenuAPropos() {
        
        Window window = scene.getWindow();
        String resourcePath = "view/help/Documentation.fxml";
        FXMLLoader loaderWindowModal = new FXMLLoader();
        loaderWindowModal.setLocation(Main.class.getResource(resourcePath));

        Pane root = null;
        
        try {
            root = loaderWindowModal.load();
            Stage stageWindowModal = new Stage();
            stageWindowModal.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stageWindowModal.setScene(scene);
            stageWindowModal.setMinHeight(380);
            stageWindowModal.setMinWidth(440);
            double x = window.getX() + (window.getWidth() - root.getPrefWidth()) / 2;
            double y = window.getY() + (window.getHeight() - root.getPrefHeight()) / 2;
            stageWindowModal.setX(x);
            stageWindowModal.setY(y);
            scene.setFill(Color.BLACK);
            stageWindowModal.show();

        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "The loading of the fxml has failed", e);
        }
        
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
